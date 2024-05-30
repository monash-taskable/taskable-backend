/*
 * This file is generated by jOOQ.
 */
package com.taskable.backend.jooq;


import com.taskable.backend.jooq.tables.Users;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Testdb extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>testdb</code>
     */
    public static final Testdb TESTDB = new Testdb();

    /**
     * The table <code>testdb.Users</code>.
     */
    public final Users USERS = Users.USERS;

    /**
     * No further instances allowed
     */
    private Testdb() {
        super("testdb", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Users.USERS
        );
    }
}
