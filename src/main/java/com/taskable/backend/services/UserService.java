package com.taskable.backend.services;

import com.taskable.protobufs.UserProto.User;
import com.taskable.protobufs.UserProto;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getUser() {
        return User.newBuilder()
            .setId(1)
            .setUsername("admin alice")
            .setEmail("alice@taskable.com")
            .build();
    }
}
