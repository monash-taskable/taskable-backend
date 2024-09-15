package com.taskable.backend.controllers;

import com.taskable.backend.auth.CustomUserDetails;
import com.taskable.backend.services.UserService;
import com.taskable.protobufs.PersistenceProto.User;
import com.taskable.protobufs.UserProto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/get-profile")
    public GetProfileResponse getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return userService.getProfile(userDetails.userId());
    }

    @PostMapping("/{user_id}/update")
    public void updateProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("user_id") Integer userId,
            @RequestBody UpdateProfileRequest req) {
        userService.updateProfile(userId, req);
    }
}
