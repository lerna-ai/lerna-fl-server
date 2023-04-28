create table dash_role
(
    id        bigserial
        constraint dash_role_pk
            primary key,
    key varchar(255),
    description  varchar(255)
);
