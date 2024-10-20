package ru.ifmo.se;

import ru.ifmo.se.commands.*;
import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.managers.CommandManager;
import ru.ifmo.se.managers.DumpManager;
import ru.ifmo.se.models.Person;
import ru.ifmo.se.utils.Interrogator;
import ru.ifmo.se.utils.console.StandardConsole;
import ru.ifmo.se.utils.Runner;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Interrogator.setUserScanner(new Scanner(System.in));
        var console = new StandardConsole();

        String fileName = System.getenv("PERSON_FILE");
        if (fileName == null) {
            console.println("Не указано имя файла через переменную окружения");
            return;
        }

        var dumpManager = new DumpManager(fileName, console);
        var collectionManager = new CollectionManager(dumpManager);

        Person.updateNextId(collectionManager);
        collectionManager.validateAll();

        var commandManager = new CommandManager(){{
            register("help", new Help(console, this));
            register("info", new Info(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("add", new Add(console, collectionManager));
            register("update", new UpdateID(console, collectionManager));
            register("remove_by_id", new RemoveByID(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("sort", new Sort(console, collectionManager));
            register("add_if_min", new AddIfMin(console, collectionManager));
            register("print_ascending", new PrintAscending(console, collectionManager));
            register("print_descending", new PrintDescending(console, collectionManager));
            register("print_field_descending_nationality", new PrintFieldDescendingNationality(console, collectionManager));
            register("reorder", new Reorder(console, collectionManager));
        }};

        new Runner(console, commandManager).interactiveMode();

    }
}