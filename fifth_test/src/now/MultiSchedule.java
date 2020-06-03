package now;

import java.util.Iterator;
import java.util.LinkedList;

public class MultiSchedule extends old.ALSSchedule implements Runnable {
    private int[] meter, pos, state, dir, still;
    private Elevator[] elevators;
    private long base;
    private Request[] master;
    private long[] masterArriveTime, expectWake;
    protected boolean end;

    private LinkedList<Request>[][] carry;
    private LinkedList<Request> unscheduled;

    MultiSchedule() {
        super();
    }

    public void link(Elevator[] elevators, Floor[] floors) {
        this.elevators = elevators;

        int eCount = elevators.length;
        int fCount = floors.length;
        meter = new int[eCount];
        pos = new int[eCount];
        dir = new int[eCount];
        state = new int[eCount];
        still = new int[eCount];
        carry = new LinkedList[eCount][];
        master = new Request[eCount];
        masterArriveTime = new long [eCount];
        expectWake = new long[eCount];

        end = false;

        for (int i = 0; i < eCount; ++ i) {
            pos[i] = 1;
            carry[i] = new LinkedList[fCount];

            for (int j = 0; j < fCount; ++ j) {
                carry[i][j] = new LinkedList<>();
            }
        }

        unscheduled = new LinkedList<>();
    }

    public void setBase(long base) {
        this.base = base;
    }

    private void setExpectWakeByIndex(int index, long time) {
        expectWake[index - 1] = time;
    }

    public long getTime(long time) {
        return time - base;
    }

    public double getDoubleTime(long time) {
        return (time - base) / 1000.0;
    }

    public long getExpectWakeByIndex(int index) {
        return expectWake[index - 1];
    }

    public boolean isEnd() {
        return end & (master[0] == null) & (master[1] == null) &
                (master[2] == null) & (unscheduled.size() == 0);
    }

    private boolean dispatchERequest(ElevatorRequest request, long time) {
        int id = request.id;
        if (canCarry(id, request, dir[id - 1], true)) {
            carry[id - 1][request.target - 1].add(request);
            return true;
        }
        if (isIdle(id)) {
            master[id - 1] = request;
            dir[id - 1] = getMoveDir(pos[id - 1], request.target);
            still[id - 1] = dir[id - 1];
            if (still[id - 1] == 0) {
                masterArriveTime[id - 1] = time;
                setExpectWakeByIndex(id, time + Elevator.doorTime);
                state[id - 1] = 2;
            } else {
                setExpectWakeByIndex(id, time + Elevator.moveTime);
                state[id - 1] = 1;
            }
            return true;
        }
        return false;
    }

    public void push(Request request) {
        if (request instanceof ElevatorRequest) {
            if (!dispatchERequest((ElevatorRequest) request, base + request.time)) {
                unscheduled.add(request);
            }
        } else {
            if (!dispatchFRequest((FloorRequest) request, base + request.time)) {
                unscheduled.add(request);
            }
        }
    }

    private int getMoveDir(int from, int to) {
        return Integer.compare(to, from);
    }

    private boolean dispatchFRequest(FloorRequest request, long time) {
        int sit = -1, min = -1;
        int eCount = elevators.length;
        for (int i = 0; i < eCount; ++ i) {
            if (canCarry(i + 1, request, dir[i], true) && (min < 0 || meter[i] < min)) {
                min = meter[i];
                sit = i;
            }
        }
        if (sit >= 0) {
            carry[sit][request.target - 1].add(request);
            return true;
        }
        for (int i = 0; i < eCount; ++ i) {
            if (isIdle(i + 1) && (min < 0 || meter[i] < min)) {
                min = meter[i];
                sit = i;
            }
        }
        if (sit < 0) {
            return false;
        }
        master[sit] = request;
        dir[sit] = getMoveDir(pos[sit], request.target);
        still[sit] = dir[sit];

        if (still[sit] == 0) {
            masterArriveTime[sit] = time;
            setExpectWakeByIndex(sit + 1, time + Elevator.doorTime);
            state[sit] = 2;
        } else {
            setExpectWakeByIndex(sit + 1, time + Elevator.moveTime);
            state[sit] = 1;
        }
        return true;
    }

    private boolean canCarry(int id, Request request, int dir, boolean running) {
        if (master[id - 1] == null) {
            return false;
        }
        if (request instanceof ElevatorRequest) {
            return ((dir > 0 && pos[id - 1] < request.target) || (dir < 0 && pos[id - 1] > request.target)) &&
                    (!running || (dir > 0 && request.target <= master[id - 1].target) ||
                            (dir < 0 && request.target >= master[id - 1].target));
        }
        return dir == ((FloorRequest) request).dir &&
                ((dir > 0 && pos[id - 1] < request.target && request.target <= master[id - 1].target) ||
                        (dir < 0 && pos[id - 1] > request.target && request.target >= master[id - 1].target));
    }

    public boolean isIdle(int id) {
        return state[id - 1] == 0;
    }

    public boolean isMoving(int id) {
        return state[id - 1] == 1;
    }

    void moveDone(int id) {
        Iterator<Request> it;
        Request request;
        pos[id - 1] += dir[id - 1];
        ++ meter[id - 1];

        if (carry[id - 1][pos[id - 1] - 1].size() != 0 || master[id - 1].target == pos[id - 1]) {
            it = carry[id - 1][pos[id - 1] - 1].iterator();
            while (it.hasNext()) {
                request = it.next();
                System.out.printf("%d:%s/(#%d,%d,%s,%d,%.1f)\n",
                        expectWake[id - 1], request,
                        id, pos[id - 1], dir[id - 1] > 0 ? "UP" : "DOWN",
                        meter[id - 1], getDoubleTime(expectWake[id - 1])
                );
            }

            if (master[id - 1].target == pos[id - 1]) {
                masterArriveTime[id - 1] = expectWake[id - 1];
                System.out.printf("%d:%s/(#%d,%d,%s,%d,%.1f)\n",
                        expectWake[id - 1], master[id - 1],
                        id, pos[id - 1], dir[id - 1] > 0 ? "UP" : "DOWN",
                        meter[id - 1], getDoubleTime(expectWake[id - 1])
                );
                dir[id - 1] = 0;
            }

            setExpectWakeByIndex(id, expectWake[id - 1] + Elevator.doorTime);
            state[id - 1] = 2;
        } else {
            setExpectWakeByIndex(id, expectWake[id - 1] + Elevator.moveTime);
        }
    }

    void doorDone(int id) {
        Iterator<Request> it;
        Request request;
        long time = System.currentTimeMillis();

        if (carry[id - 1][pos[id - 1] - 1].size() != 0 || master[id - 1].target == pos[id - 1]) {
            it = carry[id - 1][pos[id - 1] - 1].iterator();
            while (it.hasNext()) {
                request = it.next();
                request.button.on = false;
                it.remove();
            }
            if (master[id - 1].target == pos[id - 1]) {
                master[id - 1].button.on = false;
                if (still[id - 1] == 0) {
                    System.out.printf("%d:%s/(#%d,%d,STILL,%d,%.1f)\n",
                            expectWake[id - 1], master[id - 1],
                            id, pos[id - 1], meter[id - 1], getDoubleTime(expectWake[id - 1])
                    );
                }
                Request nextMaster = null;
                it = unscheduled.iterator();
                while (it.hasNext()) {
                    request = it.next();
                    if (base + request.time < masterArriveTime[id - 1] &&
                            canCarry(id, request, still[id - 1], false)) {
                        nextMaster = request;
                        it.remove();
                        break;
                    }
                }
                if (nextMaster != null) {
                    still[id - 1] = dir[id - 1] = getMoveDir(pos[id - 1], nextMaster.target);
                }
                master[id - 1] = nextMaster;
                if (master[id - 1] != null) {
                    if (still[id - 1] == 0) {
                        masterArriveTime[id - 1] = time;
                        setExpectWakeByIndex(id, expectWake[id - 1] + Elevator.doorTime);
                        state[id - 1] = 2;
                    } else
                        setExpectWakeByIndex(id, expectWake[id - 1] + Elevator.moveTime);
                    state[id - 1] = 1;
                } else {
                    setExpectWakeByIndex(id, 0);
                    state[id - 1] = 0;
                }
            } else {
                setExpectWakeByIndex(id, expectWake[id - 1] + Elevator.moveTime);
                state[id - 1] = 1;
            }
        }
    }

    @Override
    public void run() {
        long time;
        Iterator<Request> it;
        Request request;
        while (true) {
            synchronized (this) {
                if (isEnd()) {
                    break;
                }
                time = System.currentTimeMillis();
                int i;
                for (i = 0; i < elevators.length; ++ i) {
                    if (Math.abs(time - expectWake[i]) < 10) {
                        break;
                    }
                }
                if (i >= elevators.length) {
                    it = unscheduled.iterator();
                    while (it.hasNext()) {
                        request = it.next();
                        if (request instanceof ElevatorRequest) {
                            if (dispatchERequest((ElevatorRequest) request, time)) {
                                it.remove();
                            }
                        } else {
                            if (dispatchFRequest((FloorRequest) request, time)) {
                                it.remove();
                            }
                        }
                    }
                }
            }
        }
    }
}
