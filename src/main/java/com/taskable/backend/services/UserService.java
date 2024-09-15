package com.taskable.backend.services;

import com.taskable.backend.repositories.UserRepository;
import com.taskable.protobufs.PersistenceProto.User;
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

}
