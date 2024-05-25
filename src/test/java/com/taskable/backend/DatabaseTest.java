package com.taskable.backend;
import configurations.TestDBConfig;
import org.jooq.Result;
import org.jooq.Record;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.MySQLContainer;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDBConfig.class)
class DatabaseTest {

    @Autowired
    private DSLContext dslContext;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTest.class);

    @Test
    public void testUserTableNotEmpty() {
        Result<Record> results = dslContext.select().from("Users").fetch();
        assertFalse(results.isEmpty(), "Users table should not be empty");
        logger.info(results.format());
    }
}
