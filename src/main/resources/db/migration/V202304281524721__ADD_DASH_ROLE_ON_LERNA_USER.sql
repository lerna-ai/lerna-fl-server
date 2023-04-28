ALTER TABLE lerna_user
    ADD dash_role_id bigint DEFAULT 2,
    ADD CONSTRAINT lerna_user_dash_role_id_fk
        FOREIGN KEY (dash_role_id) REFERENCES dash_role;
