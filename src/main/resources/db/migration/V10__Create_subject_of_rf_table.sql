CREATE TABLE IF NOT EXISTS subject_of_rf
(
    id             SERIAL         PRIMARY KEY,
    name_subject   VARCHAR(100)   NOT NULL UNIQUE,
    baserate_rtccf NUMERIC(10, 2) NOT NULL CHECK ( baserate_rtccf > 0 ),
    baserate_dcf   NUMERIC(10, 2) NOT NULL CHECK (baserate_dcf > 0)
);