--liquibase formatted sql

--changeset clondaic:4
CREATE TABLE IF NOT EXISTS revision
(
    id SERIAL PRIMARY KEY,
    "timestamp" BIGINT NOT NULL
);

--changeset clondaic:5
CREATE TABLE IF NOT EXISTS person_aud
(
    id BIGSERIAL,
    rev INT REFERENCES revision (id),
    revtype SMALLINT,
    username VARCHAR(64) NOT NULL UNIQUE,
    birthdate DATE,
    firstname VARCHAR(64),
    lastname VARCHAR(64),
    "role" VARCHAR(32)
);