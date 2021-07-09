public class ParallelSorter {
    public static void sort(int[] toSort, int initialThreads) throws InterruptedException {
        Boolean firstcall = true;
        for (int nThreads = initialThreads; nThreads > 0; nThreads /= 2) {
            int stepIndex = toSort.length / nThreads;

            Thread[] threads = new Thread[nThreads];
            for (int i = 0; i < nThreads; i++) {
                int fromIndex = i * stepIndex;
                int toIndex = (i + 1) * stepIndex;

                Runnable runnable = firstcall ? new ListSorter(toSort, fromIndex, toIndex)
                        : new ListMerger(toSort, fromIndex, toIndex);

                threads[i] = new Thread(runnable);
                threads[i].start();
                threads[i].join();
            }

            while (hasThreadAlive(threads))
                Thread.sleep(100);
            firstcall = false;
        }
    }

    private static Boolean hasThreadAlive(Thread[] threads) {
        for (Thread thread : threads) {
            if (thread.isAlive())
                return true;
        }
        return false;
    }
}
