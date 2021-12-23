INSERT INTO lerna_user (id, firstname, lastname, email, company)
VALUES (1, 'Miltos', 'Poulizos', 'miltos@lerna.ai', 'Lerna');

INSERT INTO lerna_app (id, user_id, token, current_version, no_ml_tasks, no_min_users)
VALUES (1, 1, 'e3785aec-e010-4e31-a822-705544e34f08', 1, 1, 5);

INSERT INTO lerna_ml (id, app_id, model, privacy_parameters, ml_parameters, accuracy)
VALUES (1, 1, 'hair color', '{"mpc": true, "epsilon": 0.5}', '{"normalization": 0.1, "iterations": 10,"learningRate": 0.05, "dimensions": 5, "dataSplit": 70}', 0);

INSERT INTO lerna_job (id, ml_id, prediction, weights, total_data_points, total_devices)
VALUES (1, 1, 'black', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAAAEAAAAFAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAAFAAVGTE9BVAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"}', 100, 10);

INSERT INTO lerna_job (id, ml_id, prediction, weights, total_data_points, total_devices)
VALUES (2, 1, 'red', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAAAEAAAAFAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAAFAAVGTE9BVAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"}', 100, 10);

INSERT INTO lerna_job (id, ml_id, prediction, weights, total_data_points, total_devices)
VALUES (3, 1, 'blonde', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAAAEAAAAFAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAAFAAVGTE9BVAAAAAAAAAAAAAAAAAAAAAAAAAAA\r\n"}', 100, 10);