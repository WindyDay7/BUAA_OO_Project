package oo_9;

class Taxi_move implements Runnable {
	private Taxi a_taxi;
	
	public Taxi_move(Taxi taxis_a) {
		//Requires: taxis_a
		//Modifies: a_taxi
		//Effect：构造方法初始化
		this.a_taxi = taxis_a;
	}   //初始化出租车运动线程中的出租车
	
	public void run() {
		//Requires: 无
		//Modifies: 无
		//Effect：出租车run，就是一直在运行，但处于什么状态是判断的
		//THREAD_REQUIRES：无
		//THREAD_EFFECT：出租车运行线程，
		
		while(true) {
			if(this.a_taxi.status==1 && this.a_taxi.fetch==1) {   //表示接客的阶段
				//System.out.println("找到车了");
				this.a_taxi.pick_up();
			}
			else if(this.a_taxi.status==1 && this.a_taxi.fetch==2) {
				this.a_taxi.move();
			}
			else if(this.a_taxi.status==2) {
				this.a_taxi.wander();
			}
			else {
				this.a_taxi.stop_taxi();
			}
		}
	}
}
