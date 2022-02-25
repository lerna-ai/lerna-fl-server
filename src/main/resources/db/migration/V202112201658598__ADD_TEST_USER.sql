INSERT INTO lerna_user (firstname, lastname, email, company)
VALUES ('Lerna', 'Inc.', 'info@lerna.ai', 'Lerna');

INSERT INTO lerna_app (user_id, token, current_version, no_ml_tasks, no_min_users)
VALUES (currval('lerna_user_id_seq'), 'e3785aec-e010-4e31-a822-705544e34f08', 1, 1, 5);

INSERT INTO lerna_ml (app_id, model, privacy_parameters, ml_parameters, accuracy)
VALUES (currval('lerna_app_id_seq'), 'LR - next app category', '{"mpc": true, "epsilon": 100}', '{"normalization": 0.1, "iterations": 10,"learningRate": 0.05, "dimensions": 62, "dataSplit": 80}', 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'audio', '{"array":"AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA+AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAAD4ABUZM\r\nT0FUP5Z+wT9xmSq/j6rdv58yUr9tIiI9msR2v1Y6JD+fcS6934ydPl/kgT6bj1W+qeKgv4xxUb6C\r\n0ag/COhVvzwGUr+wug6/wKnBv6e6bD6c0mM8lL1+vrvbu79XoSA+q71mPlW2HD+NMJ6+uhW3vpqt\r\nkb6zTPC/a73+Pxb1n77xaqw/igWcP80tjD9bdKq+Iwq4PzP4sT6PrOw+PN5RP5/h1L5ncnC/n6fE\r\nvyJ6/r9GC0U/K0TXPcv61T5AuP2/w8j6P4ut7z+lH/a/g8TjP/VZsb+MGyw/3G72PYIi4sADU7k/\r\nJfecPle//r6SIJQ++dq8v3Te7b9P40g=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'game', '{"array":"AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA+AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAAD4ABUZM\r\nT0FUv7CmYL3Pzg2+MZMBP4v/Mz9E18o/EQ2OP727Ob9gwUS/UtEWv9FtPD8mju095DKyv3/j7b/x\r\nOjy/NYORveA/9D8nHpM/OGwWv3fMHT89rIq+l65wvxzA5r+peSe/fp9ev4EwOTpmeOG8sV2Lva3M\r\nc7+dE8O+3AfYvq0CpD/CO7E/bkkkv5skq79xKcW+qxbrvr7Ytb1Gotm+u83LP8Qg+LqrzcRACQgf\r\nvwXGNb2KNpXAB1h0vx1I+D/QHZk/lDBQvw7BAD9c3Sq/gOxcPcsd2T83eiw/vQHpvya4Rj62Pvm/\r\nzF1XPH47m7/7w16/bkFov0CpmT+1vBw=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'image', '{"array":"AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA+AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAAD4ABUZM\r\nT0FUPxtvDr6mgqO+dDEov4fkU76B+v89P73hv4uprb8juqu/nLfRvuYPnz6eKbW/i+5YvwHOVL6L\r\nxKS+EwUBvvi48D9YSKQ/rrHSQC8yBD3Uma+/fIeav45Cuj028r+/kw0lvyhXwkAvZJ09x6QNvW7N\r\niT0ZrqO/NiNPP3bunj5iBEU/L+YkP2wvjD93KhE/JG4YP1TTQ7+qsci/lKfdPal1Ar5K+IO/kMfp\r\nv1NTuL7BMFU+ZMSxvvdIvz6QXtk/SJ0kv5edTL6foAm+vM6UPwyZ7b0ANxi/hrtEQAH6UD+KNcy/\r\n7M4tP4505r57rxw+nAHCv964kD4CZ6w=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'maps', '{"array":"AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA+AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAAD4ABUZM\r\nT0FUP9I4Cb9Ui1u+g9UNPXE4KD8rV48/lubzPydQ07/mqtw/aKEbv0DZ1786nea/MX8GP4TACUAO\r\nOes/xS8eP8K/fEAXXwI+pwoMv6fQEr+otw++QDqZvwz//78EWLg/mUN7QApXG78zGoG/3PPVPpEC\r\nAD+7SYw+phd2vr4jSz+F3DE+U9ZivwCLxr92Qg6/Eg7cPx/t2D6Rl69ADWJoP9EvH7+A5NM+kC1a\r\nP52xNz+OWio+fwaDPoNfHL4OQC6/zlhlvh+p9cAHbbW/bKoJPmTAY74JAwe+41E/Pss4a725tvY/\r\nFz09vo47nb3Qlbg+GfNLv2Aijz/hjo4=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'news', '{"array":"AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA+AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAAD4ABUZM\r\nT0FUvj7Hn77Il1U9ncHxPywqi73DLybAFO0wO9GYn7+EMBA/hfWzv6Gbpj+qczo/wAo3PyV0sj8J\r\na0s/SYNMvxMiIEAdz+e/Hluevkh09T9BoaG9DLDkP9nwJz6ktwu+CwUOPRbiRr6usOa/LhFOPqLG\r\nCT2a3qS/icZ5v0poQj6MTZY+qhwxvVOfOUAAXdC+jKDBvXC7ej5L+OY8r74HPt0fI79O/FS+27+a\r\nPtIOrr5ZFEs+pvkPvcBnGz9V428/pffcv3MrTL6ghIQ/iEMKv9rAHD+jxFa+RV4xv9tD779JFfC/\r\nmZOuP8tBtL8pBpu/Z2+JPs45Rj2DCLA=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'productivity', '{"array":"AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA+AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAAD4ABUZM\r\nT0FUPqvKwz+GexQ+VpzkPowzIT+VSHu+O2tlP0Ru/kADj6o+KTQbvyxrRb7BliQ+kVP6OsN59j5E\r\nWGw/lJBKv4rbEb35AES/gJtbv+oNb78IHJ4+/+4RvhHV5L+rdYi9j6MQPtXFbUASCzy83evEPiD5\r\njb6mSXW9bzJBP1c0kT9cSDW94wNpPrBNnb+nz6W9splHPv4/Rj+Kbhi/kkXXP4oukUADN/0/8/O/\r\nv9eOykAASew/APG4v6UbQb5rtFc/FzlXPx9QdT+2EIK/cSFmwAvfNj4PDAm94oy4v6bcfz6NElg+\r\npjJ8vxLsFz/WKLw/oboGPlXGHr8+B9M=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'social', '{"array":"AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA+AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAAD4ABUZM\r\nT0FUwBhXHUA4IB0+hG9UvYGfbb1Pskc/9Q92wA8h1L/pSl29hlxTvwDCaL7bMNI/SWtZvMnZAr8f\r\nDNm/5CRevuVnM79Y1ni/t1SYvydurb8Vkg0/lVOZv9MOJDz0wc4/O8TuPr+93j2Uqq6/yoFAvytM\r\nFEApICA//EOTP4/ExL4KFRk/yQ+BP7EI3j29OlTAIaYPPtPepr8hXQY/PTjQPqN7SD66MxG/flzH\r\nv425Cb8PicW/q68jvwlHsr9w0bs+nBX4vx+f+79ZdIS/NLMdP3qaMz/O/sG+5shlPZV0Xj6j0gw/\r\niEf0PrVh3D1F7zk+JPqMPqlRub8cTXs=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'video', '{"array":"AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA+AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAAD4ABUZM\r\nT0FUPtUwhD7UxIm+sXtfP3U1Bz+3DhU/H9dDvU6WOj+55jS/CzAGPwOM0r8YBO6+3jVNvyQoGL8U\r\naSQ+ecyJP72+X77S3pA+zX3Vvn0IKj9HWMK/yt/yv4JgC79G1us/kq8Av6RULD9CqTI+qdg/voxT\r\nwD7YmVm/hrldPqDkHL0GO9E+/1/Rv7HFCD6ASUI7V1qrvxcehb+yVqE/754RP9U+hD7olPy/gkic\r\nPsN8Ar4FYPu+rsYqPt/nSr8GG6o/jifAvcDiBr65FiU/Q9VWPVQCTL8DBRNAIpydPttNQb6gmc4+\r\n/N98P6QRh79alJq/ZOQHvx+K273w42Q=\r\n"}', 0, 0);

