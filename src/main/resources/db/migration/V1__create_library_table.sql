CREATE TABLE library (
    id        BIGSERIAL    PRIMARY KEY,
    title     VARCHAR(255),
    author    VARCHAR(255),
    isbn      BIGINT,
    available BOOLEAN      NOT NULL DEFAULT TRUE
);
