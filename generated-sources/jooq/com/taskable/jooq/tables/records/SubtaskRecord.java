/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables.records;


import com.taskable.jooq.tables.Subtask;

import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class SubtaskRecord extends UpdatableRecordImpl<SubtaskRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>testdb.subtask.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>testdb.subtask.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>testdb.subtask.task_id</code>.
     */
    public void setTaskId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>testdb.subtask.task_id</code>.
     */
    public Integer getTaskId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>testdb.subtask.title</code>.
     */
    public void setTitle(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>testdb.subtask.title</code>.
     */
    public String getTitle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>testdb.subtask.description</code>.
     */
    public void setDescription(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>testdb.subtask.description</code>.
     */
    public String getDescription() {
        return (String) get(3);
    }

    /**
     * Setter for <code>testdb.subtask.status</code>.
     */
    public void setStatus(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>testdb.subtask.status</code>.
     */
    public String getStatus() {
        return (String) get(4);
    }

    /**
     * Setter for <code>testdb.subtask.priority</code>.
     */
    public void setPriority(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>testdb.subtask.priority</code>.
     */
    public String getPriority() {
        return (String) get(5);
    }

    /**
     * Setter for <code>testdb.subtask.start_date</code>.
     */
    public void setStartDate(LocalDateTime value) {
        set(6, value);
    }

    /**
     * Getter for <code>testdb.subtask.start_date</code>.
     */
    public LocalDateTime getStartDate() {
        return (LocalDateTime) get(6);
    }

    /**
     * Setter for <code>testdb.subtask.due_date</code>.
     */
    public void setDueDate(LocalDateTime value) {
        set(7, value);
    }

    /**
     * Getter for <code>testdb.subtask.due_date</code>.
     */
    public LocalDateTime getDueDate() {
        return (LocalDateTime) get(7);
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
     * Create a detached SubtaskRecord
     */
    public SubtaskRecord() {
        super(Subtask.SUBTASK);
    }

    /**
     * Create a detached, initialised SubtaskRecord
     */
    public SubtaskRecord(Integer id, Integer taskId, String title, String description, String status, String priority, LocalDateTime startDate, LocalDateTime dueDate) {
        super(Subtask.SUBTASK);

        setId(id);
        setTaskId(taskId);
        setTitle(title);
        setDescription(description);
        setStatus(status);
        setPriority(priority);
        setStartDate(startDate);
        setDueDate(dueDate);
        resetChangedOnNotNull();
    }
}
