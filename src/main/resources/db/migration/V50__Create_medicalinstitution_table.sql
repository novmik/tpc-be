CREATE TABLE IF NOT EXISTS medical_institution
(
    id                          BIGSERIAL PRIMARY KEY,
    name_mi                     VARCHAR(255) NOT NULL,
    differentiation_coefficient REAL,
    level_st                    VARCHAR(10),
    coefficient_level_st        REAL,
    level_ds                    VARCHAR(10),
    coefficient_level_ds        REAL,
    name_subject                VARCHAR(100) REFERENCES subject_of_rf (name_subject),
    type_medical_institution    INTEGER DEFAULT 0 REFERENCES type_medical_institution (type_mi)
);