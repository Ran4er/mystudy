package ru.ifmo.prog.lab5.models.form;

import ru.ifmo.prog.lab5.exceptions.IncorrectInputInScriptException;
import ru.ifmo.prog.lab5.exceptions.InvalidFormException;
import ru.ifmo.prog.lab5.exceptions.MustBeNotEmptyException;
import ru.ifmo.prog.lab5.models.Location;
import ru.ifmo.prog.lab5.utils.Interrogator;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.util.NoSuchElementException;

/**
 * Форма локации.
 * @author ru6ik
 */

public class LocationForm extends Form<Location>{
    private final Console console;

    public LocationForm(Console console){
        this.console = console;
    }

    @Override
    public Location build() throws IncorrectInputInScriptException, InvalidFormException{
        var location = new Location(askX(), askY(), askName());
        if(!location.validate()) throw new InvalidFormException();
        return location;
    }

    /**
     * Запрашивает у пользователя координату X.
     * @return координату X.
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
     * @return координату Y.
     * @throws IncorrectInputInScriptException если запущен скрипт и возникает ошибка.
     */

    public double askY() throws IncorrectInputInScriptException{
        var fileMode = Interrogator.fileMode();
        double y;
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

    /**
     * Запрашивает имя локации.
     * @return имя локации.
     * @throws IncorrectInputInScriptException если запущен скрипт и возникает ошибка.
     */

    public String askName() throws IncorrectInputInScriptException{
        String name;
        var fileMode = Interrogator.fileMode();
        while(true){
            try {
                console.println("Введите название локации:");
                console.ps2();

                name = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(name);
                if(name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception){
                console.printError("Локация не распознана!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception){
                console.println("Локация не может быть пустой!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception){
                console.printError("непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return name;
    }

}
