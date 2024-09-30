import java.util.ArrayList;

public class Place {
    private ArrayList<Skills> skills = new ArrayList<Skills>();



    static class placeadd {
        private String name;
        private Places type;
        public placeadd(String _name) {
            name = _name;
            System.out.println("Место - " + _name  + " успешно создано!");
        }

        public void setType(Places _type){
            type = _type;
            String typeName = "";
            switch (_type){
                case STAIRS : typeName = "Лестница";
                    break;
                case ROOM : typeName = "Комната";
                    break;
            }
            System.out.println(name +" объявлена как " + typeName);
        }

        public String getName() {
            return name == null ? "..." : name;
        }
    }

    public ArrayList<Skills> getSkills() {
        return skills;
    }
}