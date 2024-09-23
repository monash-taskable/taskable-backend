package com.taskable.backend.services;

import com.taskable.backend.repositories.ProjectRepository;
import com.taskable.backend.repositories.TemplateRepository;
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

    public CreateTemplateResponse createTemplate(CreateTemplateRequest req) {
        return CreateTemplateResponse.newBuilder()
                .setId(templateRepository.createTemplate(req.getName()))
                .build();
    }

    public void updateTemplate(UpdateTemplateRequest req, Integer templateId) {
        templateRepository.updateTemplateDetails(templateId, req.getName(), req.getDescription(), req.getArchived());
    }
}
