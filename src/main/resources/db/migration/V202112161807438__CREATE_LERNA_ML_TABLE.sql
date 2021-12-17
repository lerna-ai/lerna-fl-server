create table lerna_ml
(
    id                 bigint,
    app_id             bigint,
    privacy_parameters jsonb,
    ml_parameters      jsonb,
    fl_parameters      jsonb,
    accuracy           numeric
);