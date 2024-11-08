--liquibase formatted sql

CREATE DATABASE ttm;

--changeset ttm:1

CREATE TABLE IF NOT EXISTS users (
    id varchar(36) default(gen_random_uuid()) primary key,
    firstname text,
    lastname text,
    email text UNIQUE,
    password text,
    description text,
    role varchar(10),
    photo text
);
--rollback drop table users

CREATE TABLE IF NOT EXISTS toolbox (
    id varchar(36) default(gen_random_uuid()) primary key,
    document varchar(250)
);
--rollback drop table toolbox

CREATE TABLE IF NOT EXISTS secteurs (
    id varchar(36) default(gen_random_uuid()) primary key,
    name text
);
--rollback drop table secteurs

CREATE TABLE IF NOT EXISTS types (
    id varchar(36) default(gen_random_uuid()) primary key,
    name text
);
--rollback drop table types

CREATE TABLE IF NOT EXISTS users_secteurs (
    users_id varchar(36),
    secteurs_id varchar(36),
    PRIMARY KEY (users_id, secteurs_id),
    CONSTRAINT fk_users_id FOREIGN KEY (users_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_secteurs_id FOREIGN KEY (secteurs_id) REFERENCES secteurs(id) ON DELETE CASCADE
);
--rollback drop table user_secteurs

CREATE TABLE IF NOT EXISTS users_types (
    users_id varchar(36),
    types_id varchar(36),
    PRIMARY KEY (users_id, types_id),
    CONSTRAINT fk_users_id FOREIGN KEY (users_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_types_id FOREIGN KEY (types_id) REFERENCES types(id) ON DELETE CASCADE
);
--rollback drop table user_types

INSERT INTO secteurs(name) VALUES('Services administratifs et soutien'),
                                 ('Activités spécialisées, techniques et scientifiques'),
                                 ('Agriculture, sylviculture et pêche'),
                                 ('Arts, spectacles et activités récréatives'),
                                 ('Commerce et réparation'),
                                 ('Contruction_BTP'),
                                 ('Enseignement'),
                                 ('Hôtels, cafés et restaurant'),
                                 ('Industrie'),
                                 ('Information et communication'),
                                 ('Eau, assainissement, gestion des déchets et dépollution'),
                                 ('Electricité, gaz, vapeur d''air conditionné'),
                                 ('Santé humaine et action sociale'),
                                 ('Services aux entreprises'),
                                 ('Services aux particuliers'),
                                 ('Transport');

INSERT INTO types(name) VALUES ('Ressources humaines'),
                               ('Finance et comptabilité'),
                               ('Juridique'),
                               ('Informatique'),
                               ('Commercial et communication');

