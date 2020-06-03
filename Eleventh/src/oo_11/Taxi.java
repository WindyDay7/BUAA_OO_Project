package oo_11;

import java.awt.Point;
import java.io.FileWriter;
import java.io.IOException;

class Taxi {
	/*不得不说这个类还是很复杂的，第一次作业还好，越到后面的的作业越复杂，因为要加很多的很奇怪的东西
	 * 首先实例化到的对象可以说是用在了各个地方，毕竟是核心
	 * 然后主要的方法就是，出租车运动的几个方法啊，wander()表示游荡，move()表示运行。stop自行脑补。
	 * 越来越复杂的原因是由于每走一步需要判断更多的条件，
	*
	*/
	protected int status, fetch;   //当前状态
	protected int location_x;    //当前位置
	protected int location_y;    //
	//protected int a_head;   //时候已经抢单
	protected int acceptance;   //是否已经接单
	protected int destination_x, departure_x;   //目的地x
	protected int destination_y, departure_y;   //目的地y
	protected int credit;  //信用度
	protected int number;  //编号
	protected int move_time;
	protected int Taxi_Direct, Old_Direct;  //表示行驶的方向，方向是  东，南，西，北分别为1，2，3，4
	protected long start_time;
	protected TaxiGUI taxi_gui;
	protected guiInfo map_mess;
	protected int Num_task;
	
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

			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 0);
			String str1 = "到达乘客位置的时刻: " + this.start_time + "乘客位置的坐标: (" + this.location_y + "," + this.location_x + ")\r\n";
			Write(str1, "result.txt");
			Write(str1, Save_path());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.start_time += 1000;
			this.fetch = 2;   //2表示送客的阶段
			this.move_time = 0;
		}
		else {
			//System.out.println("换位置了");
			//选中下一个点怎么走
			search_fps(destination,location);
			this.start_time += 500;
			String str2 = "车辆编号: " + this.number + "途经分支点的坐标: (" + this.location_x + "," + this.location_y + ") 经过时刻" + this.start_time + "\r\n";				
			Write(str2, "result.txt");
			Write(str2, Save_path());
			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 3);
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
			taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 0);
			String str3 = "到达乘客目的地的时刻: " + this.start_time + "目的地位置的坐标: (" + String.valueOf(this.location_y) + "," + String.valueOf(this.location_x) + ")\r\n";
			Write(str3, "result.txt");
			Write(str3, Save_path());
			this.credit+=3;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println(this.credit);
			this.status = 2;
			this.start_time += 1000;
			this.move_time = 0;
		}
		else {
			//选则下一个点怎么走
			search_fps(destination,location);
			this.start_time += 500;
			String str4 = "车辆编号: " + String.valueOf(this.number) + "途经分支点的坐标: (" + String.valueOf(this.location_x) + "," + String.valueOf(this.location_y) + ") 经过时刻:" + String.valueOf(this.start_time) + "\r\n";				
			Write(str4, "result.txt");
			Write(str4, Save_path());
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
		int old_lo_x=0, old_lo_y=0;
		int [] tra_wan = new int[5];
		location = this.location_y*80 + this.location_x;
		location_1 = (this.location_x==79)? location:this.location_y*80 + this.location_x+1;  //表示方向向东
		location_2 = (this.location_x==0)? location:this.location_y*80 + this.location_x-1;  //表示方向向西
		location_3 = (this.location_y==0)? location:(this.location_y-1)*80 + this.location_x;  //表示方向向北
		location_4 = (this.location_y==79)? location:(this.location_y+1)*80 + this.location_x;  //表示方向向南
		tra_wan[0] = 10;
		tra_wan[1] = (this.location_x==79)? 10:this.map_mess.Flow[location_1][location];
		tra_wan[2] = (this.location_x==0)? 10:this.map_mess.Flow[location_2][location];
		tra_wan[3] = (this.location_y==0)? 10:this.map_mess.Flow[location_3][location];
		tra_wan[4] = (this.location_y==79)? 10:this.map_mess.Flow[location_4][location];
		
		wan_flag = 0;
		int wan_tem_x=0, wan_tem_y=0;
		direction = (int)(1+Math.random()*4);
		for(int i=1; i<=4; i++) {
			if(direction==1) {
				if(this.location_x<79 && map_mess.graph[location][location_1]==1 && (tra_wan[1]<=tra_wan[wan_flag] || wan_flag==0)) {
					wan_tem_x = this.location_x+1;
					wan_tem_y = this.location_y;
					wan_flag = 1;
				}
				direction = 2;
				
				this.Taxi_Direct = 1;  //表示如果向东行驶
				continue;
			}
			else if(direction==2) {
				if(this.location_x>0 && map_mess.graph[location][location_2]==1 && (tra_wan[2]<=tra_wan[wan_flag] || wan_flag==0)) {
					wan_tem_x = this.location_x-1;
					wan_tem_y = this.location_y;
					wan_flag = 2;
				}
				direction = 3;
				
				this.Taxi_Direct = 3;  //表示如果向西行驶
				continue;
			}
			else if(direction==3) {
				if(this.location_y>0 && map_mess.graph[location][location_3]==1 && (tra_wan[3]<=tra_wan[wan_flag] || wan_flag==0)) {
					wan_tem_y = this.location_y-1;
					wan_tem_x = this.location_x;
					wan_flag = 3;
				}
				direction = 4;
				
				this.Taxi_Direct = 4;  //表示如果向北行驶
				continue;
			}
			else if(direction==4) {
				if(this.location_y<79 && map_mess.graph[location][location_4]==1 && (tra_wan[4]<=tra_wan[wan_flag] || wan_flag==0)) {
					wan_tem_y = this.location_y+1;
					wan_tem_x = this.location_x;
					wan_flag = 4;
				}
				
				direction = 1;
				this.Taxi_Direct = 2;  //表示如果向南行驶
				continue;
			}
			
		}
		if(guigv.lightmap[this.location_y][this.location_x]==2) {  //东西方向为红灯，南北方向为绿灯，
			if((this.Old_Direct==1 && this.Taxi_Direct==1) || (this.Old_Direct==3 && this.Taxi_Direct==3) || (this.Old_Direct==2 && this.Taxi_Direct==3) || (this.Old_Direct==4 && this.Taxi_Direct==1)) {
				while(guigv.lightmap[this.location_y][this.location_x]==2) {			//这时需要等红灯
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else if(guigv.lightmap[this.location_y][this.location_x]==1) {  //东西方向为绿灯，南北方向为红灯。
			if((this.Old_Direct==2 && this.Taxi_Direct==2) || (this.Old_Direct==4 && this.Taxi_Direct==4) || (this.Old_Direct==1 && this.Taxi_Direct==2) || (this.Old_Direct==3 && this.Taxi_Direct==4)) {
				while(guigv.lightmap[this.location_y][this.location_x]==1) {			//这时需要等红灯
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		this.Old_Direct = this.Taxi_Direct;
		//System.out.printf("原来方向%d 现在方向%d\n", this.Old_Direct, this.Taxi_Direct);
		old_lo_y = this.location_y;
		old_lo_x = this.location_x;
		this.location_x = wan_tem_x;
		this.location_y = wan_tem_y;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.map_mess.Flow[location][location_1]=0;
		this.map_mess.Flow[location_1][location]=0;
		this.map_mess.Flow[location][location_2]=0;
		this.map_mess.Flow[location_2][location]=0;
		this.map_mess.Flow[location][location_3]=0;
		this.map_mess.Flow[location_3][location]=0;
		this.map_mess.Flow[location][location_4]=0;
		this.map_mess.Flow[location_4][location]=0;
		this.map_mess.Flow[this.location_y*80 + this.location_x][old_lo_y*80 + old_lo_x] += 1;
		this.map_mess.Flow[old_lo_y*80 + old_lo_x][this.location_y*80 + this.location_x] += 1;
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
		//Effect：出租车司机老了，变成老司机了，开车久了，需要休息
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.status = 2;
		taxi_gui.SetTaxiStatus(this.number, new Point (this.location_y,this.location_x), 2);
		this.move_time = 0;
	}
	
	public synchronized void search_fps(int destination, int location) {
		//Requires: destination, location
		//Modifies: location_x, location_y, flag, location_1, location_2, location_3, location_4,……下面的变量都会变
		//Effect：出租车当前位置是location,需要向destination走，那么下一步该怎么走呢？就是通过该方法判断，找出最适合的路径的
		int location_1=0, location_2=0, location_3=0, location_4=0;
		int old_x_lo=0, old_y_lo=0;
		int tem_x1=0, tem_x2=0, tem_x3=0, tem_x4=0;
		int tem_y1=0, tem_y2=0, tem_y3=0, tem_y4=0;
		int length1=0, length2=0, length3=0, length4=0;
		int [] traffic = new int[5];
		int length=0;
		int flag=0;
		int Old_x=0, Old_y=0;
		this.map_mess.pointbfs(destination);
		length = map_mess.D[destination][location];
		//System.out.println(length);
		//在这里详细解释一下关于选中下一个点怎么走
		/**
		 * 首先是判断这四个点那一个点是最短路劲上的点，这个判断是和上次一样，但是我这里又加入了关于流量的判断
		 * 假设现在在A点，然后B，C，D，E是其周围的四个点，然后先判断A点是不是最短路劲，设T表示下一个点，如果是T就是A
		 * 然后接着判断B点是不是最短路劲，如果T是A，那么就判断流量，否则就直接让T为B，一次类推
		 */
		tem_x1 = this.location_x+1;
		tem_y1 = this.location_y;
		location_1 = tem_y1*80 + tem_x1; //表示方向向东
		if(this.location_x<79) {
			length1 = map_mess.D[destination][location_1];
			traffic[1] = map_mess.Traffic[destination][location_1] + this.map_mess.Flow[location_1][location];
		}
		
		tem_x2 = this.location_x-1;
		tem_y2 = this.location_y;
		location_2 = tem_y2*80 + tem_x2;  //表示方向向西
		if(this.location_x>0) {
			length2 = map_mess.D[destination][location_2];
			traffic[2] = map_mess.Traffic[destination][location_2] + this.map_mess.Flow[location_2][location];
		}
		
		tem_x3 = this.location_x;
		tem_y3 = this.location_y+1;    //表示方向向南
		location_3 = tem_y3*80 + tem_x3;
		if(this.location_y<79) {
			length3 = map_mess.D[destination][location_3];
			traffic[3] = map_mess.Traffic[destination][location_3] + this.map_mess.Flow[location_3][location];
		}
		
		tem_x4 = this.location_x;
		tem_y4 = this.location_y-1;    //表示方向向北行驶
		location_4 = tem_y4*80 + tem_x4;
		if(this.location_y>0) {
			length4 = map_mess.D[destination][location_4];
			traffic[4] = map_mess.Traffic[destination][location_4] + this.map_mess.Flow[location_4][location];
		}
		
		
		if(length1==length-1 && tem_x1<80 && map_mess.graph[location_1][location]==1) {
			Old_x = tem_x1;
			Old_y = tem_y1;
			//System.out.println(map_mess.traffic[location_1][location]);
			this.Taxi_Direct = 1;
			flag = 1;
		}
		else if(length2==length-1 && tem_x2>=0 && map_mess.graph[location_2][location]==1) {
			if(flag==0 || (flag==1 && traffic[2] < traffic[1])) {
				Old_x = tem_x2;
				Old_y = tem_y2;
				//System.out.println(map_mess.traffic[location_2][location]);
				flag = 2;
				this.Taxi_Direct = 3;
			}
			
		}
		else if(length3==length-1 && tem_y3<80 && map_mess.graph[location_3][location]==1) {
			if(flag==0 || (traffic[3] < traffic[flag])) {
				Old_x = tem_x3;
				Old_y = tem_y3;
				//System.out.println(map_mess.traffic[location_3][location]);
				flag = 3;
				this.Taxi_Direct = 2;
			}
		}
		else if(length4==length-1 && tem_y4>=0 && map_mess.graph[location_4][location]==1) {
			if(flag==0 || traffic[4] < traffic[flag]) {
				Old_x = tem_x4;
				Old_y = tem_y4;
				//System.out.println(map_mess.traffic[location_4][location]);
				flag = 0;
				this.Taxi_Direct = 4;
			}
		}
		long start_wait = 0, end_wait = 0;
		start_wait = System.currentTimeMillis();
		//System.out.printf("新的坐标地点是%d %d",Old_y, Old_x);
		if(guigv.lightmap[this.location_y][this.location_x]==2) {  //东西方向为红灯，南北方向为绿灯，
			//System.out.println("遇到红绿灯了");
			if((this.Old_Direct==1 && this.Taxi_Direct==1) || (this.Old_Direct==3 && this.Taxi_Direct==3) || (this.Old_Direct==2 && this.Taxi_Direct==3) || (this.Old_Direct==4 && this.Taxi_Direct==1)) {
				try {
					Thread.sleep(guigv.Light_Still-start_wait);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else if(guigv.lightmap[this.location_y][this.location_x]==1) {  //东西方向为绿灯，南北方向为红灯。
			//System.out.println("遇到红绿灯了");
			if((this.Old_Direct==2 && this.Taxi_Direct==2) || (this.Old_Direct==4 && this.Taxi_Direct==4) || (this.Old_Direct==1 && this.Taxi_Direct==2) || (this.Old_Direct==3 && this.Taxi_Direct==4)) {
				try {
					Thread.sleep(guigv.Light_Still-start_wait);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//System.out.println("死循环了");
		this.Old_Direct = this.Taxi_Direct;
		end_wait = System.currentTimeMillis();
		this.start_time = this.start_time + end_wait - start_wait;
		old_x_lo = this.location_x;
		old_y_lo = this.location_y;
		this.location_x = Old_x;
		this.location_y = Old_y;
		try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
		}
		this.map_mess.Flow[location][location_1]=0;
		this.map_mess.Flow[location_1][location]=0;
		this.map_mess.Flow[location][location_2]=0;
		this.map_mess.Flow[location_2][location]=0;
		this.map_mess.Flow[location][location_3]=0;
		this.map_mess.Flow[location_3][location]=0;
		this.map_mess.Flow[location][location_4]=0;
		this.map_mess.Flow[location_4][location]=0;
		this.map_mess.Flow[this.location_y*80 + this.location_x][old_y_lo*80 + old_x_lo] += 1;
		this.map_mess.Flow[old_y_lo*80 + old_x_lo][this.location_y*80 + this.location_x] += 1;
	}

	public boolean repOK() {
		if(status<0 || status>2 || fetch<0 || fetch>1)
			return false;
		if(location_x<0 || location_x>=80 || location_y<0 || location_y>=80 || destination_x<0 ||destination_x>=80 || destination_y<0 || destination_y>=80 || departure_x<0 || departure_x>=80 || departure_y<0 || departure_y>=80)
			return false;
		if(credit<0 || number<1 || number>100)
			return false;
		if(move_time<0 || Taxi_Direct<0 || Taxi_Direct>4 || start_time<0)
			return false;
		if(taxi_gui==null || map_mess==null)
			return false;
		return true;
	}
	
	public synchronized void Write(String write_str, String Path_file) {
		//Requires: write_str, Path_file
		//Modifies: 输出
		//Effect：将String类型的write_str输出到路劲（相对或者绝对）Path_file的文件中
		 try {
	            FileWriter fileWriter = new FileWriter(Path_file,true);
	            fileWriter.write(write_str);
	            fileWriter.close(); // 关闭数据流  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	}

	public String Save_path() {
		//Requires: None
		//Modifies: str_save
		//Effect：出租车的编号->存储文件的路劲
		String str_save = String.valueOf(this.number);
		str_save = str_save + ".txt";
		return str_save;
	}
	
}


