/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables;


import com.taskable.jooq.Indexes;
import com.taskable.jooq.Keys;
import com.taskable.jooq.Taskable;
import com.taskable.jooq.tables.Subtask.SubtaskPath;
import com.taskable.jooq.tables.User.UserPath;
import com.taskable.jooq.tables.records.SubtaskCommentRecord;

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
public class SubtaskComment extends TableImpl<SubtaskCommentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>taskable.subtask_comment</code>
     */
    public static final SubtaskComment SUBTASK_COMMENT = new SubtaskComment();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SubtaskCommentRecord> getRecordType() {
        return SubtaskCommentRecord.class;
    }

    /**
     * The column <code>taskable.subtask_comment.id</code>.
     */
    public final TableField<SubtaskCommentRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>taskable.subtask_comment.subtask_id</code>.
     */
    public final TableField<SubtaskCommentRecord, Integer> SUBTASK_ID = createField(DSL.name("subtask_id"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>taskable.subtask_comment.user_id</code>.
     */
    public final TableField<SubtaskCommentRecord, Integer> USER_ID = createField(DSL.name("user_id"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>taskable.subtask_comment.comment</code>.
     */
    public final TableField<SubtaskCommentRecord, String> COMMENT = createField(DSL.name("comment"), SQLDataType.VARCHAR(1000), this, "");

    /**
     * The column <code>taskable.subtask_comment.create_date</code>.
     */
    public final TableField<SubtaskCommentRecord, LocalDateTime> CREATE_DATE = createField(DSL.name("create_date"), SQLDataType.LOCALDATETIME(0), this, "");

    private SubtaskComment(Name alias, Table<SubtaskCommentRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private SubtaskComment(Name alias, Table<SubtaskCommentRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>taskable.subtask_comment</code> table reference
     */
    public SubtaskComment(String alias) {
        this(DSL.name(alias), SUBTASK_COMMENT);
    }

    /**
     * Create an aliased <code>taskable.subtask_comment</code> table reference
     */
    public SubtaskComment(Name alias) {
        this(alias, SUBTASK_COMMENT);
    }

    /**
     * Create a <code>taskable.subtask_comment</code> table reference
     */
    public SubtaskComment() {
        this(DSL.name("subtask_comment"), null);
    }

    public <O extends Record> SubtaskComment(Table<O> path, ForeignKey<O, SubtaskCommentRecord> childPath, InverseForeignKey<O, SubtaskCommentRecord> parentPath) {
        super(path, childPath, parentPath, SUBTASK_COMMENT);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class SubtaskCommentPath extends SubtaskComment implements Path<SubtaskCommentRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> SubtaskCommentPath(Table<O> path, ForeignKey<O, SubtaskCommentRecord> childPath, InverseForeignKey<O, SubtaskCommentRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private SubtaskCommentPath(Name alias, Table<SubtaskCommentRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public SubtaskCommentPath as(String alias) {
            return new SubtaskCommentPath(DSL.name(alias), this);
        }

        @Override
        public SubtaskCommentPath as(Name alias) {
            return new SubtaskCommentPath(alias, this);
        }

        @Override
        public SubtaskCommentPath as(Table<?> alias) {
            return new SubtaskCommentPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Taskable.TASKABLE;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.asList(Indexes.SUBTASK_COMMENT_SUBTASK_ID, Indexes.SUBTASK_COMMENT_USER_ID);
    }

    @Override
    public Identity<SubtaskCommentRecord, Integer> getIdentity() {
        return (Identity<SubtaskCommentRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<SubtaskCommentRecord> getPrimaryKey() {
        return Keys.KEY_SUBTASK_COMMENT_PRIMARY;
    }

    @Override
    public List<ForeignKey<SubtaskCommentRecord, ?>> getReferences() {
        return Arrays.asList(Keys.SUBTASK_COMMENT_IBFK_1, Keys.SUBTASK_COMMENT_IBFK_2);
    }

    private transient SubtaskPath _subtask;

    /**
     * Get the implicit join path to the <code>taskable.subtask</code> table.
     */
    public SubtaskPath subtask() {
        if (_subtask == null)
            _subtask = new SubtaskPath(this, Keys.SUBTASK_COMMENT_IBFK_1, null);

        return _subtask;
    }

    private transient UserPath _user;

    /**
     * Get the implicit join path to the <code>taskable.user</code> table.
     */
    public UserPath user() {
        if (_user == null)
            _user = new UserPath(this, Keys.SUBTASK_COMMENT_IBFK_2, null);

        return _user;
    }

    @Override
    public SubtaskComment as(String alias) {
        return new SubtaskComment(DSL.name(alias), this);
    }

    @Override
    public SubtaskComment as(Name alias) {
        return new SubtaskComment(alias, this);
    }

    @Override
    public SubtaskComment as(Table<?> alias) {
        return new SubtaskComment(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public SubtaskComment rename(String name) {
        return new SubtaskComment(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SubtaskComment rename(Name name) {
        return new SubtaskComment(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public SubtaskComment rename(Table<?> name) {
        return new SubtaskComment(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SubtaskComment where(Condition condition) {
        return new SubtaskComment(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SubtaskComment where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SubtaskComment where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SubtaskComment where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SubtaskComment where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SubtaskComment where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SubtaskComment where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public SubtaskComment where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SubtaskComment whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public SubtaskComment whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
