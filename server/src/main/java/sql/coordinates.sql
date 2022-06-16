CREATE TABLE IF NOT EXISTS Coordinates
(
    id    SERIAL PRIMARY KEY UNIQUE,
    X     INT          NOT NULL CHECK (X < 812),
    Y     INT          NOT NULL
);