package test;

import java.awt.Point;

public class Lctrl extends Thread {
	boolean[][] exist;
	TaxiGUI gui;
	guiInfo gi;
	boolean over;
	int stime;
	int[][] state;
	int[][] degree;
	
	public Lctrl(boolean[][] a1, TaxiGUI g) {
		exist = a1;
		gui = g;
		gi = guigv.m;
		over = false;
		stime = (int)(500 + 500 * Math.random());
		state = new int[80][80];
	}
	boolean repOK() {
		return true;
	}
	
	/**
	 * @REQUIRES: none;
	 * @MODIFIES: degree;
	 * @EFFECTS:
	 * record every point's degree in the array degree[][] ;
	 */
	public void dmatr() {
		degree = new int[80][80];
		int i, j, k;
		int sum = 0;
		for(i = 0; i < 80; i++) {
			for(j = 0; j < 80; j++) {
				k = i * 80 + j;
				for(int l: gi.graph[k]) {
					sum += l;
				}
				degree[i][j] = sum;
			}
		}
	}
	/**
	 * @REQUIRES: none;
	 * @MODIFIES: state, gui ;
	 * @EFFECTS:
	 * (\all int i; 0 <= i < 80)(\all int j; 0 <= j < 80)
	 * (exist[i][j] ==> state[i][j] == 1) &&
	 * (!exist[i][j] ==> state[i][j] == 0);
	 * use the SetLightStatus method to initiate gui accordingly;
	 */
	public void init() {
		int i, j;
		Point temp;
		_Point _temp;
		for(i = 0; i < 80; i++) {
			for(j = 0; j < 80; j++) {
				temp = new Point(i,j);
				if(exist[i][j] && degree[i][j] > 2) {
					state[i][j] = 1;
					gui.SetLightStatus(temp, 1);
				}
				else {
					state[i][j] = 0;
					gui.SetLightStatus(temp, 0);
					if(exist[i][j]) {
						_temp = new _Point(temp.x,temp.y);
						System.out.print("# Setting traffic light at ");
						System.out.println(_temp.toString()+" ignored.");
					}
				}
			}
		}
	}
	/**
	 * @REQUIRES: none;
	 * @MODIFIES: state, gui ;
	 * @EFFECTS:
	 * (\all int i; 0 <= i < 80)(\all int j; 0 <= j < 80)
	 * (\old(state[i][j])==1 ==> state[i][j]==2) &&
	 * (\old(state[i][j])==2 ==> state[i][j]==1);
	 * use the SetLightStatus method to change gui accordingly;
	 */
	public void run() {
		Point temp;
		int i, j;
		try {
			dmatr();
			init();
			while(!over) {
				sleep(stime);
				for(i = 0; i < 80; i++) {
					for(j = 0; j < 80; j++) {
						if(state[i][j] != 0) {
							state[i][j] = (state[i][j]==1)? 2 : 1 ;
							temp = new Point(i,j);
							gui.SetLightStatus(temp, state[i][j]);
						}
					}
				}
			}
		}catch(InterruptedException e) {
			System.out.println(e.getMessage());
		}
	}
}