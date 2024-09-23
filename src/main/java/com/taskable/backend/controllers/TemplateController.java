package com.taskable.backend.controllers;

import com.taskable.backend.services.AuthorizationService;
import com.taskable.backend.services.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/classes/{class_id}/templates")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private AuthorizationService authorizationService;


}
