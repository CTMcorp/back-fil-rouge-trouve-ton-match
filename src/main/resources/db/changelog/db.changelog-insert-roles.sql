--liquibase formatted sql

--changeset ttm:3

INSERT INTO roles(name) VALUES ('ADMINISTRATEUR'),
                               ('PARRAIN'),
                               ('PORTEUR');