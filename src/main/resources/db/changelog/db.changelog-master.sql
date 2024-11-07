--liquibase formatted sql

CREATE DATABASE ttm;

--changeset ttm:1

CREATE TABLE IF NOT EXISTS users (
    id varchar(36) default(gen_random_uuid()) primary key,
    firstname text,
    lastname text,
    email text,
    password text,
    description text,
    role varchar(10)
);
--rollback drop table users

CREATE TABLE IF NOT EXISTS toolbox (
    id varchar(36) default(gen_random_uuid()) primary key,
    document varchar(250)
);
--rollback drop table toolbox