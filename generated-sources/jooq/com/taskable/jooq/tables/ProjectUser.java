/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables;


import com.taskable.jooq.Indexes;
import com.taskable.jooq.Keys;
import com.taskable.jooq.Testdb;
import com.taskable.jooq.tables.Project.ProjectPath;
import com.taskable.jooq.tables.Subtask.SubtaskPath;
import com.taskable.jooq.tables.SubtaskAssignee.SubtaskAssigneePath;
import com.taskable.jooq.tables.User.UserPath;
import com.taskable.jooq.tables.records.ProjectUserRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class ProjectUser extends TableImpl<ProjectUserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>testdb.project_user</code>
     */
    public static final ProjectUser PROJECT_USER = new ProjectUser();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProjectUserRecord> getRecordType() {
        return ProjectUserRecord.class;
    }

    /**
     * The column <code>testdb.project_user.user_id</code>.
     */
    public final TableField<ProjectUserRecord, Integer> USER_ID = createField(DSL.name("user_id"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>testdb.project_user.project_id</code>.
     */
    public final TableField<ProjectUserRecord, Integer> PROJECT_ID = createField(DSL.name("project_id"), SQLDataType.INTEGER.nullable(false), this, "");

    private ProjectUser(Name alias, Table<ProjectUserRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private ProjectUser(Name alias, Table<ProjectUserRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>testdb.project_user</code> table reference
     */
    public ProjectUser(String alias) {
        this(DSL.name(alias), PROJECT_USER);
    }

    /**
     * Create an aliased <code>testdb.project_user</code> table reference
     */
    public ProjectUser(Name alias) {
        this(alias, PROJECT_USER);
    }

    /**
     * Create a <code>testdb.project_user</code> table reference
     */
    public ProjectUser() {
        this(DSL.name("project_user"), null);
    }

    public <O extends Record> ProjectUser(Table<O> path, ForeignKey<O, ProjectUserRecord> childPath, InverseForeignKey<O, ProjectUserRecord> parentPath) {
        super(path, childPath, parentPath, PROJECT_USER);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class ProjectUserPath extends ProjectUser implements Path<ProjectUserRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> ProjectUserPath(Table<O> path, ForeignKey<O, ProjectUserRecord> childPath, InverseForeignKey<O, ProjectUserRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private ProjectUserPath(Name alias, Table<ProjectUserRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public ProjectUserPath as(String alias) {
            return new ProjectUserPath(DSL.name(alias), this);
        }

        @Override
        public ProjectUserPath as(Name alias) {
            return new ProjectUserPath(alias, this);
        }

        @Override
        public ProjectUserPath as(Table<?> alias) {
            return new ProjectUserPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Testdb.TESTDB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.PROJECT_USER_PROJECT_ID);
    }

    @Override
    public UniqueKey<ProjectUserRecord> getPrimaryKey() {
        return Keys.KEY_PROJECT_USER_PRIMARY;
    }

    @Override
    public List<ForeignKey<ProjectUserRecord, ?>> getReferences() {
        return Arrays.asList(Keys.PROJECT_USER_IBFK_1, Keys.PROJECT_USER_IBFK_2);
    }

    private transient UserPath _user;

    /**
     * Get the implicit join path to the <code>testdb.user</code> table.
     */
    public UserPath user() {
        if (_user == null)
            _user = new UserPath(this, Keys.PROJECT_USER_IBFK_1, null);

        return _user;
    }

    private transient ProjectPath _project;

    /**
     * Get the implicit join path to the <code>testdb.project</code> table.
     */
    public ProjectPath project() {
        if (_project == null)
            _project = new ProjectPath(this, Keys.PROJECT_USER_IBFK_2, null);

        return _project;
    }

    private transient SubtaskAssigneePath _subtaskAssignee;

    /**
     * Get the implicit to-many join path to the
     * <code>testdb.subtask_assignee</code> table
     */
    public SubtaskAssigneePath subtaskAssignee() {
        if (_subtaskAssignee == null)
            _subtaskAssignee = new SubtaskAssigneePath(this, null, Keys.FK_SA_PJ.getInverseKey());

        return _subtaskAssignee;
    }

    /**
     * Get the implicit many-to-many join path to the
     * <code>testdb.subtask</code> table
     */
    public SubtaskPath subtask() {
        return subtaskAssignee().subtask();
    }

    @Override
    public ProjectUser as(String alias) {
        return new ProjectUser(DSL.name(alias), this);
    }

    @Override
    public ProjectUser as(Name alias) {
        return new ProjectUser(alias, this);
    }

    @Override
    public ProjectUser as(Table<?> alias) {
        return new ProjectUser(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProjectUser rename(String name) {
        return new ProjectUser(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProjectUser rename(Name name) {
        return new ProjectUser(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProjectUser rename(Table<?> name) {
        return new ProjectUser(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProjectUser where(Condition condition) {
        return new ProjectUser(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProjectUser where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProjectUser where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProjectUser where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProjectUser where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProjectUser where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProjectUser where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public ProjectUser where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProjectUser whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public ProjectUser whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
