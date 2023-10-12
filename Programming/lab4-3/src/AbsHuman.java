public abstract class AbsHuman implements IntHuman {
    private String name;
    private int age;

    public AbsHuman(){
    }
    public AbsHuman (String _name){
        name = _name;
    }
    public AbsHuman (String _name, int _age){
        name = _name;
        age = _age;
    }
    public String GetName(){
        return name;
    }
    public abstract String getName();
}
