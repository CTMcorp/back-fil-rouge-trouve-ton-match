--liquibase formatted sql

--changeset ttm:3

INSERT INTO roles(name) VALUES ('Administrateur'),
                               ('Parrain'),
                               ('Porteur');