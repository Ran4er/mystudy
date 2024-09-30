package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.exceptions.*;
import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.models.form.WorkerForm;
import ru.ifmo.prog.lab5.utils.console.Console;

/**
 * Команда 'update'. Обновляет элемент коллекции.
 * @author ru6ik
 */

public class Update extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public Update(Console console, CollectionManager collectionManager){
        super("update <ID> {element}","обновить значение элемента коллекции по его KEY.");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean apply(String[] arguments){
        try {
            if(arguments[1].isEmpty()) throw new WrongAmountOfElementsException();
            if(collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            var id = Long.parseLong(arguments[1]);
            var worker = collectionManager.getById(id);
            if(worker == null) throw new NotFoundException();

            console.println("* Введите данные обновленного рабочего");
            console.ps2();

            var newWorker = (new WorkerForm(console, collectionManager)).build();
            worker.update(newWorker);

            console.println("Рабочий успешно добавлен/обновлен!");
            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            console.printError("Коллекция пуста!");
        } catch (NumberFormatException exception) {
            console.printError("ID должен быть представлен числом!");
        } catch (NotFoundException exception) {
            console.printError("Продукта с таким ID в коллекции нет!");
        } catch (IncorrectInputInScriptException e) {
            e.printStackTrace();
        } catch (InvalidFormException e) {
            console.printError("Поля продукта не валидны! Продукт не обновлен!");
        }
        return false;

    }

}
