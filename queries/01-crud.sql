-- CREATE
INSERT INTO logs (node_id, term, log_index, command)
VALUES (1, 2, 3, 'SET z=30');

-- READ
SELECT * FROM logs WHERE node_id = 1;

-- UPDATE
UPDATE logs SET is_committed = TRUE WHERE id = 1;

-- DELETE
DELETE FROM logs WHERE is_committed = FALSE;