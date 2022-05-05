create table webhook_config
(
    id      bigserial
        constraint webhook_config_pk
            primary key,
    app_id  bigint,
    type    varchar(255),
    request jsonb,
    filter  jsonb,
    enabled boolean
);
