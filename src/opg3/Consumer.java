package src.opg3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Consumer {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Jack", "Joe", "Jill");

        List<Employee> employees = names.stream()
                .map(Employee::new)
                .collect(Collectors.toList());

        java.util.function.Consumer<Employee> printEmployee = System.out::println;

        employees.forEach(printEmployee);
    }

    static class Employee {
        private String name;

        public Employee(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Employee{name='" + name + "'}";
        }
    }
}
