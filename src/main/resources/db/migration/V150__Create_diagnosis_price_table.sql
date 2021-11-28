CREATE TABLE IF NOT EXISTS diagnosis_price
(
    id                           SERIAL PRIMARY KEY,
    name_subject                 VARCHAR(100) REFERENCES subject_of_rf (name_subject),
    nomenclature_medical_care_id BIGSERIAL NOT NULL REFERENCES nomenclature_medical_care,
    price_medical_care        NUMERIC(10, 2)
);