package oo_5;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Run {
	
	protected static boolean search(String str_out, String str_in) {
		return str_out.matches(str_in);
	}
	
	public void running() {
		int i = 0;
		W_legal tem_queue = new W_legal();
		Elevator[] elevator = new Elevator[4];
		for(i=0;i<4;i++) {
			elevator[i] = new Elevator();
		}
		
		Input  in_put = new Input(tem_queue);
		Super_ride super_ride = new Super_ride(tem_queue,elevator);
		Elevator_run elev_1 = new Elevator_run(elevator[1]);
		Elevator_run elev_2 = new Elevator_run(elevator[2]);
		Elevator_run elev_3 = new Elevator_run(elevator[3]);
		
		new Thread(in_put).start();
		new Thread(elev_1).start();
		new Thread(elev_2).start();
		new Thread(elev_3).start();
		new Thread(super_ride).start();
		
	}
	
	
	public static void main(String[] args) {
		try {
			System.setOut(new PrintStream(new FileOutputStream("result.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Run rn = new Run();
		rn.running();
	}
}
