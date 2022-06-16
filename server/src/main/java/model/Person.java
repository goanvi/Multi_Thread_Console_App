package model;

import controller.IdManager;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public class Person implements Serializable { // Помнить что этот класс может бить null, переделать конструкторы при необходимости
    private int id;
    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final java.time.LocalDateTime birthday; //Поле может быть null
    private final float weight; //Значение поля должно быть больше 0
    private final String passportID; //Длина строки не должна быть больше 33, Значение этого поля должно быть уникальным, Поле может быть null

    public Person(int id, String name, LocalDateTime birthday, float weight, String passportID) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.passportID = passportID;
    }

    public Person(String name, LocalDateTime birthday, float weight, String passportID) {
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.passportID = passportID;
    }

    public Person(String name, LocalDateTime birthday, float weight) {
        this.name = name;
        this.birthday = birthday;
        this.weight = weight;
        this.passportID = IdManager.setPersonID(Math.abs(UUID.randomUUID().hashCode()));
        IdManager.savePersonID(passportID);
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

    public String getPassportID() {
        return passportID;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "name=" + name +
                ", birthday=" + birthday +
                ", weight=" + weight +
                ", passportID=" + passportID;
    }
}
