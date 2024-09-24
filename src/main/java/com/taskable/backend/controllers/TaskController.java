package com.taskable.backend.controllers;

import com.taskable.backend.auth.CustomUserDetails;
import com.taskable.backend.services.AuthorizationService;
import com.taskable.backend.services.TaskService;
import com.taskable.protobufs.TaskProto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{class_id}/projects/{project_id}/tasks")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @Autowired
  private AuthorizationService authorizationService;

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
}
