package old;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request implements Comparable {
    private int id;
    private int type;
    private int target;
    private long time;

    public static final int FR_UP = 0;
    public static final int FR_DOWN = 1;
    public static final int ER = 2;
    public static final int RUN = 3;

    @Override
    public String toString() {
        if (type == ER) {
            return String.format("ER,%d,%d", target, time / 2);
        }
        return String.format("FR,%d,%s,%d",
                target,
                type == FR_UP ? "UP" : "DOWN",
                time / 2);
    }

    @Override
    public int compareTo(Object o) {
        return id - ((Request) o).id;
    }

    Request(String request, int id) throws Throwable {

        if (request.equals("RUN")) {
            type = RUN;
            return;
        }

        if (request.length() > 100) {
            throw new Exception("Input too long!");
        }

        this.id = id;

        String erPatternStr = "\\((ER),([+]?\\d{1,15}),([+]?\\d{1,15})\\)";
        String frPatternStr = "\\((FR),([+]?\\d{1,15}),(UP|DOWN),([+]?\\d{1,15})\\)";

        Pattern erPattern = Pattern.compile(erPatternStr);
        Pattern frPattern = Pattern.compile(frPatternStr);

        Matcher erMatcher = erPattern.matcher(request);
        Matcher frMatcher = frPattern.matcher(request);

        if (erMatcher.find()) {
            if (erMatcher.start() != 0 || erMatcher.end() != request.length()) {
                throw new Exception("Invalid input format!");
            }
            type = ER;
            long tmpTarget = Long.parseLong(erMatcher.group(2));
            if (tmpTarget < 1 || tmpTarget > 10) {
                throw new Exception("Wrong target floor in ER request!");
            }
            target = (int)tmpTarget;
            time = Long.parseLong(erMatcher.group(3));
            if (time < 0 || time > (1L << 32) - 1) {
                throw new Exception("Wrong time in ER request!");
            }
            time <<= 1;
        }
        else if (frMatcher.find()) {
            if (frMatcher.start() != 0 || frMatcher.end() != request.length()) {
                throw new Exception("Invalid input format!");
            }
            if (frMatcher.group(3).equals("UP")) {
                type = FR_UP;
            }
            else {
                type = FR_DOWN;
            }
            long tmpTarget = Long.parseLong(frMatcher.group(2));
            if (tmpTarget < 1 || tmpTarget > 10) {
                throw new Exception("Wrong target floor in FR " + frMatcher.group(3) + " request!");
            }
            target = (int)tmpTarget;
            time = Long.parseLong(frMatcher.group(4));
            if (time < 0 || time > (1L << 32) - 1) {
                throw new Exception("Wrong time in FR " + frMatcher.group(3) + " request!");
            }
            time <<= 1;

            if ((type == FR_DOWN && target == 1) || (type == FR_UP && target == 10)) {
                throw new Exception("Wrong button in FR request!");
            }
        }
        else {
            throw new Exception("Invalid input format!");
        }
    }

    public boolean equals(Request req) {
        return type == req.type && target == req.target && time == req.time;
    }

    public int getType() {
        return type;
    }

    public int getTarget() {
        return target;
    }

    public long getTime() {
        return time;
    }
}
