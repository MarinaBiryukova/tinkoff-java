--liquibase formatted sql

--changeset MarinaBiryukova:1

CREATE TABLE chat (
    id SERIAL PRIMARY KEY NOT NULL,
    tg_chat_id INTEGER NOT NULL
);

CREATE TABLE link (
    id SERIAL PRIMARY KEY NOT NULL,
    link TEXT NOT NULL
);

CREATE TABLE chat_link (
    id SERIAL PRIMARY KEY NOT NULL,
    chat_id INTEGER,
    link_id INTEGER,
    FOREIGN KEY (chat_id) REFERENCES chat(id),
    FOREIGN KEY (link_id) REFERENCES link(id)
)