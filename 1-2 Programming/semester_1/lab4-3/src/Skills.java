public class Skills {
    private String name;
    private String info;
    public Skills(String _name, String _info){
        name = _name;
        info = _info;
    }
    public String getName(){
        return name;
    }
    public String getInfo(){
        if (info == null)
            return "...";
        else
            return info;
    }
}
