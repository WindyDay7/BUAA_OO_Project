package test;

import java.util.ArrayList;

public class Schedule extends Thread {
	Taxi[] alltaxi;
	SafeFile output;
	TaxiGUI gui;
	guiInfo citymap;
	Queue rqueue;
	
	int stime;
	Request prev;
	
	public Schedule(Taxi[] ts, TaxiGUI g, Queue q, int st) {
		stime = st;
		alltaxi = ts;
		int i;
		output = new SafeFile("output.txt");
		
		gui = g;
		citymap = guigv.m;
		guiInfo ginfo = new guiInfo();
		ginfo.map = citymap.map.clone();
		ginfo.initmatrix();
		// modified in hw 11
		for(i = 0; i < 30; i++) {
			alltaxi[i] = new RecTaxi(i, output, gui, stime, ginfo);
			gui.SetTaxiType(i, 1);
		}
		for(; i < 100; i++) {
			alltaxi[i] = new Taxi(i, output, gui, stime, citymap);
			gui.SetTaxiType(i, 0);
		}
		
		rqueue = q;
		prev = null;
	}
	boolean repOK() {
		return true;
	}
	
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:rqueue, output;
	 * @EFFECTS:
	 * (\exist Request r;r==rqueue.get()&& !r.equivalent(prev))&&(\exist int i;alltaxi[i] is available)==>(alltaxi[i].onservice==r);
	 * (\exist Request r;r==rqueue.get()&& !r.equivalent(prev))&&(\all int i;alltaxi[i] is not available)==>(output==\old(output)+"not available");
	 */
	public void run() {
		Request cur;
		int index;
		try {
			//gui.LoadMap(citymap.map, 80);
			//citymap.initmatrix();
			for(Taxi t: alltaxi) {
				t.start();
			}
			//System.out.println("# All taxis have started running.");
			while(true) {
				// do some work
				if(rqueue.over) break;
				cur = rqueue.get();
				if(cur == null) {
					sleep(1000);
					continue;
				}
				else if(cur.equivalent(prev)) {
					sleep(1000);
					continue;
				}
				gui.RequestTaxi(cur.src.toPoint(), cur.dst.toPoint());
				output.write(cur.toString() + "\r\n");
				index = this.dispatch(cur);
				if(index == -1) {
					System.out.println("not available");
					output.write("Sorry, no taxi is available.\r\n");
				}
				else {
					output.write("List of taxis:\r\n");
					this.process(index);
					alltaxi[index].ready();
				}
				prev = cur;
				sleep(1000);
			}
			output.close();
		}catch (InterruptedException e) {
			;
		}
	}
	/**
	 * @REQUIRES:(\exist Request r; r != null);
	 * @MODIFIES:alltaxi;
	 * @EFFECTS:(\exist int i; alltaxi[i] get picked);
	 * (\result == i);
	 */
	public int dispatch(Request r) {
		ArrayList<Taxi> tlist = new ArrayList<Taxi>();
		int i;
		int count = 0;
		int j = 0;
		int d;
		int max = 0, min = gv.MAXNUM;
		try {
			for(i = 0; i < 15; i++) {
				for(Taxi t: alltaxi) {
					t.receive(r);
				}
				sleep(500);
			}// timewindow = 7500ms
			for(Taxi t: alltaxi) {
				if(t.received){
					count++;
					if(t.credit == max){
						tlist.add(t);
					}
					else if(t.credit > max){
						max = t.credit;
						tlist.clear();
						tlist.add(t);
					}
				}
			}
			if(count == 0) return -1;
			System.out.println(count + " available");
			for(i = 0; i < tlist.size(); i++) {
				d = citymap.D[tlist.get(i).location.toInt()][r.src.toInt()];
				if (d < min){
					j = i;
					min = d;
				}
			}
			return tlist.get(j).index;
		}catch(InterruptedException e) {
			return -1;
		}
	}
	/**
	 * @REQUIRES:(\exist int index;0<=index<100);
	 * @MODIFIES:alltaxi, output;
	 * @EFFECTS:(\all Taxi t; t is in alltaxi[] && t.received)==>(output.record());
	 * (\all Taxi t; t is in alltaxi[]&&t.index!=index)==>(set t to normal status);
	 */
	public void process(int index) {
		for(Taxi t: alltaxi) {
			if(t.received) {
				output.write(t.toString());
			}
			if(t.index != index) {
				t.join = null;
				t.received = false;
			}
		}
		output.newLine();
	}
}
