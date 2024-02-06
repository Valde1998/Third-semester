package week1.opg3;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Calculator {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        Predicate<Integer> divisibleBy3 = number -> number % 3 == 0;

        List<Integer> filteredNumbers = numbers.stream()
                .filter(divisibleBy3)
                .collect(Collectors.toList());

        System.out.println("Filtered Numbers (divisible by 3): " + filteredNumbers);
    }
}
