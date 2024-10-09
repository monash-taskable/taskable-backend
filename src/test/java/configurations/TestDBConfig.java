package configurations;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
public class TestDBConfig {

    // Define the MySQLContainer as a static field to ensure it's shared across all tests
    private static final MySQLContainer<?> mySQL;

    static {
        // Initialize and start the container
        mySQL = new MySQLContainer<>(DockerImageName.parse("mysql:5.7"))
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("init/schema.sql"); // Ensure this script is in src/test/resources/init/schema.sql
        mySQL.start();
    }

    @Bean
    public DataSource dataSource() {
        // Configure HikariCP with the container's JDBC URL and credentials
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(mySQL.getJdbcUrl());
        hikariConfig.setUsername(mySQL.getUsername());
        hikariConfig.setPassword(mySQL.getPassword());
        hikariConfig.setDriverClassName(mySQL.getDriverClassName());
        hikariConfig.setAutoCommit(false); // Ensure auto-commit is disabled for transaction management
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        // Wrap the DataSource with TransactionAwareDataSourceProxy to integrate with Spring's transactions
        return new TransactionAwareDataSourceProxy(hikariDataSource);
    }

    @Bean
    public DSLContext dslContext(DataSource dataSource) {
        // Configure jOOQ's DSLContext with the transaction-aware DataSource and SQL dialect
        return DSL.using(dataSource, SQLDialect.MYSQL);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        // Configure Spring's transaction manager
        return new DataSourceTransactionManager(dataSource);
    }
}
