package dto;

import java.io.Serializable;

public enum SemesterDTO implements Serializable {
    THIRD("Три"),
    FIFTH("Пять"),
    SEVENTH("Семь");

    private final String name;

    SemesterDTO(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}