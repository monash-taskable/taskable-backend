package com.taskable.backend.services;

import com.taskable.backend.repositories.UserRepository;
import com.taskable.protobufs.PersistenceProto.User;
import com.taskable.protobufs.UserProto.*;
import com.taskable.protobufs.UserProto.SearchUserResponse;
import com.taskable.protobufs.UserProto.GetProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public GetProfileResponse getProfile(Integer userId) {
        var user = userRepository.getUserById(userId);
        return GetProfileResponse.newBuilder()
                .setUser(user)
                .build();
    }
    // TODO: ACCEPT/DENY INVITATION BUTTON

    public void updateProfile(Integer userId, UpdateProfileRequest req) {
        userRepository.updateProfileById(
            userId,
            req.hasStatus() ? req.getStatus() : null,
            req.hasColor() ? req.getColor() : null,
            req.hasLanguage() ? req.getLanguage() : null,
            req.hasTheme() ? req.getTheme() : null);
    }
}
