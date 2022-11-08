--liquibase formatted sql

--changeset clondaic:1
ALTER TABLE orders
    ADD COLUMN created_at TIMESTAMP;

ALTER TABLE orders
    ADD COLUMN modified_at TIMESTAMP;

ALTER TABLE orders
    ADD COLUMN created_by VARCHAR(32);

ALTER TABLE orders
    ADD COLUMN modified_by VARCHAR(32);

ALTER TABLE orders
ADD COLUMN address VARCHAR(256) ;

