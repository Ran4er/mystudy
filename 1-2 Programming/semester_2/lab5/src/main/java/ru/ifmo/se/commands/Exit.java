package ru.ifmo.se.commands;

import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.utils.console.Console;

/**
 * Command 'exit'. Command for leaving from program
 * @author Ra4el
 */
public class Exit extends Command {

    private final Console console;

    public Exit(Console console) {
        super("exit", "завершить программу (без сохранения в файл)");
        this.console = console;
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

        console.println("Завершение выполнения...");
        return true;
    }

}
