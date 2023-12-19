package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.exceptions.CollectionIsEmptyException;
import ru.ifmo.prog.lab5.exceptions.NotFoundException;
import ru.ifmo.prog.lab5.exceptions.WrongAmountOfElementsException;
import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.utils.console.Console;

/**
 * Команда 'remove_first'. Удаляет первый элемент коллекции.
 * @author ru6ik
 */

public class RemoveFirst extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveFirst(Console console, CollectionManager collectionManager){
        super("remove_first", "удаление первого элемента коллекции.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполнение команды.
     * @return успешность выполнения.
     */

    @Override
    public boolean apply(String[] arguments){
        try {
            if(arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
            if(collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            if(collectionManager.getById(1L) == null) throw new NotFoundException();

            collectionManager.removeFirst();
            console.println("Первый в списке рабочий успешно удален!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            console.printError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (NotFoundException exception) {
            console.printError("Рабочего с таким ID в коллекции нет!");
        }
        return false;
    }

}
