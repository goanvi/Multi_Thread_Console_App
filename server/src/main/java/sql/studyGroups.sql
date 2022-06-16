CREATE TYPE FormOfEducation AS ENUM ('Дистанционно','Очно','Вечер');

CREATE TYPE  Semester AS ENUM ('Три','Пять','Семь') ;

CREATE TABLE IF NOT EXISTS StudyGroups
(
    id              SERIAL PRIMARY KEY UNIQUE,
    ownerId         INT REFERENCES users (id)                  NOT NULL,
    name            VARCHAR(100)                               NOT NULL,
    coordinates     INT REFERENCES coordinates (id)            NOT NULL,
    creationDate    DATE                                       NOT NULL,
    studentsCount   BIGINT CHECK ( studentsCount > 0 )         NOT NULL,
    averageMark     DOUBLE PRECISION CHECK ( averageMark > 0 ) NOT NULL,
    formOfEducation FormOfEducation                            NOT NULL,
    semester        Semester                                   NOT NULL,
    person          INT REFERENCES persons (id)
);


