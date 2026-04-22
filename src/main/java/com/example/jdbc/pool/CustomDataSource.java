package com.example.jdbc.pool;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

public class CustomDataSource implements DataSource {

    private final Connection connection;

    public CustomDataSource() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5465/jdbcdb",
                "admin",
                "admin"
        );
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) {
        return connection;
    }

    @Override public PrintWriter getLogWriter(){ return null; }
    @Override public void setLogWriter(PrintWriter out){}
    @Override public void setLoginTimeout(int seconds){}
    @Override public int getLoginTimeout(){ return 0; }
    @Override public Logger getParentLogger(){ return null; }
    @Override public <T> T unwrap(Class<T> iface){ return null; }
    @Override public boolean isWrapperFor(Class<?> iface){ return false; }
}