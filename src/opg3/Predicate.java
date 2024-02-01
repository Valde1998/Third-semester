package src.opg3;

public class Predicate {

    public static void main(String[] args) {
        java.util.function.Predicate<Employee> isOlderThan18 = employee -> employee.getAge() > 18;

        // Example usage
        Employee employee1 = new Employee("John", 25);
        Employee employee2 = new Employee("Jane", 17);

        System.out.println("Is employee1 older than 18? " + isOlderThan18.test(employee1));
        System.out.println("Is employee2 older than 18? " + isOlderThan18.test(employee2));
    }

    static class Employee {
        private String name;
        private int age;

        public Employee(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Employee{name='" + name + "', age=" + age + "}";
        }
    }
}
