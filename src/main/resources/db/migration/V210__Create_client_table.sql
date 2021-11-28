CREATE TABLE IF NOT EXISTS client
(
    id            BIGSERIAL PRIMARY KEY,
    email         VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    is_enabled    BOOLEAN      NOT NULL,
    is_not_locked BOOLEAN      NOT NULL
);