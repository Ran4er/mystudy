package ru.ifmo.prog.lab5.models;

/**
 * Перечисление типов позиций(должностей).
 * @author ru6ik
 */

public enum Position {

    MANAGER,
    LABORER,
    LEAD_DEVELOPER,
    BAKER,
    CLEANER;

    /**
     * @return Строка со всеми элементами enum'а через строку.
     */
    public static String names(){
        StringBuilder nameList = new StringBuilder();
        for(var positionType : values()){
            nameList.append(positionType.names()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }

}
