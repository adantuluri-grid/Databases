package dao;

import db.DatabaseUtil;

import java.util.List;

public class LogDao {

    private final DatabaseUtil db;

    public LogDao(DatabaseUtil db) {
        this.db = db;
    }

    public void insert(int nodeId, int term, int index, String command) {
        db.execute(
                "INSERT INTO logs (node_id, term, log_index, command) VALUES (?, ?, ?, ?)",
                nodeId, term, index, command
        );
    }

    public List<String> findByNode(int nodeId) {
        return db.findMany(
                "SELECT command FROM logs WHERE node_id = ? ORDER BY log_index",
                rs -> {
                    try {
                        return rs.getString("command");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                nodeId
        );
    }

    public int nextIndex(int nodeId) {
        Integer max = db.findOne(
                "SELECT COALESCE(MAX(log_index),0) + 1 AS next_idx FROM logs WHERE node_id = ?",
                rs -> {
                    try {
                        return rs.getInt("next_idx");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                },
                nodeId
        );

        return max;
    }
}