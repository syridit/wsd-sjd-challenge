-- Sequence generator
CREATE SEQUENCE IF NOT EXISTS base_entity_seq START 1;

-- Customer Table
CREATE TABLE customer (
    id BIGINT PRIMARY KEY DEFAULT nextval('base_entity_seq'),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT NOT NULL
);

-- Product Table
CREATE TABLE product (
    id BIGINT PRIMARY KEY DEFAULT nextval('base_entity_seq'),
    name VARCHAR(255) NOT NULL,
    price NUMERIC(15, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT NOT NULL
);

-- Sales Table
CREATE TABLE sales (
    id BIGINT PRIMARY KEY DEFAULT nextval('base_entity_seq'),
    product_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    quantity BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT NOT NULL,
    CONSTRAINT fk_sales_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT fk_sales_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);

-- Wishlist Table
CREATE TABLE wishlist (
    id BIGINT PRIMARY KEY DEFAULT nextval('base_entity_seq'),
    customer_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT NOT NULL,
    CONSTRAINT fk_wishlist_customer FOREIGN KEY (customer_id) REFERENCES customer(id),
    CONSTRAINT fk_wishlist_product FOREIGN KEY (product_id) REFERENCES product(id),
    CONSTRAINT unique_wishlist UNIQUE (customer_id, product_id)
);