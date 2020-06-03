package oo_9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class mapInfo{
	int[][] map=new int[80][80];
	public void readmap(String path){ //读地图
		//Requires: path
		//Modifies: map
		//Effect: 从文件中读入map，然后改变map
		
		Scanner scan=null;
		File file=new File(path);
		if(file.exists()==false){
			System.out.println("地图文件不存在，程序退出");
			System.exit(1);
			return;
		}
		try {
			scan = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			
		}
		for(int i=0;i<80;i++){
			String[] strArray = null;
			String input_ = null;
			try{
				input_ = scan.nextLine();
				input_ = input_.replaceAll(" ","");
				strArray=input_.split("");
				if(strArray.length > 80) {
					System.out.println("地图文件信息有误，程序退出");
					System.exit(1);
				}
			}catch(Exception e){
				System.out.println("地图文件信息有误，程序退出");
				System.exit(1);
			}
			for(int j=0;j<80;j++){
				try{
					this.map[i][j]=Integer.parseInt(strArray[j]);
					if(this.map[i][j]>3 || this.map[i][j]<0) {
						System.out.println("地图文件信息有误，程序退出");
						System.exit(1);
					}
				}catch(Exception e){
					System.out.println("地图文件信息有误，程序退出");
					System.exit(1);
				}
			}
		}
		try{
			String[] strArray1 = null;
			strArray1 = scan.nextLine().split("");
			if(strArray1.length>0) {
				System.out.println("鍦板浘鏂囦欢淇℃伅鏈夎锛岀▼搴忛��鍑�");
				System.exit(1);
			}
			
		}catch(Exception e){
			;
		}
		scan.close();
	}
}

class Map_GUI {
	TaxiGUI gui=new TaxiGUI();
	mapInfo mi=new mapInfo();
	public void show() {
		//Requires: 无
		//Modifies: gui
		//Effect：从map.txt中读入文件，然后将这个地图显示在GUI上
		mi.readmap("map.txt");
		gui.LoadMap(mi.map, 80);
	}
}
