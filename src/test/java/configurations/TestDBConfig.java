package configurations;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Configuration
public class TestDBConfig {

    @Container
    public static MySQLContainer<?> mySQL = new MySQLContainer<>("mysql:5.7")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("init/schema.sql");

    @Bean
    public DSLContext dslContext() {
        mySQL.start();
        return DSL.using(mySQL.getJdbcUrl(), mySQL.getUsername(), mySQL.getPassword());
    }
}
