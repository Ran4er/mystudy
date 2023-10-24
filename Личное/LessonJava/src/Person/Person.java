package Person;

public class Person {
    String name;
    int age;

    public Person(String name, int age){
        this.name = name;
        this.age = age;
    }
    public void WriteInfo(){
        System.out.printf("Name: %s \t Age: %d \n", name, age);
    }
}
