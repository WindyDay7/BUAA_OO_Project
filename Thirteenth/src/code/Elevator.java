package code;

class Elevator {
	/*Overview
	*carry out all requests that could be excuted in the way , 
	*output the informations about the requests carry out.
	*/
	protected long[][][] in_way = new long[100][5][5]; //捎带的请求队列
	protected int[] state_in = new int[4];
	protected double st_time = 0.0;  //表示当前的时间
	protected double mo_time = 0; //表示输出的时间
	protected long [] move = new long[5];  //表示主请求
	protected long [] empty = new long[5];
	protected int[][] button = new int[11][4];   //表示楼层的按钮是否已经被按下,1,2,3分别表示楼层内，楼层外上，楼层外下
	
	protected Elevator() {
		state_in[0] = 1;  //初始化表示在第一层
	}
	public void change() {
		/*Requires:none
		 *Modifies:state_in, in_way, st_time, move, empty, button, mo_time
		 *Effects:this.state_in[0] < this.move[0] ==> this.state_in++ && get the floor in this.state_in && output information
		 *state_in[0] < this.move[0] ==> this.state_in-- && get the floor in thid.state_in && informatioin
		*/
		int count = 0;
		this.mo_time = 0;
		
		if(this.state_in[0] < this.move[0]) {
			this.state_in[1] = 1;  //表示电梯向上执行
			while(this.state_in[0] <= this.move[0]) {  //循环执行所有可以捎带的指令
				count = 0;
				if(this.in_way[this.state_in[0]][0][0] != 0) {
					if(state_in[0]==move[0]) {
						if(this.in_way[this.state_in[0]][0][1]>this.st_time) {
							this.st_time = this.in_way[this.state_in[0]][0][1];
						}
						this.st_time = this.st_time + this.mo_time;
					}
					else {
						this.st_time = this.st_time + this.mo_time;

						if(this.in_way[this.state_in[0]][0][1]>this.st_time) {
							this.st_time = this.in_way[this.state_in[0]][0][1]; 
							this.st_time = this.st_time + this.mo_time;
						}
					}
					this.mo_time = 0;   //判断是否是主指令
					while(this.in_way[this.state_in[0]][count][0] != 0) {
						output(this.in_way[this.state_in[0]][count]);
						System.out.printf("(%d,%s,%.1f)\n", this.state_in[0],"UP",this.st_time);//在此执行该条指令，循环表示执行该楼层的所有指令
						this.in_way[this.state_in[0]][count] = this.empty;
						count++;
					}
					this.mo_time++;
				}
				this.button[this.state_in[0]][1] = 0;
				this.button[this.state_in[0]][2] = 0;
				this.button[this.state_in[0]][3] = 0;
				if(this.state_in[0] == this.move[0]) {
					break;
				}
				this.state_in[0]++;
				this.mo_time += 0.5;  //每一层楼运行时间是0.5	
				//System.out.println(this.st_time);
			}
			this.st_time++;
		}
		
		else if(this.state_in[0] > this.move[0]) {
			this.state_in[1] = 2;  //表示电梯向下执行
			while(this.state_in[0] >= this.move[0]) {
				count=0;
				if(this.in_way[this.state_in[0]][0][0] != 0) {
					if(state_in[0]==move[0]) {
						if(this.in_way[this.state_in[0]][0][1]>this.st_time) {
							this.st_time = this.in_way[this.state_in[0]][0][1];
						}
						this.st_time = this.st_time + this.mo_time;
					}
					else {
						this.st_time = this.st_time + this.mo_time;
						if(this.in_way[this.state_in[0]][0][1]>this.st_time) {
							this.st_time = this.in_way[this.state_in[0]][0][1];
							this.st_time = this.st_time + this.mo_time;
						}
					}
					this.mo_time = 0;   //判断是否是主指令
					while(this.in_way[this.state_in[0]][count][0] != 0) {
						
						output(this.in_way[this.state_in[0]][count]);
						System.out.printf("(%d,%s,%.1f)\n", this.state_in[0],"DOWN",this.st_time);//在此执行该条指令，循环表示执行该楼层的所有指令
						this.in_way[this.state_in[0]][count] = this.empty;
						count++;
					}
					this.mo_time++;
				}
				this.button[this.state_in[0]][1] = 0;
				this.button[this.state_in[0]][2] = 0;
				this.button[this.state_in[0]][3] = 0;
				if(this.state_in[0] == this.move[0]) {
					break;
				}
				this.state_in[0]--;
				this.mo_time += 0.5;  //每一层楼运行时间是0.5
			}
			this.st_time++;
		}
		
		
		else {
			this.state_in[1] = 3; //表示电梯处于still状态
			count = 0;
			if(this.in_way[this.state_in[0]][0][1]>this.st_time) {
				this.st_time = this.in_way[this.state_in[0]][0][1];
			}
			this.st_time++;
			while(this.in_way[this.state_in[0]][count][0] != 0) {
				
				output(this.in_way[this.state_in[0]][count]);
				System.out.printf("(%d,%s,%.1f)\n", this.state_in[0],"STILL",this.st_time);//在此执行该条指令，循环表示执行该楼层的所有指令
				this.in_way[this.state_in[0]][count] = this.empty;
			}
			this.button[this.state_in[0]][1] = 0;
			this.button[this.state_in[0]][2] = 0;
			this.button[this.state_in[0]][3] = 0;
		}
		
		this.in_way[0][0][0] = 0; //表示主请求被执行了啊
	}
	
	public String toString() { //用toString方法返回状态和结果
		/*requires:none
		**modifies:none
	 	 *effects:this.state_in[1]==1 ==> result=="(" + this.state_in[0] + ",UP," + (this.st_time-1) + ")\n"
	 	 * this.state_in[1]==2 ==> "(" + this.state_in[0] + ",DOWN," + (this.st_time-1) + ")\n"
	 	 * this.state_in[1]==3 ==> "(" + this.state_in[0] + ",UP," + this.st_time + ")\n";
	 	 * this.state_in[1]!=2 && this.state_in[1]!=1 && this.state_in[1]!=3 ==> result=="No probility"
		*/
		if(this.state_in[1]==1) {
			return "(" + this.state_in[0] + ",UP," + (this.st_time-1) + ")\n";
		}
		else if(this.state_in[1]==2) {
			return "(" + this.state_in[0] + ",DOWN," + (this.st_time-1) + ")\n";
		}
		else if(this.state_in[1]==3) {
			return "(" + this.state_in[0] + ",UP," + this.st_time + ")\n";
		}
		else {
			return "No probility";
		}
	}

	protected void output(long put[]) {
		/*requires:put[]
		*modifies: no
		*effects:put[2]==2 ==> output"ER"  put[2]!=2 ==> output"FR"  
		*:put[3]==1 ==> output"UP"  put[3]==2 ==> output"DOWN"
		*: output put[1] + "]/"
		*/
		System.out.printf("[");
		if(put[2] == 2) {
			System.out.printf("ER,");   
		}
		else {
			System.out.printf("FR,");
		}
		System.out.printf("%d,",put[0]);
		if(put[3] == 1) {
			System.out.printf("UP,");
		}
		else if(put[3] == 2) {
			System.out.printf("DOWN,");
		}
		System.out.printf("%d%s",put[1],"]/");
	}

	public boolean repOK() {
		if(this.in_way[0][0][0]<0 || this.state_in[0]<0 || this.move[0]<0 || this.empty[0]<0 || this.button[0][0]<0)
			return false;
		else if(st_time<0 || this.mo_time<0)
			return false;
		else
			return true;
	}
}
/*state[0]表示状态
state[1]表示楼层
*/