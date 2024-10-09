package com.taskable.backend;

//import com.taskable.backend.config.JooqConfig;
import com.taskable.jooq.tables.records.UserRecord;
import configurations.TestDBConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static com.taskable.jooq.tables.User.USER;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Import(TestDBConfig.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class DatabaseTest {

    @Autowired
    private DSLContext dslContext;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTest.class);

    @BeforeEach
    public void setupTests(){
        dslContext.insertInto(USER)
            .set(USER.FIRST_NAME, "John")
            .set(USER.SUB, "test sub")
            .set(USER.EMAIL, "test email")
            .execute();
    }

    @Test
    public void testUserTableNotEmpty() {
        var results = dslContext.select().from(USER).fetch();
        assertFalse(results.isEmpty(), "Users table should not be empty");
        logger.info(results.format());
    }

    @Test
    public void testName() {
        var userRec = dslContext.select().from(USER).limit(1).fetchOneInto(UserRecord.class);
        var name = userRec.getFirstName();
        assertEquals("John", name, "Name isn't John");
        logger.info(userRec.format());
    }
}
