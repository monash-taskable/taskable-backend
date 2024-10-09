/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables;


import com.taskable.jooq.Keys;
import com.taskable.jooq.Taskable;
import com.taskable.jooq.tables.Announcement.AnnouncementPath;
import com.taskable.jooq.tables.Classroom.ClassroomPath;
import com.taskable.jooq.tables.ClassroomUser.ClassroomUserPath;
import com.taskable.jooq.tables.Project.ProjectPath;
import com.taskable.jooq.tables.ProjectUser.ProjectUserPath;
import com.taskable.jooq.tables.SubtaskComment.SubtaskCommentPath;
import com.taskable.jooq.tables.records.UserRecord;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
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
public class User extends TableImpl<UserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>taskable.user</code>
     */
    public static final User USER = new User();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<UserRecord> getRecordType() {
        return UserRecord.class;
    }

    /**
     * The column <code>taskable.user.id</code>.
     */
    public final TableField<UserRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>taskable.user.sub</code>.
     */
    public final TableField<UserRecord, String> SUB = createField(DSL.name("sub"), SQLDataType.VARCHAR(50).nullable(false), this, "");

    /**
     * The column <code>taskable.user.username</code>.
     */
    public final TableField<UserRecord, String> USERNAME = createField(DSL.name("username"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>taskable.user.email</code>.
     */
    public final TableField<UserRecord, String> EMAIL = createField(DSL.name("email"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>taskable.user.first_name</code>.
     */
    public final TableField<UserRecord, String> FIRST_NAME = createField(DSL.name("first_name"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>taskable.user.last_name</code>.
     */
    public final TableField<UserRecord, String> LAST_NAME = createField(DSL.name("last_name"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>taskable.user.status</code>.
     */
    public final TableField<UserRecord, String> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>taskable.user.language</code>.
     */
    public final TableField<UserRecord, String> LANGUAGE = createField(DSL.name("language"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>taskable.user.colour</code>.
     */
    public final TableField<UserRecord, String> COLOUR = createField(DSL.name("colour"), SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>taskable.user.theme</code>.
     */
    public final TableField<UserRecord, String> THEME = createField(DSL.name("theme"), SQLDataType.VARCHAR(20), this, "");

    private User(Name alias, Table<UserRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private User(Name alias, Table<UserRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>taskable.user</code> table reference
     */
    public User(String alias) {
        this(DSL.name(alias), USER);
    }

    /**
     * Create an aliased <code>taskable.user</code> table reference
     */
    public User(Name alias) {
        this(alias, USER);
    }

    /**
     * Create a <code>taskable.user</code> table reference
     */
    public User() {
        this(DSL.name("user"), null);
    }

    public <O extends Record> User(Table<O> path, ForeignKey<O, UserRecord> childPath, InverseForeignKey<O, UserRecord> parentPath) {
        super(path, childPath, parentPath, USER);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class UserPath extends User implements Path<UserRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> UserPath(Table<O> path, ForeignKey<O, UserRecord> childPath, InverseForeignKey<O, UserRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private UserPath(Name alias, Table<UserRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public UserPath as(String alias) {
            return new UserPath(DSL.name(alias), this);
        }

        @Override
        public UserPath as(Name alias) {
            return new UserPath(alias, this);
        }

        @Override
        public UserPath as(Table<?> alias) {
            return new UserPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Taskable.TASKABLE;
    }

    @Override
    public Identity<UserRecord, Integer> getIdentity() {
        return (Identity<UserRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<UserRecord> getPrimaryKey() {
        return Keys.KEY_USER_PRIMARY;
    }

    @Override
    public List<UniqueKey<UserRecord>> getUniqueKeys() {
        return Arrays.asList(Keys.KEY_USER_EMAIL, Keys.KEY_USER_SUB);
    }

    private transient AnnouncementPath _announcement;

    /**
     * Get the implicit to-many join path to the
     * <code>taskable.announcement</code> table
     */
    public AnnouncementPath announcement() {
        if (_announcement == null)
            _announcement = new AnnouncementPath(this, null, Keys.ANNOUNCEMENT_IBFK_2.getInverseKey());

        return _announcement;
    }

    private transient ClassroomUserPath _classroomUser;

    /**
     * Get the implicit to-many join path to the
     * <code>taskable.classroom_user</code> table
     */
    public ClassroomUserPath classroomUser() {
        if (_classroomUser == null)
            _classroomUser = new ClassroomUserPath(this, null, Keys.CLASSROOM_USER_IBFK_1.getInverseKey());

        return _classroomUser;
    }

    private transient ProjectUserPath _projectUser;

    /**
     * Get the implicit to-many join path to the
     * <code>taskable.project_user</code> table
     */
    public ProjectUserPath projectUser() {
        if (_projectUser == null)
            _projectUser = new ProjectUserPath(this, null, Keys.PROJECT_USER_IBFK_1.getInverseKey());

        return _projectUser;
    }

    private transient SubtaskCommentPath _subtaskComment;

    /**
     * Get the implicit to-many join path to the
     * <code>taskable.subtask_comment</code> table
     */
    public SubtaskCommentPath subtaskComment() {
        if (_subtaskComment == null)
            _subtaskComment = new SubtaskCommentPath(this, null, Keys.SUBTASK_COMMENT_IBFK_2.getInverseKey());

        return _subtaskComment;
    }

    /**
     * Get the implicit many-to-many join path to the
     * <code>taskable.classroom</code> table
     */
    public ClassroomPath classroom() {
        return classroomUser().classroom();
    }

    /**
     * Get the implicit many-to-many join path to the
     * <code>taskable.project</code> table
     */
    public ProjectPath project() {
        return projectUser().project();
    }

    @Override
    public User as(String alias) {
        return new User(DSL.name(alias), this);
    }

    @Override
    public User as(Name alias) {
        return new User(alias, this);
    }

    @Override
    public User as(Table<?> alias) {
        return new User(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(String name) {
        return new User(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Name name) {
        return new User(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public User rename(Table<?> name) {
        return new User(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public User where(Condition condition) {
        return new User(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public User where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public User where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public User where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public User where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public User where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public User where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public User where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public User whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public User whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
