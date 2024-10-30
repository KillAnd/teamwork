-- liquibase/scripts/tableRecommendation.sql

-- changeset TeamWorker:1
CREATE TABLE IF NOT EXISTS recommendations (
    id UUID PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_id UUID NOT NULL,
    product_text TEXT NOT NULL
);

-- changeset TeamWorker:2
CREATE TABLE IF NOT EXISTS rules (
    id UUID PRIMARY KEY,
    query VARCHAR(255) NOT NULL,
    arguments VARCHAR(255)[] NOT NULL,
    negate BOOLEAN NOT NULL,
    FOREIGN KEY (id) REFERENCES recommendations(id) ON DELETE CASCADE
);


