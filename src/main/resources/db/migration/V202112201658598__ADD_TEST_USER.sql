INSERT INTO lerna_user (firstname, lastname, email, company)
VALUES ('Lerna', 'Inc.', 'info@lerna.ai', 'Lerna');

INSERT INTO lerna_app (user_id, token, current_version, no_ml_tasks, no_min_users)
VALUES (currval('lerna_user_id_seq'), 'e3785aec-e010-4e31-a822-705544e34f08', 1, 1, 2);

INSERT INTO lerna_ml (app_id, model, privacy_parameters, ml_parameters, accuracy)
VALUES (currval('lerna_app_id_seq'), 'LR - next app category', '{"mpc": true, "epsilon": 100}', '{"normalization": 0.1, "iterations": 10,"learningRate": 0.05, "dimensions": 56, "dataSplit": 80}', 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'audio', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAADgAAAABAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAA4AAVGTE9BVL8zizO+mky2vwYgRb9GzWo/U5cVv73rwb8dMPQ/hmqUP5bK87+NgTW/Ax0A\r\nPjBXID9aHhw/UARmuxZkUD7C5lu/SSunv4ndLr+W3w0++GA9PtfrnDwvvoi/AcUIv5JAdL4SqJQ/\r\nDnF3wBOa3z3XGJU/H3gtP4rKXT/5jQg/gOySPbYIJL+Gd5s/jmUQvkocSsAzYR6/rDbnPwt9y78J\r\nbMa+oc2Lvyzf2T8UtII+4tHDQAcMor7SOlq/HxC3PbhYkD/+r5g/7mWPv1yf/j4V7j2/BNUzP6h0\r\n70AO560/I1oB\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'game', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAADgAAAABAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAA4AAVGTE9BVL8zizO+mky2vwYgRb9GzWo/U5cVv73rwb8dMPQ/hmqUP5bK87+NgTW/Ax0A\r\nPjBXID9aHhw/UARmuxZkUD7C5lu/SSunv4ndLr+W3w0++GA9PtfrnDwvvoi/AcUIv5JAdL4SqJQ/\r\nDnF3wBOa3z3XGJU/H3gtP4rKXT/5jQg/gOySPbYIJL+Gd5s/jmUQvkocSsAzYR6/rDbnPwt9y78J\r\nbMa+oc2Lvyzf2T8UtII+4tHDQAcMor7SOlq/HxC3PbhYkD/+r5g/7mWPv1yf/j4V7j2/BNUzP6h0\r\n70AO560/I1oB\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'image', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAADgAAAABAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAA4AAVGTE9BVL8zizO+mky2vwYgRb9GzWo/U5cVv73rwb8dMPQ/hmqUP5bK87+NgTW/Ax0A\r\nPjBXID9aHhw/UARmuxZkUD7C5lu/SSunv4ndLr+W3w0++GA9PtfrnDwvvoi/AcUIv5JAdL4SqJQ/\r\nDnF3wBOa3z3XGJU/H3gtP4rKXT/5jQg/gOySPbYIJL+Gd5s/jmUQvkocSsAzYR6/rDbnPwt9y78J\r\nbMa+oc2Lvyzf2T8UtII+4tHDQAcMor7SOlq/HxC3PbhYkD/+r5g/7mWPv1yf/j4V7j2/BNUzP6h0\r\n70AO560/I1oB\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'maps', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAADgAAAABAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAA4AAVGTE9BVL8zizO+mky2vwYgRb9GzWo/U5cVv73rwb8dMPQ/hmqUP5bK87+NgTW/Ax0A\r\nPjBXID9aHhw/UARmuxZkUD7C5lu/SSunv4ndLr+W3w0++GA9PtfrnDwvvoi/AcUIv5JAdL4SqJQ/\r\nDnF3wBOa3z3XGJU/H3gtP4rKXT/5jQg/gOySPbYIJL+Gd5s/jmUQvkocSsAzYR6/rDbnPwt9y78J\r\nbMa+oc2Lvyzf2T8UtII+4tHDQAcMor7SOlq/HxC3PbhYkD/+r5g/7mWPv1yf/j4V7j2/BNUzP6h0\r\n70AO560/I1oB\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'news', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAADgAAAABAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAA4AAVGTE9BVL8zizO+mky2vwYgRb9GzWo/U5cVv73rwb8dMPQ/hmqUP5bK87+NgTW/Ax0A\r\nPjBXID9aHhw/UARmuxZkUD7C5lu/SSunv4ndLr+W3w0++GA9PtfrnDwvvoi/AcUIv5JAdL4SqJQ/\r\nDnF3wBOa3z3XGJU/H3gtP4rKXT/5jQg/gOySPbYIJL+Gd5s/jmUQvkocSsAzYR6/rDbnPwt9y78J\r\nbMa+oc2Lvyzf2T8UtII+4tHDQAcMor7SOlq/HxC3PbhYkD/+r5g/7mWPv1yf/j4V7j2/BNUzP6h0\r\n70AO560/I1oB\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'productivity', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAADgAAAABAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAA4AAVGTE9BVL8zizO+mky2vwYgRb9GzWo/U5cVv73rwb8dMPQ/hmqUP5bK87+NgTW/Ax0A\r\nPjBXID9aHhw/UARmuxZkUD7C5lu/SSunv4ndLr+W3w0++GA9PtfrnDwvvoi/AcUIv5JAdL4SqJQ/\r\nDnF3wBOa3z3XGJU/H3gtP4rKXT/5jQg/gOySPbYIJL+Gd5s/jmUQvkocSsAzYR6/rDbnPwt9y78J\r\nbMa+oc2Lvyzf2T8UtII+4tHDQAcMor7SOlq/HxC3PbhYkD/+r5g/7mWPv1yf/j4V7j2/BNUzP6h0\r\n70AO560/I1oB\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'social', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAADgAAAABAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAA4AAVGTE9BVL8zizO+mky2vwYgRb9GzWo/U5cVv73rwb8dMPQ/hmqUP5bK87+NgTW/Ax0A\r\nPjBXID9aHhw/UARmuxZkUD7C5lu/SSunv4ndLr+W3w0++GA9PtfrnDwvvoi/AcUIv5JAdL4SqJQ/\r\nDnF3wBOa3z3XGJU/H3gtP4rKXT/5jQg/gOySPbYIJL+Gd5s/jmUQvkocSsAzYR6/rDbnPwt9y78J\r\nbMa+oc2Lvyzf2T8UtII+4tHDQAcMor7SOlq/HxC3PbhYkD/+r5g/7mWPv1yf/j4V7j2/BNUzP6h0\r\n70AO560/I1oB\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'video', '{"array": "AAdKQVZBQ1BQAAAACAADSU5UAAAAAgAAADgAAAABAAAAAQAAAAEAAAAAAAAAAQAAAGMAB0pBVkFD\r\nUFAAAAA4AAVGTE9BVL8zizO+mky2vwYgRb9GzWo/U5cVv73rwb8dMPQ/hmqUP5bK87+NgTW/Ax0A\r\nPjBXID9aHhw/UARmuxZkUD7C5lu/SSunv4ndLr+W3w0++GA9PtfrnDwvvoi/AcUIv5JAdL4SqJQ/\r\nDnF3wBOa3z3XGJU/H3gtP4rKXT/5jQg/gOySPbYIJL+Gd5s/jmUQvkocSsAzYR6/rDbnPwt9y78J\r\nbMa+oc2Lvyzf2T8UtII+4tHDQAcMor7SOlq/HxC3PbhYkD/+r5g/7mWPv1yf/j4V7j2/BNUzP6h0\r\n70AO560/I1oB\r\n"}', 0, 0);

