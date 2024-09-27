package com.taskable.backend.repositories;

import com.taskable.backend.exception_handling.NotFoundOnNull;
import com.taskable.backend.utils.DbMapper;
import com.taskable.jooq.tables.records.AttachmentRecord;
import com.taskable.protobufs.PersistenceProto.File;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

import java.util.List;

import static com.taskable.jooq.Tables.*;
import static com.taskable.jooq.Tables.PROJECT;

@Repository
public class FileRepository {

  private final DSLContext dsl;

  @Autowired
  public FileRepository(DSLContext dsl) { this.dsl = dsl; }

  public Integer saveAttachment(String filename,
                                Integer sizeInBytes,
                                String s3Key,
                                @Nullable Integer projectId,
                                @Nullable Integer templateId,
                                @Nullable Integer subtaskId) {
    return dsl.insertInto(ATTACHMENT)
        .set(ATTACHMENT.NAME, filename)
        .set(ATTACHMENT.SIZE_BYTES, sizeInBytes)
        .set(ATTACHMENT.S3_KEY, s3Key)
        .set(ATTACHMENT.PROJECT_ID, projectId)
        .set(ATTACHMENT.TEMPLATE_ID, templateId)
        .set(ATTACHMENT.SUBTASK_ID, subtaskId)
        .returning(ATTACHMENT.ID)
        .fetchOne(ATTACHMENT.ID);
  }

  public List<AttachmentRecord> getAttachmentRecsInProject(Integer projectId) {
    return dsl.selectFrom(ATTACHMENT)
        .where(ATTACHMENT.PROJECT_ID.eq(projectId))
        .or(ATTACHMENT.TEMPLATE_ID.eq(
            dsl.select(TEMPLATE.ID)
                .from(PROJECT)
                .where(PROJECT.ID.eq(projectId))
        ))
        .fetch();
  }

  @NotFoundOnNull(message = "File not found")
  public File getFileById(Integer attachmentId) {
    var rec = dsl.selectFrom(ATTACHMENT)
        .where(ATTACHMENT.ID.eq(attachmentId))
        .fetchOne();
    return rec != null ? DbMapper.map(rec) : null;
  }

  public void deleteFileById(Integer attachmentId) {
    dsl.deleteFrom(ATTACHMENT)
        .where(ATTACHMENT.ID.eq(attachmentId))
        .execute();
  }

  public List<File> getFilesByTemplateId(Integer templateId) {
    return dsl.selectFrom(ATTACHMENT)
        .where(ATTACHMENT.TEMPLATE_ID.eq(templateId))
        .fetch()
        .map(DbMapper::map);
  }

  public List<File> getFilesBySubtaskId(Integer subtaskId) {
    return dsl.selectFrom(ATTACHMENT)
        .where(ATTACHMENT.SUBTASK_ID.eq(subtaskId))
        .fetch()
        .map(DbMapper::map);
  }

  public void setSubtaskIdInAttachment(Integer attachmentId, Integer subtaskId) {
    dsl.update(ATTACHMENT)
        .set(ATTACHMENT.SUBTASK_ID, subtaskId)
        .where(ATTACHMENT.ID.eq(attachmentId))
        .execute();
  }

  @NotFoundOnNull(message = "Attachment not found")
  public String getS3KeyById(Integer attachmentId) {
    return dsl.select(ATTACHMENT.S3_KEY)
        .from(ATTACHMENT)
        .where(ATTACHMENT.ID.eq(attachmentId))
        .fetchOne(ATTACHMENT.S3_KEY);
  }

  public boolean checkSubtaskAttachment(Integer fileId, Integer projectId, Integer classId) {
    return dsl.fetchExists(
        dsl.selectOne()
            .from(ATTACHMENT)
            .join(SUBTASK).on(ATTACHMENT.SUBTASK_ID.eq(SUBTASK.ID))
            .and(ATTACHMENT.ID.eq(fileId))
            .join(TASK).on(SUBTASK.TASK_ID.eq(TASK.ID))
            .and(TASK.PROJECT_ID.eq(projectId))
            .join(PROJECT)
            .on(TASK.PROJECT_ID.eq(PROJECT.ID))
            .where(PROJECT.CLASSROOM_ID.eq(classId))
    );
  }

  public boolean checkProjectAttachment(Integer fileId, Integer projectId, Integer classId) {
    return dsl.fetchExists(
        dsl.selectOne()
            .from(ATTACHMENT)
            .join(PROJECT)
            .on(ATTACHMENT.PROJECT_ID.eq(PROJECT.ID))
            .and(ATTACHMENT.ID.eq(fileId))
            .and(PROJECT.CLASSROOM_ID.eq(classId))
            .where(PROJECT.ID.eq(projectId))
    );
  }

  public boolean checkTemplateAttachment(Integer fileId, Integer templateId, Integer classId) {
    return dsl.fetchExists(
        dsl.selectOne()
            .from(ATTACHMENT)
            .join(TEMPLATE)
            .on(ATTACHMENT.TEMPLATE_ID.eq(TEMPLATE.ID))
            .and(ATTACHMENT.ID.eq(fileId))
            .and(TEMPLATE.CLASSROOM_ID.eq(classId))
            .where(TEMPLATE.ID.eq(templateId))
    );
  }

}
