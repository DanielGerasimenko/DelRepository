--liquibase formatted sql

--changeset clondaic:1
CREATE TABLE IF NOT EXISTS person
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(64) NOT NULL UNIQUE ,
    birthdate DATE,
    firstname VARCHAR(64),
    lastname VARCHAR(64),
    "role" VARCHAR(32)
);

--changeset clondaic:2
CREATE TABLE orders
(
    id BIGSERIAL PRIMARY KEY,
    product VARCHAR(256) NOT NULL,
    status VARCHAR(64)
);
