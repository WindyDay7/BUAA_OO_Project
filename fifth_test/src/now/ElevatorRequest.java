package now;

public class ElevatorRequest extends Request {
    protected int id;

    ElevatorRequest(int id, int target, long time, Light button) {
        super(target, button, time);
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("[ER,#%d,%d,%.1f]", id, target, time / 1000.0);
    }
}
