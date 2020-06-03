package oo_5;

class Super_ride extends Scheduler implements Runnable{
	private Elevator[] elev_line = new Elevator[4]; //电梯数组
	private W_legal control_line;   //临时队列
	private Three_elev elev_all = new Three_elev();    //用来处理输入与电梯而产生的对象
	
	public Super_ride(W_legal line_push, Elevator[] elev_th) {
		this.control_line = line_push;
		this.elev_line = elev_th;
	}
	public void run() {
		
		synchronized (elev_line) {
			while(true) {
				control_line.release();
				
				//System.out.println("调度执行了");
				//System.out.println(control_line.input_time);
				//System.out.println(control_line.tem[1]);
				elev_all.control(control_line.input_time, control_line.tem_num, elev_line);
				elev_line[1].first = control_line.first_time;
				elev_line[2].first = control_line.first_time;
				elev_line[3].first = control_line.first_time;
				//System.out.println(elev_line[1].first);
				//System.out.println("调度执行了2");
				//System.out.println("调度执行了3");
				//System.out.printf("调度阶段的END %d\n",control_line.end);
				//System.out.println("调度阶段的电梯1的count" + elev_line[1].count);
				if(control_line.end==1) {
					elev_line[1].last = 1;
					elev_line[2].last = 1;
					elev_line[3].last = 1;
					break;
				}
				control_line.clean();
				//System.out.println("调度阶段的电梯1的count" + elev_line[1].count);
			}
		}		
	}
	
	
}
