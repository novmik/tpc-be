CREATE TABLE IF NOT EXISTS medicament_price
(
    id             BIGSERIAL PRIMARY KEY,
    inn            VARCHAR(100)   NOT NULL,
    dosage         REAL           NOT NULL,
    price_with_VAT NUMERIC(10, 2) NOT NULL
);