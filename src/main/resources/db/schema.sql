CREATE DATABASE IF NOT EXISTS melody_flow;

USE melody_flow;
    SET default_storage_engine = InnoDB;

    -- MelodyFlow database schema
-- This file documents the tables needed by the application.

    -- artists table
CREATE TABLE IF NOT EXISTS artists (
    id BIGINT NOT NULL AUTO_INCREMENT,
    external_id VARCHAR(100) DEFAULT NULL,
    image_url VARCHAR(500) DEFAULT NULL,
    name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_artists_external_id (external_id)
    );

--venue table
CREATE TABLE IF NOT EXISTS venues (
    id BIGINT NOT NULL AUTO_INCREMENT,
    address VARCHAR(255) DEFAULT NULL,
    city VARCHAR(100) NOT NULL,
    external_id VARCHAR(100) DEFAULT NULL,
    name VARCHAR(150) NOT NULL,
    state VARCHAR(50) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_venues_external_id (external_id)
    );

--Concert table
CREATE TABLE IF NOT EXISTS concerts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    event_date DATETIME(6) NOT NULL,
    external_id VARCHAR(100) NOT NULL,
    name VARCHAR(200) NOT NULL,
    ticket_url VARCHAR(500) DEFAULT NULL,
    artist_id BIGINT NOT NULL,
    venue_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_concerts_external_id (external_id),
    KEY idx_concerts_artist_id (artist_id),
    KEY idx_concerts_venue_id (venue_id),
    CONSTRAINT fk_concerts_artist
    FOREIGN KEY (artist_id) REFERENCES artists(id),
    CONSTRAINT fk_concerts_venue
    FOREIGN KEY (venue_id) REFERENCES venues(id)
    );


-- roles tables
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_roles_name (name)
    );

--users table

CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    email VARCHAR(100) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_email (email),
    UNIQUE KEY uk_users_username (username)
    );

--user roles table

CREATE TABLE IF NOT EXISTS user_roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_roles_user_role (user_id, role_id),
    KEY idx_user_roles_role_id (role_id),
    CONSTRAINT fk_user_roles_role
    FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT fk_user_roles_user
    FOREIGN KEY (user_id) REFERENCES users(id)
    );


-- saved concerts table

CREATE TABLE IF NOT EXISTS saved_concerts (
    id BIGINT NOT NULL AUTO_INCREMENT,
    attended_at DATETIME(6) DEFAULT NULL,
    saved_at DATETIME(6) NOT NULL,
    status VARCHAR(20) NOT NULL,
    concert_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_saved_concerts_user_concert (user_id, concert_id),
    KEY idx_saved_concerts_concert_id (concert_id),
    CONSTRAINT fk_saved_concerts_user
    FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_saved_concerts_concert
    FOREIGN KEY (concert_id) REFERENCES concerts(id)
    );


 --concert plans table

CREATE TABLE IF NOT EXISTS concert_plans (
    id BIGINT NOT NULL AUTO_INCREMENT,
    budget DECIMAL(10,2) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    saved_concert_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_concert_plans_saved_concert (saved_concert_id),
    CONSTRAINT fk_concert_plans_saved_concert
    FOREIGN KEY (saved_concert_id) REFERENCES saved_concerts(id)
    );




-- expenses table

CREATE TABLE IF NOT EXISTS expenses (
    id BIGINT NOT NULL AUTO_INCREMENT,
    amount DECIMAL(10,2) NOT NULL,
    category VARCHAR(30) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    description VARCHAR(255) DEFAULT NULL,
    expense_date DATE DEFAULT NULL,
    concert_plan_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    KEY idx_expenses_concert_plan_id (concert_plan_id),
    CONSTRAINT fk_expenses_concert_plan
    FOREIGN KEY (concert_plan_id) REFERENCES concert_plans(id)
    );
