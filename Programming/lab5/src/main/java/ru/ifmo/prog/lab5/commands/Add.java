package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.exceptions.IncorrectInputInScriptException;
import ru.ifmo.prog.lab5.exceptions.InvalidFormException;
import ru.ifmo.prog.lab5.exceptions.WrongAmountOfElementsException;
import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.models.form.WorkerForm;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.text.ParseException;

/**
 * Команда 'add'. Добавляет элемент в коллекцию.
 * @author ru6ik
 */

public class Add extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public Add(Console console, CollectionManager collectionManager){
        super("add","добавить новый элемент в коллекцию.");

        this.console = console;
        this.collectionManager = collectionManager;
    }


    /**
     * Выполняет команду.
     * @return Успешность выполнения.
     */

    @Override
    public boolean apply(String[] arguments){
        try{
            if(!(arguments[1].isEmpty())) throw new WrongAmountOfElementsException();
            console.println("... Создание нового рабочего:");
            collectionManager.addToCollection(new WorkerForm(console, collectionManager).build());
            console.println("Успешное создание рабочего");
            return true;

        } catch (WrongAmountOfElementsException exception){
            console.printError("Неправильное количество аргументов!");
            console.println("Использование: '" + getName() + "'");
        } catch (InvalidFormException exception){
            console.printError("Поля рабочего не валидны! Рабочий не создан!");
        } catch (IncorrectInputInScriptException | ParseException ignored){}

        return false;
    }

}
