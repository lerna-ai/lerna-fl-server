ALTER TABLE lerna_user
    ADD metadata jsonb NOT NULL DEFAULT '{}'::jsonb,
    ADD created_at timestamp DEFAULT now();
