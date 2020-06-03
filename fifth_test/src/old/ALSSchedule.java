package old;

import java.util.Iterator;

public class ALSSchedule extends Schedule{
    private RequestQueue passQueue;

    public ALSSchedule () {
        super();
    }

    ALSSchedule (RequestQueue requestQueue) {
        super(requestQueue);
        passQueue = new RequestQueue();
    }

    private long getEndTime(int type, int target) {
        if (type == Request.ER) {
            return elevator.getEndTimeByIndex(target);
        }
        return floors[target].getEndTimeByIndex(type);
    }

    private void setEndTime(int type, int target, long time) {
        if (type == Request.ER) {
            elevator.setEndTimeByIndex(target, time);
        } else {
            floors[target].setEndTimeByIndex(type, time);
        }
    }

    @Override
    public void run() {
        long nowTime = 0;
        while (!passQueue.isEmpty() || !requestQueue.isEmpty()) {

            Request mainReq;
            if (!passQueue.isEmpty()) {
                mainReq = passQueue.pollFirst();
            } else {
                mainReq = requestQueue.pollFirst();
            }

            int type = mainReq.getType();
            int target = mainReq.getTarget();
            long time = mainReq.getTime();
            int pos = elevator.getPos();

            if (getEndTime(type, target) >= time) {
                System.out.printf("#SAME[(%s)]\n", mainReq);
                continue;
            }

            nowTime = Math.max(nowTime, time);

            if (pos == target) {
                nowTime += 2;
                elevator.setTime(nowTime);
                elevator.setState("STILL");
                System.out.printf("[%s]/(%s)\n", mainReq, elevator);
                setEndTime(type, target, nowTime);
                continue;
            }

            JudgePass judgePass = new JudgePass(nowTime, mainReq);
            RequestQueue trashQueue = new RequestQueue();

            for (Iterator it = passQueue.iterator(); it.hasNext(); ) {
                judgePass.addReq((Request)it.next(), pos, target);
            }

            while (!requestQueue.isEmpty()) {
                if (requestQueue.getFrontTime() >= judgePass.calcTime(pos, target) + 2) {
                    break;
                }
                Request req = requestQueue.pollFirst();
                if (getEndTime(req.getType(), req.getTarget()) >= req.getTime()
                        || judgePass.has(req, pos)) {
                    System.out.printf("#SAME[(%s)]\n", req);
                    continue;
                }
                if (judgePass.addReq(req, pos, target)) {
                    passQueue.offerLast(req);
                } else {
                    trashQueue.offerLast(req);
                }
            }

            while (!trashQueue.isEmpty()) {
                requestQueue.offerFirst(trashQueue.pollLast());
            }

            for (int d = pos < target ? 1 : -1, f = pos + d; f != target + d; f += d) {
                nowTime += 1;
                judgePass.sortByIndex(f);
                while (!judgePass.isEmptyByIndex(f)) {
                    Request req = judgePass.pollFirstByIndex(f);
                    elevator.setPos(f);
                    elevator.setTime(nowTime);
                    elevator.setState(d == 1 ? "UP" : "DOWN");
                    System.out.printf("[%s]/(%s)\n", req, elevator);
                    setEndTime(req.getType(), f, nowTime + 2);
                }
                if (judgePass.getMaskByIndex(f) > 0) {
                    nowTime += 2;
                }
            }

            elevator.setPos(target);
            while (!passQueue.isEmpty()) {
                Request req = passQueue.pollFirst();
                if ((pos < target && target < req.getTarget())
                        || (req.getTarget() < target && target < pos)) {
                    trashQueue.offerLast(req);
                }
            }
            while (!trashQueue.isEmpty()) {
                passQueue.offerLast(trashQueue.pollFirst());
            }

        }
    }

}
