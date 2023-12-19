package ru.ifmo.prog.lab5.models.form;

import ru.ifmo.prog.lab5.exceptions.IncorrectInputInScriptException;
import ru.ifmo.prog.lab5.exceptions.InvalidFormException;
import ru.ifmo.prog.lab5.exceptions.NotInDeclaredLimitsException;
import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.models.Coordinates;
import ru.ifmo.prog.lab5.models.Organization;
import ru.ifmo.prog.lab5.models.Position;
import ru.ifmo.prog.lab5.models.Worker;
import ru.ifmo.prog.lab5.utils.console.Console;
import ru.ifmo.prog.lab5.utils.Interrogator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.NoSuchElementException;
import ru.ifmo.prog.lab5.exceptions.MustBeNotEmptyException;

/**
 * Форма рабочего.
 * @author ru6ik
 */

public class WorkerForm extends Form<Worker> {
    private final Console console;
    private final CollectionManager collectionManager;
    private final Long MIN_SALARY = 0L;


    public WorkerForm(Console console, CollectionManager collectionManager){
        this.console = console;
        this.collectionManager = collectionManager;
    }

    @Override
    public Worker build() throws IncorrectInputInScriptException, InvalidFormException{
        var worker = new Worker(
                askName(),
                askCoordinates(),
                LocalDateTime.now(),
                askSalary(),
                askStartDate(),
                askEndDate(),
                askPosition(),
                askOrganization()
        );
        if(!worker.validate()) throw new InvalidFormException();
        return worker;
    }

    private String askName() throws IncorrectInputInScriptException{
        String name;
        var fileMode = Interrogator.fileMode();
        while (true){
            try {
                console.println("Введите имя рабочего: ");
                console.ps2();

                name = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(name);
                if(name.equals("")) throw new MustBeNotEmptyException();
                break;
            } catch (NoSuchElementException exception){
                console.printError("Название не распознано!");
            } catch (MustBeNotEmptyException exception){
                console.printError("Имя не может быть пустым!");
            } catch (IllegalStateException exception){
                console.printError("непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return name;
    }

    private Coordinates askCoordinates() throws IncorrectInputInScriptException,InvalidFormException{
        return new CoordinatesForm(console).build();
    }

    private Long askSalary() throws IncorrectInputInScriptException{
        var fileMode = Interrogator.fileMode();
        Long salary;
        while(true){
            try {
                console.println("Введите зарплату: ");
                console.ps2();

                var strSalary = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(strSalary);

                salary = Long.parseLong(strSalary);
                if(salary <= MIN_SALARY) throw new NotInDeclaredLimitsException();
                break;
            } catch (NoSuchElementException exception){
                console.printError("Зарплата рабочего не распознана!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (NotInDeclaredLimitsException exception){
                console.printError("Зарплата рабочего должна быть больше нуля!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (NumberFormatException exception){
                console.printError("Цена продукта должна быть представлена числом!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (NullPointerException | IllegalStateException exception){
                console.printError("непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return salary;
    }

    private Date askStartDate() throws IncorrectInputInScriptException{
        var fileMode = Interrogator.fileMode();
        Date startDate;
        while(true){
            try {
                console.println("Введите дату начала работы: ");
                console.ps2();

                var strStartDate = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(strStartDate);
                if(strStartDate.equals(LocalDate.now())){
                    strStartDate = null;
                }

                DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                startDate = formatter.parse(strStartDate);
                break;
            } catch (NoSuchElementException exception){
                console.printError("Дата начала работы не распознана!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (ParseException e) {
                console.println("Дата начала работы должна быть представлена в виде yyyy-mm-dd");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception){
                console.printError("непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return startDate;
    }

    private LocalDate askEndDate() throws IncorrectInputInScriptException{
        var fileMode = Interrogator.fileMode();
        LocalDate endDate;
        while(true){
            try {
                console.println("Введите дату начала работы: ");
                console.ps2();

                var strEndDate = Interrogator.getUserScanner().nextLine().trim();
                if(fileMode) console.println(strEndDate);
                if(strEndDate.equals(LocalDate.now())){
                    strEndDate = null;
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                formatter = formatter.withLocale(Locale.US);
                endDate = LocalDate.parse(strEndDate, formatter);
                break;
            } catch (NoSuchElementException exception){
                console.printError("Дата начала работы не распознана!");
                if(fileMode) throw new IncorrectInputInScriptException();
            } catch (IllegalStateException exception){
                console.printError("непредвиденная ошибка!");
                System.exit(0);
            }
        }

        return endDate;
    }

    private Position askPosition() throws IncorrectInputInScriptException{
        return new PositionForm(console).build();
    }

    private Organization askOrganization() throws IncorrectInputInScriptException, InvalidFormException {
        return new OrganizationForm(console).build();
    }

}
