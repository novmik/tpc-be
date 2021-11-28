CREATE TABLE IF NOT EXISTS nomenclature_medical_care
(
    id                BIGSERIAL PRIMARY KEY,
    code_medical_care VARCHAR(50),
    name_medical_care VARCHAR(100) NOT NULL
);