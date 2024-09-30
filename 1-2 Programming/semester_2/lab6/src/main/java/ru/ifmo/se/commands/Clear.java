package ru.ifmo.se.commands;

import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.utils.console.Console;

/**
 * Command 'clear'. Clear collection.
 * @author Ra4el
 */
public class Clear extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
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

        collectionManager.clearCollection();
        console.println("Коллекция очишена!");
        return true;
    }

}
