
CREATE TABLE IF NOT EXISTS cluster (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS node (
    id SERIAL PRIMARY KEY,
    node_name VARCHAR(100) NOT NULL,
    current_term INT DEFAULT 0,
    state VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS node_cluster (
    node_id INT,
    cluster_id INT,
    PRIMARY KEY (node_id, cluster_id),
    FOREIGN KEY (node_id) REFERENCES node(id) ON DELETE CASCADE,
    FOREIGN KEY (cluster_id) REFERENCES cluster(id) ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS command (
    id SERIAL PRIMARY KEY,
    command_text TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS log_entry (
    id SERIAL PRIMARY KEY,
    node_id INT NOT NULL,
    term INT NOT NULL,
    log_index INT NOT NULL,
    command_id INT,
    is_committed BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (node_id) REFERENCES node(id) ON DELETE CASCADE,
    FOREIGN KEY (command_id) REFERENCES command(id)
);

CREATE TABLE IF NOT EXISTS state_machine (
    id SERIAL PRIMARY KEY,
    node_id INT UNIQUE,
    last_applied_index INT DEFAULT 0,

    FOREIGN KEY (node_id) REFERENCES node(id) ON DELETE CASCADE
);