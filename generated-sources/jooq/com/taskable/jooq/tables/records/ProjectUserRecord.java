/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables.records;


import com.taskable.jooq.tables.ProjectUser;

import org.jooq.Record2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class ProjectUserRecord extends UpdatableRecordImpl<ProjectUserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>taskable.project_user.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>taskable.project_user.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>taskable.project_user.project_id</code>.
     */
    public void setProjectId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>taskable.project_user.project_id</code>.
     */
    public Integer getProjectId() {
        return (Integer) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ProjectUserRecord
     */
    public ProjectUserRecord() {
        super(ProjectUser.PROJECT_USER);
    }

    /**
     * Create a detached, initialised ProjectUserRecord
     */
    public ProjectUserRecord(Integer userId, Integer projectId) {
        super(ProjectUser.PROJECT_USER);

        setUserId(userId);
        setProjectId(projectId);
        resetChangedOnNotNull();
    }
}
