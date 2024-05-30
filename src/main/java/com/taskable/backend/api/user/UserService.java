package com.taskable.backend.api.user;

import com.taskable.protobuf.UserProto;
import com.taskable.protobuf.UserProto.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getUser() {
        return User.newBuilder()
            .setId(1)
            .setUsername("admin alice")
            .setEmail("alice@taskable.com")
            .setRole(UserProto.Role.ADMIN)
            .build();
    }
}
