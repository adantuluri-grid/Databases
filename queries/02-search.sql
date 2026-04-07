-- Dynamic search
SELECT *
FROM logs
WHERE (node_id = COALESCE(NULL, node_id))
  AND (term = COALESCE(NULL, term))
ORDER BY log_index
    LIMIT 10 OFFSET 0;