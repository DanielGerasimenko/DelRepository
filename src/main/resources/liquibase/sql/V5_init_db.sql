--liquibase formatted sql

--changeset clondaic:8
ALTER TABLE orders
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;

ALTER TABLE orders
    ADD COLUMN IF NOT EXISTS modified_at TIMESTAMP;

ALTER TABLE orders
    ADD COLUMN IF NOT EXISTS created_by VARCHAR(32);

ALTER TABLE orders
    ADD COLUMN IF NOT EXISTS modified_by VARCHAR(32);

ALTER TABLE orders
    ADD COLUMN IF NOT EXISTS address VARCHAR(256);

ALTER TABLE orders
    ADD COLUMN IF NOT EXISTS person_id BIGINT;

ALTER TABLE orders
    ADD CONSTRAINT person_id_fk FOREIGN KEY (person_id) REFERENCES person (id);
