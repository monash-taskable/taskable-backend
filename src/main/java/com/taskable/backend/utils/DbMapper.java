package com.taskable.backend.utils;

import com.taskable.jooq.tables.records.ClassroomRecord;
import com.taskable.jooq.tables.records.ProjectRecord;
import com.taskable.jooq.tables.records.UserRecord;
import com.taskable.protobufs.PersistenceProto.BasicInfo;
import com.taskable.protobufs.PersistenceProto.UserSettings;
import com.taskable.protobufs.PersistenceProto.Classroom;
import com.taskable.protobufs.PersistenceProto.User;
import com.taskable.protobufs.PersistenceProto.Project;

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
        return Project.newBuilder()
                .setId(rec.getId())
                .setTitle(rec.getName())
                .setTemplateId(rec.getTemplateId())
                .build();
    }
}
