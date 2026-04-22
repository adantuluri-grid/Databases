package db;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.*;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUtilTest {

    static DatabaseUtil db;

    @BeforeAll
    static void setup() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("");

        db = new DatabaseUtil(ds);

        db.execute("""
            CREATE TABLE logs (
                id INT AUTO_INCREMENT PRIMARY KEY,
                node_id INT,
                term INT,
                log_index INT,
                command VARCHAR(50)
            )
        """);

        db.execute("INSERT INTO logs (node_id, term, log_index, command) VALUES (1, 1, 1, 'SET x=10')");
        db.execute("INSERT INTO logs (node_id, term, log_index, command) VALUES (1, 1, 2, 'SET y=20')");
        db.execute("INSERT INTO logs (node_id, term, log_index, command) VALUES (2, 1, 1, 'SET z=30')");
    }

    @Test
    void testFindMany() {
        List<String> logs = db.findMany(
                "SELECT command FROM logs WHERE node_id = ? ORDER BY log_index",
                rs -> {
                    try {
                        return rs.getString("command");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                1
        );

        assertEquals(2, logs.size());
        assertEquals("SET x=10", logs.get(0));
        assertEquals("SET y=20", logs.get(1));
    }

    @Test
    void testFindOneSuccess() {
        String log = db.findOne(
                "SELECT command FROM logs WHERE node_id = ? AND log_index = ?",
                rs -> {
                    try {
                        return rs.getString("command");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                1, 1
        );

        assertEquals("SET x=10", log);
    }

    @Test
    void testFindOneNoResult() {
        String log = db.findOne(
                "SELECT command FROM logs WHERE node_id = ? AND log_index = ?",
                rs -> {
                    try {
                        return rs.getString("command");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                99, 99
        );

        assertNull(log);
    }

    @Test
    void testFindOneMultipleResults() {
        assertThrows(RuntimeException.class, () -> {
            db.findOne(
                    "SELECT command FROM logs WHERE node_id = ?",
                    rs -> {
                        try {
                            return rs.getString("command");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    },
                    1
            );
        });
    }

    @Test
    void testExecuteInsert() {
        db.execute("INSERT INTO logs (node_id, term, log_index, command) VALUES (?, ?, ?, ?)",
                3, 1, 1, "SET a=100"
        );

        List<String> logs = db.findMany(
                "SELECT command FROM logs WHERE node_id = ?",
                rs -> {
                    try {
                        return rs.getString("command");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                3
        );

        assertEquals(1, logs.size());
        assertEquals("SET a=100", logs.get(0));
    }
}