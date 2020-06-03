package OO3;
import java.util.*;

import java.util.Scanner;

public class ElevatorMain {
	private Elevator ele = new Elevator();
	private Floor floor = new Floor();
	private RequestQue RQ = new RequestQue();
	//private double time = 0;
	private als_Dispatch dispatch = new als_Dispatch();
	
	public ElevatorMain() {
		int Rcount = 0;
		Scanner scan = new Scanner(System.in);
		while (scan.hasNext() && Rcount<100) {
			String str = scan.nextLine();
			str = str.replace(" ", "");
			if (str.equals("RUN")) break;
			else if (!Request.reMatch(str)) {
				System.out.printf("INVALID[%s]\n",str);
	//			System.out.printf("ERROR\n");
			}
			else RQ.setr(Request.getRequest(str));
			Rcount++;
		}
		scan.close();
		
	}
	
	public void run() {
	
//		for (i = 0;i<RQ.getlen();i++) {
//			System.out.printf("R%d: destination:%d  direction:%d  time:%d  exc:%b\n", i, RQ.getr(i).getdestination(),RQ.getr(i).getdirection(),RQ.getr(i).gettime(),RQ.getr(i).getexc());
//		}
		while (dispatch.ifend(RQ)) {
			dispatch.cmdcheck(RQ, ele, floor);
			//System.out.printf("time:%.1f\n", time);
			dispatch.elerun(ele, floor, RQ);
		}
	}
	
	public static void main(String args[]) {
		ElevatorMain emain = new ElevatorMain();
		emain.run();
	}
	
}
