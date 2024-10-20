package ru.ifmo.se.models;

import ru.ifmo.se.models.enums.Country;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;

import ru.ifmo.se.managers.CollectionManager;
import ru.ifmo.se.utils.Validatable;

/**
 * Class Person. He gave all information about person
 * @author Ra4el
 */

public class Person implements Validatable, Comparable<Person> {
    private static int nextId = 1;

    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float height; //Значение поля должно быть больше 0
    private java.time.LocalDateTime birthday; //Поле не может быть null
    private String passportID; //Значение этого поля должно быть уникальным, Поле не может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле не может быть null

    public Person(String name, ZonedDateTime creationDate, Coordinates coordinates, float height,
                  LocalDateTime birthday, String passportID, Country nationality, Location location) {
        this.id = nextId;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.height = height;
        this.birthday = birthday;
        this.passportID = passportID;
        this.nationality = nationality;
        this.location = location;
    }

    /**
     * Updates the next ID pointer
     * @param collectionManager manager of the collections
     */
    public static void updateNextId(CollectionManager collectionManager) {
        var maxId = collectionManager.getCollection().stream()
                .filter(Objects::nonNull).map(Person::getId)
                .mapToInt(Integer::intValue).max().orElse(0);
        nextId = maxId + 1;
    }

    public static void touchNextId() {
        nextId++;
    }

    /**
     * Validate all fields
     * @return true if everything is true, otherwise false
     */
    @Override
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (height <= 0) return false;
        if (passportID == null) return false;
        return (location != null);
    }

    public void update(Person person) {
        this.id = person.id;
        this.name = person.name;
        this.coordinates = person.coordinates;
        this.creationDate = person.creationDate;
        this.height = person.height;
        this.birthday = person.birthday;
        this.passportID = person.passportID;
        this.nationality = person.nationality;
        this.location = person.location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getPassportID() {
        return passportID;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Float.compare(height, person.height) == 0 && Objects.equals(name, person.name) && Objects.equals(coordinates, person.coordinates) && Objects.equals(creationDate, person.creationDate) && Objects.equals(birthday, person.birthday) && Objects.equals(passportID, person.passportID) && nationality == person.nationality && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, height, birthday, passportID, nationality, location);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", height=" + height +
                ", birthday=" + birthday +
                ", passportID='" + passportID + '\'' +
                ", nationality=" + nationality +
                ", location=" + location +
                '}';
    }

    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.id, other.id);
    }
}