DROP TABLE IF EXISTS transfers;
DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
                          id BIGSERIAL PRIMARY KEY,
                          owner_name VARCHAR(100) NOT NULL,
                          balance NUMERIC(15,2) NOT NULL
);

CREATE TABLE transfers (
                           id BIGSERIAL PRIMARY KEY,
                           from_account_id BIGINT REFERENCES accounts(id),
                           to_account_id BIGINT REFERENCES accounts(id),
                           amount NUMERIC(15,2) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO accounts(owner_name, balance)
VALUES
    ('Alice', 1000.00),
    ('Bob', 500.00),
    ('Charlie', 700.00);