/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables;


import com.taskable.jooq.Indexes;
import com.taskable.jooq.Keys;
import com.taskable.jooq.Testdb;
import com.taskable.jooq.tables.Classroom.ClassroomPath;
import com.taskable.jooq.tables.ProjectUser.ProjectUserPath;
import com.taskable.jooq.tables.SubtaskAssignee.SubtaskAssigneePath;
import com.taskable.jooq.tables.Task.TaskPath;
import com.taskable.jooq.tables.Template.TemplatePath;
import com.taskable.jooq.tables.User.UserPath;
import com.taskable.jooq.tables.records.ProjectRecord;

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
public class Project extends TableImpl<ProjectRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>testdb.project</code>
     */
    public static final Project PROJECT = new Project();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectRecord> getRecordType() {
        return ProjectRecord.class;
    }

    /**
     * The column <code>testdb.project.id</code>.
     */
    public final TableField<ProjectRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>testdb.project.classroom_id</code>.
     */
    public final TableField<ProjectRecord, Integer> CLASSROOM_ID = createField(DSL.name("classroom_id"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>testdb.project.template_id</code>.
     */
    public final TableField<ProjectRecord, Integer> TEMPLATE_ID = createField(DSL.name("template_id"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>testdb.project.name</code>.
     */
    public final TableField<ProjectRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>testdb.project.created_at</code>.
     */
    public final TableField<ProjectRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(0).nullable(false), this, "");

    private Project(Name alias, Table<ProjectRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Project(Name alias, Table<ProjectRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>testdb.project</code> table reference
     */
    public Project(String alias) {
        this(DSL.name(alias), PROJECT);
    }

    /**
     * Create an aliased <code>testdb.project</code> table reference
     */
    public Project(Name alias) {
        this(alias, PROJECT);
    }

    /**
     * Create a <code>testdb.project</code> table reference
     */
    public Project() {
        this(DSL.name("project"), null);
    }

    public <O extends Record> Project(Table<O> path, ForeignKey<O, ProjectRecord> childPath, InverseForeignKey<O, ProjectRecord> parentPath) {
        super(path, childPath, parentPath, PROJECT);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class ProjectPath extends Project implements Path<ProjectRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> ProjectPath(Table<O> path, ForeignKey<O, ProjectRecord> childPath, InverseForeignKey<O, ProjectRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private ProjectPath(Name alias, Table<ProjectRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public ProjectPath as(String alias) {
            return new ProjectPath(DSL.name(alias), this);
        }

        @Override
        public ProjectPath as(Name alias) {
            return new ProjectPath(alias, this);
        }

        @Override
        public ProjectPath as(Table<?> alias) {
            return new ProjectPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Testdb.TESTDB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.PROJECT_CLASSROOM_ID, Indexes.PROJECT_TEMPLATE_ID);
    }

    @Override
    public Identity<ProjectRecord, Integer> getIdentity() {
        return (Identity<ProjectRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<ProjectRecord> getPrimaryKey() {
        return Keys.KEY_PROJECT_PRIMARY;
    }

    @Override
    public List<UniqueKey<ProjectRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_PROJECT_NAME);
    }

    @Override
    public List<ForeignKey<ProjectRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PROJECT_IBFK_1, Keys.PROJECT_IBFK_2);
    }

    private transient ClassroomPath _classroom;

    /**
     * Get the implicit join path to the <code>testdb.classroom</code> table.
     */
    public ClassroomPath classroom() {
        if (_classroom == null)
            _classroom = new ClassroomPath(this, Keys.PROJECT_IBFK_1, null);

        return _classroom;
    }

    private transient TemplatePath _template;

    /**
     * Get the implicit join path to the <code>testdb.template</code> table.
     */
    public TemplatePath template() {
        if (_template == null)
            _template = new TemplatePath(this, Keys.PROJECT_IBFK_2, null);

        return _template;
    }

    private transient ProjectUserPath _projectUser;

    /**
     * Get the implicit to-many join path to the
     * <code>testdb.project_user</code> table
     */
    public ProjectUserPath projectUser() {
        if (_projectUser == null)
            _projectUser = new ProjectUserPath(this, null, Keys.PROJECT_USER_IBFK_2.getInverseKey());

        return _projectUser;
    }

    private transient SubtaskAssigneePath _subtaskAssignee;

    /**
     * Get the implicit to-many join path to the
     * <code>testdb.subtask_assignee</code> table
     */
    public SubtaskAssigneePath subtaskAssignee() {
        if (_subtaskAssignee == null)
            _subtaskAssignee = new SubtaskAssigneePath(this, null, Keys.SUBTASK_ASSIGNEE_IBFK_2.getInverseKey());

        return _subtaskAssignee;
    }

    private transient TaskPath _task;

    /**
     * Get the implicit to-many join path to the <code>testdb.task</code> table
     */
    public TaskPath task() {
        if (_task == null)
            _task = new TaskPath(this, null, Keys.TASK_IBFK_1.getInverseKey());

        return _task;
    }

    /**
     * Get the implicit many-to-many join path to the <code>testdb.user</code>
     * table
     */
    public UserPath user() {
        return projectUser().user();
    }

    @Override
    public Project as(String alias) {
        return new Project(DSL.name(alias), this);
    }

    @Override
    public Project as(Name alias) {
        return new Project(alias, this);
    }

    @Override
    public Project as(Table<?> alias) {
        return new Project(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Project rename(String name) {
        return new Project(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Project rename(Name name) {
        return new Project(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Project rename(Table<?> name) {
        return new Project(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Project where(Condition condition) {
        return new Project(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Project where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Project where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Project where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Project where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Project where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Project where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Project where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Project whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Project whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
