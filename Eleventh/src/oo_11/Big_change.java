package oo_11;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Big_change {
	/*overview
	 * 表示有重大改变的输入啦，也就是Load FileName啦，但是文件必须是F盘啦，下面的ext.txt文件
	 * 然后就是每一次输入这个家伙就会更新一次地图，所以会用新的GUI窗口
	 * 还有就是红绿灯啊，红绿灯不合法的红绿灯就不会读入啦，
	 * 有很多文件需要保证输入的正确性哦，thanks，
	 */
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
		input_2 = scan_2.nextLine(); //先把第二行给读了，第二行是空白行
		input_2 = scan_2.nextLine();  //再把第三行给读了
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
		new_gui.LoadMap(new_map.map, 80);   //更新地图视图
		air = 0;
		input_2 = scan_2.nextLine();  //读空白行
		input_2 = scan_2.nextLine();  //读#light一行
		input_2 = scan_2.nextLine();  
		
		for(int i=0; i<80; i++) {
			String[] Str_light = null;
			try{
				input_2 = scan_2.nextLine();
				if(input_2.matches("#end_light")) {
					air = 1;
					break;
				}
				input_2 = input_2.replaceAll(" ","");
				Str_light = input_2.split("");
			}catch(Exception e){
				System.out.println("红绿灯文件信息有误，程序退出");
				System.exit(1);
			}
			int temp_light=0;
			for(int j=0;j<80;j++){
				try{
					temp_light=Integer.parseInt(Str_light[j]);
					if(temp_light==0)
					{
						guigv.lightmap[i][j]=temp_light;
						continue;
					}
					else {
						temp_light = (int)(1+Math.random()*2);
						int line_tem=0;
						line_tem = Light_ok(new_map,i,j);
						if(line_tem>=3)
							new_gui.SetLightStatus(new Point (i,j), temp_light);
						//guigv.lightmap[i][j]=temp_light;   //设置红绿灯，没有使用调用void SetLightStatus(Point p, int Status)，原理一样
						else {
							System.out.println("此红绿灯的位置错啦，所以我们不把他当成红绿灯");
						}
					}
				}catch(Exception e){
					System.out.println("红绿灯文件信息有误，程序退出");
					System.exit(1);
				}
			}
		}
		if(air==0) {
			new_map.initmatrix();
			input_2 = scan_2.nextLine();   //读取#end_map这一行
			if(!input_2.matches("#end_light")) {
				System.out.println("地图红绿灯文件信息有误，程序退出");
				System.exit(1);
			}
		}
		
		input_2 = scan_2.nextLine();  //读空白行
		input_2 = scan_2.nextLine();  //读flow行
		input_2 = scan_2.nextLine();
		while(!input_2.matches("#end_flow")) {  //地图中的流量的改变
			new_traffic(new_map,input_2);
			input_2 = scan_2.nextLine();
		}
		
		input_2 = scan_2.nextLine();  //读空白行
		input_2 = scan_2.nextLine();   //读#taxi一行
		input_2 = scan_2.nextLine();   //只将提到的出租车状态改变，为说明的状态不变
		while(!input_2.matches("#end_taxi")) {
			new_taxi(new_map, input_2, new_taxi, new_gui);
			input_2 = scan_2.nextLine();
		}
		input_2 = scan_2.nextLine();  //读空白行
		input_2 = scan_2.nextLine();  //#request一行
		input_2 = scan_2.nextLine();
		while(!input_2.matches("#end_request")) {
			
			re_request.input(input_2);
			input_2 = scan_2.nextLine();
		}
		
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
					System.out.println("道路信息有误，程序退出");
					System.exit(1);
				}
			}
		}
		if(worth[0]>=80 || worth[1]>=80 ||worth[2]>=80 || worth[3]>=80) {
			System.out.println("道路信息有误，程序退出");
			System.exit(1);
		}
		location_1 = 80*worth[0] + worth[1];
		location_2 = 80*worth[2] + worth[3];
		if(tra_map.graph[location_1][location_2]==1) {   //两点之间有路才可以直接设置流量，否则报错
			for(int i=0; i<worth[4]; i++) {
				//guigv.AddFlow(worth[0], worth[1], worth[2], worth[3]);
				tra_map.Flow[location_1][location_2] += 1;
				tra_map.Flow[location_2][location_1] += 1;
			}
		}
		else {
			System.out.println("文件道路信息有误，程序退出");
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
					System.out.println("文件出租车信息有误，程序退出");
					System.exit(1);
				}
			}
		}
		if(new_worth[3]>=80 || new_worth[4]>=80 || new_worth[3]<0 || new_worth[4]<0) {
			System.out.println("文件出租车信息有误，程序退出");
			System.exit(1);
		}
		re_taxi[new_worth[0]].credit = new_worth[2];
		re_taxi[new_worth[0]].location_y = new_worth[3];
		re_taxi[new_worth[0]].location_x = new_worth[4];
		
		if(new_worth[1]==3) {
			re_taxi[new_worth[0]].status = 0;    //停滞状态
		}
		else if(new_worth[1]==2) {
			re_taxi[new_worth[0]].status = 2;    //游荡状态
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
			re_taxi[new_worth[0]].destination_y = (int)(0+Math.random()*80);  //出租车要去送客了，
		}
		else {
			System.out.println("文件出租车信息有误，程序退出");
			System.exit(1);
		}
		new_car_gui.SetTaxiStatus(new_worth[0], new Point (re_taxi[new_worth[0]].location_y,re_taxi[new_worth[0]].location_x), re_taxi[new_worth[0]].status);
	}
	
	public int Light_ok(guiInfo light_map, int a, int b) {
		int line = 0;
		int location=0;
		int [] light_loca = new int [5];
		location = a*80 + b;
		light_loca[1] = (a==79)? location:(a+1)*80 + b;  //表示方向向东
		light_loca[2] = (a==0)? location:(a-1)*80 + b;  //表示方向向西
		light_loca[3] = (b==0)? location:(a*80 + b-1);  //表示方向向北
		light_loca[4] = (b==79)? location:(a*80 + b+1);  //表示方向向南
		if(a<79 && light_map.graph[light_loca[1]][location]==1)
			line+=1;
		if(a>0 && light_map.graph[light_loca[2]][location]==1)
			line+=1;
		if(b>0 && light_map.graph[light_loca[3]][location]==1)
			line+=1;
		if(b<79 && light_map.graph[light_loca[4]][location]==1)
			line+=1;
		return line;
	}
	
	public boolean repOK() {
		return true;
	}
}

