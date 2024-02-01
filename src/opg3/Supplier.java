package src.opg3;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Supplier {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Jack", "Joe", "Jill");

        java.util.function.Supplier<Employee> employeeSupplier = () -> new Employee();

        List<Employee> employees = names.stream()
                .map(name -> {
                    Employee employee = employeeSupplier.get();
                    employee.setName(name);
                    return employee;
                })
                .collect(Collectors.toList());

        System.out.println("List of Employee objects: " + employees);
    }

    public Consumer.Employee get() {
        return null;
    }

    static class Employee {
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Employee{name='" + name + "'}";
        }
    }
}
