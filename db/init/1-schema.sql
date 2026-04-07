-- NODES
CREATE TABLE IF NOT EXISTS nodes (
                                     id SERIAL PRIMARY KEY,
                                     node_name VARCHAR(50) UNIQUE NOT NULL,
    host VARCHAR(100),
    port INT
    );

-- NODE STATE
CREATE TABLE IF NOT EXISTS node_state (
                                          node_id INT PRIMARY KEY,
                                          current_term INT,
                                          role VARCHAR(20),
    FOREIGN KEY (node_id) REFERENCES nodes(id)
    );

-- LOGS
CREATE TABLE IF NOT EXISTS logs (
                                    id SERIAL PRIMARY KEY,
                                    node_id INT,
                                    term INT,
                                    log_index INT,
                                    command TEXT,
                                    is_committed BOOLEAN DEFAULT FALSE,
                                    FOREIGN KEY (node_id) REFERENCES nodes(id)
    );

-- TERMS
CREATE TABLE IF NOT EXISTS terms (
                                     id SERIAL PRIMARY KEY,
                                     term_number INT,
                                     leader_id INT REFERENCES nodes(id)
    );

-- PEERS
CREATE TABLE IF NOT EXISTS peers (
                                     node_id INT,
                                     peer_node_id INT,
                                     PRIMARY KEY (node_id, peer_node_id)
    );