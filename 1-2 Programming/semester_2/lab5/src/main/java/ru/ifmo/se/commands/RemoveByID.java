package ru.ifmo.se.commands;

import ru.ifmo.se.SpecialException.CollectionIsEmptyException;
import ru.ifmo.se.SpecialException.NotFoundException;
import ru.ifmo.se.SpecialException.WrongAmountOfElementsException;
import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.utils.console.Console;

/**
 * Command 'remove'. Remove element from collection with ID
 * @author Ra4el
 */
public class RemoveByID extends Command {

    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveByID(Console console, CollectionManager collectionManager) {
        super("remove_by_id <ID>", "удалить элемент из коллекции по ID");
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
            if(userCommand[1].isEmpty()) throw new WrongAmountOfElementsException();
            if(collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();
            console.ps2();

            var id = Integer.parseInt(userCommand[1]);
            var productRemove = collectionManager.getById(id);
            if (productRemove == null) throw new NotFoundException();

            collectionManager.removeFromCollection(productRemove);
            console.println("Человек успешно удален из базы");
            return true;

        } catch (WrongAmountOfElementsException e) {
            console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException e) {
            console.printError("Коллекция пуста!");
        } catch (NumberFormatException e) {
            console.printError("ID должен быть представлен числом!");
        } catch (NotFoundException e) {
            console.printError("Человека с таким ID не найдено в базе!");
        }
        return false;
    }

}
