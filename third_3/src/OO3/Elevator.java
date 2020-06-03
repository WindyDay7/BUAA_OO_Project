package OO3;

public class Elevator implements eleinterface {
	private int crtfloor = 1;
	private boolean[] button = new boolean[10];
	private int[] source = new int[10];
	private int direction = 0;
	
	public Elevator () {
		int i=0;
		for(i=0;i<10;i++) {
			button[i] = false;
			crtfloor = 1;
			source[i] = 0;
			direction = 0;
		}
	}
	
	public boolean getbutton(int i) {
		return button[i-1];
	}
	
	public void setbutton(int i, boolean b) {
		button[i-1] = b;
	}
	
	public int getfloor() {
		return crtfloor;
	}
	
	public void setfloor(int i) {
		crtfloor = i;
	}
	
	public int getsource(int floor) {
		return source[floor-1];
	}
	
	public void setsource(int floor, int r) {
		source[floor-1] = r;
	}
	
	public int getdirection() {
		return direction;
	}
	
	public void setdirection(int d) {
		direction = d;
	}
	
	public String toString() {
		return (Integer.toString(crtfloor)+","+Integer.toString(direction));
	}
}
