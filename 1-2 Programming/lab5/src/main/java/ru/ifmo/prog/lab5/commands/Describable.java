package ru.ifmo.prog.lab5.commands;

/**
 * Неокоторый объект (что-то), что можно назвать и описать
 * @author ru6ik
 */

public interface Describable {
    /**
     * Получить имя объекта.
     * @return имя.
     */
    String getName();

    /**
     * Получить описание объекта.
     * @return описание.
     */

    String getDescription();

}
