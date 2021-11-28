CREATE TABLE IF NOT EXISTS coefficient_difficulty_treating
(
    id                        BIGSERIAL PRIMARY KEY,
    nomination_case_cdt_id    SERIAL REFERENCES case_cdt,
    name_subject              VARCHAR(100) REFERENCES subject_of_rf (name_subject),
    value_nomination_case_cdt REAL        NOT NULL CHECK ( value_nomination_case_cdt > 0 ),
    care_facility             VARCHAR(20) NOT NULL CHECK ( care_facility = 'st' OR care_facility = 'ds' )
);
