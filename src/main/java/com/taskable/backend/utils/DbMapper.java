package com.taskable.backend.utils;

import com.taskable.jooq.tables.records.ClassroomRecord;
import com.taskable.jooq.tables.records.ProjectRecord;
import com.taskable.jooq.tables.records.TemplateRecord;
import com.taskable.jooq.tables.records.UserRecord;
import com.taskable.protobufs.PersistenceProto.*;

public class DbMapper {
    public static User map(UserRecord rec) {
        return User.newBuilder()
                .setId(rec.getId())
                .setBasicInfo(BasicInfo.newBuilder()
                        .setEmail(rec.getEmail())
                        .setFirstName(rec.getFirstName())
                        .setLastName(rec.getLastName())
                        .build())
                .setUserSettings(UserSettings.newBuilder()
                        .setStatus(rec.getStatus())
                        .setLanguage(rec.getLanguage())
                        .setColor(rec.getColour())
                        .setTheme(rec.getTheme())
                )
                .build();
    }

    public static Classroom map(ClassroomRecord rec) {
        return Classroom.newBuilder()
                .setId(rec.getId())
                .setName(rec.getName())
                .setDescription(rec.getDescription())
                .setCreatedAt(rec.getCreatedAt().toString())
                .setArchived(rec.get(com.taskable.jooq.tables.Classroom.CLASSROOM.ARCHIVED, Boolean.class))
                .build();
    }

    public static Project map(ProjectRecord rec) {
        var projectBuilder =  Project.newBuilder()
                .setId(rec.getId())
                .setTitle(rec.getName());
        if (rec.getTemplateId() != null) {
            projectBuilder.setTemplateId(rec.getTemplateId());
        }
        return projectBuilder.build();
    }

    public static Template map(TemplateRecord rec) {
        return Template.newBuilder()
                .setId(rec.getId())
                .setName(rec.getName())
                .setDescription(rec.getDescription())
                .setArchived(rec.getArchived() == 1)
                .build();
    }
}
