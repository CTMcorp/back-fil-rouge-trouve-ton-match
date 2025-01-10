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
    role varchar(14),
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
    name text,
    id_number INT UNIQUE
);
--rollback drop table secteurs

CREATE TABLE IF NOT EXISTS types (
    id varchar(36) default(gen_random_uuid()) primary key,
    name text,
    id_number INT UNIQUE
);
--rollback drop table types

CREATE TABLE IF NOT EXISTS users_secteurs (
    users_id varchar(36),
    secteurs_id_number INT,
    PRIMARY KEY (users_id, secteurs_id_number),
    CONSTRAINT fk_users_id FOREIGN KEY (users_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_secteurs_id_number FOREIGN KEY (secteurs_id_number) REFERENCES secteurs(id_number) ON DELETE CASCADE
);
--rollback drop table user_secteurs

CREATE TABLE IF NOT EXISTS users_types (
    users_id varchar(36),
    types_id_number INT,
    PRIMARY KEY (users_id, types_id_number),
    CONSTRAINT fk_users_id FOREIGN KEY (users_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_types_id_number FOREIGN KEY (types_id_number) REFERENCES types(id_number) ON DELETE CASCADE
);
--rollback drop table user_types

INSERT INTO secteurs(name, id_number) VALUES('Services administratifs et soutien', 1),
                                 ('Activités spécialisées, techniques et scientifiques', 2),
                                 ('Agriculture, sylviculture et pêche', 3),
                                 ('Arts, spectacles et activités récréatives', 4),
                                 ('Commerce et réparation', 5),
                                 ('Contruction_BTP', 6),
                                 ('Enseignement', 7),
                                 ('Hôtels, cafés et restaurants', 8),
                                 ('Industrie', 9),
                                 ('Information et communication', 10),
                                 ('Eau, assainissement, gestion des déchets et dépollution', 11),
                                 ('Electricité, gaz, vapeur d''air conditionné', 12),
                                 ('Santé humaine et action sociale', 13),
                                 ('Services aux entreprises', 14),
                                 ('Services aux particuliers', 15),
                                 ('Transport', 16);

INSERT INTO types(name, id_number) VALUES ('Ressources humaines', 1),
                               ('Finance et comptabilité', 2),
                               ('Juridique', 3),
                               ('Informatique', 4),
                               ('Commercial et communication', 5);

