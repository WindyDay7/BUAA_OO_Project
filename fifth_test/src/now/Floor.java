package now;

public class Floor {
    private Light up, down;
    private MultiSchedule scheduler;

    Floor(MultiSchedule scheduler) {
        this.scheduler = scheduler;
        up = new Light(false);
        down = new Light(false);
    }

    public boolean tryButtonByIndex(int id, String dir, long time) {
        if (dir.equals("UP")) {
		if (up.on) {
                return false;
            }
            up.on = true;
        } else {
            if (down.on) {
                return false;
            }
            down.on = true;
        }
        scheduler.push(new FloorRequest(
                id, dir, time, dir.equals("UP") ? up : down
        ));
        return true;
    }
}
