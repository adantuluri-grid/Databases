### Local PostgreSQL Database Setup

This submodule provides a Docker Compose manifest to spin up a local PostgreSQL instance. It is configured to use environment variables for security and utilizes Docker Volumes to ensure data persists across container restarts.

### Prerequisites

1. Docker installed
2. Docker & Docker Compose installed.
3. A database GUI (e.g., DBeaver, TablePlus, or pgAdmin).

### Getting Started

1. Configure Environment Variables.
2. Create a .env file in the root of this submodule and define your custom credentials:

        POSTGRES_DB=mydatabase
        POSTGRES_USER=myuser
        POSTGRES_PASSWORD=mypassword
        DB_PORT=5433
        DB_CONTAINER_PORT=5432
        DB_VOLUME=postgres_data

3. Launch the Container. Run the following command to start the database in detached mode:

        docker-compose up -d
4. Verify Connection. Check that the container is up and running: 
   
        docker ps


You can now connect via localhost using your database client with the credentials defined in your .env file.

### Management Commands:

    Stop Containers - docker-compose stop
    Remove Containers - docker-compose down
    Wipe Data & Reset - docker-compose down -v
    View Logs - docker-compose logs -f