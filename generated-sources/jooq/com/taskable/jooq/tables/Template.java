/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables;


import com.taskable.jooq.Indexes;
import com.taskable.jooq.Keys;
import com.taskable.jooq.Testdb;
import com.taskable.jooq.tables.Attachment.AttachmentPath;
import com.taskable.jooq.tables.Classroom.ClassroomPath;
import com.taskable.jooq.tables.Project.ProjectPath;
import com.taskable.jooq.tables.records.TemplateRecord;

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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Template extends TableImpl<TemplateRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>testdb.template</code>
     */
    public static final Template TEMPLATE = new Template();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TemplateRecord> getRecordType() {
        return TemplateRecord.class;
    }

    /**
     * The column <code>testdb.template.id</code>.
     */
    public final TableField<TemplateRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>testdb.template.classroom_id</code>.
     */
    public final TableField<TemplateRecord, Integer> CLASSROOM_ID = createField(DSL.name("classroom_id"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>testdb.template.name</code>.
     */
    public final TableField<TemplateRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>testdb.template.description</code>.
     */
    public final TableField<TemplateRecord, String> DESCRIPTION = createField(DSL.name("description"), SQLDataType.VARCHAR(500).nullable(false).defaultValue(DSL.inline("", SQLDataType.VARCHAR)), this, "");

    /**
     * The column <code>testdb.template.archived</code>.
     */
    public final TableField<TemplateRecord, Byte> ARCHIVED = createField(DSL.name("archived"), SQLDataType.TINYINT.nullable(false).defaultValue(DSL.inline("0", SQLDataType.TINYINT)), this, "");

    private Template(Name alias, Table<TemplateRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Template(Name alias, Table<TemplateRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>testdb.template</code> table reference
     */
    public Template(String alias) {
        this(DSL.name(alias), TEMPLATE);
    }

    /**
     * Create an aliased <code>testdb.template</code> table reference
     */
    public Template(Name alias) {
        this(alias, TEMPLATE);
    }

    /**
     * Create a <code>testdb.template</code> table reference
     */
    public Template() {
        this(DSL.name("template"), null);
    }

    public <O extends Record> Template(Table<O> path, ForeignKey<O, TemplateRecord> childPath, InverseForeignKey<O, TemplateRecord> parentPath) {
        super(path, childPath, parentPath, TEMPLATE);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class TemplatePath extends Template implements Path<TemplateRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> TemplatePath(Table<O> path, ForeignKey<O, TemplateRecord> childPath, InverseForeignKey<O, TemplateRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private TemplatePath(Name alias, Table<TemplateRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public TemplatePath as(String alias) {
            return new TemplatePath(DSL.name(alias), this);
        }

        @Override
        public TemplatePath as(Name alias) {
            return new TemplatePath(alias, this);
        }

        @Override
        public TemplatePath as(Table<?> alias) {
            return new TemplatePath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Testdb.TESTDB;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.TEMPLATE_CLASSROOM_ID);
    }

    @Override
    public Identity<TemplateRecord, Integer> getIdentity() {
        return (Identity<TemplateRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<TemplateRecord> getPrimaryKey() {
        return Keys.KEY_TEMPLATE_PRIMARY;
    }

    @Override
    public List<ForeignKey<TemplateRecord, ?>> getReferences() {
        return Arrays.asList(Keys.TEMPLATE_IBFK_1);
    }

    private transient ClassroomPath _classroom;

    /**
     * Get the implicit join path to the <code>testdb.classroom</code> table.
     */
    public ClassroomPath classroom() {
        if (_classroom == null)
            _classroom = new ClassroomPath(this, Keys.TEMPLATE_IBFK_1, null);

        return _classroom;
    }

    private transient AttachmentPath _attachment;

    /**
     * Get the implicit to-many join path to the <code>testdb.attachment</code>
     * table
     */
    public AttachmentPath attachment() {
        if (_attachment == null)
            _attachment = new AttachmentPath(this, null, Keys.ATTACHMENT_IBFK_2.getInverseKey());

        return _attachment;
    }

    private transient ProjectPath _project;

    /**
     * Get the implicit to-many join path to the <code>testdb.project</code>
     * table
     */
    public ProjectPath project() {
        if (_project == null)
            _project = new ProjectPath(this, null, Keys.PROJECT_IBFK_2.getInverseKey());

        return _project;
    }

    @Override
    public Template as(String alias) {
        return new Template(DSL.name(alias), this);
    }

    @Override
    public Template as(Name alias) {
        return new Template(alias, this);
    }

    @Override
    public Template as(Table<?> alias) {
        return new Template(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Template rename(String name) {
        return new Template(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Template rename(Name name) {
        return new Template(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Template rename(Table<?> name) {
        return new Template(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Template where(Condition condition) {
        return new Template(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Template where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Template where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Template where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Template where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Template where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Template where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Template where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Template whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Template whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
