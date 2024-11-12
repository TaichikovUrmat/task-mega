CREATE TABLE task
(
    id          BIGINT       NOT NULL,
    created_at  BIGINT       NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    completed   BOOLEAN      NOT NULL,
    CONSTRAINT pk_task PRIMARY KEY (id)
);