package dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PersonDTO extends DTO implements Serializable { // Помнить что этот класс может бить null, переделать конструкторы при необходимости
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле может быть null
    private float weight; //Значение поля должно быть больше 0


    public PersonDTO(String name, LocalDateTime birthday, float weight, String passportID) {
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }

    public PersonDTO(String name, LocalDateTime birthday, float weight) {
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "name=" + name +
                ", birthday=" + birthday +
                ", weight=" + weight;
    }
}
