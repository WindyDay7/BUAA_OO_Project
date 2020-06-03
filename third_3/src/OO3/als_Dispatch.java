package OO3;

public class als_Dispatch extends Dispatch{
	private Request[] cmd = new Request[100];
	private Request[]output = new Request[20];
	private int headRQ = 0, headcmd = 0, endcmd = 0;
	//private double[] ftime = new double[11];
	private double time = 0;
	
	public als_Dispatch () {
		int i=0;
		for(i=0;i<100;i++) {
			cmd[i] = new Request();
		}
		for(i=0;i<20;i++) {
			output[i] = new Request();
		}
	}
	
	public void updater(RequestQue RQ, Elevator ele, Floor floor) {
		int i = headRQ;
		while (i<RQ.getlen() && RQ.gettime(i)<=time) {
			if (RQ.getifin(i)==false) {
				if (!RQ.getr(i).check(ele, floor)) {
					if (RQ.gettime(i)<=floor.getftime(RQ.getdestination(i))+1) {
						RQ.setexc(i, false);
						RQ.setifin(i, true);
						System.out.printf("#SAME");
						RQ.getr(i).printr();
						System.out.printf("\n");
					}
				}
				else {
					if (ele.getdirection()==1 && RQ.getdestination(i)>ele.getfloor() && RQ.gettime(i)<floor.getftime(RQ.getdestination(i)) && RQ.gettime(i)<time-1) {
						if (RQ.getdirection(i)==0 || (RQ.getdirection(i)==1 && RQ.getdestination(i)<=cmd[headcmd].getdestination())) {
							//RQ.getr(i).printr();
							//System.out.printf("   Direction:%d, shaodaiUPPPPPPPPPPPP\n", ele.getdirection());
							requestin(RQ.getr(i), i, ele, floor, RQ);
							//RQ.setexc(i, false);
							if (floor.getstop(RQ.getdestination(i))==1)
								updatetimes(RQ.getr(i), floor, cmd[headcmd].getdestination(), ele);
						}
					}
					else if (ele.getdirection()==-1 && RQ.getdestination(i)<ele.getfloor() && RQ.gettime(i)<floor.getftime(RQ.getdestination(i)) && RQ.gettime(i)<time-1) {
						if (RQ.getdirection(i)==0 || (RQ.getdirection(i)==-1 && RQ.getdestination(i)>=cmd[headcmd].getdestination())) {
							//RQ.getr(i).printr();
							//System.out.printf("   Direction:%d, shaodaDOWNNNNNNNNNNN\n", ele.getdirection());
							requestin(RQ.getr(i), i, ele, floor, RQ);
							//RQ.setexc(i, false);
							if (floor.getstop(RQ.getdestination(i))==1)
								updatetimes(RQ.getr(i), floor, cmd[headcmd].getdestination(), ele);
						}
					}
				}
			}
		i++;
		}
		
	}
	
	public void cmdcheck(RequestQue RQ, Elevator ele, Floor floor) {
		int i=0;
		
		if (headcmd == endcmd) {
			
			requestin(RQ.getr(headRQ), headRQ, ele, floor, RQ);
			RQ.setifin(headRQ, true);
			if (cmd[headcmd].gettime()>time)
				time = cmd[headcmd].gettime();
			for (i=1;i<11;i++) {
				floor.setftime(i, time + 0.5*Math.abs(i-ele.getfloor()));
			}
			headRQ++;
			//cmd[headcmd].printr();
			//System.out.printf("check!!!!!!!!!!!DDDDDDDD:%d\n",ele.getdirection());
			if (cmd[headcmd].getdestination()>ele.getfloor())
				ele.setdirection(1);
			else if (cmd[headcmd].getdestination()<ele.getfloor())
				ele.setdirection(-1);
			else ele.setdirection(0);
			//System.out.printf("currentfloor:%d   targetfloor:%d    direction:%d!!!!!!!!!!!\n",ele.getfloor(),cmd[headcmd].getdestination(), ele.getdirection());
			updatetimes(cmd[headcmd], floor, cmd[headcmd].getdestination(), ele);
		}
		/*if (cmd[headcmd].getdestination()>ele.getfloor())
				ele.setdirection(1);
		else if (cmd[headcmd].getdestination()<ele.getfloor())
				ele.setdirection(-1);
		else ele.setdirection(0);*/
		//System.out.printf("currentfloor:%d   targetfloor:%d   direction:%d\n",ele.getfloor(),cmd[headcmd].getdestination(),ele.getdirection());
		//System.out.printf("check???????????DDDDDDDD:%d\n",ele.getdirection());
		
	}
	
	public void elerun (Elevator ele, Floor floor, RequestQue RQ) {
		int i = 0;	
		/*System.out.printf("main request:");
		cmd[headcmd].printr();
		System.out.printf("     time:%.1f,    floor:%d\n", time, ele.getfloor());*/
		time = floor.getftime(cmd[headcmd].getdestination())+1;
		
		updater(RQ, ele, floor);
		/*if (ele.getdirection()==1) {
			for (i=ele.getfloor()+1;i<=cmd[headcmd].getdestination();i++) {
				for(j=headcmd;j<endcmd;j++) {
					if (cmd[j].getdestination()==i) {
						cmd[j].printr();
						System.out.printf("/(%d,UP,%.1f)",i,floor.getftime(i));
						System.out.printf("\n");
						setbutton(false, cmd[j].getnum(), ele, floor, RQ);
						cmd[j].setexc(false);
					}
				}
			}
		}
		else if (ele.getdirection()==-1) {
			for (i=ele.getfloor()-1;i>=cmd[headcmd].getdestination();i--) {
				for(j=headcmd;j<endcmd;j++) {
					if (cmd[j].getdestination()==i) {
						cmd[j].printr();
						System.out.printf("/(%d,DOWN,%.1f)",i,floor.getftime(i));
						System.out.printf("\n");
						setbutton(false, cmd[j].getnum(), ele, floor, RQ);
						cmd[j].setexc(false);
					}
				}
			}
		}
		else {
			cmd[headcmd].printr();
			System.out.printf("/(%d,STILL,%.1f)",cmd[headcmd].getdestination(),floor.getftime(cmd[headcmd].getdestination())+1);
			System.out.printf("\n");
			setbutton(false, cmd[headcmd].getnum(), ele, floor, RQ);
			cmd[headcmd].setexc(false);
		}*/
		if (ele.getdirection()==0) {
			output(cmd[headcmd].getnum(), ele, floor, RQ);
			floor.setstop(cmd[headcmd].getdestination(), 0);
		}
		else if (ele.getdirection()==1) {
			for (i=ele.getfloor()+1;i<=cmd[headcmd].getdestination();i++) {
				if (floor.getstop(i)==1) {
					if (floor.getup(i)==true) {
						output(floor.getsource(i, 1), ele, floor, RQ);
					}
					else if (ele.getbutton(i)==true) {
						output(ele.getsource(i), ele, floor, RQ);
					}
					else {
						output(floor.getsource(i,-1), ele, floor, RQ);
					}
				}
				else if (floor.getstop(i)==2) {
					if (floor.getdown(i)==false) {
						if (floor.getsource(i, 1)>ele.getsource(i)) {
							output(ele.getsource(i), ele, floor, RQ);
							output(floor.getsource(i, 1), ele, floor, RQ);
						}
						else {
							output(floor.getsource(i, 1), ele, floor, RQ);
							output(ele.getsource(i), ele, floor, RQ);
						}
					}
					else {
						if (floor.getup(i)) {
							output(floor.getsource(i, -1), ele, floor, RQ);
							output(floor.getsource(i, 1), ele, floor, RQ);
						}
						else {
							output(floor.getsource(i, -1), ele, floor, RQ);
							output(ele.getsource(i), ele, floor, RQ);
						}
					}
				}
				else if (floor.getstop(i)==3){
					output(floor.getsource(i, -1), ele, floor, RQ);
					if (floor.getsource(i, 1)>ele.getsource(i)) {
						output(ele.getsource(i), ele, floor, RQ);
						output(floor.getsource(i, 1), ele, floor, RQ);
					}
					else {
						output(floor.getsource(i, 1), ele, floor, RQ);
						output(ele.getsource(i), ele, floor, RQ);
					}
				}
				floor.setstop(i, 0);
			}
		}
		else {
			for (i=ele.getfloor()-1;i>=cmd[headcmd].getdestination();i--) {
				if (floor.getstop(i)==1) {
					if (floor.getdown(i)==true) {
						output(floor.getsource(i, -1), ele, floor, RQ);
					}
					else if (ele.getbutton(i)==true) {
						output(ele.getsource(i), ele, floor, RQ);
					}
					else {
						output(floor.getsource(i, 1), ele, floor, RQ);
					}
				}
				else if (floor.getstop(i)==2) {
					if (floor.getup(i)==false) {
						if (floor.getsource(i, -1)>ele.getsource(i)) {
							output(ele.getsource(i), ele, floor, RQ);
							output(floor.getsource(i, -1), ele, floor, RQ);
						}
						else {
							output(floor.getsource(i, -1), ele, floor, RQ);
							output(ele.getsource(i), ele, floor, RQ);
						}
					}
					else {
						if (floor.getdown(i)) {
							output(floor.getsource(i, 1), ele, floor, RQ);
							output(floor.getsource(i, -1), ele, floor, RQ);
						}
						else {
							output(floor.getsource(i, 1), ele, floor, RQ);
							output(ele.getsource(i), ele, floor, RQ);
						}
					}
				}
				else if (floor.getstop(i)==3){
					output(floor.getsource(i, 1), ele, floor, RQ);
					if (floor.getsource(i, -1)>ele.getsource(i)) {
						output(ele.getsource(i), ele, floor, RQ);
						output(floor.getsource(i, -1), ele, floor, RQ);
					}
					else {
						output(floor.getsource(i, -1), ele, floor, RQ);
						output(ele.getsource(i), ele, floor, RQ);
					}
				}
				floor.setstop(i, 0);
			}
		}
		ele.setfloor(cmd[headcmd].getdestination());
		//System.out.printf("1\n");
		while (headRQ<RQ.getlen() && (RQ.getifin(headRQ)==true || RQ.getexc(headRQ)==false)) {
			headRQ++;
		}
		while (headcmd<endcmd && cmd[headcmd].getexc()==false) {
			headcmd++;
		}
		//System.out.printf("2\n");
	}
	
	public void setbutton(boolean b, int r, Elevator ele, Floor floor, RequestQue RQ) {
		if (RQ.getdirection(r) == 0) {
			ele.setbutton(RQ.getdestination(r), b);
			if (b)
				ele.setsource(RQ.getdestination(r), r);
			else
				RQ.getr(ele.getsource(RQ.getdestination(r))).setexc(false);
		}
		else if (RQ.getdirection(r) == 1) {
			floor.setup(RQ.getdestination(r), b);
			if (b)
				floor.setsource(RQ.getdestination(r), RQ.getdirection(r), r);
			else
				RQ.getr(floor.getsource(RQ.getdestination(r),1)).setexc(false);
		}
		else {
			floor.setdown(RQ.getdestination(r), b);
			if (b)
				floor.setsource(RQ.getdestination(r), RQ.getdirection(r), r);
			else
				RQ.getr(floor.getsource(RQ.getdestination(r),-1)).setexc(false);
		}
	}
	
	public void requestin(Request r, int rnum, Elevator ele, Floor floor, RequestQue RQ) {
		cmd[endcmd] = r;
		endcmd++;
		setbutton(true, rnum, ele, floor, RQ);
		RQ.setifin(rnum, true);
		floor.setstop(r.getdestination(), 1);
		/*r.printr();
		System.out.printf(" is in !!!!!!!!\n");*/
	}
	
	public void updatetimes(Request r, Floor floor, int mrf, Elevator ele) {
		int i;
		int f = r.getdestination();
		int d = ele.getdirection();
		if (d==1) {
			for(i=f+1;i<=10;i++) {
				floor.setftime(i, floor.getftime(i)+1);
			}
		}
		else if (d==-1) {
			for (i=f-1;i>=1;i--) {
				floor.setftime(i, floor.getftime(i)+1);
			}
		}
		time = floor.getftime(mrf)+1;
		/*r.printr();
		for (i=1;i<11;i++) {
			System.out.printf("floor%d:%.1f  ",i, floor.getftime(i));
		}
		System.out.printf("\n");*/
	}
	
	public boolean ifend(RequestQue RQ) {
		return ((headcmd < endcmd) || (headRQ < RQ.getlen()));
	}
	
	public void output(int rnum, Elevator ele, Floor floor, RequestQue RQ) {
		if (ele.getdirection()==0) {
			RQ.getr(rnum).printr();
			System.out.printf("/(%d,STILL,%.1f)",RQ.getdestination(rnum),floor.getftime(RQ.getdestination(rnum))+1);
			System.out.printf("\n");
			setbutton(false, rnum, ele, floor, RQ);
			RQ.setexc(rnum, false);
		}
		else if (ele.getdirection()==1) {
			RQ.getr(rnum).printr();
			System.out.printf("/(%d,UP,%.1f)",RQ.getdestination(rnum),floor.getftime(RQ.getdestination(rnum)));
			System.out.printf("\n");
			setbutton(false, rnum, ele, floor, RQ);
			RQ.setexc(rnum, false);
		}
		else {
			RQ.getr(rnum).printr();
			System.out.printf("/(%d,DOWN,%.1f)",RQ.getdestination(rnum),floor.getftime(RQ.getdestination(rnum)));
			System.out.printf("\n");
			setbutton(false, rnum, ele, floor, RQ);
			RQ.setexc(rnum, false);
		}
	}
	
}
