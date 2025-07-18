--liquibase formatted sql

--changeset author:01-create-tables.sql

CREATE TABLE users (
    id    bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name  text                                    NOT NULL,
    email text                                    NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE request (
    id           bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    description  text                                    NOT NULL,
    requester_id bigint                                  NOT NULL,
    created      timestamp                               NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (requester_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE item (
    id           bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name         text                                    NOT NULL,
    description  text                                    NOT NULL,
    is_available boolean                                 NOT NULL,
    owner_id     bigint                                  NOT NULL,
    request_id   bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (request_id) REFERENCES request (id) ON DELETE CASCADE
);

CREATE TABLE booking (
    id         bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    start_date timestamp                               NOT NULL,
    end_date   timestamp                               NOT NULL,
    item_id    bigint                                  NOT NULL,
    booker_id  bigint                                  NOT NULL,
    status     text                                    NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE CASCADE,
    FOREIGN KEY (booker_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE comment (
    id        bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text      text                                    NOT NULL,
    item_id   bigint                                  NOT NULL,
    author_id bigint                                  NOT NULL,
    created   timestamp                               NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES users (id) ON DELETE CASCADE
);

--rollback DROP TABLE comment;
--rollback DROP TABLE booking;
--rollback DROP TABLE item;
--rollback DROP TABLE request;
--rollback DROP TABLE users;
