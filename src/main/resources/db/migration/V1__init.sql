

CREATE TABLE IF NOT EXISTS live."driver" (
    id uuid,
    name VARCHAR(128) NOT NULL,
    PRIMARY KEY(id)
);

CREATE TYPE live."taxi_status"
    AS ENUM('OFF', 'IDLE', 'BUSY');

CREATE TABLE IF NOT EXISTS live."taxi" (
    id uuid,
    brand VARCHAR(32) NOT NULL,
    model VARCHAR(128) NOT NULL,
    registration VARCHAR(16) NOT NULL UNIQUE,
    status live.taxi_status NOT NULL DEFAULT 'OFF',
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    driver_id uuid,
    PRIMARY KEY(id),
    CONSTRAINT fk_driver FOREIGN KEY(driver_id)
        REFERENCES driver(id)
);

CREATE TYPE live."booking_status"
    AS ENUM('OPEN', 'ONGOING', 'FINISHED');

CREATE TABLE IF NOT EXISTS live."booking" (
    id uuid,
    status live.booking_status NOT NULL DEFAULT 'OPEN',
    origin jsonb NOT NULL,
    destination jsonb NOT NULL,
    client jsonb NOT NULL,
    taxi_id uuid,
    created_at timestamptz NULL DEFAULT now(),
    PRIMARY KEY(id),
    CONSTRAINT fk_taxi FOREIGN KEY(taxi_id)
        REFERENCES taxi(id)
);
