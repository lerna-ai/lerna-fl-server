create table lerna_job
(
    id bigint,
    ml_id bigint,
    model varchar(255),
    weights jsonb,
    total_data_points bigint,
    total_devices bigint
);