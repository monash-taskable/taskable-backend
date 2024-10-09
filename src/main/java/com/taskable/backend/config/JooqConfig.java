//package com.taskable.backend.config;
//
//import org.jooq.DSLContext;
//import org.jooq.SQLDialect;
//import org.jooq.conf.MappedSchema;
//import org.jooq.conf.RenderMapping;
//import org.jooq.conf.Settings;
//import org.jooq.impl.DSL;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//@Configuration
//public class JooqConfig {
//
//    @Bean
//    public DSLContext dslContext(DataSource dataSource) {
//        // Define the schema mapping
//        Settings settings = new Settings()
//                .withRenderMapping(new RenderMapping()
//                        .withSchemata(
//                                new MappedSchema().withInput("testdb").withOutput("taskable")
//                        )
//                );
//        try (Connection connection = dataSource.getConnection()) {
//            System.out.println("Auto-commit is: " + connection.getAutoCommit());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // Create the DSLContext with the settings
//        return DSL.using(dataSource, SQLDialect.MYSQL, settings);
//    }
//}
//
