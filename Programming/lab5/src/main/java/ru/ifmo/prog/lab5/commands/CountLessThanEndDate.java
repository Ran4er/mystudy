package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.exceptions.WrongAmountOfElementsException;
import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.models.Worker;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author ru6ik
 */

public class CountLessThanEndDate extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public CountLessThanEndDate(Console console, CollectionManager collectionManager){
        super("count_less_than_endDate <END_DATE>","вывести количество элементов, значение поля endDate которых меньше заданного");
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

            String strEndDate;
            strEndDate = arguments[1];

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            formatter = formatter.withLocale(Locale.US);
            LocalDate endDate = LocalDate.parse(strEndDate, formatter);

            var lessThanEndDate = lessThanEndDate(endDate);

            if(lessThanEndDate.isEmpty()){
                console.println("Рабочих, чьи даты окончания endDate содержат '" + arguments[1] + "' не обнаружено.");
            } else {
                console.println("Рабочих, чьи даты окончания endDate содержат '" + arguments[1] + "' обнаружено " + lessThanEndDate.size() + "шт.\n");
                lessThanEndDate.forEach(console::println);
            }

            return true;
        } catch (WrongAmountOfElementsException exception){
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        }

        return false;

    }

    private List<Map.Entry<Long, Worker>> lessThanEndDate(LocalDate dateForCount){
        return collectionManager.getCollection().entrySet().stream().filter(worker -> (worker.getValue().getEndDate() != null && worker.getValue().getEndDate().equals(dateForCount)))
                .collect(Collectors.toList());
    }

}
