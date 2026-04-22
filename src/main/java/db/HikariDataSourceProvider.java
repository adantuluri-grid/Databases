package db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class HikariDataSourceProvider {

    public static DataSource create() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:postgresql://localhost:5475/consentrajdbc");
        config.setUsername("admin");
        config.setPassword("admin");

        config.setMaximumPoolSize(5);

        return new HikariDataSource(config);
    }
}