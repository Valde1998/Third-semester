import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Opg1 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        char currentChar = 'A';

        for (int i = 0; i < 25; i++) {
            executor.submit(new Task(currentChar++));
        }

        executor.shutdown();
    }

    static class Task implements Runnable {
        private char taskChar;

        Task(char taskChar) {
            this.taskChar = taskChar;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " prints " + taskChar + taskChar + taskChar);
        }
    }
}
