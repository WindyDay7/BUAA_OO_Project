package oo_9;

class Scheduler implements Runnable {
	private Request request_line;
	private guiInfo sch_map;
	private Taxi[] taxi_line = new Taxi[101];
	private Find_taxi find_taxi = new Find_taxi();
	
	public Scheduler(Request re_line, Taxi[] taxi_line, guiInfo sch_MAP) {
		//Requires: re_line, taxi_line, sch_MAP
		//Modifies: request_line, taxi_line, sch_map
		//Effect：构造方法，调度器的构造方法，传入需要的对象
		this.request_line = re_line;
		this.taxi_line = taxi_line;
		this.sch_map = sch_MAP;
	}
	
	
	public void run() {	
		//Requires: 无
		//Modifies: 无
		//Effect：启动调度器
		//THREAD_REQUIRES：find_taxi
		//THREAD_EFFECT：调度器线程启动，开始调度
		find_taxi.distribute(request_line, taxi_line, sch_map);
	}
}
