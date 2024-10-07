/*
 * This file is generated by jOOQ.
 */
package com.taskable.jooq.tables.records;


import com.taskable.jooq.tables.User;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class UserRecord extends UpdatableRecordImpl<UserRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>testdb.user.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>testdb.user.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>testdb.user.sub</code>.
     */
    public void setSub(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>testdb.user.sub</code>.
     */
    public String getSub() {
        return (String) get(1);
    }

    /**
     * Setter for <code>testdb.user.username</code>.
     */
    public void setUsername(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>testdb.user.username</code>.
     */
    public String getUsername() {
        return (String) get(2);
    }

    /**
     * Setter for <code>testdb.user.email</code>.
     */
    public void setEmail(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>testdb.user.email</code>.
     */
    public String getEmail() {
        return (String) get(3);
    }

    /**
     * Setter for <code>testdb.user.first_name</code>.
     */
    public void setFirstName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>testdb.user.first_name</code>.
     */
    public String getFirstName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>testdb.user.last_name</code>.
     */
    public void setLastName(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>testdb.user.last_name</code>.
     */
    public String getLastName() {
        return (String) get(5);
    }

    /**
     * Setter for <code>testdb.user.status</code>.
     */
    public void setStatus(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>testdb.user.status</code>.
     */
    public String getStatus() {
        return (String) get(6);
    }

    /**
     * Setter for <code>testdb.user.language</code>.
     */
    public void setLanguage(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>testdb.user.language</code>.
     */
    public String getLanguage() {
        return (String) get(7);
    }

    /**
     * Setter for <code>testdb.user.colour</code>.
     */
    public void setColour(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>testdb.user.colour</code>.
     */
    public String getColour() {
        return (String) get(8);
    }

    /**
     * Setter for <code>testdb.user.theme</code>.
     */
    public void setTheme(String value) {
        set(9, value);
    }

    /**
     * Getter for <code>testdb.user.theme</code>.
     */
    public String getTheme() {
        return (String) get(9);
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
     * Create a detached UserRecord
     */
    public UserRecord() {
        super(User.USER);
    }

    /**
     * Create a detached, initialised UserRecord
     */
    public UserRecord(Integer id, String sub, String username, String email, String firstName, String lastName, String status, String language, String colour, String theme) {
        super(User.USER);

        setId(id);
        setSub(sub);
        setUsername(username);
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setStatus(status);
        setLanguage(language);
        setColour(colour);
        setTheme(theme);
        resetChangedOnNotNull();
    }
}
