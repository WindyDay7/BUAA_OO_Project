package now;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input extends Thread {
    private int floorCnt, elevatorCnt;
    private Scanner scanner;
    private Elevator[] elevators;
    private Floor[] floors;
    private final MultiSchedule scheduler;

    Input(Floor[] floors, int floorCnt, Elevator[] elevators, int elevatorCnt, MultiSchedule scheduler) {
        scanner = new Scanner(System.in);
        this.floors = floors;
        this.floorCnt = floorCnt;
        this.elevators = elevators;
        this.elevatorCnt = elevatorCnt;
        this.scheduler = scheduler;
    }

    private void warningInvalid(long time, String request, double doubleTime) {
        System.out.printf("%d:INVALID[%s,%.1f]\n", time, request, doubleTime);
    }

    private void warningSame(long time, String request, double doubleTime) {
        System.out.printf("#%d:SAME[%s,%.1f]\n", time, request.substring(
                1, request.length() - 1
        ), doubleTime);
    }

    @Override
    public void run() {
        boolean first = true;

        Pattern erPattern = Pattern.compile("\\(ER,#([1-3]),([+]?\\d{1,15})\\)");
        Pattern frPattern = Pattern.compile("\\(FR,([+]?\\d{1,15}),(UP|DOWN)\\)");

        int cnt = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ++ cnt;
            line = line.replaceAll(" ", "");
            if (line.equals("END")) {
                scheduler.end = true;
                return;
            }
            String[] requests = line.split(";", -1);

            synchronized (scheduler) {
                long time = System.currentTimeMillis();

                for (int i = 0; i < requests.length; ++ i) {
                    if (first) {
                        scheduler.setBase(time);
                        first = false;
                    }
                    if(i >= 10) {
                        warningInvalid(time, requests[i], scheduler.getDoubleTime(time));
                        continue;
                    }
                    Matcher erMatcher = erPattern.matcher(requests[i]);
                    Matcher frMatcher = frPattern.matcher(requests[i]);
                    try {
                        if (erMatcher.find()) {
                            int id = Integer.valueOf(erMatcher.group(1));
                            int target = Integer.valueOf(erMatcher.group(2));

                            if (id > elevatorCnt || id < 1 || target < 1 || target > floorCnt)
                                warningInvalid(time, requests[i], scheduler.getDoubleTime(time));
                            else {
                                if (!elevators[id - 1].tryButton(target, scheduler.getTime(time))) {
                                    warningSame(time, requests[i], scheduler.getDoubleTime(time));
                                }
                            }
                        } else if (frMatcher.find()) {
                            int target = Integer.valueOf(frMatcher.group(1));
                            String dir = frMatcher.group(2);
                            if (target < 1 || target > floorCnt
                                    || (target == floorCnt && dir.equals("UP"))
                                    || (target == 1 && dir.equals("DOWN"))) {
                                warningInvalid(time, requests[i], scheduler.getDoubleTime(time));
                            } else {
                                if (!floors[target - 1].tryButtonByIndex(target, dir, scheduler.getTime(time))) {
                                    warningSame(time, requests[i], scheduler.getDoubleTime(time));
                                }
                            }
                        } else {
                            warningInvalid(time, requests[i], scheduler.getDoubleTime(time));
                        }
                    } catch (Throwable e) {
                        warningInvalid(time, requests[i], scheduler.getDoubleTime(time));
                    }
                }
            }
            if (cnt == 50) {
                scheduler.end = true;
                return;
            }
        }
    }
}
