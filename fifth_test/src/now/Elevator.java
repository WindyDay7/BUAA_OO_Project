package now;

public class Elevator extends Thread {
    private int id;
    public static final long moveTime = 3000;
    public static final long doorTime = 6000;

    private Light[] buttons;
    private final MultiSchedule scheduler;

    Elevator(int id, int fCount, MultiSchedule scheduler) {
        this.id = id;
        buttons = new Light[fCount];
        for (int i = 0; i < fCount; ++ i) {
            buttons[i] = new Light(false);
        }
        this.scheduler = scheduler;
    }

    public boolean tryButton(int target, long time) {
        if (buttons[target - 1].on) {
            return false;
        }
        buttons[target - 1].on = true;
        scheduler.push(new ElevatorRequest(id, target, time, buttons[target - 1]));
        return true;
    }

    @Override
    public void run() {
        boolean sleeping = false;
        while (true) {
            try {
                if (scheduler.isIdle(id)) {
                    synchronized (scheduler) {
                        if (scheduler.isEnd()) {
                            break;
                        }
                    }
                    yield();
                } else {
                    boolean move = scheduler.isMoving(id);
                    if (!sleeping) {
                        sleep((move ? moveTime : doorTime) - 50);
                        sleeping = true;
                    }
                    if (System.currentTimeMillis() < scheduler.getExpectWakeByIndex(id)) {
                        continue;
                    }
                    synchronized (scheduler) {
                        if (move) {
                            scheduler.moveDone(id);
                        } else {
                            scheduler.doorDone(id);
                        }
                        sleeping = false;
                    }
                }
            } catch (Throwable e) {
                System.out.printf("#%d elevator ERROR!\n", id);
                break;
            }
        }
    }
}

