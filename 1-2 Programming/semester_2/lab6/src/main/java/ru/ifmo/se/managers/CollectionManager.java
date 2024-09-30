package ru.ifmo.se.managers;

import com.google.common.collect.Iterables;

import ru.ifmo.se.models.Person;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements operations with commands
 */
public class CollectionManager {
    private List<Person> collection = new ArrayList<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private final DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;

        loadCollection();
    }

    public void validateAll() {

    }

    /**
     * Gave the collection
     * @return collection
     */
    public List<Person> getCollection() {
        return collection;
    }

    /**
     * Get last init time
     * @return get last init time
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * Get last save time
     * @return last save time
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Give collecttion type
     * @return name type of the collection
     */
    public String getCollectionType() {
        return collection.getClass().getName();
    }

    /**
     * Return collection size
     * @return size of the collection
     */
    public int collectionSize() {
        return collection.size();
    }

    /**
     * Save collection in file
     */
    public void saveCollection() {
        dumpManager.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Loading collection from file
     */
    public void loadCollection() {
        collection = (ArrayList<Person>) dumpManager.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    /**
     * Get first item from collection
     * @return first item from collection
     */
    public Person getFirst() {
        if (collection.isEmpty()) return null;
        return collection.get(0);
    }

    /**
     * Return element with id from collection
     * @param id collection item
     * @return element from collection
     */
    public Person getById(int id) {
        for (Person person : collection) {
            if (person.getId() == id) return person;
        }
        return null;
    }

    /**
     * Check exists element by id
     * @param id id of the element
     * @return does an element with this ID exist, if correct, return true, otherwise false
     */
    public boolean checkExist(int id) {
        for (Person person : collection) {
            if (person.getId() == id) return true;
        }
        return false;
    }

    /**
     * Returns the element we are looking for
     * @param elementToFind search element
     * @return the element we are looking for, otherwise null
     */
    public Person getByValue(Person elementToFind) {
        for (Person person : collection) {
            if (person.getId() == elementToFind.getId()) return person;
        }
        return null;
    }

    /**
     * Add element to collection
     * @param elementToAdd element to add
     */
    public void addToCollection(Person elementToAdd) {
        collection.add(elementToAdd);
        Person.touchNextId();
    }

    /**
     * Remove an item from a collection
     * @param elementToRemove element to remove
     */
    public void removeFromCollection(Person elementToRemove) {
        collection.remove(elementToRemove);
    }

    /**
     * Clear collection
     */
    public void clearCollection() {
        collection.clear();
    }

    /**
     * Give last item from collection
     * @return last item from collection
     */
    public Person getLast() {
        if (collection.isEmpty()) return null;
        return Iterables.getLast(collection);
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста";
        var last = getLast();

        StringBuilder info = new StringBuilder();
        for (Person person : collection) {
            info.append(person);
            if(person != last) info.append("\n\n");
        }

        return info.toString();

    }
}
