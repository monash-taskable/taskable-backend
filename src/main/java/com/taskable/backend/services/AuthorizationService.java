package com.taskable.backend.services;

import com.taskable.backend.repositories.*;
import com.taskable.backend.utils.enums.Role;
import com.taskable.protobufs.PersistenceProto;
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

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private FileRepository fileRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    // update this method name to fulfil more roles if necessary
    public boolean checkStaffInClass(Integer userId, Integer classId) {
        return Set.of("OWNER", "ADMIN", "TUTOR").contains(classRepository.getUserRoleInClass(userId, classId));
    }

    public boolean userExistsInClass(Integer userId, Integer classId) {
        return classRepository.checkUserInClass(userId, classId);
    }

    public boolean userExistsInProject(Integer userId, Integer projectId) {
        return projectRepository.checkUserInProject(userId, projectId);
    }

    public boolean checkOwnerOrAdminInClass(Integer userId, Integer classId) {
        return Set.of("OWNER", "ADMIN").contains(classRepository.getUserRoleInClass(userId, classId));
    }

    public boolean checkOwnerInClass(Integer userId, Integer classId) {
        return "OWNER".equals(classRepository.getUserRoleInClass(userId, classId));
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

    public boolean canModifyTask(Integer userId, Integer projectId, Integer taskId, Integer classId) {
        return taskRepository.checkTaskBelongsToProjectAndClass(taskId, projectId, classId)
            && (userExistsInProject(userId, projectId) || checkOwnerOrAdminInClass(userId, classId));
    }

    public boolean canReadTask(Integer userId, Integer projectId, Integer taskId, Integer classId) {
        return taskRepository.checkTaskBelongsToProjectAndClass(taskId, projectId, classId)
            && (userExistsInProject(userId, projectId) || checkStaffInClass(userId, classId));
    }

    public boolean canModifySubtask(Integer userId, Integer subtaskId, Integer projectId, Integer classId) {
        return taskRepository.checkSubtaskBelongsToProjectAndClass(subtaskId, projectId, classId)
            && (userExistsInProject(userId, projectId) || checkOwnerOrAdminInClass(userId, classId));
    }

    public boolean canReadSubtask(Integer userId, Integer subtaskId, Integer projectId, Integer classId) {
        return taskRepository.checkSubtaskBelongsToProjectAndClass(subtaskId, projectId, classId)
            && (userExistsInProject(userId, projectId) || checkStaffInClass(userId, classId));
    }

    public boolean canEditSubtaskComment(Integer userId, Integer commentId, Integer projectId, Integer classId) {
        return taskRepository.checkStCommentBelongsToProjectAndClass(commentId, projectId, classId)
            && (taskRepository.checkSubtaskCommentBelongsToUser(commentId, userId) || checkOwnerOrAdminInClass(userId, classId));
    }

    public boolean canReadSubtaskComment(Integer userId, Integer commentId, Integer projectId, Integer classId) {
        return taskRepository.checkStCommentBelongsToProjectAndClass(commentId, projectId, classId)
            && (userExistsInProject(userId, projectId) || checkStaffInClass(userId, classId));
    }

    public boolean canModifyProject(Integer userId, Integer projectId, Integer classId) {
        return taskRepository.checkProjectBelongsToClass(projectId, classId)
            && checkOwnerOrAdminInClass(userId, classId);
    }

    public boolean canReadProject(Integer userId, Integer projectId, Integer classId) {
        return taskRepository.checkProjectBelongsToClass(projectId, classId)
            && (userExistsInProject(userId, projectId) || checkStaffInClass(userId, classId));
    }

    public boolean canReadAnnouncement(Integer userId, Integer announcementId, Integer classId) {
        return classRepository.checkAnnouncementInClass(announcementId, classId)
            && userExistsInClass(userId, classId);
    }

    public boolean canModifyAnnouncement(Integer userId, Integer announcementId, Integer classId) {
        return classRepository.checkAnnouncementInClass(announcementId, classId)
            && checkStaffInClass(userId, classId);
    }

    public boolean canModifyTemplate(Integer userId, Integer templateId, Integer classId) {
        return templateRepository.checkTemplateInClass(templateId, classId)
            && (checkOwnerOrAdminInClass(userId, classId));
    }

    public boolean canReadTemplate(Integer userId, Integer templateId, Integer classId) {
        return templateRepository.checkTemplateInClass(templateId, classId)
            && (checkStaffInClass(userId, classId));
    }

    public boolean canReadSubtaskFile(Integer userId, Integer fileId, Integer projectId, Integer classId) {
        return fileRepository.checkSubtaskAttachment(fileId, projectId, classId)
            && (userExistsInProject(userId, projectId) || checkStaffInClass(userId, classId));
    }

    public boolean canModifySubtaskFile(Integer userId, Integer fileId, Integer projectId, Integer classId) {
        return fileRepository.checkSubtaskAttachment(fileId, projectId, classId)
            && (userExistsInProject(userId, projectId) || checkOwnerOrAdminInClass(userId, classId));
    }

    public boolean canReadProjectFile(Integer userId, Integer fileId, Integer projectId, Integer classId) {
        return fileRepository.checkProjectAttachment(fileId, projectId, classId)
            && (userExistsInProject(userId, projectId) || checkStaffInClass(userId, classId));
    }

    public boolean canModifyProjectFile(Integer userId, Integer fileId, Integer projectId, Integer classId) {
        return fileRepository.checkProjectAttachment(fileId, projectId, classId)
            && (userExistsInProject(userId, projectId) || checkOwnerOrAdminInClass(userId, classId));
    }

    public boolean canReadTemplateFile(Integer userId, Integer fileId, Integer templateId, Integer classId) {
        return fileRepository.checkTemplateAttachment(fileId, templateId, classId)
            && checkStaffInClass(userId, classId);
    }

    public boolean canModifyTemplateFile(Integer userId, Integer fileId, Integer templateId, Integer classId) {
        return fileRepository.checkTemplateAttachment(fileId, templateId, classId)
            && checkOwnerOrAdminInClass(userId, classId);
    }
}
