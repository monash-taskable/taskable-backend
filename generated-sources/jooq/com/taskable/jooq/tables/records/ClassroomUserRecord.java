/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables.records;


import com.taskable.jooq.tables.ClassroomUser;

import org.jooq.Record2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class ClassroomUserRecord extends UpdatableRecordImpl<ClassroomUserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>taskable.classroom_user.user_id</code>.
     */
    public void setUserId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>taskable.classroom_user.user_id</code>.
     */
    public Integer getUserId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>taskable.classroom_user.classroom_id</code>.
     */
    public void setClassroomId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>taskable.classroom_user.classroom_id</code>.
     */
    public Integer getClassroomId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>taskable.classroom_user.role</code>.
     */
    public void setRole(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>taskable.classroom_user.role</code>.
     */
    public String getRole() {
        return (String) get(2);
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
     * Create a detached ClassroomUserRecord
     */
    public ClassroomUserRecord() {
        super(ClassroomUser.CLASSROOM_USER);
    }

    /**
     * Create a detached, initialised ClassroomUserRecord
     */
    public ClassroomUserRecord(Integer userId, Integer classroomId, String role) {
        super(ClassroomUser.CLASSROOM_USER);

        setUserId(userId);
        setClassroomId(classroomId);
        setRole(role);
        resetChangedOnNotNull();
    }
}
