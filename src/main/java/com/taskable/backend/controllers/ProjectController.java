package com.taskable.backend.controllers;

import com.taskable.backend.services.AuthorizationService;
import com.taskable.backend.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private AuthorizationService authorizationService;



}
