package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.managers.CommandManager;
import ru.ifmo.prog.lab5.utils.console.Console;

/**
 * Команда 'help'. Вывод справки по всем доступным командам.
 * @author ru6ik
 */

public class Help extends Command{
    private final Console console;
    private final CommandManager commandManager;

    public Help(Console console, CommandManager commandManager){
        super("help", "вывести справку по доступным командам.");

        this.console = console;
        this.commandManager = commandManager;

    }


    @Override
    public boolean apply(String[] arguments){
        if(!(arguments[1].isEmpty())){
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        commandManager.getCommands().values().forEach(command -> {
            console.printTable(command.getName(), command.getDescription());
        });
        return true;
    }

}
