package ru.ifmo.prog.lab5.models;

import ru.ifmo.prog.lab5.managers.CollectionManager;
import ru.ifmo.prog.lab5.utils.Element;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * Класс рабочих.
 * @author ru6ik
 */

public class Worker extends Element{
    private static Long nextId = 1L;

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long salary; //Поле не может быть null, Значение поля должно быть больше 0
    private java.util.Date startDate; //Поле не может быть null
    private java.time.LocalDate endDate; //Поле может быть null
    private Position position; //Поле не может быть null
    private Organization organization; //Поле может быть null

    public Worker(String name, Coordinates coordinates, LocalDateTime creationDate, Long salary, Date startDate, LocalDate endDate, Position position, Organization organization){
        this.id = nextId;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.salary = salary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.organization = organization;
    }

    /**
     * Обновляет указатель следующего ID.
     * @param collectionManager менеджер коллекций.
     */

    public static void updateNextId(CollectionManager collectionManager){
        var maxId = collectionManager
                .getCollection().values().stream()
                .filter(Objects::nonNull)
                .mapToLong(Worker::getId) // Применяем mapToLong к Worker::getId
                .max().orElse(0);

        nextId = maxId + 1;
    }

    /**
     * Проверяет валидность полей.
     * @return true, если все верно, иначе false.
     */

    @Override
    public boolean validate(){
        if(id <= 0) return false;
        if(name == null || name.isEmpty()) return false;
        if(coordinates == null) return false;
        if(creationDate == null) return false;
        if(salary <= 0) return false;
        if(startDate == null) return false;
        if(endDate == null) return false;
        if(position == null) return false;
        return organization == null;
    }

    public void update(Worker worker){
        this.name = worker.name;
        this.coordinates = worker.coordinates;
        this.creationDate = worker.creationDate;
        this.salary = worker.salary;
        this.startDate = worker.startDate;
        this.endDate = worker.endDate;
        this.position = worker.position;
        this.organization = worker.organization;
    }

    public static void touchNextId(){
        nextId++;
    }

    @Override
    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Coordinates getCoordinates(){
        return coordinates;
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    public Long getSalary(){
        return salary;
    }

    public Date getStartDate(){
        return startDate;
    }

    public LocalDate getEndDate(){
        return endDate;
    }

    public Position getPosition(){
        return position;
    }

    public Organization getOrganization(){
        return organization;
    }

    @Override
    public int compareTo(Element element){
        return (int) (this.id - element.getId());
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker product = (Worker) o;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(coordinates, product.coordinates)
                && Objects.equals(creationDate, product.creationDate) && Objects.equals(salary, product.salary)
                && Objects.equals(startDate, product.startDate) && Objects.equals(endDate, product.endDate)
                && position == product.position
                && Objects.equals(organization, product.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, salary, startDate, endDate, position, organization);
    }

    @Override
    public String toString() {
        String info = "";
        info += "Рабочий №" + id;
        info += " (добавлен " + creationDate.toString() + ")";
        info += "\n Имя: " + name;
        info += "\n Местоположение: " + coordinates;
        info += "\n Зарплата: " + salary;
        info += "\n Начало работы: " + ((startDate == null) ? null : "'" + startDate + "'");
        info += "\n Конец работы: " + ((endDate == null) ? null : "'" + endDate + "'");
        info += "\n Позиция (Должность): " + position;
        info += "\n Организация:\n    " + organization;
        return info;
    }

}
