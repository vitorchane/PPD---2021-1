import java.lang.Math;

import java.util.Arrays;

public class ResultAnalyzer {
    private long[] times;

    public ResultAnalyzer(long[] times) {
        setTimes(times);
    }

    private void setTimes(long[] times) {
        Arrays.sort(times);
        this.times = new long[times.length - 2];
        for (int i = 0; i < this.times.length; i++) {
            this.times[i] = times[i + 1];
        }
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
