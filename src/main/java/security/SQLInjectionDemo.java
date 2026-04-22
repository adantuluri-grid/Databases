package security;

import java.sql.*;

public class SQLInjectionDemo {

    private final String url = "jdbc:postgresql://localhost:5475/consentrajdbc";
    private final String user = "admin";
    private final String password = "admin";

    public void vulnerableLogin(String username) throws Exception {
        Connection conn = DriverManager.getConnection(url, user, password);

        Statement stmt = conn.createStatement();

        String query = "SELECT * FROM nodes WHERE node_name = '" + username + "'";
        System.out.println("Executing: " + query);

        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            System.out.println("Found: " + rs.getString("node_name"));
        }
    }

    public void safeLogin(String username) throws Exception {
        Connection conn = DriverManager.getConnection(url, user, password);

        PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM nodes WHERE node_name = ?"
        );

        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            System.out.println("Found: " + rs.getString("node_name"));
        }
    }
}