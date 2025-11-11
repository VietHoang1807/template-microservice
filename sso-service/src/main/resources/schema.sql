CREATE TABLE
    IF NOT EXISTS users (
        id SERIAL PRIMARY KEY,
        username VARCHAR(200) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL,
        variable CHAR(1) NOT NULL -- Or consider using BOOLEAN
    );

CREATE TABLE
    IF NOT EXISTS detail_user (
        id SERIAL PRIMARY KEY,
        user_id INT REFERENCES users (id), -- Adjust data type if necessary
        firstname VARCHAR(100),
        lastname VARCHAR(100),
        available BOOLEAN NOT NULL DEFAULT FALSE,
        email VARCHAR(250) UNIQUE,
        crt_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        upd_at TIMESTAMP, -- Consider using a trigger or application logic to update
        version INT DEFAULT 0
    );

CREATE TABLE
    IF NOT EXISTS pre_password (
        id SERIAL PRIMARY KEY,
        user_id INT REFERENCES users (id), -- Adjust data type if necessary
        pre_password VARCHAR(255) NOT NULL
    );

CREATE TABLE
    IF NOT EXISTS roles (
        id UUID PRIMARY KEY,
        user_id INT REFERENCES users (id), -- Adjust data type if necessary
        role_name VARCHAR(50) NOT NULL
    );