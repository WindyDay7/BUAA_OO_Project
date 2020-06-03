package now;

public class FloorRequest extends Request {
    protected int dir;

    FloorRequest(int target, String dir, long time, Light button) {
        super(target, button, time);
        this.dir = dir.equals("UP") ? 1 : -1;
    }

    @Override
    public String toString() {
        return String.format("[FR,%d,%s,%.1f]", target, dir == 1 ? "UP" : "DOWN", time / 1000.0);
    }
}
