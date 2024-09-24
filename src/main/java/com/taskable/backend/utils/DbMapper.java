package com.taskable.backend.utils;

import com.taskable.jooq.tables.records.*;
import com.taskable.protobufs.AnnouncementProto.Announcement;
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
            .setTitle(rec.getName())
            .setArchived(rec.getArchived() == 1);
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

    public static Announcement map(AnnouncementRecord rec) {
        return Announcement.newBuilder()
            .setId(rec.getId())
            .setClassId(rec.getClassroomId())
            .setAuthorId(rec.getUserId())
            .setTitle(rec.getTitle())
            .setContent(rec.getMessage())
            .setSentAt(rec.getSentAt().toString())
            .build();
    }

    public static Task map(TaskRecord rec) {
        return Task.newBuilder()
            .setId(rec.getId())
            .setProjectId(rec.getProjectId())
            .setTitle(rec.getTitle())
            .setDescription(rec.getDescription())
            .setColor(rec.getColor())
            .build();
    }

    public static Subtask map(SubtaskRecord rec) {
        return Subtask.newBuilder()
            .setId(rec.getId())
            .setTaskId(rec.getTaskId())
            .setTitle(rec.getTitle())
            .setDescription(rec.getDescription())
            .setStatus(rec.getStatus())
            .setPriority(rec.getPriority())
            .setStart(rec.getStartDate().toString())
            .setEnd(rec.getDueDate().toString())
            .build();
    }
}
