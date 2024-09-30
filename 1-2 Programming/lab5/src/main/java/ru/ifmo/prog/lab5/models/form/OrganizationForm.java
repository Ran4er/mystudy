package ru.ifmo.prog.lab5.models.form;

import ru.ifmo.prog.lab5.exceptions.IncorrectInputInScriptException;
import ru.ifmo.prog.lab5.exceptions.InvalidFormException;
import ru.ifmo.prog.lab5.exceptions.NotInDeclaredLimitsException;
import ru.ifmo.prog.lab5.models.Address;
import ru.ifmo.prog.lab5.models.Organization;
import ru.ifmo.prog.lab5.models.OrganizationType;
import ru.ifmo.prog.lab5.utils.Interrogator;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.util.NoSuchElementException;

/**
 * Форма организации.
 * @author ru6ik
 */

public class OrganizationForm extends Form<Organization> {
    private final Console console;
    private final int MIN_ANNUAL_TURNOVER = 1;

    public OrganizationForm(Console console){
        this.console = console;
    }

    @Override
    public Organization build() throws IncorrectInputInScriptException, InvalidFormException{
        console.println("Введите null, чтобы оставить организацию пустой или id=x, чтобы использовать организацию. " +
                "Любой другой ввод приведет к созданию новой организации");
        console.ps2();

        var fileMode = Interrogator.fileMode();
        String input = Interrogator.getUserScanner().nextLine().trim();
        if(fileMode) console.println(input);
        if(input.equals("null")) return null;

        console.println("! Создание новой организации:");
        var organization = new Organization(
                askAnnualTurnover(),
                askType(),
                askOfficialAddress()
        );
        if(!organization.validate()) throw new InvalidFormException();
        return organization;
    }

    private int askAnnualTurnover() throws IncorrectInputInScriptException{
        var fileMode = Interrogator.fileMode();
        int annualTurnover;

        while(true){
            try {
                console.println("Введите годовой оборот:");
                console.ps2();

                var strAnnualTurnover = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(strAnnualTurnover);

                annualTurnover = Integer.parseInt(strAnnualTurnover);
                if(annualTurnover < MIN_ANNUAL_TURNOVER) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception) {
                console.printError("Годовой оборот не распознан!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception) {
                console.printError("Годовой оборот должен быть больше нуля!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception) {
                console.printError("непредвиденная ошибка!");
            }
        }

        return annualTurnover;
    }

    private OrganizationType askType() throws IncorrectInputInScriptException{
        return new OrganizationTypeForm(console).build();
    }

    private Address askOfficialAddress() throws IncorrectInputInScriptException, InvalidFormException {
        return new AddressForm(console).build();
    }

}
