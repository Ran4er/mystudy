package ru.ifmo.se.commands;

import ru.ifmo.se.managers.CommandManager;
import ru.ifmo.se.utils.console.Console;

/**
 * Command 'help'. Output all commands we have
 * @author Ra4el
 */
public class Help extends Command {

    private final Console console;
    private final CommandManager commandManager;

    public Help(Console console, CommandManager commandManager) {
        super("help", "вывести справку по доступным командам");
        this.console = console;
        this.commandManager = commandManager;
    }

    /**
     * Run command
     * @return successful execution of the command
     */
    @Override
    public boolean apply(String[] userCommand) {
        if (!userCommand[0].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        commandManager.getCommands().values().forEach(command -> {
            console.println(command.getName() + ": " + command.getDescription());
        });
        return true;
    }

}
