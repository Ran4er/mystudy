package ru.ifmo.se.managers;

import com.google.common.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;

import com.thoughtworks.xstream.security.AnyTypePermission;
import ru.ifmo.se.models.Person;
import ru.ifmo.se.utils.console.Console;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

public class DumpManager {

    private final String fileName;
    private final File file;

    private final Console console;

    public DumpManager(String fileName, Console console) {
        if(!(new File(fileName).exists())) {
            fileName = "../" + fileName;
        }
        this.fileName = fileName;
        this.file = new File(fileName);
        this.console = console;
    }

    /**
     * Write collection into the file
     * @param collection collection
     */
    public void writeCollection(Collection<Person> collection) {
        try (PrintWriter colPrintWriter = new PrintWriter(new File(fileName))) {
            XStream xStream = new XStream();
            xStream.addPermission(AnyTypePermission.ANY); // Разрешение для всех типов
            xStream.alias("person", Person.class);
            colPrintWriter.println(xStream.toXML(collection));
            console.println("Коллекция успешно сохранена в файл");
        } catch (IOException e) {
            console.printError("Загрузочный файл не может быть открыт!");
        }
    }

    /**
     * Return collection from reading file
     * @return read collection, otherwise empty collection
     */
    public Collection<Person> readCollection() {
        if (file.exists() && fileName != null) {
            try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file))) {
                var collectionType = new TypeToken<ArrayList<Person>>() {}.getType();
                XStream xStream = new XStream();
                xStream.addPermission(AnyTypePermission.ANY); // Разрешение для всех типов
                xStream.alias("person", Person.class);
                xStream.alias("persons", List.class);
                List<Person> persons = (List<Person>) xStream.fromXML(inputStreamReader);

                console.println("Коллекция успешно загружена!");
                return persons;
            } catch (FileNotFoundException exception) {
                console.printError("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                console.printError("Загрузочный файл пуст!");
            } catch (IllegalStateException | IOException exception) {
                console.printError("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            console.printError("Файл не найден по переменной окружения!");
        }

        return new ArrayList<>();
    }

}
