CREATE TABLE IF NOT EXISTS subject_of_rf
(
    id             SERIAL         PRIMARY KEY,
    name_subject   VARCHAR(100)   NOT NULL UNIQUE,
    baserate_st NUMERIC(10, 2) NOT NULL CHECK ( baserate_st > 0 ),
    baserate_ds   NUMERIC(10, 2) NOT NULL CHECK (baserate_ds > 0)
);