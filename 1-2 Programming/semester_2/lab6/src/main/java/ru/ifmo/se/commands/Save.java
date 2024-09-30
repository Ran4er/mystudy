package ru.ifmo.se.commands;

import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.utils.console.Console;

/**
 * Command 'save'. Save collection into the file.
 * @author Ra4el
 */
public class Save extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public Save(Console console, CollectionManager collectionManager) {
        super("save", "сохранить коллекцию в файл");
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

        collectionManager.saveCollection();
        return true;
    }

}
