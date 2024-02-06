import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Opg4 {

    public static void main(String[] args) {
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);

        System.out.println("Main starts");
        for (int i = 0; i < cores; i++) {
            executor.submit(new Task());
        }
        System.out.println("Main is done");

        executor.shutdown();
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            while (true) {
                int sum = 0;
                for (int i = 0; i < Integer.MAX_VALUE; i++) {
                    sum += i;
                }
                System.out.println(Thread.currentThread().getName() + ": Computation done");
            }
        }
    }
}
