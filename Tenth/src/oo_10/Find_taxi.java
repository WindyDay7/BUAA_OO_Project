package oo_10;

import java.util.ArrayList;

class Find_taxi {
	/*overview
	 * 这个类的作用简单粗暴的说就是每有一个请求就开始找车，为请求找到最合适的车来响应请求
	 * 对于筛选所有的抢单的车，找出最适合的车
	 */
	protected int order;
	protected int times = 0;
	protected ArrayList<Integer> list = new ArrayList<Integer>();
	public void distribute(Request demand, Taxi[] taxis, guiInfo map_che) {
		//Requires: demand, taxis, map_che
		//Modifies: max_x, max_y, min_x, min_y, taxis, list
		//Effect：在7500ms内找出租车，对出租车进行判断，而且找到出租车后还要进行对出租车状态的改变
		
		int max_x=0, min_x=0, max_y=0, min_y=0, destination=0;
		int car_1, car_2;
		max_y = (demand.find_num[0]+2)>79? 79:(demand.find_num[0]+2);
		max_x = (demand.find_num[1]+2)>79? 79:(demand.find_num[1]+2);
		min_y = (demand.find_num[0]-2)<0? 0:(demand.find_num[0]-2);
		min_x = (demand.find_num[1]-2)<0? 0:(demand.find_num[1]-2);   //判断出出租车所在的区域
		destination = demand.find_num[0]*80 + demand.find_num[1];
		map_che.pointbfs(destination);
		while(true) {   //找出应该去接的那一量出租车
			for(int i=1; i<=100; i++) {
				if(taxis[i].location_x<=max_x && taxis[i].location_y<=max_y && min_x<=taxis[i].location_x && min_y<=taxis[i].location_y && taxis[i].status==2) {
					if(order!=0) {
						if(taxis[i].credit>taxis[order].credit) {  //优先选则100辆车中信用度最高的
							order = i;
							add_i(order);
							//System.out.printf("车辆编号:%d, 车辆位置:(%d,%d), 车辆状态:%d, 车辆信用度:%d\n",i,taxis[i].location_y,taxis[i].location_x,taxis[i].status,taxis[i].credit);
						}
						else if(taxis[i].credit==taxis[order].credit) {  //信用度相等，选其中距离近的
							car_1 = taxis[i].location_y*80 + taxis[i].location_x;
							car_2 = taxis[order].location_y*80 + taxis[order].location_x;
							if(map_che.D[destination][car_1] < map_che.D[destination][car_2]) {
								order = i;
								add_i(i);
								//System.out.printf("车辆编号:%d, 车辆位置:(%d,%d), 车辆状态:%d, 车辆信用度:%d\n",i,taxis[i].location_y,taxis[i].location_x,taxis[i].status,taxis[i].credit);
							}
						}
					}
					else
					{
						order = i;    //先不考虑距离最近的吧
						add_i(i);
						//System.out.printf("车辆编号:%d, 车辆位置:(%d,%d), 车辆状态:%d, 车辆信用度:%d\n",i,taxis[i].location_y,taxis[i].location_x,taxis[i].status,taxis[i].credit);
					}
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			times++;
			if(times==15) {
				break;
			}
		}
		for(int i=0; i<list.size(); i++) {
			taxis[list.get(i)].credit++;
			int j=0;
			j = list.get(i);
			System.out.printf("车辆编号:%d, 车辆位置:(%d,%d), 车辆状态:%d, 车辆信用度:%d\n",j,taxis[j].location_y,taxis[j].location_x,taxis[j].status,taxis[j].credit);
		}
		if(order==0) {
			System.out.println("没有车可以接应请求");
			return ;
		}
		else {
			taxis[order].start_time = demand.in_time+7500;
			taxis[order].destination_x = demand.find_num[3];
			taxis[order].destination_y = demand.find_num[2];   //出租车要去接客了，而不是直接送到目的地
			taxis[order].departure_x = demand.find_num[1];
			taxis[order].departure_y = demand.find_num[0];
			taxis[order].status = 1;  //被选中的车进入服务状态，进入服务状态，所以出租车的接单动作是调度器控制的
			taxis[order].fetch = 1;//表示接客的阶段
			System.out.printf("车辆编号:%d 派单时车辆的位置坐标:(%d,%d) 派单时刻:%d\n",order,taxis[order].location_y,taxis[order].location_x,demand.in_time+7500);
		}
	}
	
	public void add_i(int i_in) {
		//Requires: i_in
		//Modifies: list
		//Effect：将新的的抢单的出租车加入到队列中去
		for(int j=0; j<list.size(); j++) {
			if(list.get(j)==i_in) {
				return ;
			}
		}
		list.add(i_in);
	}

	public boolean repOK(){
		if(this.order<1 || order > 100 || times <0 || times > 15)
			return false;
		for (int i=0; i<this.list.size(); i++) {
			if(this.list.get(i)<0) {
				return false;
			}
		}
		return true;
	}

}