CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS blog CASCADE;
DROP TABLE IF EXISTS post CASCADE;
DROP TABLE IF EXISTS comment CASCADE;
DROP TABLE IF EXISTS comment_like CASCADE;
DROP TABLE IF EXISTS file CASCADE;
DROP TABLE IF EXISTS post_files CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;

DROP TYPE IF EXISTS post_status_enum;

CREATE TYPE post_status_enum AS ENUM ('ACTIVE', 'NOT_ACTIVE');

CREATE TABLE IF NOT EXISTS blog (
    id SERIAL PRIMARY KEY NOT NULL,
    guid UUID DEFAULT uuid_generate_v4(),
    name VARCHAR(150),
    about TEXT,
    published_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY NOT NULL,
    blog_id INT,
    title VARCHAR(150),
    content TEXT,
    "user" INT,
    tags VARCHAR[],
    post_status post_status_enum DEFAULT 'ACTIVE'
);

CREATE TABLE IF NOT EXISTS comment (
    id SERIAL PRIMARY KEY NOT NULL,
    parent_id INT,
    content TEXT,
    post_id INT
);

CREATE TABLE IF NOT EXISTS "user" (
    id SERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(150),
    password VARCHAR(150),
    email VARCHAR(150),
    is_active BOOLEAN,
    activated_at TIMESTAMP DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);

CREAETE TABLE IF NOT EXISTS comment_like (
    id BIGSERIAL NOT NULL,
    comment_id INT8,
    post_id INT8,
    user_id INT8,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS file (
    id BIGSERIAL NOT NULL,
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS post_files (
    post_id INT8 NOT NULL,
    file_id INT8 NOT NULL,
    PRIMARY KEY (file_id, post_id)
);