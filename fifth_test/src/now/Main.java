package now;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class Main {
    private static int eCount = 3;

    private Input input;
    private MultiSchedule scheduler;
    private Elevator[] elevators;

    private Main() {
        int fCount = 20;
        scheduler = new MultiSchedule();
        elevators = new Elevator[eCount];
        Floor[] floors = new Floor[fCount];

        for (int i = 0; i < eCount; ++ i) {
            elevators[i] = new Elevator(i + 1, fCount, scheduler);
        }
        for (int i = 0; i < fCount; ++ i) {
            floors[i] = new Floor(scheduler);
        }
        input = new Input(floors, fCount, elevators, eCount, scheduler);
        scheduler.link(elevators, floors);
    }

    public static void main(String[] args) {
        try {
            System.setOut(new PrintStream(new FileOutputStream(
                    "result.txt"
            )));
            Main program = new Main();
            Thread manager = new Thread(program.scheduler);
            manager.start();
            for (int i = 0; i < eCount; ++ i) {
                program.elevators[i].start();
            }
            program.input.start();
        } catch (Throwable e) {
            System.out.println("Cannot create result.txt!");
        }
    }
}
