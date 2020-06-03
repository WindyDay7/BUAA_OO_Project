package oo.app;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//STATIONARY,SERVING,WAITING4SERVING,ORDERTAKING
public class InputHandler {
	private Scanner keyboard;
	private String Regex = "^\\[CR\\,\\(([\\d]+)\\,([\\d]+)\\)\\,\\(([\\d]+)\\,([\\d]+)\\)\\]$";
	private String SInquiryRegex = "^print taxi ([\\d]+) information$";
	private String TInquiryRegex = "^print taxiID in (STATIONARY|SERVING|WAITING4SERVING|ORDERTAKING)$";
	private LinkedList<Request> registedreq = new LinkedList<Request>();
	private Taxi[] taxiarr;
	private TaxiGUI mygui;
	private File output2file;
	
	public InputHandler(Taxi[] taxiarr,TaxiGUI mygui,String outputfile) {
		this.taxiarr = taxiarr;
		this.mygui = mygui;
		this.output2file = new File(outputfile);
	}
	public void formatRequest() {
		try {
			String tmpline;
			Request tmpreq;
			boolean sameflag;
			this.keyboard = new Scanner(System.in);
			new FileOutputStream(this.output2file);//clear
			
			Pattern requestpat = Pattern.compile(this.Regex);
			Pattern sinquirypat = Pattern.compile(this.SInquiryRegex);
			Pattern tinquitypat = Pattern.compile(this.TInquiryRegex);
			if(!this.output2file.exists())
				this.output2file.createNewFile();
			while(true) {
				tmpline = this.keyboard.nextLine();
				Matcher requestmat = requestpat.matcher(tmpline);
				Matcher sinquirymat = sinquirypat.matcher(tmpline);
				Matcher tinquirymat = tinquitypat.matcher(tmpline);
				if(requestmat.matches()) {
					if((tmpreq=parseRequest(requestmat.group(1),requestmat.group(2),requestmat.group(3),requestmat.group(4)))!=null) {
						sameflag = false;
						
						for(int i=0;i<this.registedreq.size();i++) {
							if(tmpreq.equals(this.registedreq.get(i))) {
								sameflag = true;
								break;
							}
						}
						
						if(sameflag) {
							continue;
						}else {
							this.registedreq.add(tmpreq);
							new Thread(tmpreq).start();
						}
					}else
						continue;
				}else if(sinquirymat.matches()) {
					this.inquiryByID(sinquirymat.group(1));
				}else if(tinquirymat.matches()) {
					this.inquiryByStatus(tinquirymat.group(1));
				}else
					System.out.println("Invalid input:" + tmpline);
					continue;
			}
		}catch(Exception e) {
		}
	}
	public void inquiryByID(String tmpID) {
		int taxiID;
		try {
			taxiID = Integer.parseInt(tmpID);
			if(taxiID>79||taxiID<0)
				return;
			else {
				System.out.print("InquiryTime:"+System.currentTimeMillis()+" "+this.taxiarr[taxiID].toString());
			}
		}catch(Exception e) {
		}
	}
	public void inquiryByStatus(String tmpstatus) {
		TaxiStatus status = TaxiStatus.parseTaxiStatus(tmpstatus);
		int i;
		System.out.println("taxiIDs:");
		for(i=0;i<100;i++) {
			if(this.taxiarr[i].sameStatus(status)) {
				System.out.print(i+" ");
			}
			if(i==99) {
				System.out.print("\n");
			}
		}
	}
	public Request parseRequest(String srchor,String srcver,String dsthor,String dstver) {
		int srcx,srcy,dstx,dsty;
		try {
			srcx = Integer.parseInt(srchor);
			srcy = Integer.parseInt(srcver);
			dstx = Integer.parseInt(dsthor);
			dsty = Integer.parseInt(dstver);
			if(numIsValid(srcx)&&numIsValid(srcy)&&numIsValid(dstx)&&numIsValid(dsty)&&!(srchor==dsthor&&srcver==dstver))
				return new Request(srcx,srcy,dstx,dsty,System.currentTimeMillis()/100*100,this.taxiarr,this.mygui,this.output2file);
			else
				return null;
		}catch(Exception e) {
			return null;
		}
	}
	public boolean numIsValid(int axis) {
		if(axis<80&&axis>=0)
			return true;
		else
			return false;
	}
}
