package ru.ifmo.prog.lab5.commands;

import java.util.Objects;

/**
 * Абстрактный класс для работы с названием и описанием команд
 * @author ru6ik
 */

public abstract class Command implements Executable, Describable{
    private final String name;
    private final String description;

    public Command(String name, String description){
        this.name = name;
        this.description = description;
    }

    /**
     * @return Название и испольозвание команды
     */

    public String getName(){
        return name;
    }

    /**
     * @return Описание команды
     */

    public String getDescription(){
        return description;
    }

    @Override
    public boolean equals(Object e){
        if(this == e) return true;
        if(e == null || getClass() != e.getClass()) return false;
        Command command = (Command) e;
        return Objects.equals(name, command.name) && Objects.equals(description, command.description);
    }

    @Override
    public int hashCode(){
        return Objects.hash(name,description);
    }

    @Override
    public String toString(){
        return "Command{"+
                "name='" + name + '\'' +
                "description='" + description + '\'' +
                '}';

    }

}
