package com.taskable.backend.services;

import com.taskable.backend.controllers.ClassController;
import com.taskable.backend.repositories.ClassRepository;
import com.taskable.backend.repositories.UserRepository;
import com.taskable.backend.utils.enums.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthorizationService {
    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    // update this method name to fulfil more roles if necessary
    public boolean canUserAddToClass(Integer userId, Integer classId) {
        return Set.of("OWNER", "ADMIN", "TEACHER").contains(classRepository.getUserRoleInClass(userId, classId));
    }

    public boolean userExistsInClass(Integer userId, Integer classId) {
        return classRepository.checkUserInClass(userId, classId);
    }

    public boolean checkOwnerOrAdminInClass(Integer userId, Integer classId) {
        return Set.of("OWNER", "ADMIN").contains(classRepository.getUserRoleInClass(userId, classId));
    }

    public boolean canUserUpdateRole(Integer userId, Integer assigneeId, String newRole, Integer classId) {
        if (userId.equals(assigneeId)) {
            return false;
        }
        var userRolePos = Role.valueOf(classRepository.getUserRoleInClass(userId, classId)).ordinal();
        var newRolePos = Role.valueOf(newRole).ordinal();
        if (userRolePos <= newRolePos) {
            return false;
        }
        var assigneeRolePos = Role.valueOf(classRepository.getUserRoleInClass(assigneeId, classId)).ordinal();
        return userRolePos > assigneeRolePos;
    }
}
