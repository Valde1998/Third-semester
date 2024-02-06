package week1.opg3;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Function {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("John", "Jane", "Jack", "Joe", "Jill");

        Supplier<Employee> employeeSupplier = () -> new Employee();

        List<Employee> employees = names.stream()
                .map(name -> {
                    Employee employee = employeeSupplier.get();
                    employee.setName(name);
                    return employee;
                })
                .collect(Collectors.toList());

        java.util.function.Function<Employee, String> employeeToName = Employee::getName;

        List<String> employeeNames = employees.stream()
                .map(employeeToName)
                .collect(Collectors.toList());

        System.out.println("List of Employee names: " + employeeNames);
    }

    static class Employee {
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Employee{name='" + name + "'}";
        }
    }
}
