package com.taskable.backend.services;

import com.taskable.backend.repositories.ClassRepository;
import com.taskable.backend.repositories.ProjectRepository;
import com.taskable.backend.repositories.UserRepository;
import com.taskable.protobufs.ClassroomProto.GetMembersResponse;
import com.taskable.protobufs.PersistenceProto;
import com.taskable.protobufs.ProjectProto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private UserRepository userRepository;

    public GetProjectResponse getProject(Integer projectId) {
        return GetProjectResponse.newBuilder()
                .setProject(projectRepository.getProjectById(projectId))
                .build();
    }

    public GetProjectsResponse getProjects(Integer userId, Integer classId) {
        List<PersistenceProto.Project> projects;
        if (Set.of("OWNER", "ADMIN", "TUTOR").contains(classRepository.getUserRoleInClass(userId, classId))) {
            projects = projectRepository.getProjectsInClass(classId);
        }
        else {
            projects = projectRepository.getProjectsByUserId(userId);
        }
        return GetProjectsResponse.newBuilder()
                .addAllProjects(projects)
                .build();
    }

    public AddProjectMembersResponse addProjectMembers(Integer projectId, Integer classId, AddProjectMembersRequest req) {
        var emails = req.getEmailsList();
        Map<String, Integer> emailToId = classRepository.getUserIdsInClassByEmails(emails, classId);
        List<String> invalidEmails = new ArrayList<>();
        List<Integer> userIdsToAdd = new ArrayList<>();
        for (var email : emails) {
            var userId = emailToId.getOrDefault(email, null);
            if (userId != null) {
                userIdsToAdd.add(userId);
            }
            else {
                invalidEmails.add(email);
            }
        }
        projectRepository.addUsersToProject(userIdsToAdd, projectId);
        return AddProjectMembersResponse.newBuilder().addAllInvalidEmails(invalidEmails).build();
    }

    public GetMembersResponse getProjectMembers(Integer projectId, Integer classId) {
        return GetMembersResponse.newBuilder()
                .addAllClassMembers(projectRepository.getMembersFromProject(projectId, classId))
                .build();
    }

    public void deleteProjectMember(Integer userId, Integer projectId) {
        projectRepository.deleteProjectUser(userId, projectId);
    }

    public void updateProject(UpdateProjectRequest req, Integer projectId) {
        projectRepository.updateProjectDetails(
            projectId,
            req.hasTitle() ? req.getTitle() : null,
            req.hasDescription() ? req.getDescription() : null,
            req.hasArchived() ? req.getArchived(): null);
    }

    public void deleteProject(Integer projectId) {
        projectRepository.deleteProject(projectId);
    }

    public void detachProject(Integer projectId) {
        projectRepository.deleteTemplateId(projectId);
    }
}
