package model;

import model.Exceptions.IncorrectNameEnumException;

import java.io.Serializable;

public enum Semester implements Serializable {
    THIRD("Три"),
    FIFTH("Пять"),
    SEVENTH("Семь");

    private final String name;

    Semester(String name) {
        this.name = name;
    }

    public static Semester equals(String name) throws IncorrectNameEnumException {
        switch (name.toLowerCase()) {
            case "три":
                return Semester.THIRD;
            case "пять":
                return Semester.FIFTH;
            case "семь":
                return Semester.SEVENTH;
            default:
                throw new IncorrectNameEnumException();
        }
    }

    public String getName() {
        return name;
    }
}