package oo_9;

import java.awt.Point;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Request {
	protected String str1="\\[CR,\\([0-7]?[0-9],[0-7]?[0-9]\\),\\([0-7]?[0-9],[0-7]?[0-9]\\)\\]";
	protected String str2 = "[0-7]?[0-9]";
	protected int [] find_num = new int[4];  //坐标的位置，起始坐标和终点坐标
	protected long in_time;   //输入的时间
	protected int sign=0, end=0;  
	private Taxi[] taxi_l = new Taxi[100];
	private guiInfo request_map;
	private TaxiGUI request_gui;
	
	public Request(Taxi[] taxi_l, guiInfo map_request, TaxiGUI gui_request) {
		//Requires: taxis_l, map_request, gui_request
		//Modifies: taxi_l, request_map, request_gui
		//Effect：构造方法初始化
		this.taxi_l = taxi_l;
		this.request_map = map_request;
		this.request_gui = gui_request;
	}
	
	public void input(String str_in) {
		//Requires: str_in
		//Modifies: find_num
		//Effect：对输入的请求进行判断是否合法，以及获取到输入的数据
	//System.out.println("调用输入");
		if(str_in.equals("END")) {
			return ;
		}
		else {
			str_in = str_in.replaceAll(" ","");
			if(str_in.matches(str1)) {   //表示匹配成功
				search(find_num,str_in);
				in_time = System.currentTimeMillis()/100;
				//System.out.println("输入有效");
				if(find_num[0]==find_num[2] && find_num[1]==find_num[3]) {
					System.out.println("出发点与目的地相同，不执行");
				}
				else {
					System.out.printf("发出时刻%d, 请求坐标(%d,%d), 目的地坐标(%d,%d)\n",(System.currentTimeMillis()/100),find_num[0],find_num[1],find_num[2],find_num[3]);
					request_gui.RequestTaxi(new Point(find_num[0],find_num[1]), new Point(find_num[2],find_num[3]));
					Scheduler sche = new Scheduler(this,taxi_l,request_map);    //输入一个构建一个线程，用来表示一个调度
					new Thread(sche).start();
				}
			}
			else {    //表示匹配不成功
					System.out.println("Wrong demand");
			}
		}
		return ;
	}
	
	public void search(int num[], String big) {
		//Requires: num, big
		//Modifies: num
		//Effect：提取出读到的数据
		Pattern p_1 = Pattern.compile(str2);
		Matcher m_1 = p_1.matcher(big);
		
		for(int i=0; i<4; i++) {
			if(m_1.find()) {
				num[i] = Integer.parseInt(m_1.group());
			}
		}
	}
}
