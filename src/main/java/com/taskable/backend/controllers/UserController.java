package com.taskable.backend.controllers;

import com.taskable.backend.services.UserService;
import com.taskable.protobufs.UserProto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/get-user", produces = "application/x-protobuf" )
    public byte[] getUser() {
        return userService.getUser().toByteArray();
    }
}
