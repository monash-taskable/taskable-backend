package com.taskable.backend;
import com.taskable.jooq.tables.records.UserRecord;
import com.taskable.jooq.tables.User;
import configurations.TestDBConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.jooq.DSLContext;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestDBConfig.class)
class DatabaseTest {

    @Autowired
    private DSLContext dslContext;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseTest.class);

    @Test
    public void testUserTableNotEmpty() {
        var results = dslContext.select().from(User.USER).fetch();
        assertFalse(results.isEmpty(), "Users table should not be empty");
        logger.info(results.format());
    }

    @Test
    public void testName() {
        var userRec = dslContext.select().from(User.USER).limit(1).fetchOneInto(UserRecord.class);
        var name = userRec.getFirstName();
        assertEquals(name, "john", "Name isn't john");
        logger.info(userRec.format());
    }
}
