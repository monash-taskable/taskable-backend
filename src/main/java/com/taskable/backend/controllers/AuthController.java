package com.taskable.backend.controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.taskable.backend.services.GoogleTokenService;
import com.taskable.protobufs.AuthProto.GetCsrfResponse;
import com.taskable.protobufs.AuthProto.LoginExchangeRequest;
import com.taskable.protobufs.AuthProto.LoginExchangeResponse;
import jakarta.servlet.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final GoogleTokenService googleTokenService;

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(GoogleTokenService googleTokenService) {
        this.googleTokenService = googleTokenService;
    }

    @PostMapping("/login-exchange")
    public LoginExchangeResponse loginExchange(@RequestBody LoginExchangeRequest req, HttpSession httpSession, HttpServletRequest request, HttpServletResponse response) throws Exception {
        httpSession.invalidate();
        HttpSession newSession = request.getSession(true);

        String authCode = req.getAuthorizationCode();

        GoogleIdToken idToken = googleTokenService.exchangeAuthorizationCodeForIdToken(authCode);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                idToken.getPayload().getSubject(),
                null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        securityContextRepository.saveContext(securityContext, request, response);


        logger.info("SecurityContext after setting authentication: {}", SecurityContextHolder.getContext().getAuthentication());
        logger.info("sent session ID:{}", newSession.getId());

        CsrfToken csrfToken = csrfTokenRepository.generateToken(request);
        csrfTokenRepository.saveToken(csrfToken, request, response);

        return LoginExchangeResponse.newBuilder().setCsrfToken(csrfToken.getToken()).build();
    }

    @GetMapping("/get-csrf")
    public GetCsrfResponse getCsrf(HttpServletRequest req) {
        String token =  csrfTokenRepository.loadToken(req).getToken();
        return GetCsrfResponse.newBuilder().setCsrfToken(token).build();
    }

    @GetMapping("/get-temp-csrf")
    public GetCsrfResponse getTempCsrf(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        CookieCsrfTokenRepository csrfTokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();

        CsrfToken csrfToken = csrfTokenRepository.generateToken(request);
        csrfTokenRepository.saveToken(csrfToken, request, response);
        logger.info("session from temp: " + session.getId());
        logger.info("generated CSRF Token from temp: " + csrfToken.getToken());
        return GetCsrfResponse.newBuilder().setCsrfToken(csrfToken.getToken()).build();
    }

    @PostMapping("/test")
    public boolean test(HttpSession session, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        String sessionId = session.getId();
        String csrfTokenValue = null;

        // Iterate through the cookies to find the session and CSRF cookies
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JSESSIONID".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                } else if ("XSRF-TOKEN".equals(cookie.getName())) { // XSRF-TOKEN is the default name for the CSRF cookie
                    csrfTokenValue = cookie.getValue();
                }
            }
        }


        String requestHeaderToken = request.getHeader("X-XSRF-TOKEN");
        String cookieToken = csrfTokenRepository.loadToken(request).getToken();

        // Log the session ID and CSRF token
        logger.info("Session ID from test (cookie): " + sessionId);
        logger.info("CSRF Token (cookie): " + csrfTokenValue);
        logger.info("CSRF Token (header): " + request.getHeader("X-XSRF-TOKEN"));
        logger.info("CSRF Token (from repo):" + csrfTokenRepository.loadToken(request).getToken());
        logger.info("CSRF token in header matches cookie: " + requestHeaderToken.equals(cookieToken));

        return true;
    }


    @GetMapping("/verify")
    public boolean verifySession() {
        return true;
    }
}