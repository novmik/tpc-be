CREATE TABLE IF NOT EXISTS medical_institution
(
    id                          BIGSERIAL PRIMARY KEY,
    name_mi                     VARCHAR(255) NOT NULL,
    differentiation_coefficient REAL,
    level_mo_rtccf              VARCHAR(10),
    coefficient_level_mo_rtccf  REAL,
    level_mo_dcf                VARCHAR(10),
    coefficient_level_mo_dcf    REAL,
    name_subject                VARCHAR(100) REFERENCES subject_of_rf (name_subject),
    type_medical_institution    INTEGER DEFAULT 0 REFERENCES type_medical_institution (type_mi)
);