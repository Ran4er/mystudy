package ru.ifmo.prog.lab5.utils;

import java.util.Scanner;

/**
 * Отвечает за режим ввода пользовательских данных.
 * @author ru6ik
 */

public class Interrogator {
    private static Scanner userScanner;
    private static boolean fileMode = false;

    public static Scanner getUserScanner(){
        return userScanner;
    }

    public static void setUserScanner(Scanner userScanner){
        Interrogator.userScanner = userScanner;
    }

    public static boolean fileMode(){
        return fileMode;
    }

    public static void setUserMode(){
        Interrogator.fileMode = false;
    }

    public static void setFileMode(){
        Interrogator.fileMode = true;
    }

}