import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookProcessing {

    public static void main(String[] args) {
        // Create a collection of Book objects
        List<Book> books = Arrays.asList(
                new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, 180, 4.5),
                new Book("To Kill a Mockingbird", "Harper Lee", 1960, 281, 4.8),
                new Book("1984", "George Orwell", 1949, 328, 4.3),
                new Book("Pride and Prejudice", "Jane Austen", 1813, 279, 4.7),
                new Book("The Catcher in the Rye", "J.D. Salinger", 1951, 224, 4.2)
        );

        // Use the Stream API for various operations
        // Find the average rating of all books
        double averageRating = books.stream().mapToDouble(Book::getRating).average().orElse(0.0);
        System.out.println("Average Rating of all books: " + averageRating);

        // Filter and display books published after a specific year
        int specificYear = 1950;
        List<Book> booksPublishedAfterYear = books.stream().filter(book -> book.getPublicationYear() > specificYear).collect(Collectors.toList());
        System.out.println("Books published after " + specificYear + ": " + booksPublishedAfterYear);

        // Sort books by rating in descending order
        List<Book> sortedBooksByRating = books.stream()
                .sorted((b1, b2) -> Double.compare(b2.getRating(), b1.getRating()))
                .collect(Collectors.toList());
        System.out.println("Books sorted by rating in descending order: " + sortedBooksByRating);

        // Find and display the title of the book with the highest rating
        Book highestRatedBook = books.stream()
                .max((b1, b2) -> Double.compare(b1.getRating(), b2.getRating()))
                .orElse(null);
        System.out.println("Title of the book with the highest rating: " +
                (highestRatedBook != null ? highestRatedBook.getTitle() : "N/A"));

        // Group books by author and calculate the average rating for each author's books
        Map<String, Double> averageRatingByAuthor = books.stream()
                .collect(Collectors.groupingBy(Book::getAuthor,
                        Collectors.averagingDouble(Book::getRating)));
        System.out.println("Average rating by author: " + averageRatingByAuthor);

        // Calculate the total number of pages for all books
        int totalPages = books.stream()
                .mapToInt(Book::getPages)
                .sum();
        System.out.println("Total number of pages for all books: " + totalPages);

        // Chaining and Composition example: Filter books published after a year and sort them by rating
        List<Book> filteredAndSortedBooks = books.stream().filter(book -> book.getPublicationYear() > specificYear).sorted((b1, b2) -> Double.compare(b2.getRating(), b1.getRating()))
                .collect(Collectors.toList());
        System.out.println("Books published after " + specificYear +
                " sorted by rating in descending order: " + filteredAndSortedBooks);
    }

    // Book class definition
    static class Book {
        private String title;
        private String author;
        private int publicationYear;
        private int pages;
        private double rating;

        public Book(String title, String author, int publicationYear, int pages, double rating) {
            this.title = title;
            this.author = author;
            this.publicationYear = publicationYear;
            this.pages = pages;
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public int getPublicationYear() {
            return publicationYear;
        }

        public int getPages() {
            return pages;
        }

        public double getRating() {
            return rating;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "title='" + title + '\'' +
                    ", author='" + author + '\'' +
                    ", publicationYear=" + publicationYear +
                    ", pages=" + pages +
                    ", rating=" + rating +
                    '}';
        }
    }
}
