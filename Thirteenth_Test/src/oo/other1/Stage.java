package oo.other1;

public class Stage {
	/**
	 * @OVERVIEW: 
	 * record information of up and down keys on each stage
	 */
	public double[] light = new double[2];
	
	/**
	 * @REQUIRES: None;
	 * @MODIFIES: this.light;
	 * @EFFECTS: this.light == {0.0,0.0};
	 */
	public Stage() {
		light[0] = 0.0;
		light[1] = 0.0;
		}
	
	/**
	 * @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == this.light;
	 */
	public double[] getLight() {
		return light;
	}
	
	/**
	 * @EFFECTS: \result == invariant(this);
	 */
	public boolean repOK() {
		if(light[0] < 0) return false;
		if(light[1] < 0) return false;
		return true;
	}
}
