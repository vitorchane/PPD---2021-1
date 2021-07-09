import java.util.Random;
import java.util.Arrays;

class ListSorter implements Runnable {
    public int[] array;
    private int start;
    private int end;

    public ListSorter(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        Arrays.sort(array, start, end);
    }
}

class ListMerger implements Runnable {
    public int[] array;
    private int left;
    private int right;
    private int mid;

    public ListMerger(int[] array, int start, int end) {
        this.array = array;
        this.left = start;
        this.right = end - 1;
        this.mid = (this.left + this.right) / 2;
    }

    @Override
    public void run() {
        // calculating lengths
        int lengthLeft = mid - left + 1;
        int lengthRight = right - mid;

        // creating temporary subarrays
        int leftArray[] = new int[lengthLeft];
        int rightArray[] = new int[lengthRight];

        // copying our sorted subarrays into temporaries
        for (int i = 0; i < lengthLeft; i++)
            leftArray[i] = array[left + i];
        for (int i = 0; i < lengthRight; i++)
            rightArray[i] = array[mid + i + 1];

        // iterators containing current index of temp subarrays
        int leftIndex = 0;
        int rightIndex = 0;

        // copying from leftArray and rightArray back into array
        for (int i = left; i < right + 1; i++) {
            // if there are still uncopied elements in R and L, copy minimum of the two
            if (leftIndex < lengthLeft && rightIndex < lengthRight) {
                if (leftArray[leftIndex] < rightArray[rightIndex]) {
                    array[i] = leftArray[leftIndex];
                    leftIndex++;
                } else {
                    array[i] = rightArray[rightIndex];
                    rightIndex++;
                }
            }
            // if all the elements have been copied from rightArray, copy the rest of
            // leftArray
            else if (leftIndex < lengthLeft) {
                array[i] = leftArray[leftIndex];
                leftIndex++;
            }
            // if all the elements have been copied from leftArray, copy the rest of
            // rightArray
            else if (rightIndex < lengthRight) {
                array[i] = rightArray[rightIndex];
                rightIndex++;
            }
        }
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

        System.out.println("Iniciando ordenacao");
        try {
            for (int instance : problemInstances) {
                System.out.println("Starting instance to " + instance + " threads");
                System.out.println("Cloning list");

                int[] listToOrder = randomNumbers.clone();
                Boolean firstcall = true;

                long startTime = System.nanoTime();
                for (int nThreads = instance; nThreads > 0; nThreads /= 2) {
                    int stepIndex = listSize / nThreads;

                    Thread[] threads = new Thread[nThreads];
                    for (int i = 0; i < nThreads; i++) {
                        int fromIndex = i * stepIndex;
                        int toIndex = (i + 1) * stepIndex;

                        Runnable runnable = firstcall ? new ListSorter(listToOrder, fromIndex, toIndex)
                                : new ListMerger(listToOrder, fromIndex, toIndex);

                        threads[i] = new Thread(runnable);
                        threads[i].start();
                        threads[i].join();
                    }

                    while (hasThreadAlive(threads))
                        Thread.sleep(100);
                    firstcall = false;
                }
                long endTime = System.nanoTime();
                System.out.println(instance + " threads: " + ((endTime - startTime) / 1000000) + "ms");

                // for (int i : listToOrder) {
                // System.out.println(i);
                // }
                // System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}