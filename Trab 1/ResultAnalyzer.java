import java.lang.Math;

public class ResultAnalyzer {
    private int nThreads;
    private long[] times;

    public ResultAnalyzer(int nThreads, long[] times) {
        this.nThreads = nThreads;
        this.times = times;
    }

    public int getNThreads() {
        return nThreads;
    }

    public double averageTime() {
        double result = 0;
        for (long time : times)
            result += time;
        return result / times.length;
    }

    public double standartDeviation() {
        double result = 0;
        double avg = averageTime();

        for (long time : times)
            result += Math.pow(time - avg, 2);

        return Math.sqrt(result / (times.length - 1));
    }
}
