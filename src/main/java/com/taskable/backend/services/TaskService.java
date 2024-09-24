package com.taskable.backend.services;

import com.taskable.backend.repositories.TaskRepository;
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
}
