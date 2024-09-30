import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        System.out.println();
        ArrayList<Skills> skills = new ArrayList<>();

        Human house = new Human("Дом");
        house.addSkill(new Skills("Ходить", "ходуном"));
        System.out.println();

        Human rabbit = new Human("Кролик");
        rabbit.addSkill(new Skills("Бегать", "искать Алису"));
        System.out.println();

        Human alice = new Human("Алиса");
        alice.addSkill(new Skills("В тысячу раз больше Кролика", null));
        System.out.println();

        Place.placeadd stairs = new Place.placeadd("Лестница");
        stairs.setType(Places.STAIRS);
        rabbit.addSkill(new Skills("Издавать звук", "легкий, быстрый топоток"));
        System.out.println();

        Place.placeadd room = new Place.placeadd("Комната");
        room.setType(Places.ROOM);
        alice.addSkill(new Skills("Дрожать", "что весь дом заходил ходуном"));
        alice.addSkill(new Skills("Догадываться", "что Кролик бежит её искать"));

        alice.addSkill(new Skills("Не дать кролику добраться до нее", "упереться в дверь"));
        rabbit.addSkill(new Skills("Поиск другого варианта попасть к Алисе", "предположительно через окно"));
        alice.addSkill(new Skills("Попытка поймать кролика", "через окно"));
    }
}

