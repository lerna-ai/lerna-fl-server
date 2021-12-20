create table lerna_ml
(
    id                 bigint,
    app_id             bigint,
    model              varchar(255),
    privacy_parameters jsonb,
    ml_parameters      jsonb,
    fl_parameters      jsonb,
    accuracy           numeric
);
alter table lerna_ml
    add constraint lerna_ml_pk
        primary key (id);
alter table lerna_ml
    add constraint lerna_ml_lerna_app_id_fk
        foreign key (app_id) references lerna_app (id);
