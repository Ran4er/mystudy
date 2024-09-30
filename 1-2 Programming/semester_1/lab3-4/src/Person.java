import java.util.ArrayList;

interface Iout{
    void justPrint();
}

public class Person extends AbsPerson{

    private String surName;

    private ArrayList<Skills> skills = new ArrayList<Skills>();
    public Person(){
        Iout iout = new Iout() {
            @Override
            public void justPrint() {
                System.out.printf("Обычный персонаж успешно создан!");
            }
        };
        iout.justPrint();
    }

    public Person(String name){
        super(name);

        class CreatePerson{

            class StringIsNull extends Exception{
                public StringIsNull(String message){
                    super(message);
                }
            }

            public String create(String _name) throws StringIsNull {
                if (_name.equals(null)) {
                    throw new StringIsNull("Имя задано не верно!(пусто)");
                } else {
                    return "Персонаж - " + _name + " успешно создан!";
                }
            }
        }
        CreatePerson person = new CreatePerson();
        try {
            System.out.println(person.create(name));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Person (String _name, ArrayList<Skills> _skills){
        super (_name);

        skills = _skills;
        System.out.println("Персонаж - " + _name  + " успешно создан!");
    }

    public void placeadd(String _name, ArrayList<Skills> _skills){
        skills = _skills;
        System.out.println("Объект - " + _name + "успешно создан!");
    }

    public ArrayList<Skills> getSkills() {
        return skills;
    }

    public boolean addSkill(Skills skill){
        if (skills.add(skill)){
            System.out.println("Объекту - \"" + this.GetName() + "\" успешно присвоено умение " + skill.getName()
                    + " (" + skill.getInfo()+ ")");
            return true;
        }
        else{
            System.out.println("При добавлении умения произошла ошибка...");
            return false;
        }
    }
    public void setSkills(ArrayList<Skills> skills) {
        this.skills = skills;
    }
    @Override
    public int hashCode() {
        return super.hashCode()+this.getName().hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String toString(){
        return "Персонаж по имени " + this.getName();
    }

    @Override
    public void walk(AbsPlace place) {
        System.out.println(getName() + "прибежал в " + place.getPlace());
        AbsPlace where = place;
    }
}
