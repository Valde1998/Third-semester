package src.opg7;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Collector {

    public static void main(String[] args) {

        List<Transaction> transactions = Arrays.asList(
                new Transaction(1, 100.50, "USD"),
                new Transaction(2, 150.75, "EUR"),
                new Transaction(3, 200.25, "USD"),
                new Transaction(4, 75.60, "EUR"),
                new Transaction(5, 120.30, "USD")
        );


        double totalSum = transactions.stream()
                .collect(Collectors.summingDouble(Transaction::getAmount));
        System.out.println("Total sum of transaction amounts: " + totalSum);


        Map<String, Double> sumByCurrency = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCurrency,
                        Collectors.summingDouble(Transaction::getAmount)));
        System.out.println("Sum of amounts by currency: " + sumByCurrency);


        Optional<Transaction> highestTransaction = transactions.stream()
                .collect(Collectors.maxBy((t1, t2) -> Double.compare(t1.getAmount(), t2.getAmount())));
        highestTransaction.ifPresent(transaction ->
                System.out.println("Highest transaction amount: " + transaction.getAmount()));


        double averageAmount = transactions.stream()
                .collect(Collectors.averagingDouble(Transaction::getAmount));
        System.out.println("Average transaction amount: " + averageAmount);
    }



}
