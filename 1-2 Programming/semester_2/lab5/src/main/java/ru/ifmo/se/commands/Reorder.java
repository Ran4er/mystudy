package ru.ifmo.se.commands;

import ru.ifmo.se.SpecialException.CollectionIsEmptyException;
import ru.ifmo.se.SpecialException.WrongAmountOfElementsException;
import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.models.Person;
import ru.ifmo.se.utils.console.Console;

import java.util.Collections;
import java.util.List;

public class Reorder extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public Reorder(Console console, CollectionManager collectionManager) {
        super("reorder", "сортирует коллекцию в порядке, обратном нынешнему");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String[] userCommand) {
        try{
            if(!userCommand[1].isEmpty()) throw new WrongAmountOfElementsException();
            if(collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            List<Person> collection = collectionManager.getCollection();
            Collections.sort(collection, Collections.reverseOrder());
            collection.forEach(System.out::println);

            console.println("Коллекция успешно выведена!");
            return true;
        } catch (WrongAmountOfElementsException e) {
            console.printError("Неверное кол-во аргументов!");
        } catch (CollectionIsEmptyException e) {
            console.printError("Коллекция пуста!");
        }
        return false;
    }

}
