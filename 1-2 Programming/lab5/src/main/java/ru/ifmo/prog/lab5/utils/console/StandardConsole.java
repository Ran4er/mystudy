package ru.ifmo.prog.lab5.utils.console;

/**
 * Класс реализующий вывод информации в консоль.
 * @author ru6ik
 */

public class StandardConsole implements Console{

    private static final String PS1 = "$";
    private static final String PS2 = ">";

    /**
     * Выводит obj.toString() в консоль.
     * @param obj Объект для печати.
     */

    public void print(Object obj){
        System.out.print(obj);
    }

    /**
     * Выводит obj.toString() + \n в консоль.
     * @param obj Объект для печати.
     */

    public void println(Object obj){
        System.out.println(obj);
    }

    /**
     * Выводит ошибку: obj.toString() в консоль.
     * @param obj Ошибка для печати.
     */

    public void printError(Object obj){
        System.out.println("ошибка: " + obj);
    }

    /**
     *
     * @param elementLeft
     * @param elementRight
     */

    public void printTable(Object elementLeft, Object elementRight){
        System.out.printf(" %-35s%-1s%n", elementLeft, elementRight);
    }

    /**
     * Выводит PS1 текущей консоли.
     */

    public void ps1(){
        print(PS1);
    }

    /**
     * Выводит PS2 текущей консоли.
     */

    public void ps2(){
        print(PS2);
    }

    /**
     * @return PS1 текущей консоли.
     */

    public String getPS1(){
        return PS1;
    }

    /**
     * @return PS2 текущей консоли.
     */

    public String getPS2(){
        return PS2;
    }

}
