-- liquibase/scripts/tableRecommendation.sql

-- changeset TeamWorker:1
CREATE TABLE IF NOT EXISTS recommendation (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    product_id UUID NOT NULL,
    text TEXT NOT NULL
);

-- changeset TeamWorker:2
CREATE TABLE IF NOT EXISTS rule (
    id UUID PRIMARY KEY,
    query VARCHAR(255) NOT NULL,
    arguments VARCHAR(255)[] NOT NULL,
    negate BOOLEAN NOT NULL,
    recommendation_id UUID NOT NULL,
    FOREIGN KEY (recommendation_id) REFERENCES recommendation(id) ON DELETE CASCADE
);

-- changeset TeamWorker:3
CREATE TABLE IF NOT EXISTS recommendation_stat (
    recommendation_id UUID PRIMARY KEY,
    counter INT NOT NULL DEFAULT 0,
    FOREIGN KEY (recommendation_id) REFERENCES recommendation(id) ON DELETE CASCADE
);


