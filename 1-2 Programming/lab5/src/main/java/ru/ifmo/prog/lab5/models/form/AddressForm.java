package ru.ifmo.prog.lab5.models.form;

import ru.ifmo.prog.lab5.exceptions.IncorrectInputInScriptException;
import ru.ifmo.prog.lab5.exceptions.InvalidFormException;
import ru.ifmo.prog.lab5.exceptions.MustBeNotEmptyException;
import ru.ifmo.prog.lab5.models.Address;
import ru.ifmo.prog.lab5.models.Location;
import ru.ifmo.prog.lab5.utils.Interrogator;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.util.NoSuchElementException;

/**
 * Форма адреса.
 * @author ru6ik
 */

public class AddressForm extends Form<Address> {
    private final Console console;

    public AddressForm(Console console){
        this.console = console;
    }

    @Override
    public Address build() throws IncorrectInputInScriptException, InvalidFormException {
        var address = new Address(askStreet(), askTown());
        if(!address.validate()) throw new InvalidFormException();
        return address;
    }

    /**
     * Запрашивает у пользователя улицу.
     * @return улицу.
     * @throws IncorrectInputInScriptException если запущен скрипт и возникает ошибка.
     */

    public String askStreet() throws IncorrectInputInScriptException{
        String street;
        var fileMode = Interrogator.fileMode();
        while(true){
            try {
                console.println("Введите название улицы:");
                console.ps2();

                street = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(street);
                if(street.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception){
                console.printError("Улица не распознана!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (MustBeNotEmptyException exception){
                console.println("Улица не может быть пустой!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception){
                console.printError("непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return street;
    }

    /**
     * Запрашивает у пользователя локацию.
     *
     * @return локацию.
     * @throws IncorrectInputInScriptException если запущен скрипт и возникает ошибка.
     */

    private Location askTown() throws IncorrectInputInScriptException, InvalidFormException{
        return new LocationForm(console).build();
    }

}
