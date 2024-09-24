package com.taskable.backend.controllers;

import com.taskable.backend.auth.CustomUserDetails;
import com.taskable.backend.services.AuthorizationService;
import com.taskable.backend.services.ProjectService;
import com.taskable.protobufs.ClassroomProto.GetMembersResponse;
import com.taskable.protobufs.ProjectProto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes/{class_id}/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private AuthorizationService authorizationService;

    private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

    @GetMapping("/{project_id}")
    @PreAuthorize("@authorizationService.userExistsInProject(#userDetails.userId(), #projectId) || " +
                  "@authorizationService.checkStaffInClass(#userDetails.userId(), #classId)")
    public GetProjectResponse getProject(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @PathVariable("class_id") Integer classId,
                                                      @PathVariable("project_id") Integer projectId) {
        return projectService.getProject(projectId);
    }

    @GetMapping("")
    public GetProjectsResponse getProjects(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @PathVariable("class_id") Integer classId) {
        return projectService.getProjects(userDetails.userId(), classId);
    }

    @PostMapping("/{project_id}/members/add")
    @PreAuthorize("@authorizationService.checkOwnerOrAdminInClass(#userDetails.userId(), #classId)")
    public AddProjectMembersResponse addProjectMembers(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @PathVariable("class_id") Integer classId,
                                                       @PathVariable("project_id") Integer projectId,
                                                       @RequestBody AddProjectMembersRequest req) {
        return projectService.addProjectMembers(projectId, classId, req);
    }

    @GetMapping("/{project_id}/members")
    @PreAuthorize("@authorizationService.userExistsInProject(#userDetails.userId(), #projectId) || " +
                  "@authorizationService.checkStaffInClass(#userDetails.userId(), #classId)")
    public GetMembersResponse getProjectMembers(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                @PathVariable("class_id") Integer classId,
                                                @PathVariable("project_id") Integer projectId) {
        return projectService.getProjectMembers(projectId);
    }

    @DeleteMapping("/{project_id}/members/{user_id}/delete")
    @PreAuthorize("@authorizationService.checkOwnerOrAdminInClass(#userDetails.userId(), #classId)")
    public void deleteProjectMember(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable("user_id") Integer userId,
                                    @PathVariable("class_id") Integer classId,
                                        @PathVariable("project_id") Integer projectId) {
        projectService.deleteProjectMember(userId, projectId);
    }
}
