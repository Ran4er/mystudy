package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.exceptions.CollectionIsEmptyException;
import ru.ifmo.prog.lab5.exceptions.WrongAmountOfElementsException;
import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.models.Worker;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Команда 'filter_contains_name'. Фильтр рабочих по полю name.
 * @author ru6ik
 */

public class FilterContainsName extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public FilterContainsName(Console console, CollectionManager collectionManager){
        super("filter_contains_name <NAME>","вывести элементы, значение поля name которых содержит заданную подстрокy.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    /**
     * Выполняет команду.
     * @return успешность выполнения.
     */

    @Override
    public boolean apply(String[] arguments){
        try {
            if(arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
            if(collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            var workers = filterByName(arguments[1]);

            if(workers.isEmpty()){
                console.println("Рабочих, чьи имена name содержат '" + arguments[1] + "' не обнаружено.");
            } else {
                console.println("Рабочих, чьи имена name содержат '" + arguments[1] + "' обнаружено " + workers.size() + "шт.\n");
                workers.forEach(console::println);
            }

            return true;
        } catch (WrongAmountOfElementsException exception){
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception){
            console.printError("Коллекция пуста!");
        }

        return false;
    }

    private List<Map.Entry<Long, Worker>> filterByName(String nameSubstring){
        return collectionManager.getCollection().entrySet().stream().filter(worker -> (worker.getValue().getName() != null && worker.getValue().getName().contains(nameSubstring)))
                .collect(Collectors.toList());
    }

}
