package old;

import java.util.Arrays;

public class Elevator implements ElevatorInterface {
    private int pos;
    private long time;
    private String state;
    private long[] endTime;

    Elevator() {
        time = 0;
        state = "STILL";
        pos = 1;
        endTime = new long[11];
        Arrays.fill(endTime, -1);
    }

    public int getPos() {
        return pos;
    }

    public long getEndTimeByIndex(int ind) {
        return endTime[ind];
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setEndTimeByIndex(int ind, long endTime) {
        this.endTime[ind] = endTime;
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%.1f",
                pos,
                state,
                time / 2.0);
    }
}
