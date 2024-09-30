package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.utils.console.Console;

/**
 * Команда 'save', сохраняет коллекцию в файл.
 * @author ru6ik
 */

public class Save extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public Save(Console console, CollectionManager collectionManager){
        super("save", "сохранить коллекцию в файл.");

        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполнение команды сохранения.
     * @return Успешность выполнения команды.
     */

    @Override
    public boolean apply(String[] arguments){
        if(!(arguments[1].isEmpty())){
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        collectionManager.saveCollection();
        return true;
    }

}
