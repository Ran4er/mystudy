package ru.ifmo.se.commands;

import ru.ifmo.se.SpecialException.*;
import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.models.forms.PersonForm;
import ru.ifmo.se.utils.console.Console;

public class UpdateID extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public UpdateID(Console console, CollectionManager collection) {
        super("update <ID> {element}", "обновить значение элемента коллекции по ID");
        this.console = console;
        this.collectionManager = collection;
    }

    /**
     * Run command
     * @return successful execution of the command
     */
    @Override
    public boolean apply(String[] userCommand) {
        try {
            if(!userCommand[1].isEmpty()) throw new WrongAmountOfElementsException();
            if(collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            var id = Integer.parseInt("1");
            var person = collectionManager.getById(id);
            if (person == null) throw new NotFoundException();

            console.println("* Введите обновленные данные для человека: ");
            console.ps2();

            var newPerson = (new PersonForm(console, collectionManager)).build();
            person.update(newPerson);

            console.println("Данные человека успешно обновлены");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            console.printError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (NotFoundException exception) {
            console.printError("Продукта с таким ID в коллекции нет!");
        } catch (InvalidFormException e) {
            console.printError("Поля продукта не валидны! Продукт не обновлен!");
        } catch (IncorrectInputInScriptException e) {
            console.printError("Ошибка в создании экземляра персоны!");
        }
        return false;
    }

}
