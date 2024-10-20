package ru.ifmo.se.models.forms;

import ru.ifmo.se.models.enums.Country;
import ru.ifmo.se.utils.Interrogator;
import ru.ifmo.se.utils.console.Console;

import java.util.NoSuchElementException;

public class CountryForm extends Form<Country> {

    private final Console console;

    public CountryForm(Console console) {
        this.console = console;
    }

    @Override
    public Country build() {
        var fileMode = Interrogator.fileMode();
        String strCountry;
        Country country;
        while (true) {
            try {
                console.println("Список всех стран: " + Country.names());
                console.println("Введите страну: ");
                console.ps2();
                strCountry = Interrogator.getUserScanner().nextLine().trim();
                if (fileMode) console.println(strCountry);
                country = Country.valueOf(strCountry.toUpperCase());
                break;
            } catch (NoSuchElementException e) {
                System.out.println("Страна не указана!");
            } catch (IllegalArgumentException e) {
                System.out.println("Такой страны нету в списке!");
            } catch (IllegalStateException e) {
                System.out.println("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return country;
    }
}
