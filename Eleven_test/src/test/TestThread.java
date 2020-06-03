package test;

public class TestThread extends Thread {
	/**@OVERVIEW:
	 * This is a testing thread.
	 * You can use the method provided to check the information you need.
	 * 
	 * @INVARIANT:
	 * alltaxi.length == 100;
	 */
	Taxi[] alltaxi;
	Queue rqueue;

	public TestThread(Taxi[] ts, Queue q) {
		alltaxi = ts;
		rqueue = q;
	}
	public boolean repOK() {
		return alltaxi.length == 100;
	}
	public void run() {
		// add your code here:
	}
	
	/**
	 * @REQUIRES:(\exist int index; 0 <= index < 100)
	 * @MODIFIES:none;
	 * @EFFECTS:
	 * output alltaxi[index]'s status into the console;
	 */
	public synchronized void query_taxi(int index) {
		System.out.println("\tQuery: Taxi No." + index);
		System.out.println("Time: " + alltaxi[index].time);
		_Point loc = alltaxi[index].location;
		System.out.printf("Location: (%d,%d)\r\n", loc.x, loc.y);
		System.out.println("Status: " + alltaxi[index].status2str());
	}
	/**
	 * @REQUIRES:(\exist int state; 0 <= state <= 3)
	 * @MODIFIES:none;
	 * @EFFECTS:
	 * (\all Taxi t; alltaxi.contains(t)):
	 * (t.status == state)==>(output its index in the console);
	 */
	public synchronized void query_status(int state) {
		System.out.println("\tQuery: Status " + state);
		boolean flag = false;
		int i;
		for(i = 0; i < 100; i++) {
			if(alltaxi[i].status == state) {
				System.out.print(alltaxi[i].index + " ");
				flag = true;
			}
		}
		if(flag) {
			System.out.println("");
		}
		else {
			System.out.println("No taxi is in this status.");
		}
	}
}
