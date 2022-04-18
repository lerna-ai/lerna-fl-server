CREATE TABLE inferences
(
    date     timestamp,
    uid      bigint,
    accuracy numeric,
    model    varchar(80),
    version  bigint
);
