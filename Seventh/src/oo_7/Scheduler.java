package oo_7;

class Scheduler implements Runnable {
	private Request request_line;
	private guiInfo sch_map;
	private Taxi[] taxi_line = new Taxi[101];
	private Find_taxi find_taxi = new Find_taxi();
	
	public Scheduler(Request re_line, Taxi[] taxi_line, guiInfo sch_MAP) {
		this.request_line = re_line;
		this.taxi_line = taxi_line;
		this.sch_map = sch_MAP;
	}
	
	
	public void run() {	
		find_taxi.distribute(request_line, taxi_line, sch_map);
	}
}
