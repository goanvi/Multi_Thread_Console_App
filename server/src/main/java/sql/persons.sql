CREATE TABLE IF NOT EXISTS Persons
(
    id         SERIAL PRIMARY KEY UNIQUE,
    name       VARCHAR(100) NOT NULL,
    birthday   TIMESTAMP,
    weight     FLOAT CHECK ( weight > 0) NOT NULL ,
    passportID VARCHAR(33) NOT NULL
);