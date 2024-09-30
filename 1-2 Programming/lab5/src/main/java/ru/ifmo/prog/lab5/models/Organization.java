package ru.ifmo.prog.lab5.models;

import ru.ifmo.prog.lab5.utils.Validatable;

import java.util.Objects;

/**
 * Класс организации.
 * @author ru6ik
 */

public class Organization implements Validatable {
    private int annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле может быть null
    private Address officialAddress; //Поле не может быть null

    public Organization(int annualTurnover, OrganizationType type, Address officialAddress){
        this.annualTurnover = annualTurnover;
        this.type = type;
        this.officialAddress = officialAddress;
    }

    /**
     * Валидирует правильность полей.
     * @return true, если все верно, иначе false.
     */

    public boolean validate(){
        if(annualTurnover <= 0) return false;
        return officialAddress != null;
    }

    public int getAnnualTurnover(){
        return annualTurnover;
    }

    public OrganizationType getType(){
        return type;
    }

    public Address getOfficialAddress(){
        return officialAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        if(type != that.type) return false;
        return annualTurnover == that.annualTurnover
                && Objects.equals(officialAddress, that.officialAddress)
                && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annualTurnover, type, officialAddress);
    }

    @Override
    public String toString(){
        return "Годовой оборот \"" + annualTurnover + "\"" +
                "; Вид: " + type +
                "; Адрес: " + officialAddress;
    }
}
