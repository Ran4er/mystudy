package ru.ifmo.prog.lab5.models.form;

import ru.ifmo.prog.lab5.exceptions.IncorrectInputInScriptException;
import ru.ifmo.prog.lab5.exceptions.InvalidFormException;
import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.models.Coordinates;
import ru.ifmo.prog.lab5.utils.Interrogator;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.util.NoSuchElementException;

/**
 *
 * @author ru6ik
 */

public class CoordinatesForm extends Form<Coordinates> {
    private final Console console;

    public CoordinatesForm(Console console){
        this.console = console;
    }

    @Override
    public Coordinates build() throws IncorrectInputInScriptException, InvalidFormException {
        var coordinates = new Coordinates(askX(), askY());
        if(!coordinates.validate()) throw new InvalidFormException();
        return coordinates;
    }

    /**
     * Запрашивает у пользователя координату X.
     * @return координату x.
     * @throws IncorrectInputInScriptException если запущен скрипт и возникает ошибка.
     */

    public Double askX() throws IncorrectInputInScriptException{
        var fileMode = Interrogator.fileMode();
        Double x;
        while (true){
            try {
                console.println("Введите координату X: ");
                console.ps2();

                var strX = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(strX);

                x = Double.parseDouble(strX);
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата X не распознана!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Координата X должна быть представлена числом с плавающей точкой!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception){
                console.printError("непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    /**
     * Запрашивает у пользователя координату Y.
     * @return координату y.
     * @throws IncorrectInputInScriptException если запущен скрипт и возникает ошибка.
     */

    private Double askY() throws IncorrectInputInScriptException{
        var fileMode = Interrogator.fileMode();
        Double y;
        while(true){
            try {
                console.println("Введите координату Y: ");
                console.ps2();

                var strY = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(strY);

                y = Double.parseDouble(strY);
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата Y не распознана!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Координата Y должна быть представлена числом с плавающей точкой!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception){
                console.printError("непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }

}
