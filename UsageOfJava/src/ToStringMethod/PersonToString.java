package ToStringMethod;

class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}

public class PersonToString {
    public static void main(String[] args) {
        Person person = new Person("Alice", 30);
        System.out.println(person.toString()); // Outputs: Person{name='Alice', age=30}
    }
}
