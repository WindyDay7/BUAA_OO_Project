package now;

public class Request {
    protected int target;
    protected Light button;
    protected long time;

    Request(int target, Light button, long time) {
        this.target = target;
        this.button = button;
        this.time = time;
    }
}
