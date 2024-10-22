-- liquibase/scripts/tableRecommendation.sql

-- changeset TeamWorker:1
CREATE TABLE products (
    id UUID PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    product_id UUID NOT NULL,
    product_text TEXT NOT NULL
);

-- changeset TeamWorker:2
CREATE TABLE rules (
    id UUID PRIMARY KEY,
    product_id UUID NOT NULL,
    query VARCHAR(255) NOT NULL,
    arguments TEXT NOT NULL,
    negate BOOLEAN NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id)
);
