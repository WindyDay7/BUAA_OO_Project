package oo.app;

import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import oo.app.GraphInfo.GraphCal;



public class Taxi implements Runnable{
	private int horatt;
	private int veratt;
	private int ID;
	private TaxiStatus status;
	private int credit;
	private int [][] map;
	private int [][] graph;
	private TaxiGUI mygui;
	private Random rand;
	private int srcx,srcy,dstx,dsty;
	private OutputInfo outputinfo;
	private long ordertakingtime;
	
	public Taxi(int index,int[][] map,TaxiGUI mygui,int [][] mapgraph) {
		this.rand	=	new Random();
		this.horatt	=	rand.nextInt(80);
		this.veratt	=	rand.nextInt(80);
		this.ID		=	index;
		this.status =	TaxiStatus.WAITING4SERVING;
		this.credit	=	0;
		this.map	=	map;	
		this.mygui	=	mygui;
		this.graph	=	mapgraph;
		this.outputinfo	=	null;
		this.ordertakingtime = 0;
	}
	
	public void randomMove() {
		while(true) {
			int direction = rand.nextInt()%4;
			switch(direction){
			case(0):{
				if(this.veratt>0&&map[this.veratt-1][this.horatt]>=2) {
					this.veratt	-=	1;
					mygui.SetTaxiStatus(this.ID,new Point(this.veratt,this.horatt),2);
					return;
				}
				break;
			}
			case(1):{
				if(this.horatt<79&&(map[this.veratt][this.horatt]==1||map[this.veratt][this.horatt]==3)) {
					this.horatt	+=	1;
					mygui.SetTaxiStatus(this.ID,new Point(this.veratt,this.horatt),2);
					return;
				}
				break;
			}
			case(2):{
				if(this.veratt<79&&(map[this.veratt][this.horatt]>=2)) {
					this.veratt +=	1;
					mygui.SetTaxiStatus(this.ID,new Point(this.veratt,this.horatt),2);
					return;
				}
				break;
			}
			case(3):{
				if(this.horatt>0&&(map[this.veratt][this.horatt-1]==1||map[this.veratt][this.horatt-1]==3)) {
					this.horatt -=	1;
					mygui.SetTaxiStatus(this.ID,new Point(this.veratt,this.horatt),2);
					return;
				}
				break;
			}
			}
		}
	}

	public void run() {
		try {
			int i;
			int randmovnum = 0;
			Vector<NNode> path;
			
			while(true) {
				switch(this.status) {
				case WAITING4SERVING :{
					if(randmovnum<50) {//recorrect
						this.randomMove();//randomly moves
						mygui.SetTaxiStatus(this.ID, new Point(this.getXAxis(),this.getYAxis()),2);
						gv.stay(200);
						randmovnum++;
					}else {
						this.status = TaxiStatus.STATIONARY;
						mygui.SetTaxiStatus(this.ID, new Point(this.getXAxis(),this.getYAxis()), 0);//have a rest for 1s;
						gv.stay(1000);
						randmovnum = 0;
						this.status = TaxiStatus.WAITING4SERVING;
					}
					break;
				}
				case ORDERTAKING :{
					long virtualtime = this.ordertakingtime;
					
					path = GraphCal.pointBFS(this.getXAxis() * 80 + this.getYAxis(),srcx * 80 + srcy,this.graph);
					for(i=0;i<path.size();i++) {
						if(i==0) {
							continue;
						}
						else{
							this.veratt = path.get(i).No/80;
							this.horatt = path.get(i).No%80;
							mygui.SetTaxiStatus(this.ID, new Point(this.getXAxis(),this.getYAxis()),3);
							gv.stay(200);
							virtualtime += 200;
							if(i!=path.size()-1)
								this.outputinfo.appendDetail(String.format("passingby:\t\t(%d,%d)\tat %d\n",this.getXAxis(),this.getYAxis(),virtualtime));
							else
								this.outputinfo.appendDetail(String.format("reachpassengerslocation:\t\t(%d,%d)\tat %d\n",this.getXAxis(),this.getYAxis(),virtualtime));
						}
					}//run to where the passengers are
					
					this.status = TaxiStatus.STATIONARY;//passengers get on;
					mygui.SetTaxiStatus(this.ID, new Point(this.getXAxis(),this.getYAxis()), 0);
					gv.stay(1000);
					virtualtime += 1000;
					
					this.status = TaxiStatus.SERVING;
					path = GraphCal.pointBFS(this.getXAxis() * 80 + this.getYAxis(),this.dstx * 80 + dsty,this.graph);
					for(i=0;i<path.size();i++) {
						if(i==0)
							continue;
						else{
							this.veratt = path.get(i).No/80;
							this.horatt = path.get(i).No%80;
							mygui.SetTaxiStatus(this.ID, new Point(this.getXAxis(),this.getYAxis()),1);
							gv.stay(200);
							virtualtime += 200;
							if(i!=path.size()-1)
								this.outputinfo.appendDetail(String.format("passingby:\t\t(%d,%d)\tat %d\n",this.getXAxis(),this.getYAxis(),virtualtime));
							else
								this.outputinfo.appendDetail(String.format("reachdestiny:\t\t(%d,%d)\tat %d\n",this.getXAxis(),this.getYAxis(),virtualtime));
						}//run to destiny
					}
					
					this.status = TaxiStatus.STATIONARY;//passengers get off
					mygui.SetTaxiStatus(this.ID, new Point(this.getXAxis(),this.getYAxis()), 0);
					gv.stay(1000);
					virtualtime += 1000;
					
					this.credit += 3;//end serving
					this.status = TaxiStatus.WAITING4SERVING;
					
					randmovnum = 0;
					this.outputinfo.appendDetail("----------------------------------------------------------------------------\n\n\n");
					this.outputinfo.flush();
					break;
				}
				default :{
					break;
				}
				}
			}
		}catch(Exception e) {
			return;
		}
	}
	
	
	public String toString() {
		String str;
		str = String.format("ID:%d,location:(%d,%d),status:%s,credit:%d\n",this.ID,this.getXAxis(),this.getYAxis(),this.status.toString(),this.credit);
		return str;
	}
	
	public int getXAxis() {
		return this.veratt;
	}
	public int getYAxis() {
		return this.horatt;
	}

	public boolean isInRange(int luverx, int luvery, int rdverx, int rdvery) {
		if(this.getXAxis()>=luverx&&this.getXAxis()<=rdverx&&this.getYAxis()>=luvery&&this.getYAxis()<=rdvery)
			return true;
		else
			return false;
	}
	public boolean isWaiting4Ser() {
		if(this.status==TaxiStatus.WAITING4SERVING) 
			return true;
		else
			return false;
	}
	public boolean equals(Taxi cmp) {
		if(this.ID==cmp.ID)
			return true;
		else
			return false;
	}

	public void apply4Ser() {
		this.credit += 1;
	}

	public int getCredit() {
		return this.credit; 
	}

	public int getDistance(int srcx, int srcy) {
		Vector<NNode> path;
		path = GraphCal.pointBFS(this.getXAxis() * 80 + this.getYAxis(),srcx * 80 + srcy,this.graph);
		return path.size()-1;
	}

	public synchronized void startSer(int srcx, int srcy, int dstx, int dsty,OutputInfo outputinfo,long ordertakingtime) {
		this.status = TaxiStatus.ORDERTAKING;
		this.srcx = srcx;
		this.srcy = srcy;
		this.dstx = dstx;
		this.dsty = dsty;
		this.outputinfo = outputinfo;
		this.ordertakingtime = ordertakingtime;
		
		this.outputinfo.appendDetail(String.format("[SelectedTaxiInfo] \nID:%d,orilocation:(%d,%d),otmoment:%d\n", this.ID,this.getXAxis(),this.getYAxis(),this.ordertakingtime));
		System.out.println("#Taxi_NO" + this.ID + "runs to src");
	}

	public boolean sameStatus(TaxiStatus status) {
		if(this.status.equals(status))
			return true;
		else
			return false;
	}

}
