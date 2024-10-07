/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables.records;


import com.taskable.jooq.tables.SubtaskAssignee;

import org.jooq.Record3;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class SubtaskAssigneeRecord extends UpdatableRecordImpl<SubtaskAssigneeRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>testdb.subtask_assignee.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>testdb.subtask_assignee.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>testdb.subtask_assignee.project_id</code>.
     */
    public void setProjectId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>testdb.subtask_assignee.project_id</code>.
     */
    public Integer getProjectId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>testdb.subtask_assignee.subtask_id</code>.
     */
    public void setSubtaskId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>testdb.subtask_assignee.subtask_id</code>.
     */
    public Integer getSubtaskId() {
        return (Integer) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record3<Integer, Integer, Integer> key() {
        return (Record3) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached SubtaskAssigneeRecord
     */
    public SubtaskAssigneeRecord() {
        super(SubtaskAssignee.SUBTASK_ASSIGNEE);
    }

    /**
     * Create a detached, initialised SubtaskAssigneeRecord
     */
    public SubtaskAssigneeRecord(Integer userId, Integer projectId, Integer subtaskId) {
        super(SubtaskAssignee.SUBTASK_ASSIGNEE);

        setUserId(userId);
        setProjectId(projectId);
        setSubtaskId(subtaskId);
        resetChangedOnNotNull();
    }
}
