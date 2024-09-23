package com.taskable.backend.repositories;

import com.taskable.backend.utils.DbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.jooq.DSLContext;

import com.taskable.protobufs.PersistenceProto.Project;

import java.util.List;

import static com.taskable.jooq.tables.Project.PROJECT;
import static com.taskable.jooq.tables.ProjectUser.PROJECT_USER;
import static com.taskable.jooq.tables.Classroom.CLASSROOM;

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

    public List<Project> getProjectsInClass(Integer classId) {
        return dsl.select(PROJECT.fields())
                .from(PROJECT)
                .join(CLASSROOM)
                .on(PROJECT.CLASSROOM_ID.eq(CLASSROOM.ID))
                .where(CLASSROOM.ID.eq(classId))
                .fetchInto(PROJECT)
                .map(DbMapper::map);
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

}
