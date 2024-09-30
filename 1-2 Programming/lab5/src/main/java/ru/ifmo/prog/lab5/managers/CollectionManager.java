package ru.ifmo.prog.lab5.managers;

import com.google.common.collect.Iterables;

import ru.ifmo.prog.lab5.models.Worker;
import ru.ifmo.prog.lab5.utils.console.Console;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Служит для оперирования коллекцией.
 * @author ru6ik
 */

public class CollectionManager {

    private Map<Long, Worker> collection = new LinkedHashMap<Long, Worker>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;


    public CollectionManager(DumpManager dumpManager) throws ParseException {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;

        loadCollection();
    }

    /**
     * Проверяет валидность всех элементов коллекций.
     * @param console для вывода результата.
     */

    public void validateAll(Console console){
        (new HashMap<>(this.getCollection())).values().forEach(worker -> {
            if(!worker.validate()){
                console.printError("Рабочий с id=" + worker.getId() + " имеет невалидные поля.");
            }
        });
        console.println("* Загруженные рабочие валидны.");
    }

    /**
     *
     * @return коллекция.
     */
    public Map<Long, Worker> getCollection(){
        return collection;
    }

    /**
     *
     * @return последнее время инициализации.
     */

    public LocalDateTime getLastInitTime(){
        return lastInitTime;
    }

    /**
     *
     * @return последнее время сохранения.
     */

    public LocalDateTime getLastSaveTime(){
        return lastSaveTime;
    }

    /**
     *
     * @return тип коллекции.
     */

    public String collectionType(){
        return collection.getClass().getName();
    }

    /**
     *
     * @return размер коллекции.
     */

    public int collectionSize(){
        return collection.size();
    }

    /**
     *
     * @return первый элемент коллекции (null если коллекция пуста).
     */

    public Worker getFirst(){
        if(collection.isEmpty()) return null;
        return collection.get(1);
    }

    /**
     *
     * @return последний элемент коллекции (null если коллекция пуста).
     */

    public Worker getLast(){
        if(collection.isEmpty()) return null;
        return Iterables.getLast(collection.values());
    }

    /**
     *
     * @param id ID элемента.
     * @return элемент по его ID или null, если не найдено.
     */

    public Worker getById(Long id){
        for(Map.Entry<Long, Worker> item : collection.entrySet()){
            if(item.getKey() == id) return item.getValue();
        }
        return null;
    }

    /**
     *
     * @param id ID элемента.
     * @return проверяет, существует ли элемент с таким ID
     */

    public boolean checkExist(int id){
        for(Map.Entry<Long, Worker> item : collection.entrySet()){
            if(item.getKey() == id) return true;
        }
        return false;
    }

    /**
     * Ищет элемент по его значению.
     * @param elementToFind элемент, который нужно найти по значению
     * @return найденный элемент (null, если элемент не найден).
     */

    public Worker getByValue(Worker elementToFind){
        for(Map.Entry<Long, Worker> item : collection.entrySet()){
            if(item.equals(elementToFind)) return item.getValue();
        }
        return null;
    }

    /**
     * Добавление элемента в коллекцию.
     * @param worker элемент для добавления.
     */

    public void addToCollection(Worker worker) throws ParseException {
        Worker.updateNextId(new CollectionManager(dumpManager));
        collection.put(worker.getId(), worker);
        Worker.touchNextId();
    }

    /**
     * Удаляет элемент из коллекции.
     * @param worker элемент для удаления.
     */

    public void removeFromCollection(Worker worker){
        collection.remove(worker);
    }

    /**
     * Удаляет первый элемент коллекции.
     */

    public void removeFirst(){
        collection.remove(collection.entrySet().iterator().next());
    }



    /**
     * Очищает коллекцию.
     */

    public void clearCollection(){
        collection.clear();
    }

    /**
     * Сохраняет коллекцию.
     */

    public void saveCollection(){
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Загружает коллекцию из файла.
     */

    public void loadCollection() throws ParseException {
        collection = (LinkedHashMap<Long, Worker>) dumpManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    @Override
    public String toString(){
        if(collection.isEmpty()) return "Коллекция пуста";
        var last = getLast();

        StringBuilder info = new StringBuilder();
        for(Map.Entry<Long, Worker> item : collection.entrySet()){
            info.append(item.getValue());
            if(item.getValue() != getLast()) info.append("\n\n");
        }
        return info.toString();
    }

}
