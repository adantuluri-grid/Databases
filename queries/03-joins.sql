-- Logs with node details
SELECT l.id,
       l.command,
       l.term,
       n.node_name,
       n.host
FROM logs l
         JOIN nodes n ON l.node_id = n.id;