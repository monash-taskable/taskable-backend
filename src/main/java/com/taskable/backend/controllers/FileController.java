package com.taskable.backend.controllers;


import com.taskable.backend.auth.CustomUserDetails;
import com.taskable.backend.services.FileService;
import com.taskable.protobufs.AttachmentProto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes/{class_id}")
public class FileController {

  private final FileService fileService;

  @Autowired
  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @PostMapping("/projects/{project_id}/files/pre-upload")
  @PreAuthorize("@authorizationService.canModifyProject(#userDetails.userId(), #projectId, #classId)")
  public FilePreUploadResponse getProjectFileUploadUrl(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @PathVariable("class_id") Integer classId,
                                                    @PathVariable("project_id") Integer projectId,
                                                    @RequestBody FilePreUploadRequest req) {
    return fileService.generatePresignedUploadUrl(req.getFilename(), req.getSize(), projectId, null, null);

  }

  @GetMapping("/projects/{project_id}/files")
  @PreAuthorize("@authorizationService.canReadProject(#userDetails.userId(), #projectId, #classId)")
  public GetFilesResponse getFilesInProject(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PathVariable("class_id") Integer classId,
                                            @PathVariable("project_id") Integer projectId) {
    return fileService.getFilesInProject(projectId);
  }

  @GetMapping("/projects/{project_id}/files/{file_id}")
  @PreAuthorize("@authorizationService.canReadProjectFile(#userDetails.userId(), #fileId, #projectId, #classId)")
  public GetFileResponse getProjectFile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                 @PathVariable("class_id") Integer classId,
                                 @PathVariable("project_id") Integer projectId,
                                 @PathVariable("file_id") Integer fileId) {
    return fileService.getFile(fileId);
  }

  @DeleteMapping("/projects/{project_id}/files/{file_id}/delete")
  @PreAuthorize("@authorizationService.canModifyProjectFile(#userDetails.userId(), #fileId, #projectId, #classId)")
  public void deleteProjectFile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable("class_id") Integer classId,
                                @PathVariable("project_id") Integer projectId,
                                @PathVariable("file_id") Integer fileId) {
    fileService.deleteFile(fileId);
  }

  @GetMapping("/projects/{project_id}/files/{file_id}/download")
  @PreAuthorize("@authorizationService.canReadProjectFile(#userDetails.userId(), #fileId, #projectId, #classId)")
  public GetFileDownloadResponse getProjectFileDownloadUrl(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                     @PathVariable("class_id") Integer classId,
                                                     @PathVariable("project_id") Integer projectId,
                                                     @PathVariable("file_id") Integer fileId) {
    return fileService.generatePresignedDownloadUrl(fileId);
  }

  @PostMapping("/templates/{template_id}/files/pre-upload")
  @PreAuthorize("@authorizationService.canModifyTemplate(#userDetails.userId(), #templateId, #classId)")
  public FilePreUploadResponse getTemplateFileUploadUrl(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @PathVariable("class_id") Integer classId,
                                                        @PathVariable("project_id") Integer projectId,
                                                        @PathVariable("template_id") Integer templateId,
                                                        @RequestBody FilePreUploadRequest req) {
    return fileService.generatePresignedUploadUrl(req.getFilename(), req.getSize(), null, templateId, null);
  }

  @GetMapping("/templates/{template_id}/files")
  @PreAuthorize("@authorizationService.canReadTemplate(#userDetails.userId(), #templateId, #classId)")
  public GetFilesResponse getTemplateFiles(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                        @PathVariable("class_id") Integer classId,
                                                        @PathVariable("project_id") Integer projectId,
                                                        @PathVariable("template_id") Integer templateId,
                                                        @RequestBody FilePreUploadRequest req) {
    return fileService.getTemplateFiles(templateId);
  }

  @GetMapping("/projects/{template_id}/files/{file_id}")
  @PreAuthorize("@authorizationService.canReadTemplateFile(#userDetails.userId(), #fileId, #templateId, #classId)")
  public GetFileResponse getTemplateFile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable("class_id") Integer classId,
                                        @PathVariable("template_id") Integer templateId,
                                        @PathVariable("file_id") Integer fileId) {
    return fileService.getFile(fileId);
  }

  @DeleteMapping("/projects/{template_id}/files/{file_id}/delete")
  @PreAuthorize("@authorizationService.canModifyTemplateFile(#userDetails.userId(), #fileId, #templateId, #classId)")
  public void deleteTemplateFile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                @PathVariable("class_id") Integer classId,
                                @PathVariable("template_id") Integer templateId,
                                @PathVariable("file_id") Integer fileId) {
    fileService.deleteFile(fileId);
  }

  @DeleteMapping("/projects/{template_id}/files/{file_id}/download")
  @PreAuthorize("@authorizationService.canReadTemplateFile(#userDetails.userId(), #fileId, #templateId, #classId)")
  public GetFileDownloadResponse getDownloadTemplateFileUrl(@AuthenticationPrincipal CustomUserDetails userDetails,
                                   @PathVariable("class_id") Integer classId,
                                   @PathVariable("template_id") Integer templateId,
                                   @PathVariable("file_id") Integer fileId) {
    return fileService.generatePresignedDownloadUrl(fileId);
  }

  @PostMapping("/projects/{project_id}/tasks/{task_id}/subtasks/{subtask_id}/files/pre-upload")
  @PreAuthorize("@authorizationService.canModifySubtask(#userDetails.userId(), #subtaskId, #projectId, #classId)")
  public FilePreUploadResponse getSubtaskFileUploadUrl(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @PathVariable("class_id") Integer classId,
                                                       @PathVariable("project_id") Integer projectId,
                                                       @PathVariable("subtask_id") Integer subtaskId,
                                                       @RequestBody FilePreUploadRequest req) {
    return fileService.generatePresignedUploadUrl(req.getFilename(), req.getSize(), projectId, null, subtaskId);
  }

  @GetMapping("/projects/{project_id}/tasks/{task_id}/subtasks/{subtask_id}/files")
  @PreAuthorize("@authorizationService.canReadSubtask(#userDetails.userId(), #subtaskId, #projectId, #classId)")
  public GetSubtaskFilesResponse getSubtaskFiles(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @PathVariable("class_id") Integer classId,
                                                 @PathVariable("subtask_id") Integer subtaskId,
                                                 @PathVariable("project_d") Integer projectId) {
    return fileService.getSubtaskFiles(subtaskId);
  }

  @GetMapping("/projects/{project_id}/tasks/{task_id}/subtasks/{subtask_id}/files/{file_id}")
  @PreAuthorize("@authorizationService.canReadSubtaskFile(#userDetails.userId(), #fileId, #projectId, #classId)")
  public GetFileResponse getSubtaskFile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                 @PathVariable("class_id") Integer classId,
                                                 @PathVariable("subtask_id") Integer subtaskId,
                                                 @PathVariable("file_id") Integer fileId,
                                        @PathVariable("project_id") Integer projectId) {
    return fileService.getFile(fileId);
  }

  @DeleteMapping("/projects/{project_id}/tasks/{task_id}/subtasks/{subtask_id}/files/{file_id}/detach")
  @PreAuthorize("@authorizationService.canModifySubtaskFile(#userDetails.userId(), #fileId, #projectId, #classId)")
  public void detachFileFromSubtask(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @PathVariable("class_id") Integer classId,
                                        @PathVariable("subtask_id") Integer subtaskId,
                                        @PathVariable("file_id") Integer fileId,
                                        @PathVariable("project_id") Integer projectId) {
    fileService.detachFileFromSubtask(fileId);
  }

  @PostMapping("/projects/{project_id}/tasks/{task_id}/subtasks/{subtask_id}/files/attach")
  @PreAuthorize("@authorizationService.canModifySubtaskFile(#userDetails.userId(), #fileId, #projectId, #classId)")
  public void attachFile(@AuthenticationPrincipal CustomUserDetails userDetails,
                         @PathVariable("class_id") Integer classId,
                         @PathVariable("subtask_id") Integer subtaskId,
                         @PathVariable("project_id") Integer projectId,
                         @RequestBody AttachFileRequest req) {
    fileService.attachFileToSubtask(req.getId(), subtaskId);
  }

  @GetMapping("/projects/{project_id}/tasks/{task_id}/subtasks/{subtask_id}/files/{file_id}/download")
  @PreAuthorize("@authorizationService.canReadSubtaskFile(#userDetails.userId(), #fileId, #projectId, #classId)")
  public GetFileDownloadResponse downloadSubtaskFile(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                     @PathVariable("class_id") Integer classId,
                                                     @PathVariable("subtask_id") Integer subtaskId,
                                                     @PathVariable("project_id") Integer projectId,
                                                     @PathVariable("file_id") Integer fileId) {
    return fileService.generatePresignedDownloadUrl(fileId);
  }

}
