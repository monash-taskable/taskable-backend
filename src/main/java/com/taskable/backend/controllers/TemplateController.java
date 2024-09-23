package com.taskable.backend.controllers;

import com.taskable.backend.auth.CustomUserDetails;
import com.taskable.backend.services.AuthorizationService;
import com.taskable.backend.services.TemplateService;
import com.taskable.protobufs.TemplateProto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/classes/{class_id}/templates")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping("")
    @PreAuthorize("@authorizationService.checkStaffInClass(#userDetails.userId(), #classId)")
    public GetTemplatesResponse getTemplates(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @PathVariable("class_id") Integer classId) {
        return templateService.getTemplates(classId);
    }

}
