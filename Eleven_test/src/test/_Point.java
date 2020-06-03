package test;

import java.awt.Point;

public class _Point {
	/**@OVERVIEW:
	 * _Point objects are immutable.
	 * Each _Point object contains 2 short data representing a coordinate in this city.
	 * A typical _Point is (x,y) .
	 * 
	 * @INVARIANT:
	 * 0 <= x < 80;
	 * 0 <= y < 80;
	 */
	short x;
	short y;
	
	public _Point(short cox, short coy){
		x = cox;
		y = coy;
	}
	public _Point(int cox, int coy) {
		x = (short)cox;
		y = (short)coy;
	}
	public boolean repOK() {
		return 0 <= x && x < 80 &&
				0 <= y && y < 80 ;
	}
	
	/**
	 * @REQUIRES:(\exist String in; in == str1 + "," + str2);
	 * @MODIFIES:none;
	 * @EFFECTS:(\exist _Point p; p.x == str1 && p.y == str2);
	 * (\result == p);
	 */
	public static _Point parse_p(String in){
		_Point np;
		String temp = in.replaceAll("[\\(\\)]", "");
		String[] point = temp.split(",");
		short x = Short.parseShort(point[0]);
		short y = Short.parseShort(point[1]);
		if((x < 0) || (x > 79) || (y < 0) || (y > 79)) return null;
		else {
			np = new _Point(x, y);
			return np;
		}
	}
	/**
	 * @REQUIRES:(\exist int i; 0<=i<6400);
	 * @MODIFIES:none;
	 * @EFFECTS:(\exist _Point re; re.x == i / 80 && re.y == i % 80);
	 * (\result == re);
	 */
	public static _Point parse_i(int i) {
		short cox = (short)(i / 80);
		short coy = (short)(i % 80);
		return new _Point(cox,coy);
	}
	/**
	 * @REQUIRES:(\exist _Point np);
	 * @MODIFIES:none;
	 * @EFFECTS:(\result == (np == this));
	 */
	public boolean equals(_Point np) {
		return (x == np.x) && (y == np.y);
	}
	/**
	 * @REQUIRES:(\exist _Point src);
	 * @MODIFIES:none;
	 * @EFFECTS:
	 * determine whether this point is in the 4 * 4 area around src;
	 */
	public boolean within(_Point src) {
		short i = src.x;
		short j = src.y;
		return (i-2 <= x && x <= i+2) &&
				(j-2 <= y && y <= j+2);
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:none;
	 * @EFFECTS:
	 * return a String object representing the coordinate of this _Point;
	 */
	public String toString() {
		return String.format("(%d,%d)", x, y);
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:none;
	 * @EFFECTS:(\exist Point p; p.x == x && p.y == y);
	 * (\result == p);
	 */
	public Point toPoint() {
		return new Point(x, y);
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:none;
	 * @EFFECTS:(\exist int i; i == x * 80 + y);
	 * (\result == i);
	 */
	public int toInt() {
		return (x * 80 + y);
	}
}
