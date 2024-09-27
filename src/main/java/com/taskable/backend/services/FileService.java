package com.taskable.backend.services;

import com.taskable.backend.repositories.FileRepository;
import com.taskable.backend.utils.DbMapper;
import com.taskable.protobufs.AttachmentProto.*;
import com.taskable.protobufs.PersistenceProto.File;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import javax.annotation.Nullable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@Service
public class FileService {

  private final FileRepository fileRepository;
  private final S3Presigner s3Presigner;
  private final S3Client s3Client;
  private final String bucketName;

  private static final Logger logger = LoggerFactory.getLogger(FileService.class);

  @Autowired
  public FileService(S3Presigner s3Presigner,
                     S3Client s3Client,
                     @Value("${aws.s3.bucket}") String bucketName,
                     FileRepository fileRepository) {
    this.s3Client = s3Client;
    this.s3Presigner = s3Presigner;
    this.bucketName = bucketName;
    this.fileRepository = fileRepository;
  }

  public GetFilesResponse getFilesInProject(Integer projectId) {
    var recList = fileRepository.getAttachmentRecsInProject(projectId);
    List<File> projectFileList = new ArrayList<>();
    List<File> templateFileList = new ArrayList<>();
    for (var rec : recList) {
      if (rec.getProjectId() != null) {
        projectFileList.add(DbMapper.map(rec));
      }
      else {
        templateFileList.add(DbMapper.map(rec));
      }
    }
    return GetFilesResponse.newBuilder()
        .addAllProjectFiles(projectFileList)
        .addAllTemplateFiles(templateFileList)
        .build();
  }

  public GetFileResponse getFile(Integer fileId) {
    return GetFileResponse.newBuilder()
        .setFile(fileRepository.getFileById(fileId))
        .build();
  }

  public void deleteFile(Integer fileId) {
    var s3Key = fileRepository.deleteFileById(fileId);
    deleteFileFromS3(s3Key);
  }

  public GetFilesResponse getTemplateFiles(Integer templateId) {
    return GetFilesResponse.newBuilder()
        .addAllTemplateFiles(fileRepository.getFilesByTemplateId(templateId))
        .build();
  }

  public GetSubtaskFilesResponse getSubtaskFiles(Integer subtaskId) {
    return GetSubtaskFilesResponse.newBuilder()
        .addAllFiles(fileRepository.getFilesBySubtaskId(subtaskId))
        .build();
  }

  public void detachFileFromSubtask(Integer fileId) {
    fileRepository.setSubtaskIdInAttachment(fileId, null);
  }

  public void attachFileToSubtask(Integer fileId, Integer subtaskId) {
    fileRepository.setSubtaskIdInAttachment(fileId, subtaskId);
  }

  public FilePreUploadResponse generatePresignedUploadUrl(String filename,
                                                           Integer size,
                                                           @Nullable Integer projectId,
                                                           @Nullable Integer templateId,
                                                           @Nullable Integer subtaskId) {
    var s3Key = randomUUID().toString() + "-" + filename;

    Integer attachmentId = fileRepository.saveAttachment(filename, size, s3Key, projectId, templateId, subtaskId);
    var putObjectRequest = PutObjectRequest.builder()
        .bucket(bucketName)
        .key(s3Key)
        .contentType("application/octet-stream")
//        .acl("private")
        .build();
    var presignRequest = PutObjectPresignRequest.builder()
        .signatureDuration(Duration.ofMinutes(15)) // url will be valid for 15 minutes
        .putObjectRequest(putObjectRequest)
        .build();

    var presignedUrl = s3Presigner.presignPutObject(presignRequest).url().toString();
    return FilePreUploadResponse.newBuilder()
        .setId(attachmentId)
        .setUrl(presignedUrl)
        .build();
  }

  public GetFileDownloadResponse generatePresignedDownloadUrl(Integer attachmentId) {
    var s3Key = fileRepository.getS3KeyById(attachmentId);

    var getObjectRequest = GetObjectRequest.builder()
        .bucket(bucketName)
        .key(s3Key)
        .build();

    var presignRequest = GetObjectPresignRequest.builder()
        .signatureDuration(Duration.ofMinutes(15))
        .getObjectRequest(getObjectRequest)
        .build();
    return GetFileDownloadResponse.newBuilder()
        .setUrl(s3Presigner.presignGetObject(presignRequest).url().toString())
        .build();
  }

  private void deleteFileFromS3(String s3Key) {
    try {
      var deleteObjectRequest = DeleteObjectRequest.builder()
          .bucket(bucketName)
          .key(s3Key)
          .build();

      s3Client.deleteObject(deleteObjectRequest);
    } catch (Exception e) {
      logger.error("Error deleting object {}: {}", s3Key, e.getMessage());
    }
  }
}
