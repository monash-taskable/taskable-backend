package com.taskable.backend.services;

import com.taskable.backend.repositories.ClassRepository;
import com.taskable.backend.repositories.ProjectRepository;
import com.taskable.protobufs.ClassroomProto.GetMembersResponse;
import com.taskable.protobufs.PersistenceProto;
import com.taskable.protobufs.ProjectProto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ClassRepository classRepository;

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

    public AddProjectMembersResponse addProjectMembers(Integer projectId, AddProjectMembersRequest req) {
        return AddProjectMembersResponse.newBuilder().build();
    }

//    public GetMembersResponse getProjectMembers()

}
