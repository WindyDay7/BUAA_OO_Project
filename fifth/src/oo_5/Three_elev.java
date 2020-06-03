package oo_5; 

class Three_elev {
	//private Elevator[] elev = new Elevator[4]; 
	public synchronized void control(double p_t, int[][] p_l, Elevator[] elev2) {
		//System.out.println(p_t);
		/*while(p_t==0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		//System.out.println("调度器在运行");
		push(p_t,p_l,elev2);
		//notifyAll();
	}
	
	public void push(double push_time, int[][] push_line, Elevator[] elev) {
		int count_num = 0;
		int tem;
		
		for(count_num=0;push_line[count_num][1]!=0;count_num++) {
			if(push_line[count_num][0]==1) {    //表示在电梯内的请求
				tem = push_line[count_num][2];
				if(elev[tem].button[push_line[count_num][1]][3] == 1) { //判断是否时同质请求，及电梯按钮有没有被按下
					System.out.printf("%d:SAME[ER,#%d,%d,%.1f]\n",System.currentTimeMillis(),push_line[count_num][2],push_line[count_num][1],push_time);
					continue;
				}
				else {
					//加入指令到电梯的队列中去
					elev[tem].button[push_line[count_num][1]][3]=1;
					elev[tem].re_queue[elev[tem].count][0] = push_line[count_num][0];
					elev[tem].re_queue[elev[tem].count][1] = push_line[count_num][1];
					elev[tem].re_queue[elev[tem].count][2] = push_line[count_num][2];
					elev[tem].re_queue[elev[tem].count][3] = push_line[count_num][3];
					elev[tem].re_queue[elev[tem].count][4] = push_time;
					elev[tem].count++;
				}
 			}
			//表示楼层发出的请求
			else {
				
				if(elev[1].button[push_line[count_num][1]][push_line[count_num][3]]==1 || elev[2].button[push_line[count_num][1]][push_line[count_num][3]]==1 || elev[3].button[push_line[count_num][1]][push_line[count_num][3]]==1) { //判断是否时同质请求，及电梯按钮有没有被按下
					if(push_line[count_num][3]==1) {
						System.out.printf("%d:SAME[FR,%d,UP,%.1f]\n",System.currentTimeMillis(),push_line[count_num][1],push_time);
					}
					else {
						System.out.printf("%d:SAME[FR,%d,DOWN,%.1f]\n",System.currentTimeMillis(),push_line[count_num][1],push_time);
					}
					continue;
				}
				else {
					bring(elev,push_line[count_num],1);
					bring(elev,push_line[count_num],2);
					bring(elev,push_line[count_num],3);
					tem = push_line[count_num][2];
					
					elev[tem].button[push_line[count_num][1]][push_line[count_num][3]]=1;//表示电梯按下那个电梯的楼层的上行或者下行按钮
					//加入指令到电梯的队列
					elev[tem].re_queue[elev[tem].count][0] = push_line[count_num][0];
					elev[tem].re_queue[elev[tem].count][1] = push_line[count_num][1];
					elev[tem].re_queue[elev[tem].count][2] = push_line[count_num][2];
					elev[tem].re_queue[elev[tem].count][3] = push_line[count_num][3];
					elev[tem].re_queue[elev[tem].count][4] = push_time;
					elev[tem].count++;
				}
			}
		}
	}
	
	public void bring(Elevator[] ele_run, int[] request_br, int elev_num) {
		ele_run[elev_num].find();
		//表示电梯向上运行
		if(request_br[3]==1) {
			if(ele_run[elev_num].direction==1) {
				if(ele_run[elev_num].floor_num<request_br[1] && request_br[1]<=ele_run[elev_num].max) {
					if(request_br[2]==0) {  //表示可以捎带
						//之前没有电梯可以捎带
						request_br[2] = ele_run[elev_num].number; 
					}
					else if(request_br[2]==1 && ele_run[elev_num].number!=1) {
						//已判断第一个电梯可以捎带
						request_br[2] = chose(1,ele_run[elev_num].number,ele_run);
					}
					else if(request_br[2]==2 && ele_run[elev_num].number!=2) {
						//已判断第二个电梯可以捎带
						request_br[2] = chose(2,ele_run[elev_num].number,ele_run);
					}
				}
				else {
					unbring(request_br,ele_run);//表示不能捎带
				}
			}
			else {
				unbring(request_br,ele_run);//不能捎带
			}
		}
		
		//表示电梯向下运行
		else {
			if(ele_run[elev_num].direction==2) {
				if(ele_run[elev_num].floor_num>request_br[1] && request_br[1]>=ele_run[elev_num].min) {
					if(request_br[2]==0) {  //表示可以捎带
						//之前没有电梯可以捎带
						request_br[2] = ele_run[elev_num].number; 
					}
					else if(request_br[2]==1 && ele_run[elev_num].number!=1) {
						//已判断第一个电梯可以捎带
						request_br[2] = chose(1,ele_run[elev_num].number,ele_run);
					}
					else if(request_br[2]==2 && ele_run[elev_num].number!=2) {
						//已判断第二个电梯可以捎带
						request_br[2] = chose(2,ele_run[elev_num].number,ele_run);
					}
				}
				else {
					unbring(request_br,ele_run);//表示不能捎带
				}
			}
			else {
				unbring(request_br,ele_run);//不能捎带
			}
		}
		
	}
	
	public int chose(int ele_1, int ele_2, Elevator[] ele_run3) {
		if(ele_run3[ele_1].energy>ele_run3[ele_2].energy) {
			return ele_2;
		}
		else {
			return ele_1;
		}
	}
	
	public void unbring(int[] request_br, Elevator[] elev) {
		if(request_br[2] != 0) {
			return ;
		}
		spare(request_br,elev);
		if(elev[1].count!=0 && elev[2].count!=0 && elev[3].count!=0)
		{
			if(elev[1].energy<elev[2].energy && elev[1].energy<elev[3].energy) {
				request_br[2] = 1;
			}
			else if(elev[2].energy<elev[3].energy && elev[2].energy<elev[1].energy) {
				request_br[2] = 2;
			}
			else if(elev[3].energy<elev[1].energy && elev[3].energy<elev[2].energy) {
				request_br[2] = 3;
			}
			else {
				request_br[2] = 1;
			}
		}
		
	}
	
	public void spare(int[] request_br, Elevator[] elev) {
		if(elev[1].count==0) {
			request_br[2] = 1;
		}
		if(elev[2].count==0) {
			if(request_br[2]==1) {
				if(elev[2].energy<elev[1].energy) {
					request_br[2] = 2;
				}
			}
			else {
				request_br[2] = 2;
			}
		}
		if(elev[3].count==0) {
			if(request_br[2]==1) {
				if(elev[3].energy<elev[1].energy) {
					request_br[2] = 3;
				}
			}
			else if(request_br[2]==2) {
				if(elev[3].energy<elev[2].energy) {
					request_br[2] = 3;
				}
			}
			else {
				request_br[2] = 3;
			}
		}
	}
}
