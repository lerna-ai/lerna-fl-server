INSERT INTO dash_role ("id", "key", "description") VALUES (DEFAULT, 'admin', 'Super user');
INSERT INTO dash_role ("id", "key", "description") VALUES (DEFAULT, 'dev-lite', 'Developer with simple dashboard');
INSERT INTO dash_role ("id", "key", "description") VALUES (DEFAULT, 'dev', 'Developer with full dashboard');

INSERT INTO dash_permission ("id", "key", "description") VALUES (DEFAULT, 'dashboard-page', 'Show dashboard page');
INSERT INTO dash_permission ("id", "key", "description") VALUES (DEFAULT, 'access-api-page', 'Show access API page');
INSERT INTO dash_permission ("id", "key", "description") VALUES (DEFAULT, 'models-page', 'Show models page');

INSERT INTO dash_role_has_permission ("role_id", "permission_id") VALUES (1, 1);
INSERT INTO dash_role_has_permission ("role_id", "permission_id") VALUES (1, 2);
INSERT INTO dash_role_has_permission ("role_id", "permission_id") VALUES (1, 3);
INSERT INTO dash_role_has_permission ("role_id", "permission_id") VALUES (2, 1);
INSERT INTO dash_role_has_permission ("role_id", "permission_id") VALUES (3, 1);
INSERT INTO dash_role_has_permission ("role_id", "permission_id") VALUES (3, 2);
INSERT INTO dash_role_has_permission ("role_id", "permission_id") VALUES (3, 3);
