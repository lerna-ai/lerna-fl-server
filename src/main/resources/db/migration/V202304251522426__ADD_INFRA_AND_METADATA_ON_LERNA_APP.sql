alter table lerna_app
    add infrastructure_id bigint
        constraint lerna_app_infrastructure_id_fk
            references infrastructure,
    ADD metadata          jsonb NOT NULL DEFAULT '{}'::jsonb;
