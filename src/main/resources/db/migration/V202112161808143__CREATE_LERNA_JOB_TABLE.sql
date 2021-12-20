create table lerna_job
(
    id                bigint,
    ml_id             bigint,
    prediction        varchar(255),
    weights           jsonb,
    total_data_points bigint,
    total_devices     bigint
);
alter table lerna_job
    add constraint lerna_job_pk
        primary key (id);
alter table lerna_job
    add constraint lerna_job_lerna_ml_id_fk
        foreign key (ml_id) references lerna_ml (id);
