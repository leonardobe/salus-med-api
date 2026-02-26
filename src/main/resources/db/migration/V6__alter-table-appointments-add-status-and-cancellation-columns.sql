ALTER TABLE appointments
    ADD COLUMN status              VARCHAR(50) NOT NULL DEFAULT 'SCHEDULED',
    ADD COLUMN cancellation_reason VARCHAR(50) NULL,
    ADD COLUMN cancellation_date   DATETIME    NULL;