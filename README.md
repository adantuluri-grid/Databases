## Query with SQL Domain

### Running the database

1. Setup environment variables

   ```cp .env.example .env```


2. Start the database

    ```docker compose down -v```
    
    ```docker compose up -d```


3. Verify container

   ```docker compose ps```


4. Connect to database
   - Host: localhost
   - Port: 5435
   - Database: consentra
   - Username: admin
   - Password: admin
   
### Database Initialization
   - SQL scripts are located in: ```db/init/```
   - Executed automatically on first container startup
   - Uses ```CREATE TABLE IF NOT EXISTS``` to avoid failures
   - Data is seeded using ```02-data.sql```

### Queries Implemented
1. CRUD Operations
   - Create, Read, Update, Delete operations on logs
2. Search Queries
   - Dynamic filtering
   - Pagination (LIMIT, OFFSET)
   - Sorting (ORDER BY)
3. Join Queries
   - Fetch logs along with node details
4. Statistics Queries
   - Number of logs per node
5. Top Queries
   - Node with the highest number of logs

### Example Query
```
    SELECT n.node_name,
      COUNT(l.id) AS log_count
    FROM nodes n
      JOIN logs l ON n.id = l.node_id
    GROUP BY n.node_name
    ORDER BY log_count DESC
      LIMIT 1;
```

### Reset Database
   If you need to re-run schema and data scripts:
```
   docker compose down -v
   docker compose up -d
```
