package ru.ifmo.se.commands;

import ru.ifmo.se.SpecialException.CollectionIsEmptyException;
import ru.ifmo.se.SpecialException.WrongAmountOfElementsException;
import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.utils.console.Console;

import java.util.Collections;

public class Sort extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public Sort(Console console, CollectionManager collectionManager) {
        super("sort", "сортирует коллекцию в естественном формате");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String[] userCommand) {
        try {
            if (!userCommand[1].isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            Collections.sort(collectionManager.getCollection());

            console.println("Коллекция успешно отсортирована в естественном порядке (по id).");
            return true;
        } catch (WrongAmountOfElementsException e) {
            console.printError("Неверное кол-во аргументов!");
        } catch (CollectionIsEmptyException e) {
            console.printError("Коллекция пуста!");
        } catch (IllegalStateException e) {
            console.printError("Непредвиденная ошибка!");
        }
        return false;
    }

}
