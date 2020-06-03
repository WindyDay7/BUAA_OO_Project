package oo_7;

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
	protected int caulate_1, caulate_2;
	protected TaxiGUI taxi_gui;
	protected guiInfo map_mess;
	
	public Taxi(int num, TaxiGUI taxi_gui, guiInfo map_in) {
		this.location_x = (int)(0+Math.random()*80);
		this.location_y = (int)(0+Math.random()*80);
		this.status = 2;  //初始化出租车
		this.number = num;
		this.taxi_gui = taxi_gui;
		this.map_mess = map_in;
	}
	
	public void pick_up() {  //出租车去接客
		int location=0, destination=0;
		location = this.location_y*80 + this.location_x;
		destination = this.departure_y*80 + this.departure_x;
		search_fps(destination,location);
		if(this.location_x!=this.departure_x && this.location_y!=this.departure_y) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.start_time += 2;
		}
		System.out.printf("车辆编号:%d 途经分支点的坐标:(%d,%d) 经过时刻:%d\n", this.number,this.location_y,this.location_x,this.start_time);
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
			this.caulate_2 = 0;
			this.move_time = 0;
		}
		else {
			//System.out.println("换位置了");
			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 1);
		}
	}
	
	public void move() {   //出租车运行，送往目的地
		int location=0, destination=0;
		
		location = this.location_y*80 + this.location_x;
		destination = this.destination_y*80 + this.destination_x;
		search_fps(destination,location);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.start_time += 2;
		System.out.printf("车辆编号:%d 途经分支点的坐标:(%d,%d) 经过时刻:%d\n", this.number,this.location_y,this.location_x,this.start_time);
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
			this.caulate_1 = 0;
			this.move_time = 0;
		}
		else {
			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 1);
		}
	}
	
	public void wander() {    //出租车处于游荡状态，四处游荡
		int direction = 0;
		int location=0, location_1=0, location_2=0, location_3=0, location_4=0;
		location = this.location_y*80 + this.location_x;
		location_1 = this.location_y*80 + this.location_x+1;
		location_2 = this.location_y*80 + this.location_x-1;
		location_3 = (this.location_y-1)*80 + this.location_x;
		location_4 = (this.location_y+1)*80 + this.location_x;   //计算出可能的四个位置
		
		while(true) {
			direction = (int)(1+Math.random()*5);   //随机选一个位置
			if(direction==1 && this.location_x<79 && map_mess.graph[location][location_1]==1) {
				this.location_x = this.location_x+1;
				break;
			}
			else if(direction==2 && this.location_x>0 && map_mess.graph[location][location_2]==1) {
				this.location_x = this.location_x-1;
				break;
			}
			else if(direction==3 && this.location_y>0 && map_mess.graph[location][location_3]==1) {
				this.location_y = this.location_y-1;
				break;
			}
			else if(direction==4 && this.location_y<79 && map_mess.graph[location][location_4]==1) {
				this.location_y = this.location_y+1;
				break;
			}
		}
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		move_time += 200;
		if(move_time==20000) {
			this.status = 0;
			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 0);
		}
		else {
			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 2);
		}
	}
	
	public void stop_taxi() {    //让小汽车停下来
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
		int location_1=0, location_2=0, location_3=0, location_4=0;
		int tem_x1=0, tem_x2=0, tem_x3=0, tem_x4=0;
		int tem_y1=0, tem_y2=0, tem_y3=0, tem_y4=0;
		int length1=0, length2=0, length3=0, length4=0;
		int length=0;
		if(this.caulate_1==0 || this.caulate_2==0) {
			this.map_mess.pointbfs(destination);
			this.caulate_1 = 1;
			this.caulate_2 = 1;
		}
		
		length = map_mess.D[destination][location];
		
		
		tem_x1 = this.location_x+1;
		tem_y1 = this.location_y;
		location_1 = tem_y1*80 + tem_x1;
		length1 = map_mess.D[destination][location_1];
		
		tem_x2 = this.location_x-1;
		tem_y2 = this.location_y;
		location_2 = tem_y2*80 + tem_x2;
		length2 = map_mess.D[destination][location_2];
		
		tem_x3 = this.location_x;
		tem_y3 = this.location_y+1;
		location_3 = tem_y3*80 + tem_x3;
		length3 = map_mess.D[destination][location_3];
		
		tem_x4 = this.location_x;
		tem_y4 = this.location_y-1;
		location_4 = tem_y4*80 + tem_x4;
		length4 = map_mess.D[destination][location_4];
		
		if(length1==length-1 && tem_x1<80 && map_mess.graph[location_1][location]==1) {
			this.location_x = tem_x1;
			this.location_y = tem_y1;
		}
		else if(length2<length && tem_x2>=0 && map_mess.graph[location_2][location]==1) {
			this.location_x = tem_x2;
			this.location_y = tem_y2;
		}
		else if(length3<length && tem_y3<80 && map_mess.graph[location_3][location]==1) {
			this.location_x = tem_x3;
			this.location_y = tem_y3;
		}
		else if(length4<length && tem_y4>=0 && map_mess.graph[location_4][location]==1) {
			this.location_x = tem_x4;
			this.location_y = tem_y4;
		}
	}
}


