package src.opg6;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookProcessing {

    public static void main(String[] args) {
        List<Book> books = Arrays.asList(
                new Book("Harry Potter", "J.K. Rowling", 1925, 180, 10),
                new Book("Hunger Games", "Happy", 2001, 281, 7.3),
                new Book("Skyggens lærlig", "Jon", 1949, 328, 4.3),
                new Book("Game of Thones", "George R.R. Martin", 1813, 279, 4.7),
                new Book("Bogen", "Forfatter", 1951, 224, 4.2)
        );

        double averageRating = books.stream().mapToDouble(Book::getRating).average().orElse(0.0);
        System.out.println("Average Rating of all books: " + averageRating);

        int specificYear = 1950;
        List<Book> booksPublishedAfterYear = books.stream().filter(book -> book.getPublicationYear() > specificYear).collect(Collectors.toList());
        System.out.println("Books published after " + specificYear + ": " + booksPublishedAfterYear);

        List<Book> sortedBooksByRating = books.stream()
                .sorted((b1, b2) -> Double.compare(b2.getRating(), b1.getRating()))
                .collect(Collectors.toList());
        System.out.println("Books sorted by rating in descending order: " + sortedBooksByRating);

        Book highestRatedBook = books.stream()
                .max((b1, b2) -> Double.compare(b1.getRating(), b2.getRating()))
                .orElse(null);
        System.out.println("Title of the book with the highest rating: " +
                (highestRatedBook != null ? highestRatedBook.getTitle() : "N/A"));

        Map<String, Double> averageRatingByAuthor = books.stream()
                .collect(Collectors.groupingBy(Book::getAuthor,
                        Collectors.averagingDouble(Book::getRating)));
        System.out.println("Average rating by author: " + averageRatingByAuthor);

        int totalPages = books.stream()
                .mapToInt(Book::getPages)
                .sum();
        System.out.println("Total number of pages for all books: " + totalPages);

        List<Book> filteredAndSortedBooks = books.stream().filter(book -> book.getPublicationYear() > specificYear).sorted((b1, b2) -> Double.compare(b2.getRating(), b1.getRating()))
                .collect(Collectors.toList());
        System.out.println("Books published after " + specificYear +
                " sorted by rating in descending order: " + filteredAndSortedBooks);
    }


}
