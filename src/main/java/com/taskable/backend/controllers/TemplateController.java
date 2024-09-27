package com.taskable.backend.controllers;

import com.taskable.backend.auth.CustomUserDetails;
import com.taskable.backend.services.AuthorizationService;
import com.taskable.backend.services.TemplateService;
import com.taskable.protobufs.ProjectProto.BatchCreateRequest;
import com.taskable.protobufs.TemplateProto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{template_id}")
    @PreAuthorize("@authorizationService.canReadTemplate(#userDetails.userId(), #templateId, #classId)")
    public GetTemplateResponse getTemplate(@AuthenticationPrincipal CustomUserDetails userDetails,
                                           @PathVariable("class_id") Integer classId,
                                           @PathVariable("template_id") Integer templateId) {
        return templateService.getTemplate(templateId);
    }

    @PostMapping("/create")
    @PreAuthorize("@authorizationService.checkOwnerOrAdminInClass(#userDetails.userId(), #classId)")
    public CreateTemplateResponse createTemplate(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @PathVariable("class_id") Integer classId,
                                                 @RequestBody CreateTemplateRequest req) {
        return templateService.createTemplate(req, classId);
    }

    @PostMapping("/{template_id}/update")
    @PreAuthorize("@authorizationService.canModifyTemplate(#userDetails.userId(), #templateId, #classId)")
    public void updateTemplateDetails(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable("template_id") Integer templateId,
                                      @PathVariable("class_id") Integer classId,
                               @RequestBody UpdateTemplateRequest req) {
        templateService.updateTemplate(req, templateId);
    }

    @PostMapping("/{template_id}/create-single")
    @PreAuthorize("@authorizationService.canModifyTemplate(#userDetails.userId(), #templateId, #classId)")
    public CreateProjectResponse createProject(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @PathVariable("template_id") Integer templateId,
                                               @PathVariable("class_id") Integer classId,
                                              @RequestBody CreateProjectRequest req) {
        return templateService.createProject(templateId, userDetails.userId(), classId, req);
    }

    @DeleteMapping("/{template_id}/delete")
    @PreAuthorize("@authorizationService.canModifyTemplate(#userDetails.userId(), #templateId, #classId)")
    public void deleteTemplate(@AuthenticationPrincipal CustomUserDetails userDetails,
                               @PathVariable("class_id") Integer classId,
                               @PathVariable("template_id") Integer templateId) {
        templateService.deleteTemplate(templateId);
    }

    @PostMapping("/{template_id}/create-multiple")
    @PreAuthorize("@authorizationService.canModifyTemplate(#userDetails.userId(), #templateId, #classId)")
    public BatchCreateResponse batchCreateProjects(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable("class_id") Integer classId,
                                    @PathVariable("template_id") Integer templateId,
                                    @RequestBody BatchCreateRequest req) {
        return templateService.batchCreateProjects(templateId, classId, req);
    }
}
