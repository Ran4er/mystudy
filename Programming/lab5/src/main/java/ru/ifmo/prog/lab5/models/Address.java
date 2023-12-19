package ru.ifmo.prog.lab5.models;

import ru.ifmo.prog.lab5.utils.Validatable;

import java.util.Objects;

/**
 * Класс адреса.
 * @author ru6ik
 */

public class Address  implements Validatable {

    private String street; //Поле не может быть null
    private Location town; //Поле не может быть null

    public Address(String street, Location town){
        this.street = street;
        this.town = town;
    }

    /**
     * Валидирует
     * @return true, если верно, иначе false.
     */

    @Override
    public boolean validate(){
        if(street == null) return false;
        return town != null;
    }

    public String getStreet(){
        return street;
    }

    public Location getTown(){
        return town;
    }

    @Override
    public int hashCode(){
        return Objects.hash(street, town);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(town, address.town);
    }

    @Override
    public String toString(){
        return "ул. " + street + ", " +
                town + " дом";
    }



}
