import java.util.Random;

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
        int listSize = 24;
        int[] randomNumbers = generateRandomNumbers(listSize);

        int[] problemInstances = { 1, 2, 4, 8 };

        System.out.println("Iniciando ordenacao");
        try {
            for (int instance : problemInstances) {
                System.out.println("Starting instance to " + instance + " threads");
                System.out.println("Cloning list");

                int[] listToOrder = randomNumbers.clone();

                long startTime = System.nanoTime();
                ParallelSorter.sort(listToOrder, instance);
                long endTime = System.nanoTime();

                System.out.println(instance + " threads: " + ((endTime - startTime) / 1000000) + "ms");

                for (int i : listToOrder) {
                    System.out.println(i);
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}