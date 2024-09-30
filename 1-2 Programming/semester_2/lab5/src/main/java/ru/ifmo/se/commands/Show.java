package ru.ifmo.se.commands;

import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.utils.console.Console;

/**
 * Command 'show'. Show all elements from collection
 * @author Ra4el
 */
public class Show extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public Show(Console console, CollectionManager collectionManager) {
        super("show", "вывести все элементы коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Run command
     * @return successful execution of the command
     */
    @Override
    public boolean apply(String[] userCommand) {
        if(!userCommand[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println(collectionManager);
        return true;
    }

}
