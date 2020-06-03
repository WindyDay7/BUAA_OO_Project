package oo.other1;

import java.util.regex.Pattern;

public class Request {
	/**
	 * @OVERVIEW: 
	 * Request processing and store basic data
	 */
	private int is_in; // 0 for FR, 1 for ER
	private int state; // 0 for DOWN, 1 for UP
	private int floor; 
	private double time;
	private int error = 0;
	
	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: None;
	 */
	public Request() {
		
	}
	
	/**
	 * @REQUIRES: str != null;
	 * @MODIFIES: this.is_in; this.state; this.floor; this.time; this.error;
	 * @EFFECTS: true ==> (generate request using regex);
	 */
	public Request(String str) {
		String regex1 = "\\(FR,[\\+]?[0]{0,3}([1-9]|10),(UP|DOWN),[\\+]?[0]{0,3}[0123456789]{1,10}\\)";
		String regex2 = "\\(ER,[\\+]?[0]{0,3}([1-9]|10),[\\+]?[0]{0,3}[0123456789]{1,10}\\)";
		int i=0, j=0;
		char ch;
		int[] mark = new int[10];
		boolean match1 = Pattern.matches(regex1, str);
		boolean match2 = Pattern.matches(regex2, str);
		if(match1) {
			for(i=0;i<str.length();i++) {
				ch = str.charAt(i);
				if(ch == '(' || ch == ',' || ch == ')') {
					mark[j++] = i;
				}
			}
	 		String str1 = str.substring(mark[1]+1, mark[2]);
	 		String str2 = str.substring(mark[2]+1, mark[3]);
	 		String str3 = str.substring(mark[3]+1, mark[4]);
	 		
	 		this.is_in = 0;
	 		this.floor = (int) StringtoDouble(str1);
	 		
	 		
	 		if(str2.equals("DOWN")) {
	 			this.state = 0;
	 		}
	 		else {
	 			this.state = 1;
	 		}
	 		
	 		if(StringtoDouble(str3) == -1) {
	 			this.error = 1;
	 		}
	 		else this.time = StringtoDouble(str3);
	 		if((this.state == 0 && this.floor == 1) || (this.state == 1 && this.floor == 10))
	 			this.error = 1;
		}
		else if(match2) {
			this.is_in = 1;
			this.state = 2;
			for(i=0;i<str.length();i++) {
				ch = str.charAt(i);
				if(ch == '(' || ch == ',' || ch == ')') {
					mark[j++] = i;
				}
			}
	 		String str1 = str.substring(mark[1]+1, mark[2]);
	 		String str2 = str.substring(mark[2]+1, mark[3]);
	 		this.floor = (int) StringtoDouble(str1);
	 		if(StringtoDouble(str2) == -1) {
	 			this.error = 1;
	 		}
	 		else {
	 			this.time = StringtoDouble(str2);
	 		}
		}
		else {
			this.error = 1;
		}
	}
	
	/**
	 * @REQUIRES: s != null;
	 * @MODIFIES: None;
	 * @EFFECTS: (parseDouble(s) == true) ==> \result == s.parseDouble();
	 * 			 (parseDouble(s) == false) ==> \result == -1;
	 */
	public double StringtoDouble(String s) {
		int i;
		double num=0;
		if(s.charAt(0) == '+') {
			for(i=1;i<s.length();i++) {
				num = num*10 + s.charAt(i) - '0';
			}
		}
		else {
			for(i=0;i<s.length();i++) {
				num = num*10 + s.charAt(i) - '0';
			} 
		}
		if(num > 4294967295l) {
			return -1;
		}else {
			return num;
		}
	}
	
	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == this.is_in;
	 */
	public int getIs_in() {
		return is_in;
	}

	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == this.state;
	 */
	public int getState() {
		return state;
	}

	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == this.floor;
	 */
	public int getFloor() {
		return floor;
	}

	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == this.time;
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == this.error;
	 */
	public int getError() {
		return error;
	}
	
	
	/**
	 * @REQUIRES: is_in == 0 || is_in == 1;
	 * @MODIFIES: this.is_in;
	 * @EFFECTS: this.is_in == is_in;
	 */
	public void setIs_in(int is_in) {
		this.is_in = is_in;
	}

	/**
	 * @REQUIRES: is_in == 0 || is_in == 1;
	 * @MODIFIES: this.is_in;
	 * @EFFECTS: this.is_in == is_in;
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @REQUIRES: 0 <= state <= 2;
	 * @MODIFIES: this.state;
	 * @EFFECTS: this.state == state;
	 */
	public void setFloor(int floor) {
		this.floor = floor;
	}

	/**
	 * @REQUIRES: time >= 0;
	 * @MODIFIES: this.time;
	 * @EFFECTS: this.time == time;
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * @REQUIRES: error == 0 || error == 1;
	 * @MODIFIES: this.error;
	 * @EFFECTS: this.error == error;
	 */
	public void setError(int error) {
		this.error = error;
	}

	/**
	 * @EFFECTS: \result == invariant(this);
	 */
	public boolean repOK() {
		if(is_in > 1 || is_in < 0) return false;
		if(floor > 10 || floor <= 0) return false;
		if(time < 0) return false;
		if(error < 0 || error > 1) return false;
		return true;
	}
	
}

