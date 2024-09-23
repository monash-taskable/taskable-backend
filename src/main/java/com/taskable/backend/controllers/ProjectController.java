package com.taskable.backend.controllers;

import com.taskable.backend.auth.CustomUserDetails;
import com.taskable.backend.services.AuthorizationService;
import com.taskable.backend.services.ProjectService;
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
    @PreAuthorize("@authorizationService.checkStaffInClass(#userDetails.userId(), #classId) || " +
                  "@authorizationService.userExistsInProject(#userDetails.userId(), #projectId)")
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
        return projectService.addProjectMembers(projectId, req);
    }
}
