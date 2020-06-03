package oo_9;

import java.awt.Point;

class Taxi {
	protected int status, fetch;   //当前状态
	protected int location_x;    //当前位置
	protected int location_y;    //
	protected int a_head;   //时候已经抢单
	protected int acceptance;   //是否已经接单
	protected int destination_x, departure_x;   //目的地x
	protected int destination_y, departure_y;   //目的地y
	protected int credit;  //信用度
	protected int number;  //编号
	protected int move_time;
	protected long start_time;
	protected TaxiGUI taxi_gui;
	protected guiInfo map_mess;
	
	public Taxi(int num, TaxiGUI taxi_gui, guiInfo map_in) {
		//Requires: num, taxi_gui, map_in
		//Modifies: location_x, location_y, status, number, taxi_gui, map_mess
		//Effect：构造方法，构造出租车对象的时候直接初始化
		
		this.location_x = (int)(0+Math.random()*80);
		this.location_y = (int)(0+Math.random()*80);
		this.status = 2;  //初始化出租车
		this.number = num;
		this.taxi_gui = taxi_gui;
		this.map_mess = map_in;
	}
	
	public void pick_up() {  //出租车去接客
		//Requires: 无
		//Modifies: location_x, location_y, status， start_time, fetch
		//Effect：出租车去接乘客的时候，去乘客的出发点，这一段路
		int location=0, destination=0;
		location = this.location_y*80 + this.location_x;
		destination = this.departure_y*80 + this.departure_x;
		
		if(this.location_x==this.departure_x && this.location_y==this.departure_y) {

			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 1);
			System.out.printf("到达乘客位置的时刻:%d 乘客位置的坐标:(%d,%d)\n", this.start_time,this.location_y,this.location_x);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.start_time += 10;
			this.fetch = 2;   //2表示送客的阶段
			this.move_time = 0;
		}
		else {
			//System.out.println("换位置了");
			//选中下一个点怎么走
			search_fps(destination,location);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.start_time += 5;
			System.out.printf("车辆编号:%d 途经分支点的坐标:(%d,%d) 经过时刻:%d\n", this.number,this.location_y,this.location_x,this.start_time);
			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 1);
		}
	}
	
	public void move() {   //出租车运行，送往目的地
		//Requires: 无
		//Modifies: location_x, location_y, credit, start_time, status, 
		//Effect：出租车送客的时候，将出租车从当前位置送到乘客请求的目的地
		int location=0, destination=0;
		
		location = this.location_y*80 + this.location_x;
		destination = this.destination_y*80 + this.destination_x;
		
		if(this.location_x==this.destination_x && this.location_y==this.destination_y) {
			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 2);
			System.out.printf("到达目的地的时刻:%d 目的地的坐标:(%d,%d)\n", this.start_time,this.location_y,this.location_x);
			this.credit+=3;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println(this.credit);
			this.status = 2;
			this.start_time += 10;
			this.move_time = 0;
		}
		else {
			//选则下一个点怎么走
			search_fps(destination,location);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.start_time += 5;
			System.out.printf("车辆编号:%d 途经分支点的坐标:(%d,%d) 经过时刻:%d\n", this.number,this.location_y,this.location_x,this.start_time);
			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 1);
		}
	}
	
	public synchronized void wander() {    //出租车处于游荡状态，四处游荡
		//Requires: 无
		//Modifies: location_x, location_y, move_time, status
		//Effect：出租车的等待服务的状态，表示出租车这时正在四处游荡，所以是wander
		int direction = 0;
		int wan_flag=0;
		int location=0, location_1=0, location_2=0, location_3=0, location_4=0;
		int old_x=0, old_y=0;
		int [] tra_wan = new int[5];
		location = this.location_y*80 + this.location_x;
		location_1 = (this.location_x==79)? location:this.location_y*80 + this.location_x+1;   //向东
		location_2 = (this.location_x==0)? location:this.location_y*80 + this.location_x-1;    //向西
		location_3 = (this.location_y==0)? location:(this.location_y-1)*80 + this.location_x;   //向北
		location_4 = (this.location_y==79)? location:(this.location_y+1)*80 + this.location_x;   //向南
		tra_wan[0] = 10;
		tra_wan[1] = (this.location_x==79)? 10:guigv.GetFlow(location_y, location_x, location_y, location_x+1);
		tra_wan[2] = (this.location_x==0)? 10:guigv.GetFlow(location_y, location_x, location_y, location_x-1);
		tra_wan[3] = (this.location_y==0)? 10:guigv.GetFlow(location_y, location_x, location_y-1, location_x);
		tra_wan[4] = (this.location_y==79)? 10:guigv.GetFlow(location_y, location_x, location_y+1, location_x);
		if(this.number==5) System.out.printf("初始位置%d %d\r\n",this.location_y, this.location_x);
		if(this.number==5) System.out.printf("%d %d %d %d\r\n",tra_wan[1], tra_wan[2], tra_wan[3], tra_wan[4]);
		wan_flag = 0;
		int wan_tem_x=0, wan_tem_y=0;
		direction = (int)(1+Math.random()*4);
		if(this.number==5) System.out.println("随机数是" + direction);
		for(int i=1; i<=4; i++) {
			if(direction==1) {
				if(this.number==5) System.out.println("随机数是" + direction);
				if(map_mess.graph[location][location_1]==1 && (tra_wan[1]<tra_wan[wan_flag] || wan_flag==0)) {
					wan_tem_x = this.location_x+1;
					wan_tem_y = this.location_y;
					wan_flag = 1;
					if(this.number==5) System.out.printf("会不会执行这一步 %d %d\r\n",tra_wan[1],wan_flag);
				}
				if(tra_wan[1]!=0)
					//guigv.ClearFlow(wan_tem_y, wan_tem_x, location_y, location_x);
				direction = 2;
				continue;
			}
			else if(direction==2) {
				if(this.number==5) System.out.println("随机数是" + direction);
				if(map_mess.graph[location][location_2]==1 && (tra_wan[2]<tra_wan[wan_flag] || wan_flag==0)) {
					wan_tem_x = this.location_x-1;
					wan_tem_y = this.location_y;
					wan_flag = 2;
					if(this.number==5) System.out.printf("会不会执行这一步 %d %d\r\n",tra_wan[2],wan_flag);
				}
				if(tra_wan[2]!=0)
					//guigv.ClearFlow(wan_tem_y, wan_tem_x, location_y, location_x);
				direction = 3;
				continue;
			}
			else if(direction==3) {
				if(this.number==5) System.out.println("随机数是" + direction);
				if(map_mess.graph[location][location_3]==1 && (tra_wan[3]<tra_wan[wan_flag] || wan_flag==0)) {
					wan_tem_y = this.location_y-1;
					wan_tem_x = this.location_x;
					wan_flag = 3;
					if(this.number==5) System.out.printf("会不会执行这一步 %d %d\r\n",tra_wan[3],wan_flag);
				}
				if(tra_wan[3]!=0)
					//guigv.ClearFlow(wan_tem_y, wan_tem_x, location_y, location_x);
				direction = 4;
				continue;
			}
			else if(direction==4) {
				if(this.number==5) System.out.println("随机数是" + direction);
				if(map_mess.graph[location][location_4]==1 && (tra_wan[4]<tra_wan[wan_flag] || wan_flag==0)) {
					wan_tem_y = this.location_y+1;
					wan_tem_x = this.location_x;
					wan_flag = 4;
					if(this.number==5) System.out.printf("会不会执行这一步 %d %d\r\n",tra_wan[4],wan_flag);
				}
				if(tra_wan[4]!=0)
					//guigv.ClearFlow(wan_tem_y, wan_tem_x, location_y, location_x);
				direction = 1;
				continue;
			}
		}
		//guigv.drawboard.repaint();
		if(this.number==5) System.out.println("随机数最终是" + direction);
		//System.out.printf("真实的流量%d\r\n", guigv.GetFlow(location_y, location_x, wan_tem_y, wan_tem_x));
		old_x = this.location_x;
		old_y = this.location_y;
		this.location_x = wan_tem_x;
		this.location_y = wan_tem_y;
		if(this.number==5) System.out.printf("结束位置%d %d\r\n\r\n",this.location_y, this.location_x);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		guigv.AddFlow(old_y, old_x, this.location_y, this.location_x);
		move_time += 500;
		if(move_time==20000) {
			this.status = 0;
			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 0);
		}
		else {
			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 2);
		}
	}
	
	public void stop_taxi() {    //让小汽车停下来
		//Requires: 无
		//Modifies: status, move_time
		//Effect：出租车随机游荡时间达到两分钟->出租车停止休息
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.status = 2;
		taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 2);
		this.move_time = 0;
	}
	
	public void search_fps(int destination, int location) {
		//Requires: destination, location
		//Modifies: location_x, location_y, flag, location_1, location_2, location_3, location_4,……下面的变量都会变
		//Effect：出租车当前位置是location,需要向destination走，那么下一步该怎么走呢？就是通过该方法判断，找出最适合的路径的
		int location_1=0, location_2=0, location_3=0, location_4=0;
		int tem_x1=0, tem_x2=0, tem_x3=0, tem_x4=0;
		int tem_y1=0, tem_y2=0, tem_y3=0, tem_y4=0;
		int length1=0, length2=0, length3=0, length4=0;
		int [] traffic = new int[5];
		int length=0;
		int flag=0;
		this.map_mess.pointbfs(destination);
		length = map_mess.D[destination][location];
		System.out.println(length);
		//在这里详细解释一下关于选中下一个点怎么走
		/**
		 * 首先是判断这四个点那一个点是最短路劲上的点，这个判断是和上次一样，但是我这里又加入了关于流量的判断
		 * 假设现在在A点，然后B，C，D，E是其周围的四个点，然后先判断A点是不是最短路劲，设T表示下一个点，如果是T就是A
		 * 然后接着判断B点是不是最短路劲，如果T是A，那么就判断流量，否则就直接让T为B，一次类推
		 */
		tem_x1 = this.location_x+1;
		tem_y1 = this.location_y;
		location_1 = tem_y1*80 + tem_x1;
		if(this.location_x<79) {
			length1 = map_mess.D[destination][location_1];
			traffic[1] = map_mess.traffic[destination][location_1] + guigv.GetFlow(location_y, location_x, tem_y1, tem_x1);
		}
		
		tem_x2 = this.location_x-1;
		tem_y2 = this.location_y;
		location_2 = tem_y2*80 + tem_x2;
		if(this.location_x>0) {
			length2 = map_mess.D[destination][location_2];
			traffic[2] = map_mess.traffic[destination][location_2] + guigv.GetFlow(location_y, location_x, tem_y2, tem_x2);
		}
		
		tem_x3 = this.location_x;
		tem_y3 = this.location_y+1;
		location_3 = tem_y3*80 + tem_x3;
		if(this.location_y<79) {
			length3 = map_mess.D[destination][location_3];
			traffic[3] = map_mess.traffic[destination][location_3] + guigv.GetFlow(location_y, location_x, tem_y3, tem_x3);
		}
		
		tem_x4 = this.location_x;
		tem_y4 = this.location_y-1;
		location_4 = tem_y4*80 + tem_x4;
		if(this.location_y>0) {
			length4 = map_mess.D[destination][location_4];
			traffic[4] = map_mess.traffic[destination][location_4] + guigv.GetFlow(location_y, location_x, tem_y4, tem_x4);
		}
		
		
		if(length1==length-1 && tem_x1<80 && map_mess.graph[location_1][location]==1) {
			this.location_x = tem_x1;
			this.location_y = tem_y1;
			//System.out.println(map_mess.traffic[location_1][location]);
			flag = 1;
		}
		else if(length2==length-1 && tem_x2>=0 && map_mess.graph[location_2][location]==1) {
			if(flag==0 || (flag==1 && traffic[2] < traffic[1])) {
				this.location_x = tem_x2;
				this.location_y = tem_y2;
				//System.out.println(map_mess.traffic[location_2][location]);
				flag = 2;
			}
		}
		else if(length3==length-1 && tem_y3<80 && map_mess.graph[location_3][location]==1) {
			if(flag==0 || (traffic[3] < traffic[flag])) {
				this.location_x = tem_x3;
				this.location_y = tem_y3;
				//System.out.println(map_mess.traffic[location_3][location]);
				flag = 3;
			}
			
		}
		else if(length4==length-1 && tem_y4>=0 && map_mess.graph[location_4][location]==1) {
			if(flag==0 || traffic[4] < traffic[flag]) {
				this.location_x = tem_x4;
				this.location_y = tem_y4;
				//System.out.println(map_mess.traffic[location_4][location]);
				flag = 0;
			}
		}
	}
}


