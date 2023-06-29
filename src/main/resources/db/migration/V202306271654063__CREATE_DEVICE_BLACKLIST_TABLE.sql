create table device_blacklist
(
    id        bigserial
        constraint device_blacklist_pk
            primary key,
    app_id    bigint
        constraint device_blacklist_lerna_app_id_fk
            references lerna_app,
    device_id bigint,
    metadata  jsonb NOT NULL DEFAULT '{}'::jsonb
);
create unique index device_blacklist_device_id_uindex
    on device_blacklist (device_id);
