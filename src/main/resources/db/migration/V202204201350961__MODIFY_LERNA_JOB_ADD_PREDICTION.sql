ALTER TABLE lerna_job
    ADD prediction_value bigint;

UPDATE lerna_job SET prediction_value = 1 WHERE prediction = 'audio';
UPDATE lerna_job SET prediction_value = 2 WHERE prediction = 'game';
UPDATE lerna_job SET prediction_value = 3 WHERE prediction = 'image';
UPDATE lerna_job SET prediction_value = 4 WHERE prediction = 'maps';
UPDATE lerna_job SET prediction_value = 5 WHERE prediction = 'news';
UPDATE lerna_job SET prediction_value = 6 WHERE prediction = 'productivity';
UPDATE lerna_job SET prediction_value = 7 WHERE prediction = 'social';
UPDATE lerna_job SET prediction_value = 8 WHERE prediction = 'video';

ALTER TABLE lerna_job
    ADD CONSTRAINT lerna_job_unique_prediction_per_ml
        UNIQUE (ml_id, prediction_value);
