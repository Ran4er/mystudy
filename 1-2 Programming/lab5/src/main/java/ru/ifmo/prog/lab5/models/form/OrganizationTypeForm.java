package ru.ifmo.prog.lab5.models.form;

import ru.ifmo.prog.lab5.exceptions.IncorrectInputInScriptException;
import ru.ifmo.prog.lab5.models.OrganizationType;
import ru.ifmo.prog.lab5.utils.Interrogator;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.util.NoSuchElementException;

/**
 * Форма типа организации.
 * @author ru6ik
 */

public class OrganizationTypeForm extends Form<OrganizationType> {
    private final Console console;

    public OrganizationTypeForm(Console console){
        this.console = console;
    }

    @Override
    public OrganizationType build() throws IncorrectInputInScriptException {
        var fileMode = Interrogator.fileMode();

        String strOrganizationType;
        OrganizationType organizationType;
        while(true){
            try {
                console.println("Список типов организаций - " + OrganizationType.names());
                console.println("Введите тип организации:");
                console.ps2();

                strOrganizationType = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(strOrganizationType);

                organizationType = OrganizationType.valueOf(strOrganizationType.toUpperCase());
                break;
            } catch (NoSuchElementException exception){
                console.printError("Тип организации не распознан!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalArgumentException exception){
                console.printError("Типа организации нету в списке!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception){
                console.printError("непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return organizationType;
    }

}
