/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq;


import com.taskable.jooq.tables.ClassroomUser;
import com.taskable.jooq.tables.Project;
import com.taskable.jooq.tables.ProjectUser;
import com.taskable.jooq.tables.Subtask;
import com.taskable.jooq.tables.SubtaskAssignee;
import com.taskable.jooq.tables.SubtaskComment;
import com.taskable.jooq.tables.Task;
import com.taskable.jooq.tables.Template;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables in testdb.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index CLASSROOM_USER_CLASSROOM_ID = Internal.createIndex(DSL.name("classroom_id"), ClassroomUser.CLASSROOM_USER, new OrderField[] { ClassroomUser.CLASSROOM_USER.CLASSROOM_ID }, false);
    public static final Index PROJECT_CLASSROOM_ID = Internal.createIndex(DSL.name("classroom_id"), Project.PROJECT, new OrderField[] { Project.PROJECT.CLASSROOM_ID }, false);
    public static final Index TEMPLATE_CLASSROOM_ID = Internal.createIndex(DSL.name("classroom_id"), Template.TEMPLATE, new OrderField[] { Template.TEMPLATE.CLASSROOM_ID }, false);
    public static final Index PROJECT_USER_PROJECT_ID = Internal.createIndex(DSL.name("project_id"), ProjectUser.PROJECT_USER, new OrderField[] { ProjectUser.PROJECT_USER.PROJECT_ID }, false);
    public static final Index SUBTASK_ASSIGNEE_PROJECT_ID = Internal.createIndex(DSL.name("project_id"), SubtaskAssignee.SUBTASK_ASSIGNEE, new OrderField[] { SubtaskAssignee.SUBTASK_ASSIGNEE.PROJECT_ID }, false);
    public static final Index TASK_PROJECT_ID = Internal.createIndex(DSL.name("project_id"), Task.TASK, new OrderField[] { Task.TASK.PROJECT_ID }, false);
    public static final Index SUBTASK_ASSIGNEE_SUBTASK_ID = Internal.createIndex(DSL.name("subtask_id"), SubtaskAssignee.SUBTASK_ASSIGNEE, new OrderField[] { SubtaskAssignee.SUBTASK_ASSIGNEE.SUBTASK_ID }, false);
    public static final Index SUBTASK_COMMENT_SUBTASK_ID = Internal.createIndex(DSL.name("subtask_id"), SubtaskComment.SUBTASK_COMMENT, new OrderField[] { SubtaskComment.SUBTASK_COMMENT.SUBTASK_ID }, false);
    public static final Index SUBTASK_TASK_ID = Internal.createIndex(DSL.name("task_id"), Subtask.SUBTASK, new OrderField[] { Subtask.SUBTASK.TASK_ID }, false);
    public static final Index PROJECT_TEMPLATE_ID = Internal.createIndex(DSL.name("template_id"), Project.PROJECT, new OrderField[] { Project.PROJECT.TEMPLATE_ID }, false);
    public static final Index SUBTASK_COMMENT_USER_ID = Internal.createIndex(DSL.name("user_id"), SubtaskComment.SUBTASK_COMMENT, new OrderField[] { SubtaskComment.SUBTASK_COMMENT.USER_ID }, false);
}
