--liquibase formatted sql

--changeset ttm:2

ALTER TABLE users DROP COLUMN role;

CREATE TABLE IF NOT EXISTS roles (
    id varchar(36) default(gen_random_uuid()) primary key,
    name varchar(40)
);
--rollback drop table role

CREATE TABLE IF NOT EXISTS users_roles (
    users_id varchar(36),
    roles_id varchar(36),
    PRIMARY KEY (users_id, roles_id),
    CONSTRAINT fk_users_id FOREIGN KEY (users_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_roles_id FOREIGN KEY (roles_id) REFERENCES roles(id) ON DELETE CASCADE
);
--rollback drop table users_role