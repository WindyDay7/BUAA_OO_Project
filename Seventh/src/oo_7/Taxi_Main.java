package oo_7;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
class Taxi_Main {
	public void Running() {
		Map_GUI map_GUI = new Map_GUI();
		map_GUI.show();
		
		guiInfo guiinfo = new guiInfo();
		guiinfo.map = map_GUI.mi.map;
		guiinfo.initmatrix();
		
		Taxi[] cars = new Taxi[101];
		for(int i=1; i<=100; i++) {
			cars[i] = new Taxi(i,map_GUI.gui,guiinfo);
		}
		Request request_in = new Request(cars,guiinfo,map_GUI.gui);
		
		Taxi_move[] taxi_run = new Taxi_move[101];
		for(int i=1; i<=100; i++) {
			taxi_run[i] = new Taxi_move(cars[i]);
			new Thread(taxi_run[i]).start();
		}
		request_in.input();
	}
	
	public static void main(String[] args) {
		try {
			System.setOut(new PrintStream(new FileOutputStream("result.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Taxi_Main rn = new Taxi_Main();
		rn.Running();
	}
}
