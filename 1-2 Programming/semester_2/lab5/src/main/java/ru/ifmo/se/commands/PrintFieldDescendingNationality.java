package ru.ifmo.se.commands;

import ru.ifmo.se.SpecialException.CollectionIsEmptyException;
import ru.ifmo.se.SpecialException.WrongAmountOfElementsException;
import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.models.enums.Country;
import ru.ifmo.se.models.Person;
import ru.ifmo.se.utils.console.Console;


import java.util.List;
import java.util.stream.Collectors;

public class PrintFieldDescendingNationality extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public PrintFieldDescendingNationality(Console console, CollectionManager collectionManager) {
        super("print_field_descending_nationality", "вывести значения поля nationality всех элементов в порядке убывания");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String[] userCommand) {
        try {
            if (!userCommand[1].isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            List<Country> nationalities = collectionManager.getCollection().stream()
                    .map(Person::getNationality)
                    .filter(nationality -> nationality != null)
                    .sorted((n1, n2) -> n2.compareTo(n1))
                    .collect(Collectors.toList());

            nationalities.forEach(System.out::println);

            console.println("Успешный вывод поля nationality");
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
