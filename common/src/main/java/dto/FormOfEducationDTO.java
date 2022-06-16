package dto;

import java.io.Serializable;

public enum FormOfEducationDTO implements Serializable {
    DISTANCE_EDUCATION("Дистанционно"),
    FULL_TIME_EDUCATION("Очно"),
    EVENING_CLASSES("Вечер");

    private final String name;

    FormOfEducationDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
