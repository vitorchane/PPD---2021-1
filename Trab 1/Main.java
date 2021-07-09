import java.util.Random;

import javax.naming.spi.DirStateFactory.Result;

class Main {
    public static int[] generateRandomNumbers(int quantity) {
        long seed = 1;
        Random random = new Random(seed);
        int[] randomNumbers = new int[quantity];

        for (int i = 0; i < quantity; i++)
            randomNumbers[i] = random.nextInt();

        return randomNumbers;
    }

    public static void main(String args[]) {
        int listSize = 50000000;
        int[] randomNumbers = generateRandomNumbers(listSize);

        int[] problemInstances = { 1, 2, 4, 8 };
        int nSamples = 10;
        System.out.println("Iniciando ordenacao");
        try {
            for (int instance : problemInstances) {
                System.out.println("Instantiating for " + instance + " thread(s).");
                System.out.println("Running " + nSamples + " samples.");

                System.out.println("Cloning list");
                int[] listToOrder = randomNumbers.clone();

                long[] samples = new long[nSamples];

                for (int i = 0; i < nSamples; i++) {
                    long startTime = System.nanoTime();
                    ParallelSorter.sort(listToOrder, instance);
                    long totalTime = (System.nanoTime() - startTime) / 1000000;
                    System.out.println("Sample " + i + ": " + totalTime + "ms");
                    samples[i] = totalTime;
                }

                ResultAnalyzer analyzer = new ResultAnalyzer(instance, samples);
                System.out.printf("AVERAGE=%.4fms; STANDART DEVIATION=%.4fms\n\n", analyzer.averageTime(),
                        analyzer.standartDeviation());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}