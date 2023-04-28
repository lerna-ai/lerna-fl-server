create table dash_role_has_permission
(
    role_id bigint
        constraint dash_role_has_permission_dash_role_id_fk
            references dash_role,
    permission_id bigint
        constraint dash_role_has_permission_dash_permission_id_fk
            references dash_permission
);
