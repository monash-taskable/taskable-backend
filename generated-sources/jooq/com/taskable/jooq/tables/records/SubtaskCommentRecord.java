/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables.records;


import com.taskable.jooq.tables.SubtaskComment;

import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class SubtaskCommentRecord extends UpdatableRecordImpl<SubtaskCommentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>testdb.subtask_comment.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>testdb.subtask_comment.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>testdb.subtask_comment.subtask_id</code>.
     */
    public void setSubtaskId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>testdb.subtask_comment.subtask_id</code>.
     */
    public Integer getSubtaskId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>testdb.subtask_comment.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>testdb.subtask_comment.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>testdb.subtask_comment.comment</code>.
     */
    public void setComment(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>testdb.subtask_comment.comment</code>.
     */
    public String getComment() {
        return (String) get(3);
    }

    /**
     * Setter for <code>testdb.subtask_comment.create_date</code>.
     */
    public void setCreateDate(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>testdb.subtask_comment.create_date</code>.
     */
    public LocalDateTime getCreateDate() {
        return (LocalDateTime) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SubtaskCommentRecord
     */
    public SubtaskCommentRecord() {
        super(SubtaskComment.SUBTASK_COMMENT);
    }

    /**
     * Create a detached, initialised SubtaskCommentRecord
     */
    public SubtaskCommentRecord(Integer id, Integer subtaskId, Integer userId, String comment, LocalDateTime createDate) {
        super(SubtaskComment.SUBTASK_COMMENT);

        setId(id);
        setSubtaskId(subtaskId);
        setUserId(userId);
        setComment(comment);
        setCreateDate(createDate);
        resetChangedOnNotNull();
    }
}
