package ru.ifmo.se.models.forms;

import ru.ifmo.se.SpecialException.IncorrectInputInScriptException;
import ru.ifmo.se.SpecialException.InvalidFormException;
import ru.ifmo.se.SpecialException.MustBeNotEmptyException;
import ru.ifmo.se.SpecialException.NotInDeclaredLimitsException;
import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.models.Coordinates;
import ru.ifmo.se.models.Location;
import ru.ifmo.se.models.Person;
import ru.ifmo.se.models.enums.Country;
import ru.ifmo.se.utils.Interrogator;
import ru.ifmo.se.utils.console.Console;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PersonForm extends Form<Person>{

    private final Console console;
    private final CollectionManager collectionManager;

    public PersonForm(Console console, CollectionManager collectionManager) {
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Person build() throws InvalidFormException, IncorrectInputInScriptException {
        var person = new Person(
                askName(),
                ZonedDateTime.now(),
                askCoordinates(),
                askHeight(),
                askBirthday(),
                askPasportID(),
                askNationality(),
                askLocation()
        );
        if (!person.validate()) throw new InvalidFormException();
        return person;
    }

     private String askName() {
        var fileMode = Interrogator.fileMode();
        String name;
        while (true) {
            try {
                console.println("Введите имя человека:");
                console.ps2();
                name = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(name);
                if (name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException e) {
                console.printError("Имя не распознано!");
            } catch (MustBeNotEmptyException e) {
                console.printError("Имя не может быть пустым!");
            } catch (IllegalStateException e) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return name;
    }

    private Coordinates askCoordinates() throws InvalidFormException, IncorrectInputInScriptException {
        return new CoordinatesForm(console).build();
    }

    private Float askHeight() {
        var fileMode = Interrogator.fileMode();
        float height;
        while (true) {
            try {
                console.println("Введите рост человека!");
                console.ps2();
                var strHeight = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(strHeight);

                height = Float.parseFloat(strHeight);
                if (height < 0) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Рост не указан!");
            } catch (NotInDeclaredLimitsException e) {
                System.out.println("Рост должен быть больше нуля!");
            } catch (NumberFormatException e) {
                System.out.println("Рост должен быть указан в числах!");
            } catch (IllegalStateException e) {
                System.out.println("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return height;
    }

    private LocalDateTime askBirthday() {
        var fileMode = Interrogator.fileMode();
        LocalDateTime birthday;
        while (true) {
            try {
                console.println("Введите день рождения человека: ");
                console.ps2();
                var strBirthday = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strBirthday);

                birthday = LocalDateTime.parse(strBirthday);
                break;
            } catch (NoSuchElementException e) {
                System.out.println("День рождения не указан!");
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка чтения дня рождения!");
            } catch (DateTimeException e) {
                System.out.println("День рождения должен быть указан в формате даты!");
            } catch (IllegalStateException e) {
                System.out.println("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return birthday;
    }

    private String askPasportID() {
        var fileMode = Interrogator.fileMode();
        String pasportID;
        while (true) {
            try {
                console.println("Введите номер паспорта:");
                console.ps2();
                pasportID = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(pasportID);
                if (pasportID.equals("")) {
                    pasportID = null;
                }
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Номер паспорта не указан!");
            } catch (IllegalStateException e) {
                System.out.println("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return pasportID;
    }

    private Country askNationality() {
        return new CountryForm(console).build();
    }

    private Location askLocation() throws InvalidFormException, IncorrectInputInScriptException {
        return new LocationForm(console).build();
    }

}
