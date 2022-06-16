package model;

import controller.IdManager;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class StudyGroup implements Serializable { //Потребуется переделать конструкторы для корректной работы по ТЗ
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private int ownerId;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount; //Значение поля должно быть больше 0
    private double averageMark; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле может быть null

    public StudyGroup(String name, Coordinates coordinates, long studentsCount,
                      double averageMark, FormOfEducation formOfEducation, Semester semesterEnum, Person person) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.studentsCount = studentsCount;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = person;
        IdManager.saveStudyGroupID(this.id);
    }

    //Конструктор для создания объектов и их сохранения в бд
    public StudyGroup(int ownerId,String name, Coordinates coordinates, long studentsCount,
                      double averageMark, FormOfEducation formOfEducation, Semester semesterEnum, Person person) {
        this.ownerId =ownerId;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.studentsCount = studentsCount;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = person;
    }

    //Конструктор для создания объектов из бд
    public StudyGroup(int id,int ownerId, String name, Coordinates coordinates,LocalDate date, long studentsCount,
                      double averageMark, FormOfEducation formOfEducation, Semester semesterEnum, Person person) {
        this.id = id;
        this.ownerId=ownerId;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = date;
        this.studentsCount = studentsCount;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = person;
        IdManager.saveStudyGroupID(this.id);
    }
    //Нужно будет удалить
    public StudyGroup(String[] string) {
        this.id = Integer.parseInt(string[0]);
        this.name = string[1];
        this.coordinates = new Coordinates(Integer.parseInt(string[2]), Integer.parseInt(string[3]));
        this.creationDate = LocalDate.parse(string[4]);
        this.studentsCount = Long.parseLong(string[5]);
        this.averageMark = Double.parseDouble(string[6]);
        this.formOfEducation = FormOfEducation.valueOf(string[7]);
        this.semesterEnum = Semester.valueOf(string[8]);
        if (!string[9].equals("null")) {
            if (!string[10].equalsIgnoreCase("null")) {
                this.groupAdmin = new Person(string[9],
                        LocalDateTime.parse(string[10]), Float.parseFloat(string[11]), string[12]);
            } else {
                this.groupAdmin = new Person(string[9],
                        null, Float.parseFloat(string[11]), string[12]);
            }

        } else groupAdmin = null;
    }

    public StudyGroup() {
        this.name = null;
        this.coordinates = null;
        this.studentsCount = 0;
        this.averageMark = 0;
        this.formOfEducation = null;
        this.semesterEnum = null;
        this.groupAdmin = null;
    }

    public double compareTo(StudyGroup studyGroup) {
        return averageMark - studyGroup.getAverageMark();
    }

    public Integer getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        this.formOfEducation = formOfEducation;
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setGroupAdmin(Person groupAdmin) {
        this.groupAdmin = groupAdmin;
    }

    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    public void setSemesterEnum(Semester semesterEnum) {
        this.semesterEnum = semesterEnum;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(long studentsCount) {
        this.studentsCount = studentsCount;
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        if (groupAdmin == null) {
            return "id=" + id +
                    ", name=" + name +
                    ", coordinatesX=" + coordinates.getX() +
                    ", coordinatesY=" + coordinates.getY() +
                    ", creationDate=" + creationDate +
                    ", studentsCount=" + studentsCount +
                    ", averageMark=" + averageMark +
                    ", formOfEducation=" + formOfEducation +
                    ", semesterEnum=" + semesterEnum +
                    ", groupAdmin=null";
        } else {
            return "id=" + id +
                    ", name=" + name +
                    ", coordinatesX=" + coordinates.getX() +
                    ", coordinatesY=" + coordinates.getY() +
                    ", creationDate=" + creationDate +
                    ", studentsCount=" + studentsCount +
                    ", averageMark=" + averageMark +
                    ", formOfEducation=" + formOfEducation +
                    ", semesterEnum=" + semesterEnum +
                    ", groupAdmin=" + groupAdmin.toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyGroup that = (StudyGroup) o;
        return id == that.id && ownerId == that.ownerId && studentsCount == that.studentsCount && Double.compare(that.averageMark, averageMark) == 0 && name.equals(that.name) && coordinates.equals(that.coordinates) && creationDate.equals(that.creationDate) && formOfEducation == that.formOfEducation && semesterEnum == that.semesterEnum && Objects.equals(groupAdmin, that.groupAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, name, coordinates, creationDate, studentsCount, averageMark, formOfEducation, semesterEnum, groupAdmin);
    }
}

