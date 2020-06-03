package old;

public interface ElevatorInterface {
    int getPos();
    long getEndTimeByIndex(int ind);
    void setPos(int pos);
    void setTime(long time);
    void setState(String state);
    void setEndTimeByIndex(int ind, long endTime);
}
