public abstract class AbsPlace {
    private String placeName;
    AbsPlace(String name) {
        placeName = name;
    }
    @Override
    public int hashCode() {
        return super.hashCode()+this.getPlace().hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
    @Override
    public String toString() {
        return "Место " + this.getPlace();
    }
    public String getPlace() {
        return placeName;
    }
}

