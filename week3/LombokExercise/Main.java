package LombokExercise;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("John","Doe",25);
        System.out.println(person);

        person.setAge(26);
        System.out.println(person.getAge());

        if(person.canEqual(person)){
            System.out.println("True!");
        }

        Person person2 = Person.builder()
                .firstName("Valde")
                .lastName("Ch")
                .age(25)
                .build();
        System.out.println(person2);
    }
}
