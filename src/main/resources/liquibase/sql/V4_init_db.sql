--liquibase formatted sql

--changeset clondaic:1
ALTER TABLE person
    ADD COLUMN password VARCHAR(128) DEFAULT '{noop}123';

--changeset clondaic:2
ALTER TABLE person_aud
    ADD COLUMN password VARCHAR(128);