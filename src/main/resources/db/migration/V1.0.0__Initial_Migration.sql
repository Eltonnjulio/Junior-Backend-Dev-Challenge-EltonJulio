CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(256) ,
    path VARCHAR(256) ,
    username VARCHAR(256) NOT NULL,
    email VARCHAR(256) NOT NULL UNIQUE,
    role VARCHAR(32) NOT NULL,
    user_status VARCHAR(256) DEFAULT 'ACTIVE' NOT NULL,
    deleted_at TIMESTAMP NULL,
    password VARCHAR(256) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    UNIQUE (username, email)
);

CREATE TABLE albums (
    id SERIAL PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    images TEXT NOT NULL,
    created_by_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL
);

CREATE TABLE comments (
    id SERIAL PRIMARY KEY,
    created_by_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    comment VARCHAR(256) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL
);

CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    title VARCHAR(256) NOT NULL,
    cover_picture VARCHAR(256) NOT NULL,
    uuid VARCHAR(256) NOT NULL,
    images TEXT NOT NULL,
    description TEXT NOT NULL,
    created_by_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL
);
