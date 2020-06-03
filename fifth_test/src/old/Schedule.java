package old;

public class Schedule {
    RequestQueue requestQueue;
    Elevator elevator;
    Floor[] floors;

    Schedule() {}

    Schedule(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
        elevator = new Elevator();
        floors = new Floor[11];
        for (int i = 0; i < 11; ++ i) {
            floors[i] = new Floor();
        }
    }

    public void run() {
        long nowTime = 0;
        while (!requestQueue.isEmpty()) {
            Request nowRequest = requestQueue.pollFirst();

            int type = nowRequest.getType();
            int target = nowRequest.getTarget();
            long time = nowRequest.getTime();

            nowTime = Math.max(nowTime, time);

            if (type == Request.ER) {
                if (elevator.getEndTimeByIndex(target) >= time) {
                    System.out.println("# Repeated request in elevator!");
                    continue;
                }
            }
            else {
                if (floors[target].getEndTimeByIndex(type) >= time) {
                    System.out.println("# Repeated request in floors!");
                    continue;
                }
            }
            if (elevator.getPos() == target) {
                nowTime += 2;
                System.out.printf("(%d,STILL,%.1f)\n", target, nowTime / 2.0);
            }
            else {
                nowTime += Math.abs(target - elevator.getPos());
                System.out.printf("(%d,%s,%.1f)\n",
                        target,
                        elevator.getPos() > target ? "DOWN" : "UP",
                        nowTime / 2.0);
                nowTime += 2;
            }
            elevator.setPos(target);
            if (type == Request.ER) {
                elevator.setEndTimeByIndex(target, nowTime);
            }
            else {
                floors[target].setEndTimeByIndex(type, nowTime);
            }
        }
    }
}
