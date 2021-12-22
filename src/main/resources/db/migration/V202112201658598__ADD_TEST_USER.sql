INSERT INTO public.lerna_user (id, firstname, lastname, email, company)
VALUES (1, 'Miltos', 'Poulizos', 'miltos@lerna.ai', 'Lerna');

INSERT INTO public.lerna_app (id, user_id, token, current_version, no_ml_tasks)
VALUES (1, 1, 'e3785aec-e010-4e31-a822-705544e34f08', 1, 1);

INSERT INTO public.lerna_ml (id, app_id, model, privacy_parameters, ml_parameters, fl_parameters, accuracy)
VALUES (1, 1, 'hair color', '{"mpc": true, "epsilon": 0.5}', '{"normalization": 0.1, "iterations": 10,"learningRate": 0.05, "dimensions": 5, "dataSplit": 70}', '{"minNoUsers": 5, "noJobs": 3}', 0);

INSERT INTO public.lerna_job (id, ml_id, prediction, total_data_points, total_devices)
VALUES (1, 1, 'black', 100, 10);

INSERT INTO public.lerna_job (id, ml_id, prediction, total_data_points, total_devices)
VALUES (2, 1, 'red', 100, 10);

INSERT INTO public.lerna_job (id, ml_id, prediction, total_data_points, total_devices)
VALUES (3, 1, 'blonde', 100, 10);