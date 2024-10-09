/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables;


import com.taskable.jooq.Indexes;
import com.taskable.jooq.Keys;
import com.taskable.jooq.Taskable;
import com.taskable.jooq.tables.Attachment.AttachmentPath;
import com.taskable.jooq.tables.ProjectUser.ProjectUserPath;
import com.taskable.jooq.tables.SubtaskAssignee.SubtaskAssigneePath;
import com.taskable.jooq.tables.SubtaskComment.SubtaskCommentPath;
import com.taskable.jooq.tables.Task.TaskPath;
import com.taskable.jooq.tables.records.SubtaskRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Subtask extends TableImpl<SubtaskRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>taskable.subtask</code>
     */
    public static final Subtask SUBTASK = new Subtask();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SubtaskRecord> getRecordType() {
        return SubtaskRecord.class;
    }

    /**
     * The column <code>taskable.subtask.id</code>.
     */
    public final TableField<SubtaskRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>taskable.subtask.task_id</code>.
     */
    public final TableField<SubtaskRecord, Integer> TASK_ID = createField(DSL.name("task_id"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>taskable.subtask.title</code>.
     */
    public final TableField<SubtaskRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>taskable.subtask.description</code>.
     */
    public final TableField<SubtaskRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.VARCHAR(1000), this, "");

    /**
     * The column <code>taskable.subtask.status</code>.
     */
    public final TableField<SubtaskRecord, String> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>taskable.subtask.priority</code>.
     */
    public final TableField<SubtaskRecord, String> PRIORITY = createField(DSL.name("priority"), SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>taskable.subtask.start_date</code>.
     */
    public final TableField<SubtaskRecord, LocalDateTime> START_DATE = createField(DSL.name("start_date"), SQLDataType.LOCALDATETIME(0).nullable(false), this, "");

    /**
     * The column <code>taskable.subtask.due_date</code>.
     */
    public final TableField<SubtaskRecord, LocalDateTime> DUE_DATE = createField(DSL.name("due_date"), SQLDataType.LOCALDATETIME(0), this, "");

    private Subtask(Name alias, Table<SubtaskRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Subtask(Name alias, Table<SubtaskRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>taskable.subtask</code> table reference
     */
    public Subtask(String alias) {
        this(DSL.name(alias), SUBTASK);
    }

    /**
     * Create an aliased <code>taskable.subtask</code> table reference
     */
    public Subtask(Name alias) {
        this(alias, SUBTASK);
    }

    /**
     * Create a <code>taskable.subtask</code> table reference
     */
    public Subtask() {
        this(DSL.name("subtask"), null);
    }

    public <O extends Record> Subtask(Table<O> path, ForeignKey<O, SubtaskRecord> childPath, InverseForeignKey<O, SubtaskRecord> parentPath) {
        super(path, childPath, parentPath, SUBTASK);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class SubtaskPath extends Subtask implements Path<SubtaskRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> SubtaskPath(Table<O> path, ForeignKey<O, SubtaskRecord> childPath, InverseForeignKey<O, SubtaskRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private SubtaskPath(Name alias, Table<SubtaskRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public SubtaskPath as(String alias) {
            return new SubtaskPath(DSL.name(alias), this);
        }

        @Override
        public SubtaskPath as(Name alias) {
            return new SubtaskPath(alias, this);
        }

        @Override
        public SubtaskPath as(Table<?> alias) {
            return new SubtaskPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Taskable.TASKABLE;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.SUBTASK_TASK_ID);
    }

    @Override
    public Identity<SubtaskRecord, Integer> getIdentity() {
        return (Identity<SubtaskRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<SubtaskRecord> getPrimaryKey() {
        return Keys.KEY_SUBTASK_PRIMARY;
    }

    @Override
    public List<ForeignKey<SubtaskRecord, ?>> getReferences() {
        return Arrays.asList(Keys.SUBTASK_IBFK_1);
    }

    private transient TaskPath _task;

    /**
     * Get the implicit join path to the <code>taskable.task</code> table.
     */
    public TaskPath task() {
        if (_task == null)
            _task = new TaskPath(this, Keys.SUBTASK_IBFK_1, null);

        return _task;
    }

    private transient AttachmentPath _attachment;

    /**
     * Get the implicit to-many join path to the
     * <code>taskable.attachment</code> table
     */
    public AttachmentPath attachment() {
        if (_attachment == null)
            _attachment = new AttachmentPath(this, null, Keys.ATTACHMENT_IBFK_3.getInverseKey());

        return _attachment;
    }

    private transient SubtaskAssigneePath _subtaskAssignee;

    /**
     * Get the implicit to-many join path to the
     * <code>taskable.subtask_assignee</code> table
     */
    public SubtaskAssigneePath subtaskAssignee() {
        if (_subtaskAssignee == null)
            _subtaskAssignee = new SubtaskAssigneePath(this, null, Keys.SUBTASK_ASSIGNEE_IBFK_1.getInverseKey());

        return _subtaskAssignee;
    }

    private transient SubtaskCommentPath _subtaskComment;

    /**
     * Get the implicit to-many join path to the
     * <code>taskable.subtask_comment</code> table
     */
    public SubtaskCommentPath subtaskComment() {
        if (_subtaskComment == null)
            _subtaskComment = new SubtaskCommentPath(this, null, Keys.SUBTASK_COMMENT_IBFK_1.getInverseKey());

        return _subtaskComment;
    }

    /**
     * Get the implicit many-to-many join path to the
     * <code>taskable.project_user</code> table
     */
    public ProjectUserPath projectUser() {
        return subtaskAssignee().projectUser();
    }

    @Override
    public Subtask as(String alias) {
        return new Subtask(DSL.name(alias), this);
    }

    @Override
    public Subtask as(Name alias) {
        return new Subtask(alias, this);
    }

    @Override
    public Subtask as(Table<?> alias) {
        return new Subtask(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Subtask rename(String name) {
        return new Subtask(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Subtask rename(Name name) {
        return new Subtask(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Subtask rename(Table<?> name) {
        return new Subtask(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subtask where(Condition condition) {
        return new Subtask(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subtask where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subtask where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subtask where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Subtask where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Subtask where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Subtask where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Subtask where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subtask whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Subtask whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
