-- Store E-Commerce - PostgreSQL Database Initialization
-- Run as postgres or a superuser to create database and user.
-- Usage: psql -U postgres -f scripts/init-db.sql

-- Create database (skip if exists)
SELECT 'CREATE DATABASE store_db OWNER postgres'
WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'store_db')\gexec

-- Connect to store_db and create user/schema (run in separate session or adjust)
-- For local development, you can use:
-- CREATE USER store_user WITH PASSWORD 'store_password';
-- GRANT ALL PRIVILEGES ON DATABASE store_db TO store_user;
-- \c store_db
-- GRANT ALL ON SCHEMA public TO store_user;
-- GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO store_user;
-- GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO store_user;

-- Alternatively run from project root after creating DB manually:
-- createdb store_db
-- psql -U postgres -d store_db -f src/main/resources/db/migration/V1__create_schema.sql
