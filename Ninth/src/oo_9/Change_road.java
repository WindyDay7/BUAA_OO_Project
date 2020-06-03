package oo_9;

import java.awt.Point;

class Change_road {
	
	public void road_change(guiInfo road_map, TaxiGUI road_gui, String road_way) {
		//Requires: road_map, road_gui, road_way
		//Modifies: road_map.graph, road_gui
		//Effect：表示道路的改变，道路就是两点之间的连线，表示道路开关和闭合
		String[] strs = road_way.split("[\\(\\), ]");
		int i = 0, j =0;;
		int [] worth = new int[7];
		int location_1=0, location_2=0;
		for(i=0; i<strs.length; i++) {
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
		location_1 = 80*worth[0] + worth[1];
		location_2 = 80*worth[2] + worth[3];
		road_map.graph[location_1][location_2] = worth[4];
		road_map.graph[location_2][location_1] = worth[4];
		if(worth[4]==0 || worth[4]==1) {
			road_gui.SetRoadStatus(new Point (worth[0],worth[1]), new Point (worth[2],worth[3]), worth[4]);
			if(worth[4]==0) {
				road_map.traffic[location_1][location_2] = 0;
				road_map.traffic[location_2][location_1] = 0;
			}
		}
		else {
			System.out.println("Somethin wrong in input");
		}
	}
}
