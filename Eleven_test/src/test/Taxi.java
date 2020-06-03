package test;

import java.util.ArrayList;

public class Taxi extends Thread {
	/**@OVERVIEW:
	 * A Taxi simulates a real taxi's behavior.
	 * Typically, a Taxi object contains a index of type int,
	 * a _Point object representing its location,
	 * and a status of type int.
	 * 
	 * @INVARIANT:
	 * 0 <= index < 100;
	 * 0 <= status < 4;
	 * time >= 0;
	 * credit >= 0;
	 * picked ==> status == 3 || status == 1 ;
	 * received ==> status == 2;
	 */
	int index;
	_Point location;
	int status;
	/* 0: out of service
	 * 1: running (with customer)
	 * 2: running (without customer)
	 * 3: ready to pick up
	 */
	guiInfo citymap;
	SafeFile out;
	TaxiGUI gui;
	// steal
	boolean received;
	boolean picked;
	int credit;
	Request join;
	// output
	double time;
	String runtime;
	String info;
	// Load file, serve
	protected _Point ssrc, sdst;
	Request onservice;
	_Point dst_final;
	// traffic light
	_Point past, future;
	int stime;
	
	public Taxi(int i, SafeFile sf, TaxiGUI g, int st, guiInfo gi) {
		index = i;
		short randx = (short)(Math.random() * 80);
		short randy = (short)(Math.random() * 80);
		location = new _Point(randx,randy);
		status = 2;
		
		out = sf;
		gui = g;
		citymap = gi;//gi is different in subclass
		
		credit = 0;
		time = 0.0;
		// you can modify ssrc and sdst:
		ssrc = new _Point(19,19);
		sdst = new _Point(59,59);
		past = new _Point(randx,randy);
		stime = st;
		
		gui.SetTaxiStatus(i, location.toPoint(), 2);
	}
	public boolean repOK() {
		boolean b1 = location.repOK() &&
				0 <= index && index < 100 &&
				0 <= status && status <= 3;
		boolean b2 = credit >= 0 && time >= 0.0;
		return b1 && b2;
	}
	
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:none;
	 * @EFFECTS:(\exist String re; re == the meaning of status)==>(\result == re);
	 */
	public String status2str() {
		int temp = status;
		String re;
		if(temp == 0) re = "停止状态";
		else if(temp == 1) re = "服务状态";
		else if(temp == 2) re = "等待服务";
		else if(temp == 3) re = "接单状态（准备服务）";
		else re = "Unknown";
		return re;
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:none;
	 * @EFFECTS:(\exist String re; re == info of this taxi)==>(\result == re);
	 */
	public String toString() {
		String s;
		s = "index: " + index;
		s = s + "; location: " + location.toString();
		s = s + "; status: " + this.status2str();
		s = s + "; credit: " + credit + ";\r\n";
		return s;
	}
	
	public void run() {
		int period = 0;
		try {
			while(true) {
				switch(status) {
				case 0:
					sleep(1000);
					period = 0;
					time += 1.0;
					status = 2;
					break;
				case 1:
					dst_final = (onservice == null) ? sdst : onservice.dst;
					serve();
					credit += 3;
					picked = false;
					onservice = null;
					status = 0;
					break;
				case 2:
					while(status == 2) {
						if(picked) {
							status = 3;
							break;
						}
						//if run 20s, stop for 1s
						if(period >= 20000) {
							status = 0;
							break;
						}
						sleep(500);
						this.run_edge();
						if(judge_light()) {
							sleep(stime);
							time += stime / 1000.0;
						}
						past = location;
						location = future;
						gui.SetTaxiStatus(index, location.toPoint(), status);
						time += 0.5;
						period += 500;
					}
					break;
				case 3:
					dst_final = (onservice == null) ? ssrc : onservice.src;
					pickup();
					sleep(1000);
					time += 1.0;
					status = 1;
					break;
				default: break;
				}
			}
		}catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * @REQUIRES:(\exist int i; 0<=i<6400);
	 * @MODIFIES:none;
	 * @EFFECTS:(the point that i represents is in this city)==>(\result == true);
	 * (the point that i represents is not in this city)==>(\result == true);
	 */
	public boolean incity(int i) {
		int x = i / 80;
		int y = i % 80;
		return (x >= 0 && x < 80 && y >= 0 && y < 80);
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:location;
	 * @EFFECTS:
	 * for all the connected points to location,
	 * find the point with the minimum flow.
	 * if more than one point are found, then choose randomly.
	 */
	public void run_edge() {
		int location_1=0, location_2=0, location_3=0, location_4=0;
		location_1 = (location.x + 1) * 80 + location.y;
		location_2 = (location.x - 1) * 80 + location.y;
		location_3 = location.x * 80 + location.y + 1;
		location_4 = location.x * 80 + location.y - 1;
		int loc = location.toInt();
		ArrayList<_Point> plist = new ArrayList<_Point>();
		if(incity(location_1) && citymap.graph[location_1][loc]==1) {
			plist.add(_Point.parse_i(location_1));
		}
		if(incity(location_2) && citymap.graph[location_2][loc]==1) {
			plist.add(_Point.parse_i(location_2));
		}
		if(incity(location_3) && citymap.graph[location_3][loc]==1) {
			plist.add(_Point.parse_i(location_3));
		}
		if(incity(location_4) && citymap.graph[location_4][loc]==1) {
			plist.add(_Point.parse_i(location_4));
		}
		if(plist.size() == 1) {
			future = plist.get(0);// modified
			return ;
		}
		int i, j = 0;
		int index = 0;
		int flow, flow_min = Integer.MAX_VALUE;
		int x1 = location.x;
		int y1 = location.y;
		int x2, y2;
		int[] flowlist = new int[4];
		for(i = 0; i < plist.size(); i++) {
			x2 = plist.get(i).x;
			y2 = plist.get(i).y;
			flow = guigv.GetFlow(x1, y1, x2, y2);
			flowlist[j] = flow;
			if(flow < flow_min) {
				index = i;
				flow_min = flow;
			}
		}
		future = plist.get(index);
		ArrayList<_Point> plist2 = new ArrayList<_Point>();
		for(i = 0; i < plist.size(); i++) {
			if(flowlist[i] == flow_min) {
				plist2.add(plist.get(i));
			}
		}
		int dir = (int)(Math.random() * plist2.size());
		future = plist.get(dir);// modified
	}
	/**
	 * @REQUIRES:
	 * (\exist _Point p1);
	 * (\exist _Point p2);
	 * @MODIFIES: none;
	 * @EFFECTS:
	 * return the character representing the direction.
	 */
	public char getdire(_Point p1, _Point p2) {
		if(p2.equals(p1)) return 'T';
		else if(p2.x == p1.x + 1) return 'E';
		else if(p2.x == p1.x - 1) return 'W';
		else if(p2.y == p1.y + 1) return 'N';
		else if(p2.y == p1.y - 1) return 'S';
		else return '?';
	}
	/**
	 * @REQUIRES: none;
	 * @MODIFIES: none;
	 * @EFFECTS:
	 * judge whether the taxi needs to wait for a green light.
	 */
	public boolean judge_light() {
		char d1, d2;
		d1 = getdire(past, location);
		d2 = getdire(location, future);
		int lst = guigv.lightmap[location.x][location.y];
		
		if(lst == 1) {
			if(d1 == 'E' && d2 == 'N') return true;
			else if(d1 == 'W' && d2 == 'S') return true;
			else if(d1 == 'S' && d2 == 'S') return true;
			else if(d1 == 'N' && d2 == 'N') return true;
			else return false;
		}
		else if(lst == 2) {
			if(d1 == 'E' && d2 == 'E') return true;
			else if(d1 == 'W' && d2 == 'W') return true;
			else if(d1 == 'S' && d2 == 'E') return true;
			else if(d1 == 'N' && d2 == 'W') return true;
			else return false;
		}
		else return false;
	}
	/**
	 * @REQUIRES:(\exist Request r; r != null);
	 * @MODIFIES:join, credit, received;
	 * @EFFECTS:\old(received)==>(\result == true);
	 * (location.within(join.src)&&!\old(received))==>
	 * (credit == \old(credit) + 1) &&
	 * (received == true) &&
	 * (join == r) &&
	 * (\result == true);
	 */
	public boolean receive(Request r) {
		join = r;
		if(received) return true;
		else {
			if (location.within(join.src) &&
				status == 2) {
				credit += 1;
				received = true;
				return true;
			}
			else return false;
		}
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:onservice, join, picked, received, status
	 * @EFFECTS:(modify these variables to tell the taxi to pick up the customer);
	 */
	public synchronized void ready() {
		onservice = join;
		join = null;
		picked = true;
		status = 3;
		System.out.println("Taxi No." + index + " is ready.");
	}
	/**
	 * @REQUIRES:(\exist int dst; 0 <= dst < 6400);
	 * @MODIFIES:location
	 * @EFFECTS:(\exist int location_x; route(location_x,dst) == \min(route(\old(location).nextlist,dst)))
	 * ==>(location == location_x);
	 * If this taxi encounters red light, wait for it to turn green.
	 */
	public void search(int dst) {
		int location_1=0, location_2=0, location_3=0, location_4=0;
		int length1=0, length2=0, length3=0, length4=0;
		int length=0;
		int loc = location.toInt();
		
		location_1 = (location.x + 1) * 80 + location.y;
		location_2 = (location.x - 1) * 80 + location.y;
		location_3 = location.x * 80 + location.y + 1;
		location_4 = location.x * 80 + location.y - 1;
		
		citymap.pointbfs(dst);
		length = citymap.D[dst][loc];
		length1 = citymap.D[dst][location_1];
		length2 = citymap.D[dst][location_2];
		length3 = citymap.D[dst][location_3];
		length4 = citymap.D[dst][location_4];
		
		if(length1 < length && this.incity(location_1) &&
				citymap.graph[location_1][loc] == 1) {
			future = _Point.parse_i(location_1);
		}
		else if(length2 < length && this.incity(location_2) &&
				citymap.graph[location_2][loc] == 1) {
			future = _Point.parse_i(location_2);
		}
		else if(length3 < length && this.incity(location_3) &&
				citymap.graph[location_3][loc] == 1) {
			future = _Point.parse_i(location_3);
		}
		else if(length4 < length && this.incity(location_4) &&
				citymap.graph[location_4][loc] == 1) {
			future = _Point.parse_i(location_4);
		}// modified
		try {
			if(judge_light()) {
				sleep(stime);
				time += stime / 1000.0;
			}
			past = location;
			location = future;
		}catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:time, location;
	 * @EFFECTS:(time == \old(time)+D[\old(location)][\old(dst)]*0.5);
	 * (location == \old(dst));
	 */
	public void pickup(){
		info = "Runtime information:\r\n";
		info += "Index: " + index + "\r\n";
		info += "Location1: " + location;
		info += String.format("; Time1: %.1f\r\n", time);
		runtime = "Route:\r\n";
		runtime += String.format("[%.1f,%s]", time, location.toString());
		try {
			while( ! location.equals(dst_final)) {
				sleep(500);
				this.search(dst_final.toInt());
				time += 0.5;
				gui.SetTaxiStatus(index, location.toPoint(), status);
				runtime += String.format(",[%.1f,%s]", time, location.toString());
			}
			info += "Location2: " + location;
			info += String.format("; Time2: %.1f\r\n", time);
			//info += "; Time2: " + time + "\r\n";
			runtime += "\r\n";
			if(out == null) System.out.println("#");
			out.write(info + runtime);
			out.flush();
			info = null;
			runtime = null;
		}catch(InterruptedException e) {
			;
		}
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:time, location;
	 * @EFFECTS:(time == \old(time)+D[\old(location)][\old(dst)]*0.5);
	 * (location == \old(dst));
	 */
	public void serve() {
		runtime = "Route:\r\n";
		runtime += String.format("[%.1f,%s]", time, location.toString());
		try {
			while( ! location.equals(dst_final)) {
				sleep(500);
				this.search(dst_final.toInt());
				time += 0.5;
				gui.SetTaxiStatus(index, location.toPoint(), status);
				runtime += String.format(",[%.1f,%s]", time, location.toString());
			}
			runtime += "\r\n";
			info = "Location3: " + location;
			info += String.format("; Time3: %.1f\r\n", time);
			//info += "; Time3: " + time + "\r\n";
			out.write(info + runtime);
			out.newLine();
			out.flush();
			System.out.println("# finished");
		}catch(InterruptedException e) {
			;
		}
	}
	
	/**
	 * @REQUIRES:(\exist int state; 0<=state<=3);
	 * (\exist int cre; cre >= 0);
	 * (\exist _Point loc; incity(loc));
	 * @MODIFIES:status, credit, location, gui;
	 * @EFFECTS:
	 * status == state;
	 * credit == cre;
	 * location == loc;
	 * modify gui accordingly;
	 */
	public synchronized void modify(int state, int cre, _Point loc) {
		status = state;
		credit = cre;
		location = loc;
		// modified
		past = loc;
		future = null;
		gui.SetTaxiStatus(index, location.toPoint(), status);
	}
}
