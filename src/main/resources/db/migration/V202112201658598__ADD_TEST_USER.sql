INSERT INTO lerna_user (firstname, lastname, email, company)
VALUES ('Lerna', 'Inc.', 'info@lerna.ai', 'Lerna');

INSERT INTO lerna_app (user_id, token, current_version, no_ml_tasks, no_min_users)
VALUES (currval('lerna_user_id_seq'), 'e3785aec-e010-4e31-a822-705544e34f08', 1, 1, 2);

INSERT INTO lerna_ml (app_id, model, privacy_parameters, ml_parameters, accuracy)
VALUES (currval('lerna_app_id_seq'), 'LR - next app category', '{"mpc": true, "epsilon": 100}', '{"normalization": 0.1, "iterations": 10,"learningRate": 0.05, "dimensions": 56, "dataSplit": 80}', 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'audio', '{"array": "AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA4AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAADgABUZM\r\nT0FUP+q6Gr867rS/1HBsP7baDD2TDly+YWLBv8C8kL9D0/w+LzTFP0h1Wb04Gr+/l1xbPghbSD66\r\nOm+/onbqPaR7Br4mKyI/xmNev1dNHz5AdQO+DHMhPZNGP73yhFW/B+2FvolPkj/sORw/zGJnv4hu\r\n6j+8zRs//x94vCtCfD/uueW+lazBP4MIk74Msg29bl+YPwfeWr7Rjwu/xuDhP2azNz6u7OW/uFeT\r\nvYn6sT+VQwY+ge1gv8wL77+B3CO/AX2pP8Nb9T7s1Gq/cjXZv+A4TD+Wsbi+MzwsPq7eWkAPuiY=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'game', '{"array": "AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA4AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAADgABUZM\r\nT0FUP+q6Gr867rS/1HBsP7baDD2TDly+YWLBv8C8kL9D0/w+LzTFP0h1Wb04Gr+/l1xbPghbSD66\r\nOm+/onbqPaR7Br4mKyI/xmNev1dNHz5AdQO+DHMhPZNGP73yhFW/B+2FvolPkj/sORw/zGJnv4hu\r\n6j+8zRs//x94vCtCfD/uueW+lazBP4MIk74Msg29bl+YPwfeWr7Rjwu/xuDhP2azNz6u7OW/uFeT\r\nvYn6sT+VQwY+ge1gv8wL77+B3CO/AX2pP8Nb9T7s1Gq/cjXZv+A4TD+Wsbi+MzwsPq7eWkAPuiY=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'image', '{"array": "AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA4AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAADgABUZM\r\nT0FUP+q6Gr867rS/1HBsP7baDD2TDly+YWLBv8C8kL9D0/w+LzTFP0h1Wb04Gr+/l1xbPghbSD66\r\nOm+/onbqPaR7Br4mKyI/xmNev1dNHz5AdQO+DHMhPZNGP73yhFW/B+2FvolPkj/sORw/zGJnv4hu\r\n6j+8zRs//x94vCtCfD/uueW+lazBP4MIk74Msg29bl+YPwfeWr7Rjwu/xuDhP2azNz6u7OW/uFeT\r\nvYn6sT+VQwY+ge1gv8wL77+B3CO/AX2pP8Nb9T7s1Gq/cjXZv+A4TD+Wsbi+MzwsPq7eWkAPuiY=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'maps', '{"array": "AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA4AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAADgABUZM\r\nT0FUP+q6Gr867rS/1HBsP7baDD2TDly+YWLBv8C8kL9D0/w+LzTFP0h1Wb04Gr+/l1xbPghbSD66\r\nOm+/onbqPaR7Br4mKyI/xmNev1dNHz5AdQO+DHMhPZNGP73yhFW/B+2FvolPkj/sORw/zGJnv4hu\r\n6j+8zRs//x94vCtCfD/uueW+lazBP4MIk74Msg29bl+YPwfeWr7Rjwu/xuDhP2azNz6u7OW/uFeT\r\nvYn6sT+VQwY+ge1gv8wL77+B3CO/AX2pP8Nb9T7s1Gq/cjXZv+A4TD+Wsbi+MzwsPq7eWkAPuiY=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'news', '{"array": "AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA4AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAADgABUZM\r\nT0FUP+q6Gr867rS/1HBsP7baDD2TDly+YWLBv8C8kL9D0/w+LzTFP0h1Wb04Gr+/l1xbPghbSD66\r\nOm+/onbqPaR7Br4mKyI/xmNev1dNHz5AdQO+DHMhPZNGP73yhFW/B+2FvolPkj/sORw/zGJnv4hu\r\n6j+8zRs//x94vCtCfD/uueW+lazBP4MIk74Msg29bl+YPwfeWr7Rjwu/xuDhP2azNz6u7OW/uFeT\r\nvYn6sT+VQwY+ge1gv8wL77+B3CO/AX2pP8Nb9T7s1Gq/cjXZv+A4TD+Wsbi+MzwsPq7eWkAPuiY=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'productivity', '{"array": "AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA4AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAADgABUZM\r\nT0FUP+q6Gr867rS/1HBsP7baDD2TDly+YWLBv8C8kL9D0/w+LzTFP0h1Wb04Gr+/l1xbPghbSD66\r\nOm+/onbqPaR7Br4mKyI/xmNev1dNHz5AdQO+DHMhPZNGP73yhFW/B+2FvolPkj/sORw/zGJnv4hu\r\n6j+8zRs//x94vCtCfD/uueW+lazBP4MIk74Msg29bl+YPwfeWr7Rjwu/xuDhP2azNz6u7OW/uFeT\r\nvYn6sT+VQwY+ge1gv8wL77+B3CO/AX2pP8Nb9T7s1Gq/cjXZv+A4TD+Wsbi+MzwsPq7eWkAPuiY=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'social', '{"array": "AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA4AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAADgABUZM\r\nT0FUP+q6Gr867rS/1HBsP7baDD2TDly+YWLBv8C8kL9D0/w+LzTFP0h1Wb04Gr+/l1xbPghbSD66\r\nOm+/onbqPaR7Br4mKyI/xmNev1dNHz5AdQO+DHMhPZNGP73yhFW/B+2FvolPkj/sORw/zGJnv4hu\r\n6j+8zRs//x94vCtCfD/uueW+lazBP4MIk74Msg29bl+YPwfeWr7Rjwu/xuDhP2azNz6u7OW/uFeT\r\nvYn6sT+VQwY+ge1gv8wL77+B3CO/AX2pP8Nb9T7s1Gq/cjXZv+A4TD+Wsbi+MzwsPq7eWkAPuiY=\r\n"}', 0, 0);

INSERT INTO lerna_job (ml_id, prediction, weights, total_data_points, total_devices)
VALUES (currval('lerna_ml_id_seq'), 'video', '{"array": "AApMT05HX1NIQVBFAAAAAAAAAAgABExPTkcAAAAAAAAAAgAAAAAAAAA4AAAAAAAAAAEAAAAAAAAA\r\nAQAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAABjAApMT05HX1NIQVBFAAAAAAAAADgABUZM\r\nT0FUP+q6Gr867rS/1HBsP7baDD2TDly+YWLBv8C8kL9D0/w+LzTFP0h1Wb04Gr+/l1xbPghbSD66\r\nOm+/onbqPaR7Br4mKyI/xmNev1dNHz5AdQO+DHMhPZNGP73yhFW/B+2FvolPkj/sORw/zGJnv4hu\r\n6j+8zRs//x94vCtCfD/uueW+lazBP4MIk74Msg29bl+YPwfeWr7Rjwu/xuDhP2azNz6u7OW/uFeT\r\nvYn6sT+VQwY+ge1gv8wL77+B3CO/AX2pP8Nb9T7s1Gq/cjXZv+A4TD+Wsbi+MzwsPq7eWkAPuiY=\r\n"}', 0, 0);

