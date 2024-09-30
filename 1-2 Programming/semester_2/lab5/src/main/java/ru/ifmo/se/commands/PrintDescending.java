package ru.ifmo.se.commands;

import ru.ifmo.se.SpecialException.CollectionIsEmptyException;
import ru.ifmo.se.SpecialException.WrongAmountOfElementsException;
import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.models.Person;
import ru.ifmo.se.utils.console.Console;

import java.util.Comparator;

public class PrintDescending extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public PrintDescending(Console console, CollectionManager collectionManager) {
        super("print_descending", "выводит элементы в порядке убывания");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String[] userCommand) {
        try{
            if(!userCommand[1].isEmpty()) throw new WrongAmountOfElementsException();
            if(collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            collectionManager.getCollection().stream()
                    .sorted(Comparator.comparingInt(Person::getId).reversed())
                    .forEach(System.out::println);
            console.println("Коллекция успешно выведена!");

            return true;
        } catch (WrongAmountOfElementsException e) {
            console.printError("Неверное кол-во аргументов!");
        } catch (CollectionIsEmptyException e) {
            console.printError("Коллекция пуста!");
        } catch (IllegalArgumentException e) {
            console.printError("Непредвиденная ошибка!");
        }
        return false;
    }

}
