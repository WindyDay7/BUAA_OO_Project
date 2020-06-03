package OO3;
import java.util.*;
import java.lang.Math;

public class Dispatch {
	private int headRQ = 0, headcmd = 0, endcmd = 0;
	private Request[] cmd = new Request[100]; 
	
	public Dispatch () {
		int i=0;
		for(i=0;i<100;i++) {
			cmd[i] = new Request();
		}
	}
	
	public void updater(double time, RequestQue RQ, Elevator ele, Floor floor) {
		for(headRQ=headRQ;(headRQ<RQ.getlen()&&RQ.gettime(headRQ)<=time);headRQ++) {
			if (RQ.getr(headRQ).check(ele, floor)) {
				cmd[endcmd] = RQ.getr(headRQ);
				if (RQ.getdirection(headRQ)==1) 
					floor.setup(RQ.getdestination(headRQ), true);
				else if (RQ.getdirection(headRQ)==-1)
					floor.setdown(RQ.getdestination(headRQ), true);
				else ele.setbutton(RQ.getdestination(headRQ), true);
				//System.out.printf("set:%d   state:%b\n", RQ.getdestination(headRQ), ele.getbutton(RQ.getdestination(headRQ)));
				endcmd++;
			}
			else {
				System.out.printf("#ºöÂÔÖ¸Áî--");
				RQ.getr(headRQ).printr();

			}
		}
	}
	
	public boolean ifend(RequestQue RQ) {
		return ((headcmd >= endcmd) && (headRQ >= RQ.getlen()));
	}
	
	public double elerun (double time, Elevator ele, Floor floor, RequestQue RQ) {
		if (cmd[headcmd].gettime()>time)
				time = cmd[headcmd].gettime();
		time = time + 1 + (Math.abs(ele.getfloor()-cmd[headcmd].getdestination())*0.5);
		updater(time, RQ, ele, floor);
		
		
		if (cmd[headcmd].getdirection()==1) 
			floor.setup(cmd[headcmd].getdestination(), false);
		else if (cmd[headcmd].getdirection()==-1)
			floor.setdown(cmd[headcmd].getdestination(), false);
		else ele.setbutton(cmd[headcmd].getdestination(), false);
		
		if (cmd[headcmd].getdestination()>ele.getfloor()) {
			System.out.printf("(%d,UP,%.1f)\n", cmd[headcmd].getdestination(),time-1);
		}
		else if (cmd[headcmd].getdestination()<ele.getfloor()) {
			System.out.printf("(%d,DOWN,%.1f)\n", cmd[headcmd].getdestination(),time-1);
		}
		else if (cmd[headcmd].getdestination()==ele.getfloor()) {
			System.out.printf("(%d,STILL,%.1f)\n", cmd[headcmd].getdestination(),time);
		}
		ele.setfloor(cmd[headcmd].getdestination());
		headcmd++;
		return time;
	}
	
	public void cmdcheck(RequestQue RQ, Elevator ele, Floor floor) {
		if (headcmd == endcmd && headRQ<RQ.getlen()) {
			cmd[endcmd] = RQ.getr(headRQ);
			if (RQ.getdirection(headRQ)==1) 
				floor.setup(RQ.getdestination(headRQ), true);
			else if (RQ.getdirection(headRQ)==-1)
				floor.setdown(RQ.getdestination(headRQ), true);
			else ele.setbutton(RQ.getdestination(headRQ), true);
			endcmd++;
			headRQ++;
		}
	}
	
}
