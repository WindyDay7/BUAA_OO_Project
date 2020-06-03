package oo_11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


class Test {
	/*overview
	 * 这时测试线程，和上次一样，只有一个方法，实例化用来测试出租车的状态
	 */
	public synchronized void test_out(Taxi[] test_taxi, String test_in) {
		//Requires: test_taxi, test_in, 
		//Modifies: car_num
		//Effect：用于测试程序，输出测试的汽车的状态
		int car_num = 0;
		String path=null, write_path;
		String input_2 = null;
		BufferedReader reader=null;  
		car_num = Integer.parseInt(test_in);
		ArrayList<String> list = new ArrayList<String>();
		if(car_num>=30) {
			System.out.printf("车辆编号:%d 途经分支点的坐标:(%d,%d) 经过时刻:%d出租车的信用度%d\r\n", test_taxi[car_num].number,test_taxi[car_num].location_y,test_taxi[car_num].location_x,(System.currentTimeMillis()/100),test_taxi[car_num].credit);
		}
		else {
			path = String.valueOf(car_num) + ".txt";
			write_path = "D:\\测试出租车" + String.valueOf(car_num) + ".txt";
			File file=new File(path);
			//System.out.println(path);
			if(file.exists()==false){
				System.out.println("地图文件不存在，程序退出");
				System.exit(1);
				return;
			}
			try {
				reader = new BufferedReader(new FileReader(path)); 
			} catch (FileNotFoundException e) {
				System.out.println("异常错误");
			}
			try {
				//while()
				while((input_2=reader.readLine())!=null) {
					list.add(input_2);
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			Iterator<String> listIt = list.iterator();
		    while(listIt.hasNext()){
		    	Write_r(listIt.next()+"\r\n",write_path);
		    }
		}
		return ;
	}
	
	public synchronized void Write_r(String write_str, String Path_file) {
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
	
}
