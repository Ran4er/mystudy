package ru.ifmo.prog.lab5;

import ru.ifmo.prog.lab5.commands.*;

import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.managers.DumpManager;
import ru.ifmo.prog.lab5.managers.CommandManager;

import ru.ifmo.prog.lab5.models.Worker;
import ru.ifmo.prog.lab5.utils.*;
import ru.ifmo.prog.lab5.utils.console.StandardConsole;

import java.text.ParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        Interrogator.setUserScanner(new Scanner(System.in));
        var console = new StandardConsole();

        if(System.getenv("pathfile") == null){
            console.println("Передадите правильно переменную окружения (с именем: pathfile) выражающую путь до файла");
            System.exit(1);
        }

        var dumpManage = new DumpManager(args[0], console);
        var collectionManager = new CollectionManager(dumpManage);

        Worker.updateNextId(collectionManager);
        collectionManager.validateAll(console);

        var CommandManager = new CommandManager(){{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("remove_by_id", new RemoveById(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("remove_first", new RemoveFirst(console, collectionManager));
            register("head", new Head(console, collectionManager));
            register("remove_greater", new RemoveGreater(console, collectionManager));
            register("remove_all_by_organization", new RemoveAllByOrganization(console, collectionManager));
            register("count_less_than_end_date", new CountLessThanEndDate(console, collectionManager));
            register("filter_contains_name", new FilterContainsName(console, collectionManager));
        }};

        new Runner(console, CommandManager).interactiveMode();

    }
}