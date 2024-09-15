/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables.records;


import com.taskable.jooq.tables.Classroom;

import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class ClassroomRecord extends UpdatableRecordImpl<ClassroomRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>testdb.classroom.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>testdb.classroom.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>testdb.classroom.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>testdb.classroom.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>testdb.classroom.description</code>.
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>testdb.classroom.description</code>.
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>testdb.classroom.created_at</code>.
     */
    public void setCreatedAt(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>testdb.classroom.created_at</code>.
     */
    public LocalDateTime getCreatedAt() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>testdb.classroom.archived</code>.
     */
    public void setArchived(Byte value) {
        set(4, value);
    }

    /**
     * Getter for <code>testdb.classroom.archived</code>.
     */
    public Byte getArchived() {
        return (Byte) get(4);
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
     * Create a detached ClassroomRecord
     */
    public ClassroomRecord() {
        super(Classroom.CLASSROOM);
    }

    /**
     * Create a detached, initialised ClassroomRecord
     */
    public ClassroomRecord(Integer id, String name, String description, LocalDateTime createdAt, Byte archived) {
        super(Classroom.CLASSROOM);

        setId(id);
        setName(name);
        setDescription(description);
        setCreatedAt(createdAt);
        setArchived(archived);
        resetChangedOnNotNull();
    }
}