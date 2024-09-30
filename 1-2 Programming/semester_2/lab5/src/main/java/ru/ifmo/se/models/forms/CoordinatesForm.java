package ru.ifmo.se.models.forms;

import ru.ifmo.se.SpecialException.IncorrectInputInScriptException;
import ru.ifmo.se.SpecialException.InvalidFormException;
import ru.ifmo.se.models.Coordinates;
import ru.ifmo.se.utils.Interrogator;
import ru.ifmo.se.utils.console.Console;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class CoordinatesForm extends Form<Coordinates> {

    private final Console console;

    public CoordinatesForm(Console console) {
        this.console = console;
    }

    @Override
    public Coordinates build() throws InvalidFormException, IncorrectInputInScriptException {
        var coordinates = new Coordinates(askX(), askY());
        if (!coordinates.validate()) throw new InvalidFormException();
        return coordinates;
    }

    private Integer askX() throws IncorrectInputInScriptException {
        var fileMode = Interrogator.fileMode();
        int x;
        while (true) {
            try {
                console.println("Введите координату X: ");
                console.ps2();
                var strX = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strX);

                x = Integer.parseInt(strX);
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата X не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Координата X должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }

        }

        return x;
    }

    private Float askY() throws IncorrectInputInScriptException {
        var fileMode = Interrogator.fileMode();
        Float y;
        while (true) {
            try {
                console.println("Введите координату Y: ");
                console.ps2();
                String Y = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(Y);

                y = Float.parseFloat(Y);
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата Y не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Координата Y должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return y;
    }

}
