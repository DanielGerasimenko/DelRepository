--liquibase formatted sql

--changeset clondaic:1
ALTER TABLE person
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE person
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE person
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE person
    ADD COLUMN modified_by VARCHAR(32);