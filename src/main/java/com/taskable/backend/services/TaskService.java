package com.taskable.backend.services;

import com.taskable.backend.repositories.TaskRepository;
import com.taskable.protobufs.PersistenceProto.Subtask;
import com.taskable.protobufs.TaskProto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  public CreateTaskResponse createTask(Integer projectId, CreateTaskRequest req) {
    return CreateTaskResponse.newBuilder()
        .setId(taskRepository.createTask(projectId,
            req.getTitle(),
            req.getDescription(),
            req.getColor()))
        .build();
  }

  public GetTaskResponse getTask(Integer taskId) {
    return GetTaskResponse.newBuilder()
        .setTask(taskRepository.getTask(taskId))
        .build();
  }

  public GetTasksResponse getTasks(Integer projectId) {
    return GetTasksResponse.newBuilder()
        .addAllTasks(taskRepository.getTasksInProject(projectId))
        .build();
  }

  public void updateTask(Integer taskId, UpdateTaskRequest req) {
    taskRepository.updateTask(taskId,
        req.hasTitle() ? req.getTitle() : null,
        req.hasDescription() ? req.getDescription() : null,
        req.hasColor() ? req.getColor() : null);
  }

  public void deleteTask(Integer taskId) {
    taskRepository.deleteTask(taskId);
  }

  public CreateSubtaskResponse createSubtask(Integer taskId, CreateSubtaskRequest req) {
    var subtask = Subtask.newBuilder()
        .setTaskId(taskId)
        .setTitle(req.getTitle())
        .setDescription(req.getDescription())
        .setStatus(req.getStatus())
        .setPriority(req.getPriority())
        .setStart(req.getStart())
        .setEnd(req.getEnd())
        .build();
    return CreateSubtaskResponse.newBuilder()
        .setId(taskRepository.createSubtask(subtask))
        .build();
  }

  public GetSubtaskResponse getSubtask(Integer subTaskId) {
    var userIds = taskRepository.getUserIdsInSubtask(subTaskId);
    var subtask = taskRepository.getSubtask(subTaskId);
    return GetSubtaskResponse.newBuilder()
        .setSubtask(subtask)
        .addAllAssigneeIds(userIds)
        .build();
  }

  public GetSubtasksResponse getSubtasks(Integer taskId) {
    return GetSubtasksResponse.newBuilder()
        .addAllSubtaskResponses(taskRepository.getSubtasks(taskId).stream()
            .map(
              subtaskWithAssignees -> GetSubtaskResponse.newBuilder()
                  .setSubtask(subtaskWithAssignees.subtask())
                  .addAllAssigneeIds(subtaskWithAssignees.assigneeIds())
                  .build()
            ).toList()).build();
  }
}
