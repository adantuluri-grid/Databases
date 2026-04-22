-- =========================
-- NODES TABLE
-- =========================
CREATE TABLE IF NOT EXISTS nodes (
                                     id SERIAL PRIMARY KEY,
                                     node_name VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- =========================
-- LOGS TABLE (RAFT STYLE)
-- =========================
CREATE TABLE IF NOT EXISTS logs (
                                    id SERIAL PRIMARY KEY,
                                    node_id INT NOT NULL,
                                    term INT NOT NULL,
                                    log_index INT NOT NULL,
                                    command TEXT NOT NULL,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                    CONSTRAINT fk_node
                                    FOREIGN KEY (node_id)
    REFERENCES nodes(id)
    ON DELETE CASCADE,

    CONSTRAINT unique_log UNIQUE (node_id, log_index)
    );

-- =========================
-- INDEXES (IMPORTANT)
-- =========================
CREATE INDEX IF NOT EXISTS idx_logs_node_id ON logs(node_id);
CREATE INDEX IF NOT EXISTS idx_logs_term ON logs(term);
CREATE INDEX IF NOT EXISTS idx_logs_index ON logs(log_index);

-- =========================
-- SAMPLE DATA
-- =========================

-- Insert nodes
INSERT INTO nodes (node_name) VALUES ('Node-A') ON CONFLICT DO NOTHING;
INSERT INTO nodes (node_name) VALUES ('Node-B') ON CONFLICT DO NOTHING;
INSERT INTO nodes (node_name) VALUES ('Node-C') ON CONFLICT DO NOTHING;

-- Insert logs
INSERT INTO logs (node_id, term, log_index, command)
VALUES
    (1, 1, 1, 'SET x=10'),
    (1, 1, 2, 'SET y=20'),
    (2, 1, 1, 'SET x=5'),
    (3, 2, 1, 'SET z=30')
    ON CONFLICT DO NOTHING;