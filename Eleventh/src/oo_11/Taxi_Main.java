package oo_11;

class Taxi_Main {
	/*overview
	 * 不做过多的介绍了，对象都在方法里面实现啦
	 */
	public void Running() {
		//Requires: 无
		//Modifies: map_GUI, guiinfo, cars, taxi_run, in_in_in
		//Effect：构造对象，以及需要的线程对象，但是还没又开始运行程序
		guigv.Light_time = (int)(500+Math.random()*501);
		//guigv.Light_time = (guigv.Light_time/100) * 100; //表示红绿灯的时间
		Map_GUI map_GUI = new Map_GUI();
		map_GUI.show();
		
		guiInfo guiinfo = new guiInfo();
		guiinfo.map = map_GUI.mi.map;
		guiinfo.initmatrix();
		
		Taxi[] cars = new Taxi[101];
		
		//Request request_in = new Request(cars,guiinfo,map_GUI.gui);
		for(int i=1; i<=100; i++) {
			if(i<=30) {
				cars[i] = new Super_Taxi(i,map_GUI.gui,guiinfo);
				map_GUI.gui.SetTaxiType(i, 1);
			}
			else {
				cars[i] = new Taxi(i,map_GUI.gui,guiinfo); 
				map_GUI.gui.SetTaxiType(i, 0);
			}
		}

		Taxi_move[] taxi_run = new Taxi_move[101];
		for(int i=1; i<=100; i++) {
			taxi_run[i] = new Taxi_move(cars[i]);
			new Thread(taxi_run[i]).start();
		}
		In_put in_in_in = new In_put(cars, guiinfo, map_GUI.gui);
		new Thread(in_in_in).start();
		Light_Red Red_light = new Light_Red();
		new Thread(Red_light).start();
		//request_in.input();
	}
	
	public static void main(String[] args) {
		//Requires: 无
		//Modifies: rn
		//Effect：启动程序
		
		Taxi_Main rn = new Taxi_Main();
		rn.Running();
	}
	
	public boolean repOK() {
		return true;
	}
}
