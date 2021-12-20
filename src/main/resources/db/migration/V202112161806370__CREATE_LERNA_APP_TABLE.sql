create table lerna_app
(
    id              bigint,
    user_id         bigint,
    token           varchar(255),
    current_version bigint,
    no_ml_tasks     integer
);
alter table lerna_app
    add constraint lerna_app_pk
        primary key (id);
alter table lerna_app
    add constraint lerna_app_lerna_user_id_fk
        foreign key (user_id) references lerna_user (id);
