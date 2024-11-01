-- liquibase/scripts/tableRecommendation.sql

-- changeset TeamWorker:1
CREATE TABLE IF NOT EXISTS recommendation (
    id UUID PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_id UUID NOT NULL,
    product_text TEXT NOT NULL
);

-- changeset TeamWorker:2
CREATE TABLE IF NOT EXISTS rule (
    id UUID PRIMARY KEY,
    query VARCHAR(255) NOT NULL,
    arguments VARCHAR(255)[] NOT NULL,
    negate BOOLEAN NOT NULL,
    recommendation_id UUID,
    FOREIGN KEY (recommendation_id) REFERENCES recommendation(id) ON DELETE CASCADE
);

-- changeset TeamWorker:3
CREATE TABLE IF NOT EXISTS stat (
    id UUID PRIMARY KEY,
    counter int NOT NULL DEFAULT 0,
    FOREIGN KEY (id) REFERENCES recommendation(id) ON DELETE CASCADE
);


