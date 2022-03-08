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

CREATE TABLE Employees (

    id SERIAL PRIMARY KEY,
    name VARCHAR(256) NOT NULL,
    surname VARCHAR(256) NOT NULL,
    marital_status VARCHAR(256) NOT NULL,
    identification_doc VARCHAR(256) NOT NULL,
    identification_doc_num VARCHAR(256) NOT NULL,
    nuit VARCHAR(256) NOT NULL,
    date_of_birth DATE NOT NULL,
    address TEXT NOT NULL,
    contacts TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    deleted_at TIMESTAMP NULL,

    UNIQUE (nuit, identification_doc_num)
);


