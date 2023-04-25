create table infrastructure
(
    id     bigserial
        constraint infrastructure_pk
            primary key,
    name   varchar(255),
    mpc_uri varchar(255),
    fl_uri varchar(255)
);
