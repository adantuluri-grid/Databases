# JDBC Relational Database Project


## Modules

### 1. Transactions / ACID Consistency

Shows inconsistent state without transactions and rollback protection with transactions.

### 2. Isolation Levels

Compares PostgreSQL default isolation level (READ COMMITTED) with SERIALIZABLE.

### 3. Indexing

Tests query performance with and without indexes using millions of rows.

### 4. Compound Indexes

Tests multi-column index behavior using full and partial column matches.



---

## Run Database

```bash id="3v5xpa"
docker compose up -d
```
## Full Execution Steps

### Step 1: Start PostgreSQL Database

From project root directory run:

```bash
docker compose up -d
```

#### This starts PostgreSQL with:

```
Database : jdbc_rdbms
Port     : 5485
User     : admin
Password : admin
```

### Step 2: Open DBeaver and Connect
Use these credentials:
```aiignore
Host     : localhost
Port     : 5485
Database : jdbc_rdbms
User     : admin
Password : admin
```

### Step 3: Create Tables and Seed Data
Open SQL Editor in DBeaver and run:
```aiignore
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
```

### Step 4: Verify Database Connection

Run:
`Main.java`

Expected Output:
`Database Connected: true`

### What It Demonstrates

#### Without Transaction

Money deducted from sender account but crash happens before receiver gets money.

#### With Transaction

Rollback restores balances.

### Expected Result
~~~
WITHOUT TRANSACTION
Alice loses money

WITH TRANSACTION
Balances restored
~~~

### Module 2: Isolation Levels
#### Execute:
`IsolationDemoMain.java`

#### What It Demonstrates

Two threads withdraw money simultaneously.

#### Default Isolation (READ COMMITTED)

Unsafe concurrent behavior possible.

#### SERIALIZABLE

One transaction is blocked or rolled back safely.

### Expected Output
DEFAULT LEVEL:
```
Both may succeed

SERIALIZABLE:
One succeeds, one fails safely
```

### Module 3: Indexing Performance Test

#### Add status column
Run in DBeaver:

```
ALTER TABLE transfers
ADD COLUMN IF NOT EXISTS status VARCHAR(20);
```

#### Insert Large Data
```
INSERT INTO transfers
(from_account_id, to_account_id, amount, created_at, status)

SELECT
(random() * 2 + 1)::BIGINT,
(random() * 2 + 1)::BIGINT,
(random() * 1000)::NUMERIC(10,2),
NOW() - (random() * interval '365 days'),
CASE
WHEN random() < 0.33 THEN 'PENDING'
WHEN random() < 0.66 THEN 'SUCCESS'
ELSE 'FAILED'
END
FROM generate_series(1, 2000000);
```

#### Query Without Index

```
EXPLAIN ANALYZE
SELECT *
FROM transfers
WHERE from_account_id = 1;
```

#### Create Index
```
CREATE INDEX idx_transfers_from_account
ON transfers(from_account_id);
```

#### Query With Index
```
EXPLAIN ANALYZE
SELECT *
FROM transfers
WHERE from_account_id = 1;
```

Compare execution time.

### Module 4: Compound Index
#### Create Compound Index
```aiignore

CREATE INDEX idx_transfers_status_created
ON transfers(status, created_at);
```

### Full Column Usage
```
EXPLAIN ANALYZE
SELECT *
FROM transfers
WHERE status = 'PENDING'
AND created_at > NOW() - interval '30 days';
```

#### Partial Leftmost Usage
```
EXPLAIN ANALYZE
SELECT *
FROM transfers
WHERE status = 'PENDING';
```
#### Partial Non-leftmost Usage
```
EXPLAIN ANALYZE
SELECT *
FROM transfers
WHERE created_at > NOW() - interval '30 days';
```
#### Stop Database
```
docker compose down
```


