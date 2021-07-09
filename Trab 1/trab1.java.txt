import java.util.Random;
import java.util.Arrays;

class ListSorter implements Runnable {
    public int[] vector;
    private int start;
    private int end;

    public ListSorter(int[] list, int start, int end) {
        this.vector = list;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        Arrays.sort(vector, start, end);
    }
}

class Main {
    public static int[] generateRandomNumbers(int quantity) {
        long seed = 1;
        Random random = new Random(seed);
        int[] randomNumbers = new int[quantity];

        for (int i = 0; i < quantity; i++)
            randomNumbers[i] = random.nextInt();

        return randomNumbers;
    }

    public static Boolean hasThreadAlive(Thread[] threads) {
        for (Thread thread : threads) {
            if (thread.isAlive())
                return true;
        }
        return false;
    }

    public static void main(String args[]) {
        int listSize = 50000000;
        int[] randomNumbers = generateRandomNumbers(listSize);

        int[] problemInstances = { 1, 2, 4, 8 };

        System.out.println("Iniciando ordenação");
        try {
            for (int instance : problemInstances) {
                long startTime = System.nanoTime();
                for (int nThreads = instance; nThreads > 0; nThreads /= 2) {
                    int stepIndex = listSize / nThreads;

                    Thread[] threads = new Thread[nThreads];
                    for (int i = 0; i < nThreads; i++) {
                        int fromIndex = i * stepIndex;
                        int toIndex = (i + 1) * stepIndex;

                        threads[i] = new Thread(new ListSorter(randomNumbers.clone(), fromIndex, toIndex));
                        threads[i].start();
                        threads[i].join();
                    }

                    while (hasThreadAlive(threads))
                        Thread.sleep(100);
                }
                long endTime = System.nanoTime();
                System.out.println(instance + " threads: " + ((endTime - startTime) / 1000000) + "ms");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}