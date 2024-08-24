package com.taskable.backend.controllers;

import com.taskable.backend.services.UserService;
import com.taskable.protobufs.UserProto.User;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping(value = "/get-user", produces = "application/x-protobuf" )
//    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public User getUser(HttpSession httpSession) {
        logger.info("user session id:{}", httpSession.getId());
//        response.addHeader("Access-Control-Allow-Origin", "*");
        return userService.getUser();
    }
}
