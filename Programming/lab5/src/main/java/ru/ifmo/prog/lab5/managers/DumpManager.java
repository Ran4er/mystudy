package ru.ifmo.prog.lab5.managers;

import ru.ifmo.prog.lab5.models.*;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Класс для записи и считывания из коллекции из/в файл.
 * @author ru6ik
 */

public class DumpManager {

    private final String fileName;
    private final Console console;

    public DumpManager(String fileName, Console console){
        if(!(new File(fileName).exists())){
            fileName = "../" + fileName;
        }
        this.fileName = fileName;
        this.console = console;
    }

    /**
     * Записывает коллекцию в файл.
     * @param collection коллекция.
     */

    public void writeCollection(Map<Long, Worker> collection){
        try(FileOutputStream fos = new FileOutputStream(fileName); BufferedOutputStream collectionBufferedWriter = new BufferedOutputStream(fos)){
            //collectionBufferedWriter.println();
            console.println("Коллекция успешно загружена в файл!");
        } catch (IOException ex){
            console.printError("Загрузочный файл не может быть открыт!");
        }
    }

    public Map<Long, Worker> readCollection() throws ParseException {

        Map<Long, Worker> collection = new LinkedHashMap<Long, Worker>();

        if(fileName != null && !fileName.isEmpty()){
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName));
                BufferedReader reader = new BufferedReader(new InputStreamReader(bis))) {

                String readLine;
                boolean skipHeader = true;

                while((readLine = reader.readLine()) != null) {
                    if(skipHeader){
                        skipHeader = false;
                        continue;
                    }

                    String[] data = readLine.split(",");

                    Date startDate;
                    LocalDate endDate;

                    Long id = Long.parseLong(data[0]);
                    String name = data[1];
                    String[] coordinates = data[2].split(";");
                    LocalDateTime creationDate = LocalDateTime.now();
                    Long salary = Long.parseLong(data[4]);
                    String strStartDate = data[5];
                    String strEndDate = data[6];
                    String position = data[7];
                    String[] organization = data[8].split(";");

                    DateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
                    startDate = formatter.parse(strStartDate);

                    DateTimeFormatter formatterForDateTime = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
                    formatterForDateTime = formatterForDateTime.withLocale(Locale.US);
                    endDate = LocalDate.parse(strEndDate, formatterForDateTime);

                    Worker worker = new Worker(name, new Coordinates(Double.parseDouble(coordinates[0])
                            ,Double.parseDouble(coordinates[1])), creationDate, salary, startDate, endDate
                            ,Position.valueOf(position.toUpperCase())
                            ,new Organization(Integer.parseInt(organization[0]), OrganizationType.valueOf(organization[1].toUpperCase())
                            ,new Address(organization[2], new Location(Double.parseDouble(organization[3])
                            ,Double.parseDouble(organization[4]), organization[5]))));
                    collection.put(id, worker);

                }

                console.println("Коллекция успешно загружена из файла!");
            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден");
            } catch (NoSuchElementException exception) {
                console.printError("Загрузочный файл пуст");
            } catch (IllegalStateException | IOException exception) {
                console.printError("Непредвиденная ошибка!");
            }
        } else{
            console.printError("Переменная окружения с путем загрузочного не найдены!");
        }

        return collection;
    }
}
