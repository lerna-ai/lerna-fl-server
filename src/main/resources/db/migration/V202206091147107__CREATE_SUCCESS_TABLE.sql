CREATE TABLE success
(
    id         bigserial
        constraint success_pk
            primary key,
    ml_id      bigint
        constraint ml_success_lerna_ml_id_fk
            references lerna_ml,
    version    bigint,
    device_id  bigint,
    prediction varchar(255),
    success    varchar(255),
    timestamp  timestamp
);
