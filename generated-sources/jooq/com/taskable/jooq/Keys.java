/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq;


import com.taskable.jooq.tables.Announcement;
import com.taskable.jooq.tables.Attachment;
import com.taskable.jooq.tables.Classroom;
import com.taskable.jooq.tables.ClassroomUser;
import com.taskable.jooq.tables.Project;
import com.taskable.jooq.tables.ProjectUser;
import com.taskable.jooq.tables.Subtask;
import com.taskable.jooq.tables.SubtaskAssignee;
import com.taskable.jooq.tables.SubtaskComment;
import com.taskable.jooq.tables.Task;
import com.taskable.jooq.tables.Template;
import com.taskable.jooq.tables.User;
import com.taskable.jooq.tables.records.AnnouncementRecord;
import com.taskable.jooq.tables.records.AttachmentRecord;
import com.taskable.jooq.tables.records.ClassroomRecord;
import com.taskable.jooq.tables.records.ClassroomUserRecord;
import com.taskable.jooq.tables.records.ProjectRecord;
import com.taskable.jooq.tables.records.ProjectUserRecord;
import com.taskable.jooq.tables.records.SubtaskAssigneeRecord;
import com.taskable.jooq.tables.records.SubtaskCommentRecord;
import com.taskable.jooq.tables.records.SubtaskRecord;
import com.taskable.jooq.tables.records.TaskRecord;
import com.taskable.jooq.tables.records.TemplateRecord;
import com.taskable.jooq.tables.records.UserRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in
 * testdb.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AnnouncementRecord> KEY_ANNOUNCEMENT_PRIMARY = Internal.createUniqueKey(Announcement.ANNOUNCEMENT, DSL.name("KEY_announcement_PRIMARY"), new TableField[] { Announcement.ANNOUNCEMENT.ID }, true);
    public static final UniqueKey<AttachmentRecord> KEY_ATTACHMENT_PRIMARY = Internal.createUniqueKey(Attachment.ATTACHMENT, DSL.name("KEY_attachment_PRIMARY"), new TableField[] { Attachment.ATTACHMENT.ID }, true);
    public static final UniqueKey<ClassroomRecord> KEY_CLASSROOM_PRIMARY = Internal.createUniqueKey(Classroom.CLASSROOM, DSL.name("KEY_classroom_PRIMARY"), new TableField[] { Classroom.CLASSROOM.ID }, true);
    public static final UniqueKey<ClassroomUserRecord> KEY_CLASSROOM_USER_PRIMARY = Internal.createUniqueKey(ClassroomUser.CLASSROOM_USER, DSL.name("KEY_classroom_user_PRIMARY"), new TableField[] { ClassroomUser.CLASSROOM_USER.USER_ID, ClassroomUser.CLASSROOM_USER.CLASSROOM_ID }, true);
    public static final UniqueKey<ProjectRecord> KEY_PROJECT_NAME = Internal.createUniqueKey(Project.PROJECT, DSL.name("KEY_project_name"), new TableField[] { Project.PROJECT.NAME }, true);
    public static final UniqueKey<ProjectRecord> KEY_PROJECT_PRIMARY = Internal.createUniqueKey(Project.PROJECT, DSL.name("KEY_project_PRIMARY"), new TableField[] { Project.PROJECT.ID }, true);
    public static final UniqueKey<ProjectUserRecord> KEY_PROJECT_USER_PRIMARY = Internal.createUniqueKey(ProjectUser.PROJECT_USER, DSL.name("KEY_project_user_PRIMARY"), new TableField[] { ProjectUser.PROJECT_USER.USER_ID, ProjectUser.PROJECT_USER.PROJECT_ID }, true);
    public static final UniqueKey<SubtaskRecord> KEY_SUBTASK_PRIMARY = Internal.createUniqueKey(Subtask.SUBTASK, DSL.name("KEY_subtask_PRIMARY"), new TableField[] { Subtask.SUBTASK.ID }, true);
    public static final UniqueKey<SubtaskAssigneeRecord> KEY_SUBTASK_ASSIGNEE_PRIMARY = Internal.createUniqueKey(SubtaskAssignee.SUBTASK_ASSIGNEE, DSL.name("KEY_subtask_assignee_PRIMARY"), new TableField[] { SubtaskAssignee.SUBTASK_ASSIGNEE.USER_ID, SubtaskAssignee.SUBTASK_ASSIGNEE.PROJECT_ID, SubtaskAssignee.SUBTASK_ASSIGNEE.SUBTASK_ID }, true);
    public static final UniqueKey<SubtaskCommentRecord> KEY_SUBTASK_COMMENT_PRIMARY = Internal.createUniqueKey(SubtaskComment.SUBTASK_COMMENT, DSL.name("KEY_subtask_comment_PRIMARY"), new TableField[] { SubtaskComment.SUBTASK_COMMENT.ID }, true);
    public static final UniqueKey<TaskRecord> KEY_TASK_PRIMARY = Internal.createUniqueKey(Task.TASK, DSL.name("KEY_task_PRIMARY"), new TableField[] { Task.TASK.ID }, true);
    public static final UniqueKey<TemplateRecord> KEY_TEMPLATE_PRIMARY = Internal.createUniqueKey(Template.TEMPLATE, DSL.name("KEY_template_PRIMARY"), new TableField[] { Template.TEMPLATE.ID }, true);
    public static final UniqueKey<UserRecord> KEY_USER_EMAIL = Internal.createUniqueKey(User.USER, DSL.name("KEY_user_email"), new TableField[] { User.USER.EMAIL }, true);
    public static final UniqueKey<UserRecord> KEY_USER_PRIMARY = Internal.createUniqueKey(User.USER, DSL.name("KEY_user_PRIMARY"), new TableField[] { User.USER.ID }, true);
    public static final UniqueKey<UserRecord> KEY_USER_SUB = Internal.createUniqueKey(User.USER, DSL.name("KEY_user_sub"), new TableField[] { User.USER.SUB }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AnnouncementRecord, ClassroomRecord> ANNOUNCEMENT_IBFK_1 = Internal.createForeignKey(Announcement.ANNOUNCEMENT, DSL.name("announcement_ibfk_1"), new TableField[] { Announcement.ANNOUNCEMENT.CLASSROOM_ID }, Keys.KEY_CLASSROOM_PRIMARY, new TableField[] { Classroom.CLASSROOM.ID }, true);
    public static final ForeignKey<AnnouncementRecord, UserRecord> ANNOUNCEMENT_IBFK_2 = Internal.createForeignKey(Announcement.ANNOUNCEMENT, DSL.name("announcement_ibfk_2"), new TableField[] { Announcement.ANNOUNCEMENT.USER_ID }, Keys.KEY_USER_PRIMARY, new TableField[] { User.USER.ID }, true);
    public static final ForeignKey<AttachmentRecord, ProjectRecord> ATTACHMENT_IBFK_1 = Internal.createForeignKey(Attachment.ATTACHMENT, DSL.name("attachment_ibfk_1"), new TableField[] { Attachment.ATTACHMENT.PROJECT_ID }, Keys.KEY_PROJECT_PRIMARY, new TableField[] { Project.PROJECT.ID }, true);
    public static final ForeignKey<AttachmentRecord, TemplateRecord> ATTACHMENT_IBFK_2 = Internal.createForeignKey(Attachment.ATTACHMENT, DSL.name("attachment_ibfk_2"), new TableField[] { Attachment.ATTACHMENT.TEMPLATE_ID }, Keys.KEY_TEMPLATE_PRIMARY, new TableField[] { Template.TEMPLATE.ID }, true);
    public static final ForeignKey<AttachmentRecord, SubtaskRecord> ATTACHMENT_IBFK_3 = Internal.createForeignKey(Attachment.ATTACHMENT, DSL.name("attachment_ibfk_3"), new TableField[] { Attachment.ATTACHMENT.SUBTASK_ID }, Keys.KEY_SUBTASK_PRIMARY, new TableField[] { Subtask.SUBTASK.ID }, true);
    public static final ForeignKey<ClassroomUserRecord, UserRecord> CLASSROOM_USER_IBFK_1 = Internal.createForeignKey(ClassroomUser.CLASSROOM_USER, DSL.name("classroom_user_ibfk_1"), new TableField[] { ClassroomUser.CLASSROOM_USER.USER_ID }, Keys.KEY_USER_PRIMARY, new TableField[] { User.USER.ID }, true);
    public static final ForeignKey<ClassroomUserRecord, ClassroomRecord> CLASSROOM_USER_IBFK_2 = Internal.createForeignKey(ClassroomUser.CLASSROOM_USER, DSL.name("classroom_user_ibfk_2"), new TableField[] { ClassroomUser.CLASSROOM_USER.CLASSROOM_ID }, Keys.KEY_CLASSROOM_PRIMARY, new TableField[] { Classroom.CLASSROOM.ID }, true);
    public static final ForeignKey<ProjectRecord, ClassroomRecord> PROJECT_IBFK_1 = Internal.createForeignKey(Project.PROJECT, DSL.name("project_ibfk_1"), new TableField[] { Project.PROJECT.CLASSROOM_ID }, Keys.KEY_CLASSROOM_PRIMARY, new TableField[] { Classroom.CLASSROOM.ID }, true);
    public static final ForeignKey<ProjectRecord, TemplateRecord> PROJECT_IBFK_2 = Internal.createForeignKey(Project.PROJECT, DSL.name("project_ibfk_2"), new TableField[] { Project.PROJECT.TEMPLATE_ID }, Keys.KEY_TEMPLATE_PRIMARY, new TableField[] { Template.TEMPLATE.ID }, true);
    public static final ForeignKey<ProjectUserRecord, UserRecord> PROJECT_USER_IBFK_1 = Internal.createForeignKey(ProjectUser.PROJECT_USER, DSL.name("project_user_ibfk_1"), new TableField[] { ProjectUser.PROJECT_USER.USER_ID }, Keys.KEY_USER_PRIMARY, new TableField[] { User.USER.ID }, true);
    public static final ForeignKey<ProjectUserRecord, ProjectRecord> PROJECT_USER_IBFK_2 = Internal.createForeignKey(ProjectUser.PROJECT_USER, DSL.name("project_user_ibfk_2"), new TableField[] { ProjectUser.PROJECT_USER.PROJECT_ID }, Keys.KEY_PROJECT_PRIMARY, new TableField[] { Project.PROJECT.ID }, true);
    public static final ForeignKey<SubtaskRecord, TaskRecord> SUBTASK_IBFK_1 = Internal.createForeignKey(Subtask.SUBTASK, DSL.name("subtask_ibfk_1"), new TableField[] { Subtask.SUBTASK.TASK_ID }, Keys.KEY_TASK_PRIMARY, new TableField[] { Task.TASK.ID }, true);
    public static final ForeignKey<SubtaskAssigneeRecord, UserRecord> SUBTASK_ASSIGNEE_IBFK_1 = Internal.createForeignKey(SubtaskAssignee.SUBTASK_ASSIGNEE, DSL.name("subtask_assignee_ibfk_1"), new TableField[] { SubtaskAssignee.SUBTASK_ASSIGNEE.USER_ID }, Keys.KEY_USER_PRIMARY, new TableField[] { User.USER.ID }, true);
    public static final ForeignKey<SubtaskAssigneeRecord, ProjectRecord> SUBTASK_ASSIGNEE_IBFK_2 = Internal.createForeignKey(SubtaskAssignee.SUBTASK_ASSIGNEE, DSL.name("subtask_assignee_ibfk_2"), new TableField[] { SubtaskAssignee.SUBTASK_ASSIGNEE.PROJECT_ID }, Keys.KEY_PROJECT_PRIMARY, new TableField[] { Project.PROJECT.ID }, true);
    public static final ForeignKey<SubtaskAssigneeRecord, SubtaskRecord> SUBTASK_ASSIGNEE_IBFK_3 = Internal.createForeignKey(SubtaskAssignee.SUBTASK_ASSIGNEE, DSL.name("subtask_assignee_ibfk_3"), new TableField[] { SubtaskAssignee.SUBTASK_ASSIGNEE.SUBTASK_ID }, Keys.KEY_SUBTASK_PRIMARY, new TableField[] { Subtask.SUBTASK.ID }, true);
    public static final ForeignKey<SubtaskCommentRecord, SubtaskRecord> SUBTASK_COMMENT_IBFK_1 = Internal.createForeignKey(SubtaskComment.SUBTASK_COMMENT, DSL.name("subtask_comment_ibfk_1"), new TableField[] { SubtaskComment.SUBTASK_COMMENT.SUBTASK_ID }, Keys.KEY_SUBTASK_PRIMARY, new TableField[] { Subtask.SUBTASK.ID }, true);
    public static final ForeignKey<SubtaskCommentRecord, UserRecord> SUBTASK_COMMENT_IBFK_2 = Internal.createForeignKey(SubtaskComment.SUBTASK_COMMENT, DSL.name("subtask_comment_ibfk_2"), new TableField[] { SubtaskComment.SUBTASK_COMMENT.USER_ID }, Keys.KEY_USER_PRIMARY, new TableField[] { User.USER.ID }, true);
    public static final ForeignKey<TaskRecord, ProjectRecord> TASK_IBFK_1 = Internal.createForeignKey(Task.TASK, DSL.name("task_ibfk_1"), new TableField[] { Task.TASK.PROJECT_ID }, Keys.KEY_PROJECT_PRIMARY, new TableField[] { Project.PROJECT.ID }, true);
    public static final ForeignKey<TemplateRecord, ClassroomRecord> TEMPLATE_IBFK_1 = Internal.createForeignKey(Template.TEMPLATE, DSL.name("template_ibfk_1"), new TableField[] { Template.TEMPLATE.CLASSROOM_ID }, Keys.KEY_CLASSROOM_PRIMARY, new TableField[] { Classroom.CLASSROOM.ID }, true);
}
