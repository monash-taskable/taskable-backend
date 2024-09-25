package com.taskable.backend.controllers;

import com.taskable.backend.auth.CustomUserDetails;
import com.taskable.backend.services.AuthorizationService;
import com.taskable.backend.services.TaskService;
import com.taskable.protobufs.TaskProto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes/{class_id}/projects/{project_id}/tasks")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @Autowired
  private AuthorizationService authorizationService;

  private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

  @PostMapping("/create")
  @PreAuthorize("@authorizationService.userExistsInProject(#userDetails.userId(), #projectId) || " +
      "@authorizationService.checkOwnerOrAdminInClass(#userDetails.userId(), #classId)")
  public CreateTaskResponse createTask(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @PathVariable("class_id") Integer classId,
                                       @PathVariable("project_id") Integer projectId,
                                       @RequestBody CreateTaskRequest req) {
    return taskService.createTask(projectId, req);
  }

  @GetMapping("/{task_id}")
  @PreAuthorize("@authorizationService.userExistsInProject(#userDetails.userId(), #projectId) || " +
      "@authorizationService.checkStaffInClass(#userDetails.userId(), #classId)")
  public GetTaskResponse getTask(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable("class_id") Integer classId,
                                 @PathVariable("project_id") Integer projectId,
                                 @PathVariable("task_id") Integer taskId) {
    return taskService.getTask(taskId);
  }

  @GetMapping("")
  @PreAuthorize("@authorizationService.userExistsInProject(#userDetails.userId(), #projectId) || " +
      "@authorizationService.checkStaffInClass(#userDetails.userId(), #classId)")
  public GetTasksResponse getTasks(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable("class_id") Integer classId,
                                   @PathVariable("project_id") Integer projectId) {
    return taskService.getTasks(projectId);
  }

  @PostMapping("{task_id}/update")
  @PreAuthorize("@authorizationService.userExistsInProject(#userDetails.userId(), #projectId) || " +
      "@authorizationService.checkOwnerOrAdminInClass(#userDetails.userId(), #classId)")
  public void updateTask(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @PathVariable("class_id") Integer classId,
                         @PathVariable("project_id") Integer projectId,
                         @PathVariable("task_id") Integer taskId,
                         @RequestBody UpdateTaskRequest req) {
    taskService.updateTask(taskId, req);
  }

  @DeleteMapping("{task_id}/delete")
  @PreAuthorize("@authorizationService.userExistsInProject(#userDetails.userId(), #projectId) || " +
      "@authorizationService.checkOwnerOrAdminInClass(#userDetails.userId(), #classId)")
  public void deleteTask(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @PathVariable("class_id") Integer classId,
                         @PathVariable("project_id") Integer projectId,
                         @PathVariable("task_id") Integer taskId) {
    taskService.deleteTask(taskId);
  }

  @PostMapping("{task_id}/subtasks/create")
  @PreAuthorize("@authorizationService.userExistsInProject(#userDetails.userId(), #projectId) || " +
      "@authorizationService.checkOwnerOrAdminInClass(#userDetails.userId(), #classId)")
  public CreateSubtaskResponse createSubtask(@AuthenticationPrincipal CustomUserDetails userDetails,
                                             @PathVariable("class_id") Integer classId,
                                             @PathVariable("project_id") Integer projectId,
                                             @PathVariable("task_id") Integer taskId,
                                             @RequestBody CreateSubtaskRequest req) {
    return taskService.createSubtask(taskId, req);
  }

  @GetMapping("{task_id}/subtasks/{subtask_id}")
  @PreAuthorize("@authorizationService.userExistsInProject(#userDetails.userId(), #projectId) || " +
      "@authorizationService.checkStaffInClass(#userDetails.userId(), #classId)")
  public GetSubtaskResponse getSubtask(@AuthenticationPrincipal CustomUserDetails userDetails,
                                       @PathVariable("class_id") Integer classId,
                                       @PathVariable("project_id") Integer projectId,
                                       @PathVariable("subtask_id") Integer subtaskId) {
    return taskService.getSubtask(subtaskId);
  }

  @GetMapping("{task_id}/subtasks")
  @PreAuthorize("@authorizationService.userExistsInProject(#userDetails.userId(), #projectId) || " +
      "@authorizationService.checkStaffInClass(#userDetails.userId(), #classId)")
  public GetSubtasksResponse getSubtasks(@AuthenticationPrincipal CustomUserDetails userDetails,
                                         @PathVariable("class_id") Integer classId,
                                         @PathVariable("project_id") Integer projectId,
                                         @PathVariable("task_id") Integer taskId) {
    return taskService.getSubtasks(taskId);
  }
}
