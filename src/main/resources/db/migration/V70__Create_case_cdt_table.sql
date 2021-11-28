CREATE TABLE IF NOT EXISTS case_cdt
(
    id        SERIAL PRIMARY KEY,
    nomination_case_cdt VARCHAR(400) NOT NULL UNIQUE
);
