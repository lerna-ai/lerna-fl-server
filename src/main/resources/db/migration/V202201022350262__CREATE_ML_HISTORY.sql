create table ml_history
(
    id           bigserial
        constraint ml_history_pk
            primary key,
    ml_id        bigint
        constraint ml_history_lerna_ml_id_fk
            references lerna_ml,
    version      bigint,
    weights      jsonb,
    accuracy_avg numeric
);
create unique index ml_history_ml_id_version_uindex
    on ml_history (ml_id, version);
