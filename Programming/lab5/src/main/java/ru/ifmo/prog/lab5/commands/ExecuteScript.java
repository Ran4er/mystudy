package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.utils.console.Console;

/**
 * Команда 'execute_script'. Выполняет скрипт из файла.
 * @author ru6ik
 */

public class ExecuteScript extends Command{
    private final Console console;

    public ExecuteScript(Console console){
        super("execute_script <file_name>", "исполнить скрипт из указанного файла.");
        this.console = console;
    }

    /**
     * Выполняет команду.
     * @return успешность выполнения команды.
     */

    @Override
    public boolean apply(String[] arguments){
        if(arguments[1].isEmpty()){
            console.printError("Использование: '" + getName() + "'");
            return false;
        }

        console.println("Выполнение скрипта: '" + arguments[1] + "' ...");
        return true;
    }

}
