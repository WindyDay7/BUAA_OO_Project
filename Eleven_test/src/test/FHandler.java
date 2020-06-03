package test;

import java.io.*;

public class FHandler extends Thread{
	private FileReader r;
	private BufferedReader br;
	
	TaxiGUI gui;
	Queue queue;
	Taxi[] taxis;
	Lctrl lc;
	
	private long basetime;

	public FHandler(String fname, TaxiGUI g, Queue q, Taxi[] ts, Lctrl LC, long t)
	throws FileNotFoundException {
		try {
			r = new FileReader(fname);
			br = new BufferedReader(r);
		}catch(FileNotFoundException e) {
			throw e;
		}
		gui = g;
		queue = q;
		taxis = ts;
		lc = LC;
		basetime = t;
	}
	boolean repOK() {
		return true;
	}
	
	/**
	 * @REQUIRES: none;
	 * @MODIFIES: br;
	 * @EFFECTS:
	 * parse each sections in this file.
	 */
	public void run() {
		byte op = 0;
		try {
			br.readLine();
			while(op < 5) {
				br.readLine();
				switch(op) {
				case 0: parse_m(); break;
				case 1: parse_l(); break;
				case 2: parse_f(); break;
				case 3: parse_t(); break;
				case 4: parse_r(); break;
				default: break;
				}
				op++;
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println(op);
		}
	}
	/**
	 * @REQUIRES: The file has the map section.
	 * @MODIFIES: br, gui;
	 * @EFFECTS:
	 * create a MHandler object to parse the map file;
	 * then load it into gui;
	 */
	void parse_m() throws Exception {
		String name;
		String end = "#end_map";
		br.readLine();
		name = br.readLine();
		if(!name.equals(end)) {
			MHandler m = new MHandler(name);
			m.scan();
			gui.LoadMap(m.map, 80);
			br.readLine();
		}
	}
	/**
	 * @REQUIRES: The file has the light section.
	 * @MODIFIES: br;
	 * @EFFECTS:
	 * create a LHandler object to parse the light file;
	 * next, terminate the current Lctrl Thread;
	 * then, create a new Lctrl Thread to adapt the changes;
	 */
	void parse_l() throws Exception {
		String name;
		String end = "#end_light";
		try {
			br.readLine();
			name = br.readLine();
			if(!name.equals(end)) {
				LHandler l = new LHandler(name);
				l.scan();
				lc.over = true;
				lc = new Lctrl(l.lmap, gui);
				lc.start();
				br.readLine();
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
	/**
	 * @REQUIRES: This file has the flow section;
	 * @MODIFIES: br;
	 * @EFFECTS:parse the flow section of this file;
	 */
	public void parse_f() throws Exception{
		String read;
		String[] temp;
		String end = "#end_flow";
		int x1,y1,x2,y2, value;
		try {
			read = br.readLine();
			read = br.readLine();
			while(!read.equals(end)) {
				temp = read.split("[\\(\\), ]");
				x1 = Integer.parseInt(temp[1]);
				y1 = Integer.parseInt(temp[2]);
				x2 = Integer.parseInt(temp[3]);
				y2 = Integer.parseInt(temp[4]);
				value = Integer.parseInt(temp[5]);
				guigv.ModifyFlow(x1, y1, x2, y2, value);
				
				read = br.readLine();
			}
		}catch(IOException e) {
			throw new Exception();
		}
	}
	/**
	 * @REQUIRES: This file has the taxi section;
	 * @MODIFIES: taxis;
	 * @EFFECTS:
	 * (\all int index; index.appears()==>taxis[index].modify());
	 */
	void parse_t() throws IOException {
		String end = "#end_taxi";
		String read;
		String[] arr;
		
		int index;
		int state;
		int credit;
		_Point loc;
		try {
			read = br.readLine();
			read = br.readLine();
			while( ! read.equals(end)) {
				arr = read.split("[. ]");
				index = Integer.parseInt(arr[1]);
				state = Integer.parseInt(arr[2]);
				credit = Integer.parseInt(arr[3]);
				loc = _Point.parse_p(arr[4]);
				taxis[index].modify(state, credit, loc);
				gui.SetTaxiStatus(index, loc.toPoint(), state);
				
				read = br.readLine();
			}
		}catch(IOException e) {
			throw e;
		}
	}
	
	/**
	 * @REQUIRES: This file has the request section;
	 * @MODIFIES: br;
	 * @EFFECTS:parse the request section of this file;
	 */
	public void parse_r() throws IOException {
		String read;
		String end = "#end_request";
		Request temp;
		try {
			read = br.readLine();
			read = br.readLine();
			while(!read.equals(end)) {
				temp = this.parse(read);
				queue.put(temp);
				
				read = br.readLine();
			}
		}catch(IOException e) {
			throw e;
		}
	}
	/**
	 * @REQUIRES:(\exists String in; in.matches(req));
	 * @MODIFIES:none;
	 * @EFFECTS:(\exists Request r; r == in.parse());
	 * (\result == r);
	 */
	public synchronized Request parse(String in){
		String[] req = in.split("[\\(\\)]");
		_Point src, dst;
		Request r;
		double time;
		src = _Point.parse_p(req[1]);
		dst = _Point.parse_p(req[3]);
		if(src == null || dst == null) return null;
		else if(src.equals(dst)) return null;
		else {
			time = (double)(System.currentTimeMillis() - basetime) / 1000.0;
			r = new Request(src, dst, time);
			return r;
		}
	}
}
