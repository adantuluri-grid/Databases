package db;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class DatabaseUtil {

    private final DataSource dataSource;

    public DatabaseUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void execute(String query, Object... args) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            setParams(stmt, args);

            boolean hasResult = stmt.execute();

            if (hasResult) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(String query, Consumer<PreparedStatement> consumer) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            consumer.accept(stmt);
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T findOne(String query, Function<ResultSet, T> mapper, Object... args) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            setParams(stmt, args);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            T result = mapper.apply(rs);

            if (rs.next()) {
                throw new RuntimeException("Expected one result but found multiple");
            }

            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> findMany(String query, Function<ResultSet, T> mapper, Object... args) {
        List<T> results = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            setParams(stmt, args);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                results.add(mapper.apply(rs));
            }

            return results;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setParams(PreparedStatement stmt, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
    }
}