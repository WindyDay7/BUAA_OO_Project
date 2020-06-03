package oo_9;

import java.util.Scanner;
class In_put implements Runnable{

	private guiInfo new_map;
	private Taxi[] new_taxi = new Taxi[101];
	protected TaxiGUI new_gui;
	
	protected String str_spe = "Load F:\\est.txt";  //表示新类型的输入
	protected String str_road = "\\([0-7]?[0-9],[0-7]?[0-9]\\),\\([0-7]?[0-9],[0-7]?[0-9]\\),[01]";  //表示道路的联通的两点
	protected String str_test = "100|([1-9]?[0-9])";   //表示出租车的编号，用于查询出租车的状态
	public In_put(Taxi[] new_taxi, guiInfo new_map, TaxiGUI new_gui) {
		this.new_map = new_map;
		this.new_taxi = new_taxi;
		this.new_gui = new_gui;
	}
	
	public synchronized void run() {
		//Requires: 无
		//Modifies: in_input
		//Effect：对输入判断，判断输入是什么类型的
		//THREAD_REQUIRES：无
		//THREAD_EFFECT：输入线程，就是对输入判断的线程
		String in_input=null;
		
		Scanner scanner = new Scanner(System.in);
		Request request_in = new Request(new_taxi, new_map, new_gui);  //对于请求类，就是输入是请求而不是其他
		Big_change big_change = new Big_change();  //对于大规模的改变创建对象
		Test test = new Test();    //对于
		Change_road road_ch = new Change_road();
		
		in_input = scanner.nextLine();
		//in_input = in_input.replaceAll(" ","");
		while(true) {
			if(in_input.equals("END")) {
				break;
			}
			else if(in_input.equals("Load F:\\est.txt")) {
				big_change.change(new_taxi, new_map, new_gui, in_input);
			}
			else if(in_input.matches(str_road)) {
				road_ch.road_change(new_map, new_gui, in_input);
			}
			else if(in_input.matches(str_test)) {
				test.test_out(new_taxi, in_input);
			}
			else
			{
				request_in.input(in_input);
			}
			in_input = scanner.nextLine();
			//in_input = in_input.replaceAll(" ","");
		}
		scanner.close();
		return ;
	}
	
	
}
