package com.taskable.backend.services;

import com.taskable.backend.repositories.ProjectRepository;
import com.taskable.backend.repositories.TemplateRepository;
import com.taskable.protobufs.ProjectProto.BatchCreateRequest;
import com.taskable.protobufs.TemplateProto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public GetTemplatesResponse getTemplates(Integer classId) {
        return GetTemplatesResponse.newBuilder()
                .addAllTemplates(templateRepository.getTemplatesInClass(classId))
                .build();
    }

    public GetTemplateResponse getTemplate(Integer templateId) {
        return GetTemplateResponse.newBuilder()
                .setTemplate(templateRepository.getTemplateById(templateId))
                .build();
    }

    public CreateTemplateResponse createTemplate(CreateTemplateRequest req, Integer classId) {
        return CreateTemplateResponse.newBuilder()
                .setId(templateRepository.createTemplate(req.getName(), classId, "", false))
                .build();
    }

    public void updateTemplate(UpdateTemplateRequest req, Integer templateId) {
        templateRepository.updateTemplateDetails(
            templateId,
            req.hasName() ? req.getName() : null,
            req.hasDescription() ? req.getDescription() : null,
            req.hasArchived() ? req.getArchived() : null);
    }

    public CreateProjectResponse createProject(Integer templateId, Integer userId, Integer classId, CreateProjectRequest req) {
        var projectId = projectRepository.createProject(templateId, req.getName(), classId, req.getCreatedAt(), "");
        projectRepository.addUserToProject(userId, projectId);
        return CreateProjectResponse.newBuilder()
                .setId(projectId)
                .build();
    }

    public void deleteTemplate(Integer templateId) {
        templateRepository.deleteTemplateById(templateId);
    }

    public BatchCreateResponse batchCreateProjects(Integer templateId, Integer classId, BatchCreateRequest req) {
        return BatchCreateResponse.newBuilder()
            .addAllInvalidEmails(projectRepository.batchCreate(templateId, classId, req))
            .build();
    }
}
