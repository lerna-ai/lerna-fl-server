create table lerna_job
(
    id                bigserial
        constraint lerna_job_pk
            primary key,
    ml_id             bigint
        constraint lerna_job_lerna_ml_id_fk
            references lerna_ml,
    prediction        varchar(255),
    weights           jsonb,
    total_data_points bigint,
    total_devices     bigint
);
