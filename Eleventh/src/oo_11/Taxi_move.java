package oo_11;

class Taxi_move implements Runnable {
	/*overview
	 * 这时出租车运行的线程，出租车一共就只有四种状态，所以用while()循环就可以啦
	 * 用到的对象就是出租车的对象，每个出租车的运行都是一个线程，所以一共开了100个线程
	 */
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
