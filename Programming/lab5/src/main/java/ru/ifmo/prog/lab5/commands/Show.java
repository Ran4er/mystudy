package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.utils.console.Console;

/**
 * Команда 'show'. Выводит список всех элементов коллекции.
 * @author ru6ik
 */

public class Show extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public Show(Console console, CollectionManager collectionManager){
        super("show", "вывести все элементы коллекции.");

        this.console = console;
        this.collectionManager = collectionManager;
    }


    /**
     * Выполнение команды.
     * @return Успешность операции.
     */
    @Override
    public boolean apply(String[] arguments){
        if(!(arguments[1].isEmpty())){
            console.println("Используется: '" + getName() + "'");
            return false;
        }

        console.println(collectionManager);
        return true;
    }


}
