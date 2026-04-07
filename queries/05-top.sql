-- Node with most logs
SELECT n.node_name,
       COUNT(l.id) AS log_count
FROM nodes n
         JOIN logs l ON n.id = l.node_id
GROUP BY n.node_name
ORDER BY log_count DESC
    LIMIT 1;