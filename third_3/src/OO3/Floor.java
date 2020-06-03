package OO3;

public class Floor {
	private boolean[] up = new boolean[10];
	private boolean[] down = new boolean[10];
	private double[] ftime = new double[10];
	private int[] stop = new int[10];
	private int[] upsource = new int[10];
	private int[] downsource = new int[10];
	
	public Floor() {
		int i=0;
		for (i=0;i<10;i++) {
			up[i] = false;
			down[i] = false;
			stop[i] = 0;
			ftime[i] = 0;
			upsource[i] = 0;
			downsource[i] = 0;
		}
	}
	
	public boolean getup(int i) {
		return up[i-1];
	}
	
	public boolean getdown(int i) {
		return down[i-1];
	}
	
	public void setup(int i, boolean b) {
		up[i-1] = b;
	}
	
	public void setdown (int i, boolean b) {
		down[i-1] = b;
	}
	
	public int getstop(int i) {
		return stop[i-1];
	}
	
	public void setstop(int i, int b) {
		if (b==1) {
			stop[i-1]++;
			//System.out.printf("Floor:%d   Stop:%d\n", i, stop[i-1]);
		}
		else if (b==0) {
			stop[i-1]=0;
			//System.out.printf("Floor:%d    Clear!!!\n", i);
		}
	}
	
	public double getftime(int i) {
		return ftime[i-1];
	}
	
	public void setftime(int i, double d) {
		ftime[i-1] = d;
	}
	
	public int getsource(int floor, int direction) {
		if (direction == 1)
			return upsource[floor-1];
		else return downsource[floor-1];
	}
	
	public void setsource(int floor, int direction, int r) {
		if (direction == 1)
				upsource[floor-1] = r;
		else downsource[floor-1] = r;
	}

}
