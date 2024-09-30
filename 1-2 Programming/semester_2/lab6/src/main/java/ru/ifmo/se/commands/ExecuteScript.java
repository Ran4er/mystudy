package ru.ifmo.se.commands;

public class ExecuteScript extends Command {

    public ExecuteScript() {
        super("execute_script <file_name>", "исполнить скрипт из указанного файла");
    }

    @Override
    public boolean apply(String[] userCommand) {
        /*if (!args[1].isEmpty()) {
            System.out.println("Использование: '" + getName() + "'");
            return false;
        }*/
        System.out.println("Выполнение скрипта '" + "'...");
        return true;
    }

}
