package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.exceptions.CollectionIsEmptyException;
import ru.ifmo.prog.lab5.exceptions.IncorrectInputInScriptException;
import ru.ifmo.prog.lab5.exceptions.InvalidFormException;
import ru.ifmo.prog.lab5.exceptions.WrongAmountOfElementsException;
import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.models.Worker;
import ru.ifmo.prog.lab5.models.form.WorkerForm;
import ru.ifmo.prog.lab5.utils.Interrogator;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.util.LinkedHashMap;
import java.util.Map;

public class RemoveGreater extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveGreater(Console console, CollectionManager collectionManager){
        super("remove_greater {element}","удалить из коллекции все элементы, превышающие заданный");
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
            if (!arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
            if(collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            console.println("* Удаление рабочих, которые превышают заданный параметр:");
            int count = 0;
            Long RemoveGreaterId = Long.parseLong(Interrogator.getUserScanner().nextLine().trim());
            for(Map.Entry<Long, Worker> elementId : collectionManager.getCollection().entrySet()){
                if(elementId.getKey() > RemoveGreaterId){
                    collectionManager.removeFromCollection(collectionManager.getById(elementId.getKey()));
                    count++;
                }
            }
            if (count > 0) {
                console.println("Рабочие успешно удалены из списка в количестве: " + count + " шт.");
            } else {
                console.println("Соответствий больше заданного не найдено!");
            }

            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            console.printError("Коллекция пуста!");
        }
        return false;
    }

}
