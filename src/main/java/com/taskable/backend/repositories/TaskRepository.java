package com.taskable.backend.repositories;

import com.taskable.backend.exception_handling.NotFoundOnNull;
import com.taskable.backend.utils.DbMapper;
import com.taskable.backend.utils.DbUtils;
import com.taskable.backend.utils.SubtaskWithAssignees;
import com.taskable.protobufs.PersistenceProto.Comment;
import com.taskable.protobufs.PersistenceProto.Subtask;
import com.taskable.protobufs.PersistenceProto.Task;
import com.taskable.protobufs.TaskProto;
import com.taskable.protobufs.TaskProto.UpdateSubtaskRequest;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
    dsl.configuration().settings().withRenderGroupConcatMaxLenSessionVariable(false);
    var assigneeIdsField = DSL.groupConcat(SUBTASK_ASSIGNEE.USER_ID).as("assigneeIds");

    return dsl.select(SUBTASK.fields())
        .select(assigneeIdsField)
        .from(SUBTASK)
        .leftJoin(SUBTASK_ASSIGNEE)
        .on(SUBTASK_ASSIGNEE.SUBTASK_ID.eq(SUBTASK.ID))
          .where(SUBTASK.TASK_ID.eq(taskId))
        .groupBy(SUBTASK.ID)
        .fetch()
        .map(record -> {
          var subtask = DbMapper.map(record.into(SUBTASK));
          String assigneeIdsStr = record.get(assigneeIdsField);
          List<Integer> assigneeIds = Arrays.stream(
                  assigneeIdsStr != null ? assigneeIdsStr.split(",") : new String[0]
              )
              .filter(s -> !s.isEmpty())
              .map(Integer::valueOf)
              .collect(Collectors.toList());
          return new SubtaskWithAssignees(subtask, assigneeIds);
        });
  }

  public void updateSubtask(Integer subtaskId, UpdateSubtaskRequest req) {
    var fieldsToUpdate = new HashMap<>();
    if (req.hasTaskId()) {
      fieldsToUpdate.put(SUBTASK.TASK_ID, req.getTaskId());
    }
    if (req.hasTitle()) {
      fieldsToUpdate.put(SUBTASK.TITLE, req.getTitle());
    }
    if (req.hasDescription()) {
      fieldsToUpdate.put(SUBTASK.DESCRIPTION, req.getDescription());
    }
    if (req.hasStatus()) {
      fieldsToUpdate.put(SUBTASK.STATUS, req.getStatus());
    }
    if (req.hasPriority()) {
      fieldsToUpdate.put(SUBTASK.PRIORITY, req.getPriority());
    }
    if (req.hasStart()) {
      fieldsToUpdate.put(SUBTASK.START_DATE, DbUtils.getDateTime(req.getStart()));
    }
    if (req.hasEnd()) {
      fieldsToUpdate.put(SUBTASK.DUE_DATE, DbUtils.getDateTime(req.getEnd()));
    }
    dsl.update(SUBTASK)
        .set(fieldsToUpdate)
        .where(SUBTASK.ID.eq(subtaskId))
        .execute();
  }

  public void deleteSubtask(Integer subtaskId) {
    dsl.deleteFrom(SUBTASK)
        .where(SUBTASK.ID.eq(subtaskId))
        .execute();
  }

  public Integer createSubtaskComment(Integer userId, Integer subtaskId, TaskProto.CreateCommentRequest req) {
    return dsl.insertInto(SUBTASK_COMMENT)
        .set(SUBTASK_COMMENT.USER_ID, userId)
        .set(SUBTASK_COMMENT.SUBTASK_ID, subtaskId)
        .set(SUBTASK_COMMENT.COMMENT, req.getComment())
        .set(SUBTASK_COMMENT.CREATE_DATE, DbUtils.getDateTime(req.getCreatedDate()))
        .returning(SUBTASK_COMMENT.ID)
        .fetchOne(SUBTASK_COMMENT.ID);
  }

  @NotFoundOnNull(message = "Comment doesn't exist")
  public Comment getComment(Integer commentId) {
    var rec = dsl.selectFrom(SUBTASK_COMMENT)
        .where(SUBTASK_COMMENT.ID.eq(commentId))
        .fetchOneInto(SUBTASK_COMMENT);
    return rec != null ? DbMapper.map(rec) : null;
  }

  public List<Comment> getCommentsInSubtask(Integer subtaskId) {
    return dsl.selectFrom(SUBTASK_COMMENT)
        .where(SUBTASK_COMMENT.ID.eq(subtaskId))
        .fetch()
        .map(DbMapper::map);
  }

  public void createSubtaskAssignees(Integer subtaskId, Integer projectId, List<Integer> userIds) {
    var insertStep = dsl.insertInto(SUBTASK_ASSIGNEE,
        SUBTASK_ASSIGNEE.PROJECT_ID,
        SUBTASK_ASSIGNEE.USER_ID,
        SUBTASK_ASSIGNEE.SUBTASK_ID);

    for (var userId : userIds) {
      insertStep.values(projectId, userId, subtaskId);
    }
    insertStep.onDuplicateKeyIgnore().execute();
  }

  public void deleteSubtaskAssignees(Integer subtaskId, Integer projectId, List<Integer> usersToDelete) {
    dsl.deleteFrom(SUBTASK_ASSIGNEE)
        .where(SUBTASK_ASSIGNEE.USER_ID.in(usersToDelete))
        .and(SUBTASK_ASSIGNEE.PROJECT_ID.eq(projectId))
        .and(SUBTASK_ASSIGNEE.SUBTASK_ID.eq(subtaskId))
        .execute();
  }

  public boolean checkSubtaskCommentBelongsToUser(Integer commentId, Integer userId) {
    return dsl.fetchExists(
        dsl.selectOne()
            .from(SUBTASK_COMMENT)
            .where(SUBTASK_COMMENT.ID.eq(commentId))
            .and(SUBTASK_COMMENT.USER_ID.eq(userId))
    );
  }

  public boolean checkStCommentBelongsToProjectAndClass(Integer commentId, Integer projectId, Integer classId) {
    return dsl.fetchExists(
        dsl.selectOne()
            .from(SUBTASK_COMMENT)
            .join(SUBTASK).on(SUBTASK_COMMENT.SUBTASK_ID.eq(SUBTASK.ID))
            .and(SUBTASK_COMMENT.ID.eq(commentId))
            .join(TASK).on(SUBTASK.TASK_ID.eq(TASK.ID))
            .and(TASK.PROJECT_ID.eq(projectId))
            .join(PROJECT)
            .on(TASK.PROJECT_ID.eq(PROJECT.ID))
            .where(PROJECT.CLASSROOM_ID.eq(classId))
    );
  }

  // Will stay a single row if it succeeds, or empty if fails somewhere
  public boolean checkSubtaskBelongsToProjectAndClass(Integer subtaskId, Integer projectId, Integer classId) {
    return dsl.fetchExists(
        dsl.selectOne()
            .from(SUBTASK)
            .join(TASK).on(SUBTASK.TASK_ID.eq(TASK.ID))
            .and(SUBTASK.ID.eq(subtaskId))
            .and(TASK.PROJECT_ID.eq(projectId))
            .join(PROJECT)
            .on(TASK.PROJECT_ID.eq(PROJECT.ID))
            .where(PROJECT.CLASSROOM_ID.eq(classId))
    );
  }

  public boolean checkTaskBelongsToProjectAndClass(Integer taskId, Integer projectId, Integer classId) {
    return dsl.fetchExists(
        dsl.selectOne()
            .from(TASK)
            .join(PROJECT).on(TASK.PROJECT_ID.eq(PROJECT.ID))
            .and(TASK.ID.eq(taskId))
            .and(TASK.PROJECT_ID.eq(projectId))
            .where(PROJECT.CLASSROOM_ID.eq(classId))
    );
  }

  public boolean checkProjectBelongsToClass(Integer projectId, Integer classId) {
    return dsl.fetchExists(
        dsl.selectOne()
            .from(PROJECT)
            .where(PROJECT.ID.eq(projectId))
            .and(PROJECT.CLASSROOM_ID.eq(classId))
    );
  }
}
