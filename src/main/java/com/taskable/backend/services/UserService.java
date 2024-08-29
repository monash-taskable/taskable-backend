package com.taskable.backend.services;

import com.taskable.backend.repositories.UserRepository;
import com.taskable.protobufs.PersistenceProto.User;
import com.taskable.protobufs.UserProto.GetProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUser() {
        return User.newBuilder()
            .setId(1)
//            .setUsername("admin alice")
            .setEmail("alice@taskable.com")
            .build();
    }

    public GetProfileResponse getProfile(Integer userId) {
        var user = userRepository.getUserById(userId);
        return GetProfileResponse.newBuilder()
                .setId(user.getId())
                .setLastName(user.getLastName())
                .setFirstName(user.getFirstName())
                .setEmail(user.getEmail())
                .setColor(user.getColor())
                .setTheme(user.getTheme())
                .setLanguage(user.getLanguage())
                .build();
    }
}
