#!/bin/bash

docker compose up -d

# Wait for MySQL to be ready (might need to adjust sleep time)
echo "Waiting for MySQL to be ready..."
sleep 10

# connecting to MySql to test db command (testpass):
# mysql -h 127.0.0.1 -P 3306 -u testuser -p -D testdb
# use testdb
# show tables;

# to check if test database initialised with correct schema
# docker compose up -d
# docker compose ps (for checking its up)
# docker compose logs mysql (check mysql related)

# ENSURE schema.sql or any .sql IS A FILE
# 'file schema.sql'

# Run jOOQ code generation
mvn clean generate-sources

# Stop Docker Compose (removes volumes so db is fresh)
docker compose down -v

