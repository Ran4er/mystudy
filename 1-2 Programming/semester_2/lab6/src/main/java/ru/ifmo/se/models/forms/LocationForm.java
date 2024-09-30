package ru.ifmo.se.models.forms;

import ru.ifmo.se.SpecialException.IncorrectInputInScriptException;
import ru.ifmo.se.SpecialException.InvalidFormException;
import ru.ifmo.se.SpecialException.MustBeNotEmptyException;
import ru.ifmo.se.models.Location;
import ru.ifmo.se.utils.Interrogator;
import ru.ifmo.se.utils.console.Console;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LocationForm extends Form<Location> {

    private final Console console;

    public LocationForm(Console console) {
        this.console = console;
    }

    @Override
    public Location build() throws InvalidFormException, IncorrectInputInScriptException {
        var location = new Location(askX(), askY(), askZ(), askName());
        if (!location.validate()) throw new InvalidFormException();
        return location;
    }

    public Long askX() throws IncorrectInputInScriptException {
        var fileMode = Interrogator.fileMode();
        Long x;
        while (true) {
            try {
                console.println("Введите координату X:");
                console.ps2();
                String X = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(X);

                x = Long.parseLong(X);
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

    public Float askY() throws IncorrectInputInScriptException {
        var fileMode = Interrogator.fileMode();
        Float y;
        while (true) {
            try {
                console.println("Введите координату Y:");
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

    public Double askZ() throws  IncorrectInputInScriptException {
        var fileMode = Interrogator.fileMode();
        Double z;
        while (true) {
            try {
                console.println("Введите координату Z:");
                console.ps2();
                String Z = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(Z);

                z = Double.parseDouble(Z);
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Координата Z не распознана!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception) {
                console.printError("Координата Z должна быть представлена числом!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return z;
    }

    public String askName() throws  IncorrectInputInScriptException {
        var fileMode = Interrogator.fileMode();
        String name;
        while (true) {
            try {
                console.println("Введите имя:");
                console.ps2();

                name = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if(name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Название не распознано!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception) {
                console.printError("Название не может быть пустым!");
                if (fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return name;
    }

}
