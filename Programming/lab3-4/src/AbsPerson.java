public abstract class AbsPerson implements IntPerson {
    private String name;
    private int age;

    public AbsPerson(){
    }
    public AbsPerson (String _name){
        name = _name;
    }
    public AbsPerson (String _name, int _age){
        name = _name;
        age = _age;
    }
    public String GetName(){
        return name;
    }

    public abstract String getName();
}