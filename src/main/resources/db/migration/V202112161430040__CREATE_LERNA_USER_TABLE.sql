create table lerna_user
(
    id        bigint
        constraint lerna_user_pk
            primary key,
    firstname varchar(255),
    lastname  varchar(255),
    email     varchar(255),
    password  varchar(255),
    company   varchar(255),
    position  varchar(255)
);
