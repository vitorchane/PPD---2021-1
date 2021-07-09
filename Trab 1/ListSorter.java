import java.util.Arrays;

public class ListSorter implements Runnable {
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