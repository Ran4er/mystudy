package ru.ifmo.se.commands;

import ru.ifmo.se.SpecialException.IncorrectInputInScriptException;
import ru.ifmo.se.SpecialException.InvalidFormException;
import ru.ifmo.se.SpecialException.WrongAmountOfElementsException;
import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.models.forms.PersonForm;
import ru.ifmo.se.utils.console.Console;


/**
 * Command 'add'. Adding new element in collection
 * @author Ra4el
 */
public class Add extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Run command
     * @return successful execution of the command
     */
    @Override
    public boolean apply(String[] userCommand) {
        try {
            if(!userCommand[1].isEmpty()) throw new WrongAmountOfElementsException();
            console.println("* Создание новой персоны");
            collectionManager.addToCollection((new PersonForm(console, collectionManager).build()));
            console.println("Персона успешно добавлена!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception) {
            console.printError("Поля продукта не валидны! Продукт не создан!");
        } catch (IncorrectInputInScriptException e) {
            console.printError("Ошибка в добавлении в коллекцию!");
        }
        return false;
    }

}
