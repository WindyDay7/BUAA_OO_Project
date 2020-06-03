package test;

import java.util.LinkedList;
import java.util.ListIterator;

public class RecTaxi extends Taxi {
	/**@OVERVIEW:
	 * The class RecTaxi inherits from the class Taxi.
	 * It not only keeps a record of every customer,
	 * but also can get access to closed roads.
	 * The Collection reclist records every service completed.
	 * The variable sreq is immutable and has value [CR,(19,19),(59,59),6.0] .
	 * 
	 * @INVARIANT:
	 * 0 <= index < 30;
	 */
	protected Request sreq;
	private Record current;
	private LinkedList<Record> reclist;
	ListIterator<Record> iter;
	
	public RecTaxi(int i, SafeFile sf, TaxiGUI g, int st, guiInfo gi) {
		super(i, sf, g, st, gi);
		reclist = new LinkedList<Record>();
		sreq = new Request(ssrc, sdst, 6.0);
	}
	public boolean repOK() {
		return super.repOK() && (index < 30);
	}

	/**
	 * @REQUIRES: none;
	 * @MODIFIES: current;
	 * @EFFECTS:
	 * first modify instance variables to get ready for picking up;
	 * then create a new Record object to start recording this service;
	 */
	public synchronized void ready() {
		super.ready();
		current = new Record(onservice, location, false);
	}
	/**
	 * @REQUIRES:(\exist int dst; 0 <= dst < 6400);
	 * @MODIFIES:current;
	 * @EFFECTS:
	 * find the next point in the shortest path;
	 * tell the taxi to go to that point;
	 * add it into the current record;
	 */
	public void search(int dst) {
		super.search(dst);
		current.path.add(location);
	}
	/**
	 * @REQUIRES: this.status == 1;
	 * @MODIFIES:
	 * dst_final, credit, picked, received, onservice, status, current, reclist;
	 * @EFFECTS:
	 * First, serve the customer and complete the record;
	 * Next, modify instance variables;
	 * Finally, add this record into the list.
	 */
	void case_1() {
		if(picked) {
			dst_final = onservice.dst;
		}
		else {
			dst_final = sdst;
			current = new Record(sreq, location, true);
		}
		serve();
		credit += 3;
		picked = false;
		received = false;
		onservice = null;
		status = 0;
		
		current.plist = current.path.listIterator();
		reclist.add(current);
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
					this.case_1();// extended
					break;
				case 2:
					while(status == 2) {
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
					if(picked) {
						dst_final = onservice.src;
					}
					else {
						dst_final = ssrc;
						current = new Record(sreq, location, true);
						picked = true;
						onservice = sreq;
					}
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
	 * @REQUIRES:(\exist Record rec; rec != null)
	 * @MODIFIES:out;
	 * @EFFECTS:
	 * write a record into the file out;
	 */
	synchronized void print(Record rec) {
		out.write("#Request: " + rec.req.toString());
		out.newLine();
		out.write("#Location: " + rec.loc.toString());
		out.newLine();
		out.write("#Path:");
		out.newLine();
		while(rec.plist.hasNext()) {
			out.write(rec.plist.next().toString());
		}
		out.newLine();
	}
	/**
	 * @REQUIRES:
	 * (\exist char s; s == 'n' || s == 'p');
	 * @MODIFIES:iter;
	 * @EFFECTS:
	 * (s=='n')==>(iterate it in the normal order);
	 * (s=='p')==>(iterate it in the reverse order);
	 */
	public void prtall(char s) {
		boolean flag = false;
		int size = reclist.size();
		String str = String.format("#Start output the record of Taxi No.%d :", index);
		out.write(str);
		out.newLine();
		if(s == 'p') {
			iter = reclist.listIterator(size);
			while(iter.hasPrevious()) {
				flag = true;
				print(iter.previous());
			}
		}
		else {
			iter = reclist.listIterator();
			while(iter.hasNext()) {
				flag = true;
				print(iter.next());
			}
		}
		if( ! flag) {
			out.write("No record.");
			out.newLine();
		}
		out.write("#End record");
		out.newLine();
	}
}
