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
        // Generate a new user ID and create the user
        User newUser = User.newBuilder().setSub(sub).build();

        // Save the new user in the repository
        userRepository.storeUser(newUser);

        // Return the UserDetails for the new user
        return new CustomUserDetails(newUser.getId(), newUser.getSub());
    }
}
