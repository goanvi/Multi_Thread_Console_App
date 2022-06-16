package dto;

import java.io.Serializable;

public class CoordinatesDTO extends DTO implements Serializable {
    private Integer x; //Максимальное значение поля: 811, Поле не может быть null
    private int y;

    public CoordinatesDTO(Integer x, int y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "x=" + x +
                "y=" + y;
    }
}
