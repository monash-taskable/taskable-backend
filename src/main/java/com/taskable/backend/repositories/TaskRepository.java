package com.taskable.backend.repositories;

import com.taskable.backend.exception_handling.NotFoundOnNull;
import com.taskable.backend.utils.DbMapper;
import com.taskable.backend.utils.DbUtils;
import com.taskable.backend.utils.SubtaskWithAssignees;
import com.taskable.protobufs.PersistenceProto.Subtask;
import com.taskable.protobufs.PersistenceProto.Task;
import org.apache.commons.lang3.tuple.Pair;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.taskable.jooq.Tables.*;

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

  public void updateTask(Integer taskId, String title, String description, String color) {
    var fieldsToUpdate = new HashMap<>();
    if (title != null) {
      fieldsToUpdate.put(TASK.TITLE, title);
    }
    if (description != null) {
      fieldsToUpdate.put(TASK.DESCRIPTION, description);
    }
    if (color != null) {
      fieldsToUpdate.put(TASK.COLOR, color);
    }
    dsl.update(TASK)
        .set(fieldsToUpdate)
        .where(TASK.ID.eq(taskId))
        .execute();
  }

  public void deleteTask(Integer taskId) {
    dsl.deleteFrom(TASK)
        .where(TASK.ID.eq(taskId))
        .execute();
  }

  public Integer createSubtask(Subtask subtask) {
    return dsl.insertInto(SUBTASK)
        .set(SUBTASK.TASK_ID, subtask.getTaskId())
        .set(SUBTASK.TITLE, subtask.getTitle())
        .set(SUBTASK.DESCRIPTION, subtask.getDescription())
        .set(SUBTASK.STATUS, subtask.getStatus())
        .set(SUBTASK.PRIORITY, subtask.getPriority())
        .set(SUBTASK.START_DATE, DbUtils.getDateTime(subtask.getStart()))
        .set(SUBTASK.DUE_DATE, DbUtils.getDateTime(subtask.getEnd()))
        .returning(SUBTASK.ID)
        .fetchOne(SUBTASK.ID);
  }

  @NotFoundOnNull(message = "Subtask not found")
  public Subtask getSubtask(Integer subtaskId) {
    var rec = dsl.selectFrom(SUBTASK)
        .where(SUBTASK.ID.eq(subtaskId))
        .fetchOneInto(SUBTASK);
    return rec != null ? DbMapper.map(rec) : null;
  }

  public List<Integer> getUserIdsInSubtask(Integer subtaskId) {
    return dsl.select(SUBTASK_ASSIGNEE.USER_ID)
        .from(SUBTASK_ASSIGNEE)
        .where(SUBTASK_ASSIGNEE.SUBTASK_ID.eq(subtaskId))
        .fetch(SUBTASK_ASSIGNEE.USER_ID);
  }

  public List<SubtaskWithAssignees> getSubtasks(Integer taskId) {
    return dsl.select(
            SUBTASK.asterisk(),
            DSL.groupConcat(SUBTASK_ASSIGNEE.USER_ID).as("assigneeIds")
        )
        .from(SUBTASK)
        .leftJoin(SUBTASK_ASSIGNEE)
        .on(SUBTASK_ASSIGNEE.SUBTASK_ID.eq(SUBTASK.ID))
        .where(SUBTASK.TASK_ID.eq(taskId))
        .groupBy(SUBTASK.ID)
        .fetch()
        .map(record -> {
          var subtask = record.into(SUBTASK);
          String assigneeIdsStr = record.get("assigneeIds", String.class);
          List<Integer> assigneeIds = Arrays.stream(
                  assigneeIdsStr != null ? assigneeIdsStr.split(",") : new String[0]
              )
              .filter(s -> !s.isEmpty())
              .map(Integer::valueOf)
              .collect(Collectors.toList());
          return new SubtaskWithAssignees(DbMapper.map(subtask), assigneeIds);
        });
  }
}
