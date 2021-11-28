CREATE TABLE IF NOT EXISTS diagnosis_related_groups
(
    id                      BIGSERIAL PRIMARY KEY,
    number_drg              VARCHAR(50)  NOT NULL UNIQUE,
    nomination_drg          VARCHAR(255) NOT NULL,
    rate_relative_intensity REAL         NOT NULL CHECK ( rate_relative_intensity > 0 ),
    wage_share              REAL         NOT NULL CHECK ( wage_share > 0 )
);