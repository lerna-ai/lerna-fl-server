create table lerna_predictions
(
    id         bigserial
        constraint lerna_predictions_pk
            primary key,
    device_id  bigint,
    ml_id      bigint
        constraint lerna_predictions_lerna_ml_id_fk
            references lerna_ml,
    version    bigint,
    model      varchar(255),
    prediction varchar(255),
    timestamp  timestamp
);
