package old;

import java.util.Arrays;

public class JudgePass {
    private long time;
    private int[] mask;
    private RequestQueue[] set;

    JudgePass(long time, Request req) {
        this.time = time;
        mask = new int[11];
        set = new RequestQueue[11];
        Arrays.fill(mask, 0);
        for (int i = 0; i < 11; ++ i) {
            set[i] = new RequestQueue();
        }
        mask[req.getTarget()] |= (1 << req.getType());
        set[req.getTarget()].offerLast(req);
    }

    public boolean has(Request req, int s) {
        int e = req.getTarget();
        return (mask[e] & (1 << req.getType())) > 0 &&
                calcTime(s, e) + 2 >= req.getTime();
    }

    public long calcTime(int s, int e) {
        if (s == e) {
            return time;
        }
        long ret = time + Math.abs(s - e);
        for (int d = s < e ? 1 : -1, i = s + d; i != e; i += d) {
            if (mask[i] > 0) {
                ret += 2;
            }
        }
        return ret;
    }

    public boolean addReq(Request req, int s, int e) {
        int target = req.getTarget();
        int type = req.getType();
        long time = req.getTime();
        if ((s < target && target <= e && type == Request.FR_UP)
                || (e <= target && target < s && type == Request.FR_DOWN)
                || (s < e && s < target && type == Request.ER)
                || (s > e && s > target && type == Request.ER)) {
            long tmpTime = Math.min(calcTime(s, target), calcTime(s, e));
            if (tmpTime > time) {
                mask[target] |= (1 << type);
                set[target].offerLast(req);
                return true;
            }
        }
        return false;
    }

    public int getMaskByIndex(int ind) {
        return mask[ind];
    }

    public boolean isEmptyByIndex(int ind) {
        return set[ind].isEmpty();
    }

    public Request pollFirstByIndex(int ind) {
        return set[ind].pollFirst();
    }

    public void sortByIndex(int ind) {
        set[ind].sort();
    }
}
