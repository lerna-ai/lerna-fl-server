create table lerna_app
(
    id              bigint
        constraint lerna_app_pk
            primary key,
    user_id         bigint
        constraint lerna_app_lerna_user_id_fk
            references lerna_user,
    token           varchar(255),
    current_version bigint,
    no_ml_tasks     integer
);
create unique index lerna_app_token_uindex
    on lerna_app (token);