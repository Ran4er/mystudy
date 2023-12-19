package ru.ifmo.prog.lab5.commands;

/**
 * Интерфейс для всех выполняемых комманд
 * @author ru6ik
 */

public interface Executable {

    /**
     * Выполнить что-либо
     *
     * @param arguments аргументы для выполнения
     * @return результат выполнения
     */
    boolean apply(String[] arguments);
}
