package com.taskable.backend.repositories;

import com.taskable.backend.utils.DbMapper;
import com.taskable.backend.utils.DbUtils;
import com.taskable.protobufs.PersistenceProto;
import com.taskable.protobufs.PersistenceProto.ClassroomMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.jooq.DSLContext;

import com.taskable.protobufs.PersistenceProto.Project;

import java.util.HashMap;
import java.util.List;

import static com.taskable.jooq.tables.ClassroomUser.CLASSROOM_USER;
import static com.taskable.jooq.tables.Project.PROJECT;
import static com.taskable.jooq.tables.ProjectUser.PROJECT_USER;
import static com.taskable.jooq.tables.Classroom.CLASSROOM;
import static com.taskable.jooq.tables.Template.TEMPLATE;
import static com.taskable.jooq.tables.User.USER;

@Repository
public class ProjectRepository {
    private final DSLContext dsl;

    @Autowired
    public ProjectRepository(DSLContext dsl) {this.dsl = dsl;}

    public Integer createProject(Integer templateId, String name, Integer classId, String createdAt, String description) {
        return dsl.insertInto(PROJECT)
                .set(PROJECT.TEMPLATE_ID, templateId)
                .set(PROJECT.NAME, name)
            .set(PROJECT.CLASSROOM_ID, classId)
            .set(PROJECT.CREATED_AT, DbUtils.getDateTime(createdAt))
                .returning(PROJECT.ID)
                .fetchOne(PROJECT.ID);
    }

    public void addUserToProject(Integer userId, Integer projectId) {
        dsl.insertInto(PROJECT_USER)
            .set(PROJECT_USER.PROJECT_ID, projectId)
            .set(PROJECT_USER.USER_ID, userId)
            .onDuplicateKeyIgnore()
            .execute();
    }

    public boolean checkUserInProject(Integer userId, Integer projectId) {
        return dsl.fetchExists(
                dsl.selectFrom(PROJECT_USER)
                        .where(PROJECT_USER.USER_ID.eq(userId))
                        .and(PROJECT_USER.PROJECT_ID.eq(projectId))
        );
    }

    public Project getProjectById(Integer projectId) {
        var rec = dsl.select(PROJECT.fields())
                .from(PROJECT)
                .where(PROJECT.ID.eq(projectId))
                .fetchOneInto(PROJECT);
        return rec != null ? DbMapper.map(rec) : null;
    }

    public void addUsersToProject(List<Integer> userIds, Integer projectId) {
        var insertStep = dsl.insertInto(PROJECT_USER,
                PROJECT_USER.PROJECT_ID,
                PROJECT_USER.USER_ID);

        for (var userId : userIds) {
            insertStep.values(projectId, userId);
        }

        insertStep.onDuplicateKeyIgnore().execute();
    }

    public List<Project> getProjectsInClass(Integer classId) {
        return dsl.select(PROJECT.fields())
                .from(PROJECT)
                .where(PROJECT.CLASSROOM_ID.eq(classId))
                .fetchInto(PROJECT)
                .map(DbMapper::map);
    }

    public List<ClassroomMember> getMembersFromProject(Integer projectId, Integer classId) {
        return dsl.select(PROJECT_USER.USER_ID, USER.FIRST_NAME, USER.LAST_NAME, USER.EMAIL, CLASSROOM_USER.ROLE)
            .from(PROJECT_USER)
            .join(USER).on(PROJECT_USER.USER_ID.eq(USER.ID))
                .and(PROJECT_USER.PROJECT_ID.eq(projectId))
            .join(CLASSROOM_USER).on(PROJECT_USER.USER_ID.eq(CLASSROOM_USER.USER_ID))
                .and(CLASSROOM_USER.CLASSROOM_ID.eq(classId))
            .fetch()
            .map(record -> ClassroomMember.newBuilder()
                .setId(record.get(PROJECT_USER.USER_ID))
                .setBasicInfo(
                    PersistenceProto.BasicInfo.newBuilder()
                        .setFirstName(record.get(USER.FIRST_NAME))
                        .setLastName(record.get(USER.LAST_NAME))
                        .setEmail(record.get(USER.EMAIL))
                        .build()
                )
                .setRole(record.get(CLASSROOM_USER.ROLE))
                .build());
    }

    public List<Project> getProjectsByUserId(Integer userId) {
        return dsl.select(PROJECT.fields())
            .from(PROJECT_USER)
            .join(PROJECT)
            .on(PROJECT_USER.PROJECT_ID.eq(PROJECT.ID))
                .and(PROJECT_USER.USER_ID.eq(userId))
            .fetchInto(PROJECT)
            .map(DbMapper::map);
    }

    public void updateProjectDetails(Integer projectId, String title, String description, Boolean archived) {
        var fieldsToUpdate = new HashMap<>();
        if (title != null) {
            fieldsToUpdate.put(PROJECT.NAME, title);
        }
        if (description != null) {
            fieldsToUpdate.put(PROJECT.DESCRIPTION, description);
        }
        if (archived != null) {
            fieldsToUpdate.put(PROJECT.ARCHIVED, (byte) (archived ? 1 : 0));
        }
        dsl.update(PROJECT)
                .set(fieldsToUpdate)
                .where(PROJECT.ID.eq(projectId))
                .execute();
    }

    public void deleteProjectUser(Integer userId, Integer projectId) {
        dsl.deleteFrom(PROJECT_USER)
                .where(PROJECT_USER.USER_ID.eq(userId))
                .and(PROJECT_USER.PROJECT_ID.eq(projectId))
                .execute();
    }

    public void deleteProject(Integer projectId) {
        dsl.deleteFrom(PROJECT)
                .where(PROJECT.ID.eq(projectId))
                .execute();
    }

    public void deleteTemplateId(Integer projectId) {
        dsl.update(PROJECT)
            .set(PROJECT.TEMPLATE_ID, (Integer) null)
            .where(PROJECT.ID.eq(projectId))
            .execute();
    }

}
