--liquibase formatted sql

--changeset clondaic:6
ALTER TABLE person
    ADD COLUMN password VARCHAR(128) DEFAULT '{noop}123';

--changeset clondaic:7
ALTER TABLE person_aud
    ADD COLUMN password VARCHAR(128);