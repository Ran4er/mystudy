package ru.ifmo.prog.lab5.models;

import ru.ifmo.prog.lab5.utils.Validatable;

import java.util.Objects;

/**
 * Класс координат.
 * @author ru6ik
 */

public class Coordinates implements Validatable {

    private Double x; //Поле не может быть null
    private Double y; //Поле не может быть null

    public Coordinates(Double x, Double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Валидирует правильность полей.
     * @return true, если все верно, иначе false.
     */

    @Override
    public boolean validate(){
        if(x == null) return false;
        return y != null;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return false;
        if(o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

}
