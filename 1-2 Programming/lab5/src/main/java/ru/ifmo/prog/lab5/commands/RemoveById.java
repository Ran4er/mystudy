package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.exceptions.*;
import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.models.form.WorkerForm;
import ru.ifmo.prog.lab5.utils.console.Console;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по его ID.
 * @author ru6ik
 */

public class RemoveById extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveById(Console console, CollectionManager collectionManager){
        super("remove_by_id <ID>", "удаление объекта из коллекции по его ID.");
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

            var id = Long.parseLong(arguments[1]);
            var workerToRemove = collectionManager.getById(id);
            if(workerToRemove == null) throw new NotFoundException();

            collectionManager.removeFromCollection(workerToRemove);
            console.println("Рабочий успешно удален!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            console.printError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (NotFoundException exception) {
            console.printError("Продукта с таким ID в коллекции нет!");
        }
        return false;
    }

}
