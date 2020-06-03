package oo_9;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Big_change {
	public void change(Taxi[] new_taxi, guiInfo new_map, TaxiGUI new_gui, String new_way) {
		//Requires: new_taxi, new_map, new_gui, new_way
		//Modifies: new_taxi, new_map, new_gui
		//Effect：读入LoadFileName大改文件，将出租车以及地图等对象大改一次
		Scanner scan_2=null;
		String[] str_new = new_way.split(" ");
		File file=new File(str_new[1]);
		Request re_request = new Request(new_taxi, new_map, new_gui);
		String input_2 = null;
		int air = 0;
		
		if(file.exists()==false){
			System.out.println("地图文件不存在，程序退出");
			System.exit(1);
			return;
		}
		try {
			scan_2 = new Scanner(new File(str_new[1]));
		} catch (FileNotFoundException e) {
			System.out.println("地图文件不存在，程序退出");
			System.exit(1);
			return;
		}  //判断文件是否有效
		
		input_2 = scan_2.nextLine(); //先把第一行给读了
		input_2 = scan_2.nextLine();  //再把第二行给读了
		for(int i=0;i<80;i++){
			String[] strArray = null;
			//String input_ = null;
			try{
				input_2 = scan_2.nextLine();
				if(input_2.matches("#end_map")) {
					air = 1;
					break;
				}
				input_2 = input_2.replaceAll(" ","");
				strArray=input_2.split("");
			}catch(Exception e){
				System.out.println("地图文件信息有误，程序退出");
				System.exit(1);
			}
			for(int j=0;j<80;j++){
				try{
					new_map.map[i][j]=Integer.parseInt(strArray[j]);
				}catch(Exception e){
					System.out.println("地图文件信息有误，程序退出");
					System.exit(1);
				}
			}
		}
		if(air==0) {
			new_map.initmatrix();
			input_2 = scan_2.nextLine();   //读取#end_map这一行
			if(!input_2.matches("#end_map")) {
				System.out.println("地图文件信息有误，程序退出");
				System.exit(1);
			}
		}

		air = 0;
		input_2 = scan_2.nextLine();
		input_2 = scan_2.nextLine();
		while(!input_2.matches("#end_flow")) {  //地图中的流量的改变
			new_traffic(new_map,input_2);
			input_2 = scan_2.nextLine();
		}
		
		input_2 = scan_2.nextLine();
		input_2 = scan_2.nextLine();   //只将提到的出租车状态改变，为说明的状态不变
		while(!input_2.matches("#end_taxi")) {
			new_taxi(new_map, input_2, new_taxi, new_gui);
			input_2 = scan_2.nextLine();
		}
		input_2 = scan_2.nextLine();
		input_2 = scan_2.nextLine();
		while(!input_2.matches("#end_request")) {
			
			re_request.input(input_2);
			input_2 = scan_2.nextLine();
		}
		new_gui.LoadMap(new_map.map, 80);   //更新地图视图
		scan_2.close();
	}
	
	public void new_traffic(guiInfo tra_map, String money) {    //道路之间的流量的改变
		//Requires: tra_map, money
		//Modifies: tra_map
		//Effect：道路之间的流量的突然改变，我认为LoadFileName就是对文件的突然改变，所以这里表示对流量的改变
		String[] strs = money.split("[\\(\\), ]");
		int [] worth = new int[7];
		int location_1=0, location_2=0;
		int j =0;
		for(int i=0; i<strs.length; i++) {
			if(strs[i].equals("")) {
				continue;
			}
			else {
				try{
					worth[j++] = Integer.parseInt(strs[i]);
				}catch(Exception e){
					System.out.println("文件信息有误，程序退出");
					System.exit(1);
				}
			}
		}
		if(worth[0]>=80 || worth[1]>=80 ||worth[2]>=80 || worth[3]>=80) {
			System.out.println("文件信息有误，程序退出");
			System.exit(1);
		}
		location_1 = 80*worth[0] + worth[1];
		location_2 = 80*worth[2] + worth[3];
		if(tra_map.graph[location_1][location_2]==1) {   //两点之间有路才可以直接设置流量，否则报错
			for(int i=0; i<worth[4]; i++) {
				guigv.AddFlow(worth[0], worth[1], worth[2], worth[3]);
			}
		}
		else {
			System.out.println("文件信息有误，程序退出");
			System.exit(1);
		}
		return ;
	}
	
	public void new_taxi(guiInfo taxi_map, String taxi_str, Taxi[] re_taxi, TaxiGUI new_car_gui) {
		//Requires: taxis_map, taxi_str, re_taxi, new_car_gui
		//Modifies: re_taxi
		//Effect：出租车状态的改变，由于是很多出租车，所以改变的是出租车对象的数组
		String[] strs = taxi_str.split("[\\(\\), ]");
		int [] new_worth = new int[7];
		int j = 0;
		for(int i=0; i<strs.length; i++) {
			if(strs[i].equals("")) {
				continue;
			}
			else {
				try{
					new_worth[j++] = Integer.parseInt(strs[i]);
				}catch(Exception e){
					System.out.println("文件信息有误，程序退出");
					System.exit(1);
				}
			}
		}
		if(new_worth[3]>=80 || new_worth[4]>=80 || new_worth[3]<0 || new_worth[4]<0) {
			System.out.println("文件信息有误，程序退出");
			System.exit(1);
		}
		re_taxi[new_worth[0]].credit = new_worth[2];
		re_taxi[new_worth[0]].location_y = new_worth[3];
		re_taxi[new_worth[0]].location_x = new_worth[4];
		
		if(new_worth[1]==3) {
			re_taxi[new_worth[0]].status = 0;
		}
		else if(new_worth[1]==2) {
			re_taxi[new_worth[0]].status = 2;
		}
		else if(new_worth[1]==1) {
			re_taxi[new_worth[0]].status = 1;
			re_taxi[new_worth[0]].fetch = 1;
			re_taxi[new_worth[0]].destination_x = (int)(0+Math.random()*80);
			re_taxi[new_worth[0]].destination_y = (int)(0+Math.random()*80);  //出租车要去接客了，而不是直接送到目的地
			re_taxi[new_worth[0]].departure_x = (int)(0+Math.random()*80);
			re_taxi[new_worth[0]].departure_y = (int)(0+Math.random()*80);
		}
		else if(new_worth[1]==0) {
			re_taxi[new_worth[0]].status = 1;
			re_taxi[new_worth[0]].fetch = 2;
			re_taxi[new_worth[0]].destination_x = (int)(0+Math.random()*80);
			re_taxi[new_worth[0]].destination_y = (int)(0+Math.random()*80);  //出租车要去接客了，而不是直接送到目的地
		}
		else {
			System.out.println("文件信息有误，程序退出");
			System.exit(1);
		}
		new_car_gui.SetTaxiStatus(new_worth[0], new Point (re_taxi[new_worth[0]].location_y,re_taxi[new_worth[0]].location_x), re_taxi[new_worth[0]].status);
	}
}

