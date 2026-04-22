package dao;
import db.DatabaseUtil;

import java.util.*;

public class NodeDao {

    private final DatabaseUtil db;

    public NodeDao(DatabaseUtil db) {
        this.db = db;
    }

    public Map<String, List<String>> getNodesWithLogs() {
        List<Map.Entry<String, String>> rows = db.findMany(
                "SELECT n.node_name, l.command FROM nodes n LEFT JOIN logs l ON n.id = l.node_id",
                rs -> {
                    try {
                        return Map.entry(
                                rs.getString("node_name"),
                                rs.getString("command")
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        Map<String, List<String>> result = new HashMap<>();

        for (Map.Entry<String, String> row : rows) {
            result.computeIfAbsent(row.getKey(), k -> new ArrayList<>())
                    .add(row.getValue());
        }

        return result;
    }
}