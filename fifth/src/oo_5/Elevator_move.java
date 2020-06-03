package oo_5;

class Elevator_move implements Move_way {
	
	protected double start_time;
	
	public void change(Elevator elevator) {
		//System.out.println("电梯在运行");
		move_run(elevator);
		//System.out.println(elevator.first);
	}
	
	
	public void move_run(Elevator elevator) {
		int i = 0;
		int flag = 0;
		elevator.find();
		this.start_time = elevator.re_queue[0][4];
			//表示电梯是向上运行的
			flag = 0;
			//System.out.println("电梯的flag_2," + elevator.flag_2);
			if(elevator.flag_2==0 && elevator.count!=0) {
				elevator.flag_2 = 1;
			}

			if(elevator.direction==1) {
				elevator.energy++;
				//System.out.println(elevator.energy);
				elevator.floor_num++;   //向上运行先上一楼，然后判断是否有指令执行
				//System.out.println(elevator.floor_num);
				//便利队列，找出该楼层可以运行的指令
				flag = 0;
				if(elevator.flag_2==1) {
					wait_love(flag, elevator);  //等待3s
				}
				
				for(i=0;i<elevator.count;i++) {
					
					//表示楼层相等，并且运行方向也是向上的，或者电梯内的，那么就执行请求
					if(elevator.re_queue[i][1]==elevator.floor_num && (elevator.re_queue[i][3]==1 || elevator.re_queue[i][3]==3)) {
						//如果运行到最高楼层了
						if(elevator.re_queue[i][1]==elevator.max) {
							flag = 1;
							if(elevator.flag_2==1) {
								wait_love(flag, elevator);  //等待3s
							}
							delete(elevator,i);
							i--;
							limit(elevator);
						}
						else {
							flag = 1;
							if(elevator.flag_2==1) {
								wait_love(flag, elevator);  //等待3s
							}
							delete(elevator,i);
							i--;
						}
					}
					//表示向上运行到最顶端,因为最高层向下是应该被执行的
					else if(elevator.re_queue[i][1]==elevator.max && elevator.re_queue[i][3]==2 && elevator.floor_num==elevator.max){
						flag = 1;
						if(elevator.flag_2==1) {
							wait_love(flag, elevator);  //等待3s
						}
						delete(elevator,i);
						i--;
						limit(elevator);
					}
				}
			}
			
			//表示电梯现在向下运行
			else if(elevator.direction==2) {
				elevator.energy++;
				elevator.floor_num--;
				//便利队列，找出该楼层可以运行的指令
				flag = 0;
				if(elevator.flag_2==1) {
					wait_love(flag, elevator);  //等待3s
				}
				
				for(i=0;i<elevator.count;i++) {
					
					//表示楼层相等，并且运行方向也是向下的，或者电梯内的，那么就执行请求
					if(elevator.re_queue[i][1]==elevator.floor_num && (elevator.re_queue[i][3]==2 || elevator.re_queue[i][3]==3)) {
						//如果运行到最底楼层了
						if(elevator.re_queue[i][1]==elevator.min) {
							flag = 1;
							if(elevator.flag_2==1) {
								wait_love(flag, elevator);  //等待3s
							}
							delete(elevator,i);
							i--;
							limit(elevator);
						}
						else {
							flag = 1;
							if(elevator.flag_2==1) {
								wait_love(flag, elevator);  //等待3s
							}
							delete(elevator,i);
							i--;
						}
					}
					//表示向下运行到最底端
					else if(elevator.re_queue[i][1]==elevator.min && elevator.re_queue[i][3]==1 && elevator.floor_num==elevator.min){
						flag = 1;
						if(elevator.flag_2==1) {
							wait_love(flag, elevator);  //等待3s
						}
						delete(elevator,i);
						i--;
						limit(elevator);
					}
				}
			}
			
			//表示电梯是静止的，那么就需要找主请求
			else {
				for(i=0;i<elevator.count;i++) {
					if(elevator.re_queue[i][1]==elevator.floor_num) {		
						flag = 2;
						if(elevator.flag_2==1) {
							wait_love(flag, elevator);  //等待3s
						}
						delete(elevator,i);
						i--;
					}
				}
				limit(elevator);
			}
	}

	public void limit(Elevator elev_li) {
		if(elev_li.re_queue[0][1]==0) {
			elev_li.direction = 0;
		}
		else if(elev_li.re_queue[0][1]<elev_li.floor_num) {
			elev_li.direction = 2;
		}
		else if(elev_li.re_queue[0][1]>elev_li.floor_num) {
			elev_li.direction = 1;
		}
		else {
			elev_li.direction = 0;
		}
	}
	
	public void delete(Elevator elev_de, int de_num) {
		int j=0;
		int floor=0,move=0;
		floor = (int) elev_de.re_queue[de_num][1];
		move = (int) elev_de.re_queue[de_num][3];
		output(elev_de.re_queue[de_num],elev_de);
		
		
		for(j=de_num;j<elev_de.count;j++) {
			elev_de.re_queue[j] = elev_de.re_queue[j+1];
		}
		
		elev_de.button[floor][move] = 0;
		elev_de.count--;
	}
	
	public void wait_love(int flag, Elevator elev_time) {
		//System.out.println(flag);
		if(flag==1) {
			//System.out.println("上一楼");
			
			try {
				Thread.sleep(6000);
				elev_time.run_time += 6.0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println(elev_time);
		}
		else if(flag==2) {
			try {
				Thread.sleep(6000);
				elev_time.run_time += 6.0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				Thread.sleep(3000);
				elev_time.run_time += 3.0;
				elev_time.first = System.currentTimeMillis();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.println(elev_time.run_time);
		}
		
	}
	
	public void output(double[] instr, Elevator elev_out) {
		int ioo=0 ,floor_n=0 ,elev_n=0 ,dir_n=0;
		ioo = (int) instr[0];
		floor_n = (int) instr[1];
		elev_n = (int) instr[2];
		dir_n = (int) instr[3];
		if(ioo==1) {
			if(elev_out.direction==1) {
				System.out.printf("%d:[ER,#%d,%d,%.1f]/(#%d,%d,UP,%d,%.1f)\n",System.currentTimeMillis(),elev_n,floor_n,instr[4],elev_n,floor_n,elev_out.energy,elev_out.run_time-6.0+start_time);
			}
			else if(elev_out.direction==2) {
				System.out.printf("%d:[ER,#%d,%d,%.1f]/(#%d,%d,DOWN,%d,%.1f)\n",System.currentTimeMillis(),elev_n,floor_n,instr[4],elev_n,floor_n,elev_out.energy,elev_out.run_time-6.0+start_time);
			}
			else {
				System.out.printf("%d:[ER,#%d,%d,%.1f]/(#%d,%d,STILL,%d,%.1f)\n",System.currentTimeMillis(),elev_n,floor_n,instr[4],elev_n,floor_n,elev_out.energy,elev_out.run_time+start_time);
			}
			
		}
		else {
			if(dir_n==1) {
				if(elev_out.direction==1) {
					System.out.printf("%d:[FR,%d,UP,%.1f]/(#%d,%d,UP,%d,%.1f)\n",System.currentTimeMillis(),floor_n,instr[4],elev_n,floor_n,elev_out.energy,elev_out.run_time-6.0+start_time);
				}
				else if(elev_out.direction==2) {
					System.out.printf("%d:[FR,%d,UP,%.1f]/(#%d,%d,DOWN,%d,%.1f)\n",System.currentTimeMillis(),floor_n,instr[4],elev_n,floor_n,elev_out.energy,elev_out.run_time-6.0+start_time);
				}
				else {
					System.out.printf("%d:[FR,%d,UP,%.1f]/(#%d,%d,STILL,%d,%.1f)\n",System.currentTimeMillis(),floor_n,instr[4],elev_n,floor_n,elev_out.energy,elev_out.run_time+start_time);
				}
			}
			else {
				if(elev_out.direction==1) {
					System.out.printf("%d:[FR,%d,DOWN,%.1f]/(#%d,%d,UP,%d,%.1f)\n",System.currentTimeMillis(),floor_n,instr[4],elev_n,floor_n,elev_out.energy,elev_out.run_time-6.0+start_time);
				}
				else if(elev_out.direction==2) {
					System.out.printf("%d:[FR,%d,DOWN,%.1f]/(#%d,%d,DOWN,%d,%.1f)\n",System.currentTimeMillis(),floor_n,instr[4],elev_n,floor_n,elev_out.energy,elev_out.run_time-6.0+start_time);
				}
				else {
					System.out.printf("%d:[FR,%d,DOWN,%.1f]/(#%d,%d,STILL,%d,%.1f)\n",System.currentTimeMillis(),floor_n,instr[4],elev_n,floor_n,elev_out.energy,elev_out.run_time+start_time);
				}
			}
		}
		
		
	}
	
}
