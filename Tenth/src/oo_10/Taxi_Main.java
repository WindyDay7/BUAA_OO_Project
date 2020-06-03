package oo_10;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
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
		for(int i=1; i<=100; i++) {
			cars[i] = new Taxi(i,map_GUI.gui,guiinfo);  //分别将显示底图与地图的属性以及地图方法传入Taxi类
		}
		//Request request_in = new Request(cars,guiinfo,map_GUI.gui);
		
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
		try {
			System.setOut(new PrintStream(new FileOutputStream("result.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Taxi_Main rn = new Taxi_Main();
		rn.Running();
	}
	
	public boolean repOK() {
		return true;
	}
}
