package ru.ifmo.prog.lab5.models;

import ru.ifmo.prog.lab5.utils.Validatable;

import java.util.Objects;

/**
 * Класс локации.
 * @author ru6ik
 */

public class Location implements Validatable {

    private Double x; //Поле не может быть null
    private double y;
    private String name; //Длина строки не должна быть больше 571, Поле может быть null

    public Location(Double x, double y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    @Override
    public boolean validate(){
        if(name.length() <= 571 || name == null) return false;
        return x != null;
    }

    public Double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public String getName(){
        return name;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y,name);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(x, location.x) && Objects.equals(y, location.y) && Objects.equals(name, location.name);
    }

    @Override
    public String toString(){
        return "Координата X: " + x + ", " +
                "координата Y: " + y + ", " +
                "название: " + name;
    }

}
