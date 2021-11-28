CREATE TABLE IF NOT EXISTS scheme_pharmacotherapy
(
    id                          BIGSERIAL PRIMARY KEY,
    code_scheme                 VARCHAR(50)  NOT NULL UNIQUE,
    inn_medicament              VARCHAR(150) NOT NULL,
    name_and_description_scheme VARCHAR(255) NOT NULL,
    number_days_drug_treatments INT          NOT NULL,
    number_drg_rttcf            VARCHAR(50),
    number_drg_dcf              VARCHAR(50)
);