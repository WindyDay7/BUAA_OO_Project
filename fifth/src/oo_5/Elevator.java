package oo_5;

class Elevator {
	
	protected int number = 0;
	protected int direction = 0;   //1表示向上，2表示向下
	protected int energy = 0;
	protected int max,min;
	protected int floor_num = 1;
	protected int[][] button = new int[22][6]; 
	//按钮的1，2，3分别表示向上按钮，向下按钮，电梯内按钮,4表示按钮被按下的时间
	protected double[][] re_queue = new double[200][6];
	protected int count;
	protected double first;
	protected double run_time;
	protected int last;
	protected int flag_2 = 0;

	public void find() {
		int floor_num1=0;
		int floor_num2=0;
		
		for(floor_num1=20;floor_num1>0;floor_num1--) {
			if(this.button[floor_num1][1]!=0 || this.button[floor_num1][2]!=0 || this.button[floor_num1][2]!=0 ) {
				this.max = floor_num1;
				break;
			}
		}
		
		for(floor_num2=0;floor_num2<=20;floor_num2++) {
			if(this.button[floor_num2][1]!=0 || this.button[floor_num2][2]!=0 || this.button[floor_num2][2]!=0 ) {
				this.min = floor_num2;
				break;
			}
		}
	}
	
}


	
/*state[0]表示状态
state[1]表示楼层
*/