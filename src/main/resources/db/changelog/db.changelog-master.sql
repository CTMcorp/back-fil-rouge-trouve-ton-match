--liquibase formatted sql

CREATE DATABASE ttm;

--changeset ttm:1

CREATE TABLE IF NOT EXISTS roles (
    id varchar(36) default(gen_random_uuid()) primary key,
    name varchar(10)
);
--rollback drop table roles

CREATE TABLE IF NOT EXISTS users (
    id varchar(36) default(gen_random_uuid()) primary key,
    firstname text,
    lastname text,
    description text,
    roles_id varchar(36),
    CONSTRAINT fk_roles FOREIGN KEY(roles_id) REFERENCES roles(id)
);
--rollback drop table users

CREATE TABLE IF NOT EXISTS toolbox (
    id varchar(36) default(gen_random_uuid()) primary key,
    document varchar(250)
);
--rollback drop table toolbox