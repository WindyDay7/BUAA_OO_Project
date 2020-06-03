package work10;

import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Vector;

/**
 * @overview 出租车线程，每一辆车的运动，停止，都在此类中。
 *
 */
class taxi implements Runnable {
	public int number;// 车子编号0为停止，1为服务，2为等待服务，3为准备服务
	public  int cre;// 信用
	public  int state;// 车的状态
	public  int location;// 车的位置
	public 	int lastpos;//车上一时刻位置；
	private TaxiGUI taxiGui;
	private Vector<Integer>[] l;
	private long time;
	private long startTime;
	private boolean call;// 是否被呼叫
	private int begin;// 发出地；
	private int end;// 目的地；
	private long outtime;

	taxi(int id, Vector<Integer>[] lattice, TaxiGUI taxiGui) {
		   /**
         * @REQUIRES:lattice!=None;
         * 			taxiGui!=None;
         * @MODIFIES:
         *      \this.number
         *      \this.cre
         *      \this.state;
         *      this.taxiGui
         *      this.l
         *      this.time 
         *      this.outtime
         *      this.call
         *      this.lastpos
         * @EFFECTS:
         *      this.number = id;
		 *		this.cre = 0;
		 *		this.state = 2;
		 *		this.taxiGui = taxiGui;
		 *		this.l = lattice;
		 *		this.outtime=this.time = startTime = gv.getTime();
		 *		this.call = false;
		 *		this.lastpos=0;
         */
		this.lastpos=0;
		this.number = id;
		this.cre = 0;
		this.state = 2;
		this.taxiGui = taxiGui;
		this.l = lattice;
		this.outtime=this.time = startTime = gv.getTime();
		this.call = false;
	}
	public boolean repOK() {
		  /**
         * @REQUIRES:None;
         * @MODIFIES:
         *     None;
         * @EFFECTS:
         *     /result=(number==1||number==0||number==2||number==3)&&(cre>=0)&&(state==1||state==0||state==2||state==3)&&location>=0&&lastpos>=0
				&&time>=0&&startTime>=0&&begin>=0&&end>=0&&l!=null;
         */
		return (number==1||number==0||number==2||number==3)&&(cre>=0)&&(state==1||state==0||state==2||state==3)&&location>=0&&lastpos>=0
				&&time>=0&&startTime>=0&&begin>=0&&end>=0&&l!=null;
	}
	public synchronized void ifcall(int begin, int end) {
		/**
         * @REQUIRES:begin!=None;
         * 			 end!=None;
         * @MODIFIES:this.call;
         * 			this.begin;
         * 			this.end;
         * @EFFECTS:
         *      this.call=true;
         *      this.begin=begin;
         *      this.end=end;
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		this.call = true;
		this.begin = begin;
		this.end = end;
	}

	public synchronized int getstate() {
		/**
         * @REQUIRES:None;
         * @MODIFIES:None;
         * @EFFECTS:
         *      \result==state;
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		return state;
	}

	public synchronized int getlocation() {
		/**
         * @REQUIRES:None;
         * @MODIFIES:None;
         * @EFFECTS:
         *      \result==location;
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		return location;
	}

	public synchronized int getnumber() {
		/**
         * @REQUIRES:None;
         * @MODIFIES:None;
         * @EFFECTS:
         *      \result==number;
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		return number;
	}

	public synchronized void starting() {
		/**
         * @REQUIRES:None;
         * @MODIFIES:this.location;
         * 			this.taxiGui.SetTaxiStatus;
         * @EFFECTS:
         *      每辆车开始时从0到6400中选择一个数字作为初始位置，并把出租车的位置表示在gui当中；
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		Random rand = new Random();
		location = rand.nextInt(6400);
		taxiGui.SetTaxiStatus(number, new Point(location / 80, location % 80), state);
	}
	public synchronized void running() {
		/**
         * @REQUIRES:None;
         * @MODIFIES:this.location;
         * 			 this.taxiGui.SetTaxiStatus;
         * 			this.time;
         * 			this.outtime;
         * @EFFECTS:
         *      从与当前位置想关联的各边当中随机选择一条连接的边作为下一次移动的位置，并将它表示在地图上，同时系统的时间增加500ms,并判断红绿灯情况和行动的方向来决定是否等待红绿灯
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		int nextpos, nextind;
		int minflow=10000;
		for(int i=0;i<l[location].size();i++) {
			int flow=guigv.GetFlow(location/80, location%80, l[location].get(i)/80, l[location].get(i)%80);
					if(flow<minflow) {
						minflow=flow;
					}
		}
		int r=0;
		Random rand = new Random();
		for(int n=0;n<100;n++) {
		nextind = rand.nextInt(l[location].size());
		int flow=guigv.GetFlow(location/80, location%80, l[location].get(nextind)/80, l[location].get(nextind)%80);
		if(l[location].size()==1) {
			r=nextind;
			break;
		}
		if(flow==minflow&&this.lastpos!=l[location].get(nextind))
			{
				r=nextind;
				break;
			}
		}
		nextpos = l[location].get(r);
		if(this.lastpos==0) {//第一次启动时
			
			if(nextpos-location==1||nextpos-location==-1) {
				if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {//东西方向为红灯
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
						gv.stay(1);
						outtime++;
					}
				}
			}
			if(nextpos-location==80||nextpos-location==-80) {
				if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {//南北方向为红灯
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
						gv.stay(1);
						outtime++;
					}
				}
			}
		}
		else {//启动过后
			if(location-this.lastpos==1) {//出租车向东行驶
				if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {//东西方向为红灯
					if(location-nextpos==80||location-nextpos==-80) //左拐或右拐
						;
					else if(lastpos==nextpos)//掉头
						;
					else {
						while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
							gv.stay(1);
							outtime++;
						}
					}
				}
				else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1){//东西方向为绿灯
					if(location-nextpos==80) {//左拐
						while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
							gv.stay(1);
							outtime++;
						}
					}
					else ;
				}
			}
			else if(location-this.lastpos==-1) {//出租车向西行驶
				if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {//东西方向为红灯
					if(location-nextpos==80||location-nextpos==-80) //左拐或右拐
						;
					else if(lastpos==nextpos)//掉头
						;
					else {
						while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
							gv.stay(1);
							outtime++;
						}
					}
				}
				else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1){//东西方向为绿灯
					if(location-nextpos==-80) {
						while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
							gv.stay(1);
							outtime++;
						}
					}
					else ;
				}
			}
			else if(location-this.lastpos==80) {//出租车向北行驶
				if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {//南北方向为红灯
					if(location-nextpos==1||location-nextpos==-1) //左拐或右拐
						;
					else if(lastpos==nextpos)//掉头
						;
					else {
						while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
							gv.stay(1);
							outtime++;
						}
					}
				}
				else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2){//南北方向为绿灯
					if(location-nextpos==1) {
						while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
							gv.stay(1);
							outtime++;
						}
					}
					else ;
				}
			}
			else if(location-this.lastpos==80) {//出租车向南行驶
				if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {//南北方向为红灯
					if(location-nextpos==1||location-nextpos==-1) //左拐
						;
					else if(lastpos==nextpos)//掉头
						;
					else {
						while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
							gv.stay(1);
							outtime++;
						}
					}
				}
				else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2){//南北方向为绿灯
					if(location-nextpos==-1) {
						while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
							gv.stay(1);
							outtime++;
						}
					}
					else ;
				}
			}
		}
		this.lastpos=location;
		state = 2;
		time += 500;
		outtime+=500;
		location = nextpos;
		taxiGui.SetTaxiStatus(number, new Point(location / 80, location % 80), state);
		gv.stay(500);
	}

	public synchronized void running(char c) {
		/**
         * @REQUIRES:c!=None;
         * @MODIFIES:this.location;
         * 			 this.taxiGui.SetTaxiStatus;
         * 			this.time;
         * 			this.outtime;
         * @EFFECTS:
         *      根据输入的c来定向的选择移动的方向，即将location改变相应的值，并将结果画在地图上。同时系统的时间增加500ms,并判断红绿灯情况和行动的方向来决定是否等待红绿灯；
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		int nextpos = 0;
		if (c == 'U')
			nextpos=location - 80;
		else if (c == 'D')
			nextpos=location + 80;
		else if (c == 'L')
			nextpos=location-1;
		else if (c == 'R')
			nextpos=location+1;
		if(location-this.lastpos==1) {//出租车向东行驶
			if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {//东西方向为红灯
				if(location-nextpos==80||location-nextpos==-80) //左拐或右拐
					;
				else if(lastpos==nextpos)//掉头
					;
				else {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
						gv.stay(1);
						outtime++;
					}
				}
			}
			else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1){//东西方向为绿灯
				if(location-nextpos==80) {//左拐
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
						gv.stay(1);
						outtime++;
					}
				}
				else ;
			}
		}
		else if(location-this.lastpos==-1) {//出租车向西行驶
			if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {//东西方向为红灯
				if(location-nextpos==80||location-nextpos==-80) //左拐或右拐
					;
				else if(lastpos==nextpos)//掉头
					;
				else {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
						gv.stay(1);
						outtime++;
					}
				}
			}
			else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1){//东西方向为绿灯
				if(location-nextpos==-80) {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
						gv.stay(1);
						outtime++;
					}
				}
				else ;
			}
		}
		else if(location-this.lastpos==80) {//出租车向北行驶
			if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {//南北方向为红灯
				if(location-nextpos==1||location-nextpos==-1) //左拐或右拐
					;
				else if(lastpos==nextpos)//掉头
					;
				else {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
						gv.stay(1);
						outtime++;
					}
				}
			}
			else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2){//南北方向为绿灯
				if(location-nextpos==1) {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
						gv.stay(1);
						outtime++;
					}
				}
				else ;
			}
		}
		else if(location-this.lastpos==80) {//出租车向南行驶
			if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {//南北方向为红灯
				if(location-nextpos==1||location-nextpos==-1) //左拐
					;
				else if(lastpos==nextpos)//掉头
					;
				else {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
						gv.stay(1);
						outtime++;
					}
				}
			}
			else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2){//南北方向为绿灯
				if(location-nextpos==-1) {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
						gv.stay(1);
						outtime++;
					}
				}
				else ;
			}
		}
		this.lastpos=location;
		time += 500;
		outtime+=500;
		location=nextpos;
		taxiGui.SetTaxiStatus(number, new Point(location / 80, location % 80), state);
		gv.stay(500);
	}

	public synchronized void runningout(char c) {
		/**
         * @REQUIRES:c!=None;
         * @MODIFIES:this.location;
         * 			 this.taxiGui.SetTaxiStatus;
         * 			this.time;
         * 			this.outtime;
         * @EFFECTS:
         *      根据输入的c来定向的选择移动的方向，即将location改变相应的值，并将结果画在地图上。同时系统的时间增加500ms，并判断红绿灯情况和行动的方向来决定是否等待红绿灯,同时输出相应的信息；
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		int nextpos = 0;
		if (c == 'U')
			nextpos=location - 80;
		else if (c == 'D')
			nextpos=location + 80;
		else if (c == 'L')
			nextpos=location-1;
		else if (c == 'R')
			nextpos=location+1;
		if(location-this.lastpos==1) {//出租车向东行驶
			if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {//东西方向为红灯
				if(location-nextpos==80||location-nextpos==-80) //左拐或右拐
					;
				else if(lastpos==nextpos)//掉头
					;
				else {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
						gv.stay(1);
						outtime++;
					}
				}
			}
			else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1){//东西方向为绿灯
				if(location-nextpos==80) {//左拐
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
						gv.stay(1);
						outtime++;
					}
				}
				else ;
			}
		}
		else if(location-this.lastpos==-1) {//出租车向西行驶
			if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {//东西方向为红灯
				if(location-nextpos==80||location-nextpos==-80) //左拐或右拐
					;
				else if(lastpos==nextpos)//掉头
					;
				else {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
						gv.stay(1);
						outtime++;
					}
				}
			}
			else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1){//东西方向为绿灯
				if(location-nextpos==-80) {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
						gv.stay(1);
						outtime++;
					}
				}
				else ;
			}
		}
		else if(location-this.lastpos==80) {//出租车向北行驶
			if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {//南北方向为红灯
				if(location-nextpos==1||location-nextpos==-1) //左拐或右拐
					;
				else if(lastpos==nextpos)//掉头
					;
				else {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
						gv.stay(1);
						outtime++;
					}
				}
			}
			else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2){//南北方向为绿灯
				if(location-nextpos==1) {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
						gv.stay(1);
						outtime++;
					}
				}
				else ;
			}
		}
		else if(location-this.lastpos==80) {//出租车向南行驶
			if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {//南北方向为红灯
				if(location-nextpos==1||location-nextpos==-1) //左拐
					;
				else if(lastpos==nextpos)//掉头
					;
				else {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==1) {
						gv.stay(1);
						outtime++;
					}
				}
			}
			else if(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2){//南北方向为绿灯
				if(location-nextpos==-1) {
					while(taxiGui.getLightStatus(new Point(location / 80, location % 80))==2) {
						gv.stay(1);
						outtime++;
					}
				}
				else ;
			}
		}
		this.lastpos=location;
		time += 500;
		location=nextpos;
		outtime+=500;
		taxiGui.SetTaxiStatus(number, new Point(location / 80, location % 80), state);
		System.out.println("车辆编号" + this.number + "位置(" + location / 80 + "," + location % 80 + ")" + "时间" + outtime);
		gv.stay(500);
	}

	private synchronized void stop() {

		this.state = 0;
		time += 1000;
		outtime+=1000;
		taxiGui.SetTaxiStatus(number, new Point(location / 80, location % 80), state);
		gv.stay(1000);
	}

	public String findpath(int _start, int _end) {
		/**
         * @REQUIRES:_start!=None;
         * 			_end!=None;
         * @MODIFIES:None;
         * @EFFECTS:
         *      根据起始点和终点去选择最短的路径，并将最短的路径存成一个字符串，用来表示车的上下左右行驶并返回这个字符串；
         */
		int start = _end;// 反转起始点与终止点,从终点开始找最短路径
		int begin = _start;
		if (_start == _end)
			return "";
		boolean[] inQ = new boolean[6400];// inQ[i]表示点i是否在队列中
		int[] dis = new int[6400];// 距离数组,表示点i到起点的距离
		int[] next = new int[6400];// next[i]表示点i的下一个点
		Queue<Integer> q = new LinkedList<>();
		for (int i = 0; i < 6400; i++)
			dis[i] = 100000;
		inQ[start] = true;
		dis[start] = 0;
		q.offer(start);
		int now, cost;
		while (!q.isEmpty()) {
			now = q.poll();
			for (int to : l[now]) {
				cost = 1 + dis[now];
				if (dis[to] > cost) {
					next[to] = now;
					dis[to] = cost;
					if (!inQ[to]) {
						inQ[to] = true;
						q.offer(to);
					}
				}
			}
			inQ[now] = false;
		}
		StringBuilder sb = new StringBuilder("");
		while (begin != _end) {
			if (next[begin] == begin + 1)
				sb.append('R');
			if (next[begin] == begin - 1)
				sb.append('L');
			if (next[begin] == begin + 80)
				sb.append('D');
			if (next[begin] == begin - 80)
				sb.append('U');
			begin = next[begin];
		}
		return sb.toString();
	}

	@Override
	public void run() {
		/**
         * @REQUIRES:None;
         * @MODIFIES:this.location;
         * 			 this.taxiGui.SetTaxiStatus;
         * 			this.state;
         * 			this.time;
         * 			this.outtime;
         * 			this。cre;
         * @EFFECTS:
         *      先判断车时候被呼叫，若被叫则按当前位置和乘客起始位置的最短路径出发，并把自己的状态改成接客，到达目的地之后，在按起始位置和乘客目的地的最短路径出发并改变自己的状态，同时增加自己的信用。
         *      另外无论是否被呼叫都每20s停止1s。然后到达目的地后停止1s。
         */
		this.starting();
		while (true) {
			if (call) {
				String path = this.findpath(location, begin);
				this.state = 3;
				for (int k = 0; k < path.length(); k++) {
					this.running(path.charAt(k));
				}
				System.out.println("到达接客地点的时间" + outtime);
				this.stop();
				path = this.findpath(begin, end);
				this.state = 1;
				for (int k = 0; k < path.length(); k++) {
					this.runningout(path.charAt(k));
				}
				this.cre += 3;
				System.out.println("到达目的地的时间" + outtime);
				this.stop();
				call = false;
			}
			if (time - startTime < 20000) {
				this.running();
			} else {
				this.stop();
				startTime = time;
			}
		}
	}

}
