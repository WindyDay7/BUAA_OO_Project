package code;

class Override {
	/**Overview
	*get the requestLine from Queue and get the mostly instration , and get the requests that could be carry out in the way
	*and judge the same requests 
	*/
	protected long[][][] on_way = new long[100][5][5]; //捎带的请求队列
	protected long[] old_instr = new long[5]; //表示刚刚完成的哪一条指令
	protected long[] pr_instr = new long[5];  //表示主请求
	protected int[][] occupy = new int[11][4]; //电梯的按钮
	protected int[] station = new int[4]; //0表示楼层，1表示电梯运行的状态,1表示向上，2表示向下
	protected int count_num = 0; //表示有效指 令的数目
	protected long [] instr_o = new long[5];
	protected long state_c = (long) 0; //用state表示电梯状态
	protected double stop_time = 0; //此时电梯一定处于停止状态
	protected long[][] request_n =  new long[1000][5]; //正确的请求队列，
	protected int i = 0;
	//
	protected void deal_t() {
		/**
		requires:none
		modifies:on_way, old_instr, pr_instr, occupy, station , count_num
		effects:on_way[0][0][0]==0 ==> get the next pr_instr && add the on_way
		*this.pr_instr[1] > this.stop_time  ==> get the stop time
		*\all k; k< this.count_num) && (this.request_n[k][1] < (j-1)  ==> judge the same request and judge the requets that could be bring
		*/
		this.on_way[11][0] = this.pr_instr;  //11号表示当前的主请求，未改变之前
		//System.out.println(this.station[1]);
		int num = 0;
		int chose=0;
		long[] temp = new long[5];
		
		if(this.on_way[0][0][0] == 0) {//判断下一个主请求是哪一个
			//  ①
			if(this.station[1]==2){ //表示电梯之前是向下运行的，所以找出楼层最低的作为主请求  
				for(num=1;num<=10;) {
					if(this.on_way[num][0][0]==0)
						num++;
					else 
						break;
				}  //判断捎带队列中的哪一个作为下一个主请求
				if(num==11) {     //num==11.表示捎带队列已经空了，所以将请求队列中的下一个作为主请求，并加入到捎带队列中
					while(this.request_n[i][4]!=0)
						i++;  //表示在请求队列中将已经执行的队列过滤掉
					this.on_way[0][0] = this.request_n[i];
					this.pr_instr = this.request_n[i];
					this.on_way[(int) this.request_n[i][0]][0] = this.request_n[i];//表示主请求的哪一个楼层的请求
					
					this.request_n[i][4] = i+1;
				}
				else {
					this.on_way[0][0] = this.on_way[num][0];  //第0个表示主请求
					this.pr_instr = this.on_way[num][0];
				}
			}//如果是下行就找到最远的哪一个作为主请求，也就是楼层最小的那个作为朱请求
			// ②
			else if(this.station[1]==1){  //表示电梯之前是向上运行的
				//System.out.println("Station==1是执行过的");
				for(num=10;num>=1;) {
					if(this.on_way[num][0][0]==0)
						num--;
					else 
						break;
				}  //判断捎带队列中的哪一个作为下一个主请求
				if(num==0) {     //num==0.表示捎带队列已经空了，所以将请求队列中下一个作为主请求，并加入到捎带队列中
					while(this.request_n[i][4]!=0)
						i++;  //表示在请求队列中将已经执行的队列过滤掉
					this.on_way[0][0] = this.request_n[i];
					this.pr_instr = this.request_n[i];
					this.on_way[(int) this.request_n[i][0]][0] = this.request_n[i];//表示主请求的哪一个楼层的请求
					this.request_n[i][4] = i+1;
				}
				else {
					this.on_way[0][0] = this.on_way[num][0];  //表示上行的时候将楼层最大的作为主请求
					this.pr_instr= this.on_way[num][0];
				}
			}
			//  ③，表示之前电梯是静止的
			else {
				
				while(this.request_n[i][4]!=0)
					i++;  //表示在请求队列中将已经执行的队列过滤掉
				this.on_way[0][0] = this.request_n[i];
				this.pr_instr = this.request_n[i];
				this.on_way[(int) this.request_n[i][0]][0] = this.request_n[i];//表示主请求的哪一个楼层的请求
				
				this.request_n[i][4] = i+1;
			}
			
			if(this.pr_instr[3]==1) {   //表示上行的
				this.occupy[(int) this.pr_instr[0]][2]=1;
			}
			else if(this.pr_instr[3]==2) {   //表示下行
				this.occupy[(int) this.pr_instr[0]][3]=1;
			}
			else if(this.pr_instr[2]==2) {   //表示在电梯内
				this.occupy[(int) this.pr_instr[0]][1]=1;
			}
		}
		
		double j = 0;
		j = Math.abs(this.station[0] - this.pr_instr[0]);
		j = j * 0.5;
		j = j + 1.0;
		if(this.pr_instr[1] > this.stop_time) {
			this.stop_time = this.pr_instr[1];
		}
		j = j + this.stop_time;   //j表示主请求执行结束的时间
		
		double arrive = 0; //记录到达楼层的时间，用于判断是否是到达之前的捎带
		int floor = 0; 
		
		while(this.request_n[i][4]!=0)
			i++;  //表示在请求队列中将已经执行的队列过滤掉
		int k = i;  
		//至此表示生成出主请求结束
		//System.out.println(j-1);
		//表示上一条执行的指令就是主请求，及主请求被执行
		//表示将请求队列中的请求加入捎带
		//System.out.println(k);
		
			while((k < this.count_num) && (this.request_n[k][1] < (j-1))) //
			{
				arrive = 0;
	/*1*/		if(this.request_n[k][0] > this.station[0] && this.pr_instr[0]>this.station[0] && this.request_n[k][2]==2 && this.request_n[k][4]==0) {
					for(floor=this.station[0];floor < this.request_n[k][0];floor++) {
						if(this.on_way[floor][0][0]!=0) {
							arrive++;
						}
						arrive+=0.5;  
					}
					//计算出从当前楼层到队列中一层的时间
					if(this.occupy[(int) this.request_n[k][0]][1]!=0 && (arrive+this.stop_time)>this.request_n[k][1])  //表示该层楼电梯内的按钮已经被占用
					{
						this.request_n[k][4] = 1;
						System.out.printf("#SAME"); 
						output2(this.request_n[k]);
					}
	/*import*/		else if((arrive+this.stop_time) > this.request_n[k][1]  && this.request_n[k][4]==0) { //判断时间问题，运行时有没有已经到达这一层
						chose = 0;
						while(this.on_way[(int) this.request_n[k][0]][chose][0]!=0) {   //表示按钮还没有被按，可以捎带,不是同质请求
							chose++;
						}
						if(chose==1){
							if((k+1)<this.on_way[(int) this.request_n[k][0]][0][4])
							{
								temp = this.on_way[(int) this.request_n[k][0]][0];
								this.on_way[(int) this.request_n[k][0]][0] = this.request_n[k];
								this.on_way[(int) this.request_n[k][0]][chose] = temp;
							}
							else {
								this.on_way[(int) this.request_n[k][0]][chose] = this.request_n[k];//表示向上运行的电梯内请求捎带
							}
						}
						else{
							this.on_way[(int) this.request_n[k][0]][chose] = this.request_n[k];//表示向上运行的电梯内请求捎带
						}
						if(this.occupy[(int) this.request_n[k][0]][2]==0 || this.occupy[(int) this.request_n[k][0]][3]==0) {
							j++; 
						}
						this.occupy[(int) this.request_n[k][0]][1]=1;  //表示电梯内的按钮被按下
						this.request_n[k][4] = k+1;

					}	
				}
				
	/*2*/		else if(this.request_n[k][0] < this.station[0] && this.pr_instr[0]<this.station[0] && this.request_n[k][2]==2 && this.request_n[k][4]==0) {
					for(floor=this.station[0];floor>this.request_n[k][0];floor--) {
						if(this.on_way[floor][0][0]!=0) {
							arrive++;
						}
						arrive+=0.5;
					}
					if(this.occupy[(int) this.request_n[k][0]][1] != 0 && (arrive+this.stop_time)>this.request_n[k][1])  //表示该层楼的电梯内按钮已经被占用
					{
						this.request_n[k][4] = 1;
						
						System.out.printf("#SAME");
						output2(this.request_n[k]);
					}
					else if((arrive+this.stop_time) > this.request_n[k][1] && this.request_n[k][4]==0){
						chose = 0;
						while(this.on_way[(int) this.request_n[k][0]][chose][0]!=0) {   //表示按钮还没有被按，可以捎带,不是同质请求
							chose++;
						}
						if(chose==1){
							if((k+1)<this.on_way[(int) this.request_n[k][0]][0][4])
							{
								temp = this.on_way[(int) this.request_n[k][0]][0];
								this.on_way[(int) this.request_n[k][0]][0] = this.request_n[k];
								this.on_way[(int) this.request_n[k][0]][chose] = temp;
							}
							else {
								this.on_way[(int) this.request_n[k][0]][chose] = this.request_n[k];//表示向下运行的电梯内请求捎带
							}
						}
						else{
							this.on_way[(int) this.request_n[k][0]][chose] = this.request_n[k];//表示向下运行的电梯内请求捎带
							
						}
						if(this.occupy[(int) this.request_n[k][0]][2]==0 || this.occupy[(int) this.request_n[k][0]][3]==0) {
							j++;
						}
						this.occupy[(int) this.request_n[k][0]][1]=1; //表示电梯内的按钮被按下
						this.request_n[k][4] = k+1;
				
					}	
				}
				
	/*3*/		else if((this.request_n[k][0]>this.station[0] && this.request_n[k][0]<=this.pr_instr[0]) && this.pr_instr[0]>=this.station[0] && this.request_n[k][3]==1 && this.request_n[k][4]==0) {
					//表示在楼层的请求，请求方向向上，并且请求楼层在主请求目标楼层与当前楼层之间
					for(floor=this.station[0];floor < this.request_n[k][0];floor++) {
						if(this.on_way[floor][0][0]!=0) {
							arrive++;
						}
						arrive+=0.5;
					}
					//计算出从当前楼层到队列中一层的时间
					if(this.occupy[(int) this.request_n[k][0]][2]!=0 && (arrive+this.stop_time)>this.request_n[k][1])  //表示该层楼向上的的按钮已经被占用
					{
						this.request_n[k][4] = 1;
						System.out.printf("#SAME");
						output2(this.request_n[k]);
					}
					else if((arrive+this.stop_time) > this.request_n[k][1]  && this.request_n[k][4]==0){
						chose = 0;
						while(this.on_way[(int) this.request_n[k][0]][chose][0]!=0) {   //表示按钮还没有被按，可以捎带,不是同质请求
							chose++;
						}
						if(chose==1){
							if((k+1)<this.on_way[(int) this.request_n[k][0]][0][4])
							{
								temp = this.on_way[(int) this.request_n[k][0]][0];
								this.on_way[(int) this.request_n[k][0]][0] = this.request_n[k];
								this.on_way[(int) this.request_n[k][0]][chose] = temp;
							}
							else
								this.on_way[(int) this.request_n[k][0]][chose] = this.request_n[k];//表示向上运行的电梯内请求捎带
						}
						else{
							this.on_way[(int) this.request_n[k][0]][chose] = this.request_n[k];//表示向上运行的电梯内请求捎带
							
						}
						if(this.occupy[(int) this.request_n[k][0]][1]==0 || this.occupy[(int) this.request_n[k][0]][3]==0) {
							j++;
						}
						this.occupy[(int) this.request_n[k][0]][2]=1;  //表示电梯内的按钮被按下
						this.request_n[k][4] = k+1;
						
					}
				}
				
	/*4*/		else if((this.request_n[k][0]<this.station[0] && this.request_n[k][0]>=this.pr_instr[0]) && this.pr_instr[0]<=this.station[0] && this.request_n[k][3]==2 && this.request_n[k][4]==0) {
					//表示在楼层的请求，请求方向向下，并且请求楼层在主请求目标楼层与当前楼层之间
					for(floor=this.station[0];floor>this.request_n[k][0];floor--) {
						if(this.on_way[floor][0][0]!=0) {
							arrive++;
						}
						arrive+=0.5;
					}
					if(this.occupy[(int) this.request_n[k][0]][3] != 0 && (arrive+this.stop_time)>this.request_n[k][1])  //表示该层楼向下的按钮已经被占用
					{
						this.request_n[k][4] = 1;
						System.out.printf("#SAME");  
						output2(this.request_n[k]);
					}
					else if((arrive+this.stop_time) > this.request_n[k][1]  && this.request_n[k][4]==0){
						chose = 0;
						while(this.on_way[(int) this.request_n[k][0]][chose][0]!=0) {   //表示按钮还没有被按，可以捎带,不是同质请求
							chose++;
						}
						if(chose==1){
							if((k+1)<this.on_way[(int) this.request_n[k][0]][0][4])
							{
								temp = this.on_way[(int) this.request_n[k][0]][0];
								this.on_way[(int) this.request_n[k][0]][0] = this.request_n[k];
								this.on_way[(int) this.request_n[k][0]][chose] = temp;
							}
							else
								this.on_way[(int) this.request_n[k][0]][chose] = this.request_n[k];//表示向上运行的电梯内请求捎带
						}
						else{
							this.on_way[(int) this.request_n[k][0]][chose] = this.request_n[k];//表示向上运行的电梯内请求捎带
							
						}
						if(this.occupy[(int) this.request_n[k][0]][1]==0 || this.occupy[(int) this.request_n[k][0]][2]==0) {
							j++;
						}
						this.occupy[(int) this.request_n[k][0]][3]=1;  //表示楼层内向下的按钮被按下
						this.request_n[k][4] = k+1;
						
					}
				}
				else {
					System.out.println("没啥事");
				}
				k++;
			}
	}
	
	protected void output2(long put[]) {
		/*requires:put[]
		*modifies: no
		*effects:result == "["
		*put[2]==2 ==> output"ER"  put[2]!=2 ==> output"FR"  
		*:put[3]==1 ==> output"UP"  put[3]==2 ==> output"DOWN"
		*: output put[1] + "]/"
		*/
		System.out.print("[");
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
		System.out.printf("%d%s\n",put[1],"]");  
	}

	public boolean repOK() {
		if(this.on_way[0][0][0]<0 || this.old_instr[0]<0 || this.pr_instr[0]<0 || this.occupy[0][0]<0 || this.station[0]<0)
			return false;
		if(this.count_num<0 || this.request_n[0][0]<0)
			return false;
		return true;  
	}
}
/*2n-1表示电梯内的请求
2n表示楼层的请求*/

//同一层楼只开关门一次，所以即使捎带，时间只会加1，错误