DROP TABLE transport IF EXISTS;

CREATE TABLE transport  (
    transport_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    transport_type VARCHAR(30),
    make VARCHAR(30),
    model VARCHAR(30),
    year VARCHAR(4),
    odometer_reading int
);
