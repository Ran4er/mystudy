package ru.ifmo.prog.lab5.models.form;

import ru.ifmo.prog.lab5.exceptions.IncorrectInputInScriptException;
import ru.ifmo.prog.lab5.models.OrganizationType;
import ru.ifmo.prog.lab5.models.Position;
import ru.ifmo.prog.lab5.utils.Interrogator;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.util.NoSuchElementException;

public class PositionForm extends Form<Position> {
    private final Console console;

    public PositionForm(Console console){
        this.console = console;
    }

    @Override
    public Position build() throws IncorrectInputInScriptException{
        var fileMode = Interrogator.fileMode();

        String strPosition;
        Position position;
        while(true){
            try {
                console.println("Список типов организаций - " + OrganizationType.names());
                console.println("Введите тип организации:");
                console.ps2();

                strPosition = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(strPosition);

                position = Position.valueOf(strPosition.toUpperCase());
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
        return position;
    }


}
