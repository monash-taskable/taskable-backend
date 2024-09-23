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
}
