package old;

import java.util.Arrays;

public class Floor {
    private long[] endTime;

    Floor() {
        endTime = new long[2];
        Arrays.fill(endTime, -1);
    }

    public long getEndTimeByIndex(int ind) {
        return endTime[ind];
    }

    public void setEndTimeByIndex(int ind, long endTime) {
        this.endTime[ind] = endTime;
    }
}
