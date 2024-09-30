package ru.ifmo.prog.lab5.models;

/**
 * Перечисление типов организации.
 * @author ru6ik
 */

public enum OrganizationType {

    COMMERCIAL,
    TRUST,
    OPEN_JOINT_STOCK_COMPANY;


    /**
     * @return Строка со всеми элементами enum'а через строку.
     */
    public static String names(){
        StringBuilder nameList = new StringBuilder();
        for(var shopType : values()){
            nameList.append(shopType.names()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }

}
