import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println();
        ArrayList<Skills> skills = new ArrayList<>();

        Person goat = new Person("Козлик");
        goat.addSkill(new Skills("Протянул деньги", "и получил две таблички(215 и 216)"));

        Person neznaika = new Person("Незнайка");
        Place.placeadd stairs = new Place.placeadd("Лестница");
        stairs.setType(Places.STAIRS);
        neznaika.addSkill(new Skills("Спустился с Козликом", "по грязной, деревянной лестнице"));

        Place.placeadd room = new Place.placeadd("Комната");
        room.setType(Places.ROOM);
        neznaika.addSkill(new Skills("Было впечатление, будто оказался в каталажке", null));

        
    }
}