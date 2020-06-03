package oo_9;

class Test {
	public void test_out(Taxi[] test_taxi, String test_in) {
		//Requires: test_taxi, test_in, 
		//Modifies: car_num
		//Effect：用于测试程序，输出测试的汽车的状态
		int car_num = 0;
		car_num = Integer.parseInt(test_in);
		System.out.printf("车辆编号:%d 途经分支点的坐标:(%d,%d) 经过时刻:%d出租车的信用度%d\n", test_taxi[car_num].number,test_taxi[car_num].location_y,test_taxi[car_num].location_x,(System.currentTimeMillis()/100),test_taxi[car_num].credit);
		return ;
	}
}
