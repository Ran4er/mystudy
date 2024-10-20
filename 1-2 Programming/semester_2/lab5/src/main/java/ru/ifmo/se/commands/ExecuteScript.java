package ru.ifmo.se.commands;

import ru.ifmo.se.utils.console.Console;

public class ExecuteScript extends Command {

    private final Console console;

    public ExecuteScript(Console console) {
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
        this.console = console;
    }

    @Override
    public boolean apply(String[] userCommand) {
        if (userCommand[1].isEmpty()) {
            console.println("Использование: '" + getName() + "'");
            return false;
        }
        console.println("Выполнение скрипта '" + userCommand[1] + "'...");
        return true;
    }

}
