package oo.other1;



public class Lift implements e_interface{
	/**
	 * @OVERVIEW:
	 * Simulate the elevator and the required data elements
	 */
	private int state;//0 for DOWN, 1 for UP, 2 for STILL
	private double time;
	private int floor_now;
	private int floor_begin;
	private int floor_target;
	
 	
	/**
	 * @REQUIRES: None;
	 * @MODIFIES: this.state; this.time; this.floor_now; this.floor_begin; this.floor_target;
	 * @EFFECTS: this.state == 1; this.time == 0.0; this.floor_now == 1; this.floor_begin == 1; this.floor_target == 1;
	 */
	public Lift() {
		this.state = 1;
		this.time =0.0;
		this.floor_now = 1;
		this.floor_begin = 1;
		this.floor_target = 1;
		
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
	 * @REQUIRES: 0 <= state <= 2;
	 * @MODIFIES: this.state;
	 * @EFFECTS: this.state == state;
	 */
	public void setState(int state) {
		this.state = state;
	}


	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == this.floor_now;
	 */
	public int getFloor_now() {
		return floor_now;
	}


	/**
	 * @REQUIRES: 1 <= floor_now <= 10;
	 * @MODIFIES: this.floor_now;
	 * @EFFECTS: this.floor_now == floor_now;
	 */
	public void setFloor_now(int floor_now) {
		this.floor_now = floor_now;
	}


	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == this.floor_begin;
	 */
	public int getFloor_begin() {
		return floor_begin;
	}


	/**
	 * @REQUIRES: 1 <= floor_begin <= 10;
	 * @MODIFIES: this.floor_begin;
	 * @EFFECTS: \result == this.floor_begin;
	 */
	public void setFloor_begin(int floor_begin) {
		this.floor_begin = floor_begin;
	}


	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == this.floor_target;
	 */
	public int getFloor_target() {
		return floor_target;
	}


	/**
	 * @REQUIRES: 1 <= floor_target <= 10;
	 * @MODIFIES: this.floor_target;
	 * @EFFECTS: this.floor_target == floor_target;
	 */
	public void setFloor_target(int floor_target) {
		this.floor_target = floor_target;
	}
	
	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == "["+state + time+"]";
	 */
	public String toString() {
		return "["+state + time+"]";
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
	 * @REQUIRES: time >= 0;
	 * @MODIFIES: this.time;
	 * @EFFECTS: this.time == time;
	 */
	public void setTime(double time) {
		this.time = time;
	}


	/**
	 * @EFFECTS: \result == invariant(this);
	 */
	public boolean repOK() {
		if(state > 2 || state < 0) return false;
		if(time < 0) return false;
		if(floor_begin > 10 || floor_begin <= 0) return false;
		if(floor_now > 10 || floor_now <= 0) return false;
		if(floor_target > 10 || floor_target <= 0) return false;
		return true;
	}

	
}
