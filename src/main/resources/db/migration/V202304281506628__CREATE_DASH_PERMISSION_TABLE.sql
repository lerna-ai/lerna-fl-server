create table dash_permission
(
    id        bigserial
        constraint dash_permission_pk
            primary key,
    key varchar(255),
    description  varchar(255)
);
