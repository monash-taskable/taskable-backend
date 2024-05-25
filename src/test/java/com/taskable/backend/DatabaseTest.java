package com.taskable.backend;
import org.jooq.Result;
import org.jooq.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MySQLContainer;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import static org.junit.jupiter.api.Assertions.assertFalse;

@Testcontainers
class DatabaseTest {

    @Container
    public static MySQLContainer<?> mySQL = new MySQLContainer<>("mysql:5.7")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("init/schema.sql");

    private DSLContext create;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTest.class);

    @BeforeEach
    public void setup() {
        create = DSL.using(mySQL.getJdbcUrl(), mySQL.getUsername(), mySQL.getPassword());
    }

    @Test
    public void testUserTableNotEmpty() {
        Result<Record> results = create.select().from("Users").fetch();
        assertFalse(results.isEmpty(), "Users table should not be empty");
        logger.info(results.format());
    }
}
