package oo.app;

import java.awt.Point;
import java.io.File;

public class Request implements Runnable{
	private int srcx,srcy,dstx,dsty;
	private int luverx,luvery,rdverx,rdvery;
	private long reqtime;
	private Taxi[] taxiarr;
	private TaxiGUI mygui;
	private File output2file;
	
	public Request(int srcx, int srcy, int dstx, int dsty, long reqtime,Taxi[] taxiarr,TaxiGUI mygui,File output2file) {
		this.srcx = srcx;
		this.srcy = srcy;
		this.dstx = dstx;
		this.dsty = dsty;
		this.reqtime = reqtime;
		this.taxiarr = taxiarr;
		this.calculateRange();
		this.mygui = mygui;
		this.output2file = output2file;
	}
	public void calculateRange() {
		this.luverx = (this.srcx>1)?this.srcx-2:0;
		this.luvery = (this.srcy>1)?this.srcx-2:0;
		this.rdverx = (this.srcx<78)?this.srcx+2:79;
		this.rdvery = (this.srcy<78)?this.srcy+2:79;
	}
	public boolean equals(Request cmp) {
		if(this.srcx==cmp.srcx&&this.srcy==cmp.srcy&&this.dstx==cmp.dstx&&this.dsty==cmp.dsty&&this.reqtime==cmp.reqtime)
			return true;
		else
			return false;
	}
	public String toString() {
		String str;
		str = String.format("[Request] startmoment:%d,srccoodinate:(%d,%d),dstcoordicate:(%d,%d)\n",this.reqtime,this.srcx,this.srcy,this.dstx,this.dsty);
		return str;
	}
	public void run() {
		try {
			int i,j;
			Taxi[] registeredtaxi = new Taxi[100];
			boolean sameflag;
			int taxinum=0;
			int wrapcount = 0;
			//this.mygui.RequestTaxi(new Point(this.srcx,this.srcy), new Point(this.dstx,this.dsty));
			
			Taxi selectedtaxi;//all needs to be initialized
			while(wrapcount<20) {
				for(i=0;i<100;i++) {
					if(taxiarr[i].isInRange(this.luverx,this.luvery,this.rdverx,this.rdvery)&&taxiarr[i].isWaiting4Ser()) {
						sameflag=false;
						for(j=0;j<taxinum;j++) {
							if(taxiarr[i].equals(registeredtaxi[j])) {
								sameflag = true;
								break;
							}
						}
						if(!sameflag) {
							taxiarr[i].apply4Ser();
							registeredtaxi[j] = taxiarr[i];
							taxinum++;
						}
					}
				}
				gv.stay(150);
				wrapcount++;
			}
				
			OutputInfo outputinfo = new OutputInfo(output2file);
			outputinfo.appendDetail(this.toString());
			outputinfo.appendDetail("[RegistedTaxis]\n");
			
			if(taxinum>0) {
				selectedtaxi = registeredtaxi[0];
				for(i=0;i<taxinum;i++) {
					outputinfo.appendDetail(registeredtaxi[i].toString());
					if(!registeredtaxi[i].isWaiting4Ser())
						continue;
					if(registeredtaxi[i].getCredit()>selectedtaxi.getCredit()) {
						selectedtaxi = registeredtaxi[i];
					}
					else if(registeredtaxi[i].getCredit()==selectedtaxi.getCredit()) {
						if(registeredtaxi[i].getDistance(this.srcx,this.srcy)<selectedtaxi.getDistance(this.srcx,this.srcy))
							selectedtaxi = registeredtaxi[i];
					}
					else
						continue;
				}
			}
			else {
				System.out.println("Request fails.");
				return;
			}
			
			if(!selectedtaxi.isWaiting4Ser()) {
				System.out.println("Scramble defeats.");
				return;
			}
			
			System.out.println("#Request has been responsed.");
			selectedtaxi.startSer(this.srcx,this.srcy,this.dstx,this.dsty,outputinfo,this.reqtime+3000);
		}catch(Exception e) {
			return;
		}
	}
}
