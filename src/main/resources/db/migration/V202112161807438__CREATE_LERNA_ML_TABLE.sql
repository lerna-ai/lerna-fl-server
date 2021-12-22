create table lerna_ml
(
    id                 bigint
        constraint lerna_ml_pk
            primary key,
    app_id             bigint
        constraint lerna_ml_lerna_app_id_fk
            references lerna_app,
    model              varchar(255),
    privacy_parameters jsonb,
    ml_parameters      jsonb,
    fl_parameters      jsonb,
    accuracy           numeric
);
