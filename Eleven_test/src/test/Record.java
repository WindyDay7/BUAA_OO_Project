package test;

import java.util.LinkedList;
import java.util.ListIterator;

public class Record {
	/**@OVERVIEW:
	 * A Record is a mutable object containing:
	 * a Request,
	 * a _Point variable indicating the location where the driver received this request,
	 * a list of _Points recording all the paths.
	 * 
	 * @INVARIANT:
	 * path.get(0) == loc;
	 * path.get(path.size()-1) == req.dst;
	 */
	boolean virtual;
	Request req;
	_Point loc;
	LinkedList<_Point> path;
	ListIterator<_Point> plist;

	public Record(Request r, _Point l, boolean v) {
		// TODO Auto-generated constructor stub
		virtual = v;
		req = r;
		loc = l;
		path = new LinkedList<_Point>();
		path.add(l);
	}

	public boolean repOK() {
		return req.repOK() && loc.repOK() && path.get(0).equals(loc);
	}
}
