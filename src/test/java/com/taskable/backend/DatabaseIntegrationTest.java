package com.taskable.backend;

import com.taskable.jooq.tables.User;
import com.taskable.jooq.tables.records.UserRecord;
import configurations.TestDBConfig;
import org.jooq.DSLContext;
import org.jooq.impl.DefaultConnectionProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Import(TestDBConfig.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class DatabaseIntegrationTest {
    @Autowired
    private DSLContext testDslContext;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseIntegrationTest.class);

    @Test
    public void testConnection() throws Exception {
        Connection connection = testDslContext.configuration().connectionProvider().acquire();
        assertNotNull(connection);
        System.out.println("Connected to database: " + connection.getMetaData().getURL());
    }
    @Test
    public void testInsertAndSelect() throws Exception {
        Connection connection = testDslContext.configuration().connectionProvider().acquire();
        assertNotNull(connection);
        logger.info("Connected to database: " + connection.getMetaData().getURL());

        UserRecord userRecord = testDslContext.newRecord(User.USER);
        userRecord.setSub("101");
        userRecord.setUsername("testuser");
        userRecord.setFirstName("john");
        userRecord.setLastName("pork");
        userRecord.setEmail("testemail@example.com");
        userRecord.setTheme("dark");
        userRecord.store();

        UserRecord rec = testDslContext.select().from(User.USER).where(User.USER.SUB.eq(("101"))).fetchOneInto(UserRecord.class);
        assertNotNull(rec);
        assertEquals(rec.getFirstName(), "john", "name is not john");
    }
}
