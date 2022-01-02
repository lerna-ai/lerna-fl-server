INSERT INTO lerna_user (firstname, lastname, email, company)
VALUES ('Miltos', 'Poulizos', 'miltos@lerna.ai', 'Lerna');

INSERT INTO lerna_app (user_id, token, current_version, no_ml_tasks, no_min_users)
VALUES (currval('lerna_user_id_seq'), 'e3785aec-e010-4e31-a822-705544e34f08', 1, 1, 2);

INSERT INTO lerna_ml (app_id, model, privacy_parameters, ml_parameters, accuracy)
VALUES (currval('lerna_app_id_seq'), 'hair color', '{"mpc": true, "epsilon": 1000}', '{"normalization": 0.1, "iterations": 10,"learningRate": 0.05, "dimensions": 5, "dataSplit": 70}', 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'black', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAAAUAAAABAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAAFAAVGTE9BVAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"}', 100, 10);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'red', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAAAUAAAABAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAAFAAVGTE9BVAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"}', 100, 10);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'blonde', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAAAUAAAABAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAAFAAVGTE9BVAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"}', 100, 10);
