package ru.ifmo.prog.lab5.commands;

import ru.ifmo.prog.lab5.exceptions.*;
import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.models.Organization;
import ru.ifmo.prog.lab5.models.Worker;
import ru.ifmo.prog.lab5.models.form.OrganizationForm;
import ru.ifmo.prog.lab5.utils.Interrogator;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.util.Map;

/**
 * Команда 'remove_all_by_organization'. Удаляет из коллекции все элементы значение поля organization которого эквивалентно заданному.
 * @author ru6ik
 */

public class RemoveAllByOrganization extends Command{
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveAllByOrganization(Console console, CollectionManager collectionManager){
        super("remove_all_by_organization <ORGANIZATION_NAME>","удалить из коллекции все элементы, значение поля organization которого эквивалентно заданному");
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

            console.println("* Удаление рабочих, которые из организации:");
            int count = 0;
            var organizationByRemove = (new OrganizationForm(console).build());
            for(Map.Entry<Long, Worker> element : collectionManager.getCollection().entrySet()){
                if(element.getValue().getOrganization().equals(organizationByRemove)){
                    collectionManager.removeFromCollection(collectionManager.getById(element.getKey()));
                    count++;
                }
            }

            if (count > 0){
                console.println("Рабочие успешно удалены из списка в количестве: " + count + " шт.");
            } else {
                console.println("Рабочих из такой заданной организации не найдено!");
            }

            return true;
        } catch (WrongAmountOfElementsException exception) {
            console.println("Использование: '" + getName() + "'");
        } catch (CollectionIsEmptyException exception) {
            console.printError("Коллекция пуста!");
        } catch (InvalidFormException | IncorrectInputInScriptException e) {}
        return false;
    }

}
