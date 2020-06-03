package test;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CHandler extends Thread {
	TaxiGUI gui;
	Taxi[] taxis;
	Queue queue;
	Lctrl lc;
	
	long basetime;
	
	String regex_req = "\\[CR,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)\\]";
	String regex_load = "Load \\S+";
	String regex_road = "(Open|Close),\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)";
	String regex_rec = "Print,\\d{1,2},(n|p)";// added in hw 11
	String end = "END";
	Scanner sc;

	/**
	 * @REQUIRES:TaxiGUI g, Taxi[] ts, Queue q;
	 * @MODIFIES:CHandler;
	 * @EFFECTS: create an CHandler Object;
	 */
	public CHandler(TaxiGUI g, Taxi[] ts, Queue q, Lctrl LC) {
		gui = g;
		taxis = ts;
		queue = q;
		lc = LC;
	}
	boolean repOK() {
		return true;
	}
	
	/**
	 * @REQUIRES:System.in
	 * @MODIFIES:queue
	 * @EFFECTS:(\exists Request r; r = this.parse())==>(queue.put(r));
	 * (\exists String in; in.equals("END"))==>(sc.close());
	 */
	public void run() {
		try {
			byte op;
			int count = 0;
			String temp;
			String[] sp;
			
			FHandler fh;
			Request req;
			
			System.out.println("# You may start input:");
			sc = new Scanner(System.in);
			temp = sc.nextLine();
			basetime = System.currentTimeMillis();
			count++;
			while(count < 500) {
				op = this.validate(temp);
				if(op == 0) break;
				else if(op == 1) {
					req = this.parse(temp);
					if(req != null) queue.put(req);
				}
				else if(op == 2) {
					try {
						sp = temp.split(" ");
						fh = new FHandler(sp[1], gui, queue, taxis, lc, basetime);
						fh.start();
					}catch(FileNotFoundException e) {
						System.out.println(e.getMessage());
						System.out.println("Exception in testfile.");
						break;
					}
				}
				else if(op == 3) {
					this.parse_r(temp);
				}
				else if(op == 4) {
					parse_rec(temp);// added in hw 11
				}
				else {
					System.out.println("line " + count + ": Wrong Format");
				}
				
				sleep(1000);
				temp = sc.nextLine();
				count++;
			}
			sc.close();
		}catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
	/**
	 * @REQUIRES:(\exists String in)
	 * @MODIFIES:none;
	 * @EFFECTS:
	 * (in == end)==>(\result == 0);
	 * (in.matches(regex_req))==>(\result == 1);
	 * (in.matches(regex_load))==>(\result == 2);
	 * (in.matches(regex_road))==>(\result == 3);
	 * (in does not match any known format)==>(\result == -1);
	 */
	public byte validate(String in) {
		Matcher m1 = Pattern.compile(regex_req).matcher(in);
		Matcher m2 = Pattern.compile(regex_load).matcher(in);
		Matcher m3 = Pattern.compile(regex_road).matcher(in);
		Matcher m4 = Pattern.compile(regex_rec).matcher(in);
		if(in.equals(end)) return 0;
		else if(m1.matches()) return 1;
		else if(m2.matches()) return 2;
		else if(m3.matches()) return 3;
		else if(m4.matches()) return 4;
		else return -1;
	}
	/**
	 * @REQUIRES:(\exists String in; in.matches(req));
	 * @MODIFIES:none;
	 * @EFFECTS:(\exists Request r; r == in.parse());
	 * (\result == r);
	 */
	public Request parse(String in){
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
	/**
	 * @REQUIRES:(\exists String in; in.matches(road));
	 * @MODIFIES:gui;
	 * @EFFECTS:
	 * Open or close a road specified by the String in;
	 * Modify gui accordingly;
	 */
	public void parse_r(String in) {
		int status = (in.charAt(0) == 'O') ? 1 : 0 ;
		String[] ps = in.split("[\\(\\)]");
		Point p1, p2;
		p1 = _Point.parse_p(ps[1]).toPoint();
		p2 = _Point.parse_p(ps[3]).toPoint();
		gui.SetRoadStatus(p1, p2, status);
	}
	/**
	 * @REQUIRES:
	 * (\exist String in; in == "Print,<str1>,<str2>")
	 * @MODIFIES:none;
	 * @EFFECTS:
	 * index == (int)str1;
	 * (\exist char d; d == str2[0])
	 * (0 <= index < 30)==>(taxis[index].prtall(d));
	 * (index >= 30)==>(output error message in console);
	 */
	public void parse_rec(String in) {
		String[] spl = in.split(",");
		int index = Integer.parseInt(spl[1]);
		char d = spl[2].charAt(0);
		if(index >= 0 && index < 30) {
			RecTaxi t = (RecTaxi)(taxis[index]);
			t.prtall(d);
		}
		else {
			System.out.println("Taxi No." + index + " is not a special taxi!");
		}
	}
}
