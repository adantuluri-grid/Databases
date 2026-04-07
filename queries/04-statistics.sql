-- Logs per node
SELECT n.node_name,
       COUNT(l.id) AS total_logs
FROM nodes n
         LEFT JOIN logs l ON n.id = l.node_id
GROUP BY n.node_name;