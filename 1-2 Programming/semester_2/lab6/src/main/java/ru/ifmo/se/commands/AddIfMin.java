package ru.ifmo.se.commands;

import ru.ifmo.se.SpecialException.IncorrectInputInScriptException;
import ru.ifmo.se.SpecialException.InvalidFormException;
import ru.ifmo.se.SpecialException.WrongAmountOfElementsException;
import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.models.Person;
import ru.ifmo.se.models.forms.PersonForm;
import ru.ifmo.se.utils.console.Console;

public class AddIfMin extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public AddIfMin(Console console, CollectionManager collectionManager) {
        super("add_if_min {element}", "добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String[] userCommand) {
        try {
            if (!userCommand[1].isEmpty()) throw new WrongAmountOfElementsException();
            console.println("* Создание новой персоны в базе (add_if_min): ");
            var person = (new PersonForm(console, collectionManager)).build();

            var height = minHeight();
            if (person.getHeight() < height) {
                collectionManager.addToCollection(person);
                console.println("Человек успешно добавлен");
            } else {
                console.println("Человек не добавлен, рост не минимален: (" + person.getHeight() + ">" + height + ")");
            }
            return true;

        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля человека не валидны! Человек не добавлен в базу!");
        } catch (IncorrectInputInScriptException e) {
            console.printError("Ошибка в присваивании персоны и её полей");
        }
        return false;
    }

    private Double minHeight() {
        return collectionManager.getCollection().stream()
                .map(Person::getHeight)
                .filter(height -> height != null)
                .mapToDouble(Float::doubleValue)
                .min()
                .orElse(2.55D);
    }

}
