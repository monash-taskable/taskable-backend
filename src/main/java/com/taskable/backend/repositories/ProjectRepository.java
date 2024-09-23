package com.taskable.backend.repositories;

import com.taskable.backend.utils.DbMapper;
import com.taskable.protobufs.PersistenceProto;
import com.taskable.protobufs.PersistenceProto.ClassroomMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.jooq.DSLContext;

import com.taskable.protobufs.PersistenceProto.Project;

import java.util.List;

import static com.taskable.jooq.tables.ClassroomUser.CLASSROOM_USER;
import static com.taskable.jooq.tables.Project.PROJECT;
import static com.taskable.jooq.tables.ProjectUser.PROJECT_USER;
import static com.taskable.jooq.tables.Classroom.CLASSROOM;
import static com.taskable.jooq.tables.User.USER;

@Repository
public class ProjectRepository {
    private final DSLContext dsl;

    @Autowired
    public ProjectRepository(DSLContext dsl) {this.dsl = dsl;}

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
                .join(CLASSROOM)
                .on(PROJECT.CLASSROOM_ID.eq(CLASSROOM.ID))
                .where(CLASSROOM.ID.eq(classId))
                .fetchInto(PROJECT)
                .map(DbMapper::map);
    }

    public List<ClassroomMember> getMembersFromProject(Integer projectId) {
        return dsl.select(USER.ID, USER.FIRST_NAME, USER.LAST_NAME, CLASSROOM_USER.ROLE)
                .from(PROJECT_USER)
                .join(CLASSROOM_USER).on(PROJECT_USER.USER_ID.eq(CLASSROOM_USER.USER_ID))
                .join(USER).on(PROJECT_USER.USER_ID.eq(USER.ID))
                .where(PROJECT_USER.PROJECT_ID.eq(projectId))
                .fetch()
                .map(record -> ClassroomMember.newBuilder()
                        .setId(record.get(CLASSROOM_USER.USER_ID))
                        .setBasicInfo(
                                PersistenceProto.BasicInfo.newBuilder()
                                        .setFirstName(record.get(USER.FIRST_NAME))
                                        .setLastName(record.get(USER.LAST_NAME))
                                        .build()
                        )
                        .setRole(record.get(CLASSROOM_USER.ROLE))
                        .build());
    }

    public List<Project> getProjectsByUserId(Integer userId) {
        return dsl.select(PROJECT.fields())
            .from(PROJECT)
            .join(PROJECT_USER)
            .on(PROJECT_USER.USER_ID.eq(userId))
            .where(PROJECT_USER.USER_ID.eq(userId))
            .fetchInto(PROJECT)
            .map(DbMapper::map);
    }

    public void deleteProjectUser(Integer userId, Integer projectId) {
        dsl.deleteFrom(PROJECT_USER)
                .where(PROJECT_USER.USER_ID.eq(userId))
                .and(PROJECT_USER.PROJECT_ID.eq(projectId))
                .execute();
    }

}
