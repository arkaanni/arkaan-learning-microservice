package dev.arkaan.schl.roomservice;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;

@Lazy
@Configuration
public class DBConfiguration {

    @Value("${datasource.jdbcUrl}")
    private String jdbcUrl;
    @Value("${datasource.driverClass}")
    private String driverClass;
    @Value("${datasource.username}")
    private String username;
    @Value("${datasource.password}")
    private String password;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        System.out.println(driverClass);
        hikariConfig.setDriverClassName(driverClass);
        return new JdbcTemplate(new HikariDataSource(hikariConfig));
    }
}

