package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.utils.console.Console;

/**
 * Команда 'clear'. Очищает коллекцию.
 * @author ru6ik
 */

public class Clear extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public Clear(Console console, CollectionManager collectionManager){
        super("clear", "очистить коллекцию.");

        this.console = console;
        this.collectionManager = collectionManager;

    }

    /**
     * Выполняет команду.
     * @return Успешность выполнения.
     */

    @Override
    public boolean apply(String[] arguments){
        if(!(arguments[1].isEmpty())){
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        collectionManager.clearCollection();
        console.println("Коллекция очищена!");
        return true;
    }

}
