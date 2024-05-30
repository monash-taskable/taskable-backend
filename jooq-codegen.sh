#!/bin/bash

docker-compose up -d

# Wait for MySQL to be ready (might need to adjust sleep time)
echo "Waiting for MySQL to be ready..."
sleep 7

# connecting to MySql to test db command (testpass):
# mysql -h 127.0.0.1 -P 3306 -u testuser -p -D testdb
# use testdb
# show tables;

# Run jOOQ code generation
mvn clean generate-sources

# Stop Docker Compose (without removing volumes)
docker-compose down