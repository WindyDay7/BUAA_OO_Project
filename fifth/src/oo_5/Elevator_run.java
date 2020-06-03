package oo_5;

class Elevator_run implements Runnable {
	
	private Elevator elevator;
	private Elevator_move elev_move = new Elevator_move();
	public Elevator_run(Elevator elev) {
		this.elevator = elev;
	}
	
	public void run() {
		synchronized (elevator) {
			while(true) {
				elev_move.change(elevator);
				/*try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(elevator.flag_2==1) {
					System.out.printf("%d  %d\n",elevator.count,elevator.last);
					System.out.println("电梯队列" + elevator.count);
				}*/
				
				if(elevator.count==0 && elevator.last==1) {  //表示调度已完成，并且执行队列为空，那么就结束线程
					break;
				}
			}
		}
	}
}
