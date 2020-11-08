CREATE TABLE manufacturers (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE car_variants (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    engine_cc INTEGER,
    weight INTEGER
);

CREATE TABLE car_catalog (
    id SERIAL PRIMARY KEY,
    model_name VARCHAR NOT NULL,
    manufacture_id INTEGER NOT NULL REFERENCES manufacturers(id) ON DELETE CASCADE,
    variant_id INTEGER NOT NULL REFERENCES car_variants(id) ON DELETE CASCADE
);

CREATE TABLE cars (
    serial_number VARCHAR PRIMARY KEY,
    catalog_id INTEGER NOT NULL REFERENCES car_catalog(id) ON DELETE CASCADE,
    condition VARCHAR(10),
    last_listed_price DECIMAL(15,2)
);

CREATE TABLE salespersons (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL
);

CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    phone INTEGER
);

CREATE TABLE sale_transactions (
    id SERIAL PRIMARY KEY,
    salesperson_id INTEGER NOT NULL REFERENCES salespersons(id) ON DELETE CASCADE,
    customer_id INTEGER NOT NULL REFERENCES customers(id) ON DELETE CASCADE,
    car_id VARCHAR NOT NULL REFERENCES cars(serial_number) ON DELETE CASCADE,
    transacted_price DECIMAL(15,2) NOT NULL
);