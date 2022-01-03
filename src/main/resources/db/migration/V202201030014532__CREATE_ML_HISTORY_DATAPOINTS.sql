create table ml_history_datapoint
(
    id         bigserial
        constraint ml_history_datapoint_pk
            primary key,
    history_id bigint
        constraint ml_history_datapoint_ml_history_id_fk
            references ml_history,
    timestamp  timestamp,
    device_id  bigint,
    accuracy   numeric
);
