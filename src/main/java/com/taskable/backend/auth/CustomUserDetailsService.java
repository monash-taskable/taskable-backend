package com.taskable.backend.auth;

import com.taskable.backend.repositories.UserRepository;
import com.taskable.protobufs.PersistenceProto.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String sub) throws UsernameNotFoundException {
        Integer userId = userRepository.getUserIdBySub(sub);
        return userId != null ? new CustomUserDetails(userId, sub) : createNewUser(sub);
    }

    private UserDetails createNewUser(String sub) {
        User newUser = User.newBuilder().build();

        userRepository.storeUser(newUser, sub);

        return new CustomUserDetails(newUser.getId(), sub);
    }
}
