/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables;


import com.taskable.jooq.Keys;
import com.taskable.jooq.Taskable;
import com.taskable.jooq.tables.Announcement.AnnouncementPath;
import com.taskable.jooq.tables.ClassroomUser.ClassroomUserPath;
import com.taskable.jooq.tables.Project.ProjectPath;
import com.taskable.jooq.tables.Template.TemplatePath;
import com.taskable.jooq.tables.User.UserPath;
import com.taskable.jooq.tables.records.ClassroomRecord;

import java.time.LocalDateTime;
import java.util.Collection;

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
public class Classroom extends TableImpl<ClassroomRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>taskable.classroom</code>
     */
    public static final Classroom CLASSROOM = new Classroom();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ClassroomRecord> getRecordType() {
        return ClassroomRecord.class;
    }

    /**
     * The column <code>taskable.classroom.id</code>.
     */
    public final TableField<ClassroomRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>taskable.classroom.name</code>.
     */
    public final TableField<ClassroomRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>taskable.classroom.description</code>.
     */
    public final TableField<ClassroomRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.VARCHAR(500).nullable(false).defaultValue(DSL.inline("", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>taskable.classroom.created_at</code>.
     */
    public final TableField<ClassroomRecord, LocalDateTime> CREATED_AT = createField(DSL.name("created_at"), SQLDataType.LOCALDATETIME(0).nullable(false), this, "");

    /**
     * The column <code>taskable.classroom.archived</code>.
     */
    public final TableField<ClassroomRecord, Byte> ARCHIVED = createField(DSL.name("archived"), SQLDataType.TINYINT.nullable(false).defaultValue(DSL.inline("0", SQLDataType.TINYINT)), this, "");

    private Classroom(Name alias, Table<ClassroomRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Classroom(Name alias, Table<ClassroomRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>taskable.classroom</code> table reference
     */
    public Classroom(String alias) {
        this(DSL.name(alias), CLASSROOM);
    }

    /**
     * Create an aliased <code>taskable.classroom</code> table reference
     */
    public Classroom(Name alias) {
        this(alias, CLASSROOM);
    }

    /**
     * Create a <code>taskable.classroom</code> table reference
     */
    public Classroom() {
        this(DSL.name("classroom"), null);
    }

    public <O extends Record> Classroom(Table<O> path, ForeignKey<O, ClassroomRecord> childPath, InverseForeignKey<O, ClassroomRecord> parentPath) {
        super(path, childPath, parentPath, CLASSROOM);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class ClassroomPath extends Classroom implements Path<ClassroomRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> ClassroomPath(Table<O> path, ForeignKey<O, ClassroomRecord> childPath, InverseForeignKey<O, ClassroomRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private ClassroomPath(Name alias, Table<ClassroomRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public ClassroomPath as(String alias) {
            return new ClassroomPath(DSL.name(alias), this);
        }

        @Override
        public ClassroomPath as(Name alias) {
            return new ClassroomPath(alias, this);
        }

        @Override
        public ClassroomPath as(Table<?> alias) {
            return new ClassroomPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Taskable.TASKABLE;
    }

    @Override
    public Identity<ClassroomRecord, Integer> getIdentity() {
        return (Identity<ClassroomRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<ClassroomRecord> getPrimaryKey() {
        return Keys.KEY_CLASSROOM_PRIMARY;
    }

    private transient AnnouncementPath _announcement;

    /**
     * Get the implicit to-many join path to the
     * <code>taskable.announcement</code> table
     */
    public AnnouncementPath announcement() {
        if (_announcement == null)
            _announcement = new AnnouncementPath(this, null, Keys.ANNOUNCEMENT_IBFK_1.getInverseKey());

        return _announcement;
    }

    private transient ClassroomUserPath _classroomUser;

    /**
     * Get the implicit to-many join path to the
     * <code>taskable.classroom_user</code> table
     */
    public ClassroomUserPath classroomUser() {
        if (_classroomUser == null)
            _classroomUser = new ClassroomUserPath(this, null, Keys.CLASSROOM_USER_IBFK_2.getInverseKey());

        return _classroomUser;
    }

    private transient ProjectPath _project;

    /**
     * Get the implicit to-many join path to the <code>taskable.project</code>
     * table
     */
    public ProjectPath project() {
        if (_project == null)
            _project = new ProjectPath(this, null, Keys.PROJECT_IBFK_1.getInverseKey());

        return _project;
    }

    private transient TemplatePath _template;

    /**
     * Get the implicit to-many join path to the <code>taskable.template</code>
     * table
     */
    public TemplatePath template() {
        if (_template == null)
            _template = new TemplatePath(this, null, Keys.TEMPLATE_IBFK_1.getInverseKey());

        return _template;
    }

    /**
     * Get the implicit many-to-many join path to the <code>taskable.user</code>
     * table
     */
    public UserPath user() {
        return classroomUser().user();
    }

    @Override
    public Classroom as(String alias) {
        return new Classroom(DSL.name(alias), this);
    }

    @Override
    public Classroom as(Name alias) {
        return new Classroom(alias, this);
    }

    @Override
    public Classroom as(Table<?> alias) {
        return new Classroom(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Classroom rename(String name) {
        return new Classroom(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Classroom rename(Name name) {
        return new Classroom(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Classroom rename(Table<?> name) {
        return new Classroom(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Classroom where(Condition condition) {
        return new Classroom(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Classroom where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Classroom where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Classroom where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Classroom where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Classroom where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Classroom where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Classroom where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Classroom whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Classroom whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
