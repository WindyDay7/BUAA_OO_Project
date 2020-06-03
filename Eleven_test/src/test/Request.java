package test;

public class Request {
	/**@OVERVIEW:
	 * A request is an immutable object, containing two _Point objects and a sys_time of type double.
	 * A typical Request is like [CR,(1,1),(3,5),1.0] .
	 * 
	 * @INVARIANT:
	 * src != dst;
	 * sys_time >= 0;
	 */
	_Point src, dst;
	double sys_time;

	/**
	 * @REQUIRES: _Point s, _Point d, double t;
	 * @MODIFIES: Request;
	 * @EFFECTS: create a request and set its time;
	 */
	public Request(_Point s, _Point d, double t){
		src = s;
		dst = d;
		sys_time = t;
	}
	public boolean repOK() {
		return src.repOK() && dst.repOK() &&
				!src.equals(dst) && (sys_time >= 0);
	}
	
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:none;
	 * @EFFECTS:(\exist String r; r == this.parse());(\result == r);
	 */
	public String toString() {
		String s1,s2;
		s1 = "[CR," + src.toString() + "," + dst.toString() ;
		s2 = String.format(",%.1f]", sys_time);
		return (s1 + s2);
	}
	/**
	 * @REQUIRES:(\exist Request r2);
	 * @MODIFIES:none;
	 * @EFFECTS:(\result == (this.route == r2.route && this.time-r2.time < 0.1s));
	 */
	public boolean equivalent(Request r2) {
		if(r2 == null) return false;
		return (src.equals(r2.src) &&
				dst.equals(r2.dst) &&
				(r2.sys_time - sys_time) < 0.1 );
	}
}
