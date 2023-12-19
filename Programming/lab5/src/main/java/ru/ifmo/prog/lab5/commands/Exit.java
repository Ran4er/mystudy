package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.utils.console.Console;

/**
 * Команда 'exit', которая служит для завершения работы
 * @author ru6ik
 **/

public class Exit extends Command{

    private final Console console;

    public Exit(Console console){

        super("exit", "завершает работу программы(без сохранения в файл)");
        this.console = console;

    }

    /**
     *
     * Выполняет команду
     * @return Успешность выполнения команды
     */

    @Override
    public boolean apply(String[] arguments){

        if(!arguments[1].isEmpty()){
            console.println("Использование: '" + getName() + "'");
            return false;
        }

        console.println("Завершение работы...");
        return true;

    }

}
