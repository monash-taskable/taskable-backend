package com.taskable.backend.repositories;

import com.taskable.backend.exception_handling.NotFoundOnNull;
import com.taskable.backend.utils.DbMapper;
import com.taskable.protobufs.PersistenceProto.Task;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.taskable.jooq.Tables.TASK;

@Repository
public class TaskRepository {
  private final DSLContext dsl;

  @Autowired
  public TaskRepository(DSLContext dsl) { this.dsl = dsl; }

  public Integer createTask(Integer projectId, String title, String description, String color) {
    return dsl.insertInto(TASK)
        .set(TASK.PROJECT_ID, projectId)
        .set(TASK.TITLE, title)
        .set(TASK.DESCRIPTION, description)
        .set(TASK.COLOR, color)
        .returning(TASK.ID)
        .fetchOne(TASK.ID);
  }

  @NotFoundOnNull(message = "Task not found")
  public Task getTask(Integer taskId) {
    var rec = dsl.selectFrom(TASK)
        .where(TASK.ID.eq(taskId))
        .fetchOneInto(TASK);
    return rec != null ? DbMapper.map(rec) : null;
  }

  public List<Task> getTasksInProject(Integer projectId) {
    return dsl.selectFrom(TASK)
        .where(TASK.PROJECT_ID.eq(projectId))
        .fetch()
        .map(DbMapper::map);
  }
}
