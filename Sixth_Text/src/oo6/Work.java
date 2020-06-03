package oo6;
import java.io.FileWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;
import java.io.PrintStream;
public class Work {

	public static void main(String[] args) {
		

		long time;
		SafeFile outputfile= new SafeFile("C:/OO6output");
		if(outputfile.exists()==false) {
			outputfile.mkdirs();
		}
		
		Test test1=new Test();//建立测试线程
		
		PrintStream old=System.out;
		Summary summary=new Summary();
		Detail detail=new Detail();
		
		
		
		Vector<Monitor> monitors=new Vector<Monitor>();
		InputHandler inputhandler=new InputHandler(monitors);
		Scanner read=new Scanner(System.in);
		
		
		//从控制台读监控作业
		try {
			while(true) {
				String s;
				if(read.hasNextLine()) {
					s=read.nextLine();
					if(s.equals("END")) {
						
						break;
					}
					String st[]=s.split(" ");
					Task task=null;
					Tigger tigger=null;
					if(st[2].equals("renamed")) {
						tigger=Tigger.RENAMED;
					}
					else if(st[2].equals("modified")) {
						tigger=Tigger.MODIFIED;
					}
					else if(st[2].equals("pathChanged")) {
						tigger=Tigger.PATH_CHANGED;
					}
					else if(st[2].equals("sizeChanged")) {
						tigger=Tigger.SIZE_CHANGED;
					}
					else {
						
					}
					
					if(st[4].equals("recordSummary")) {
						task=Task.RECORD_SUMMARY;
					}
					else if(st[4].equals("recordDetail")) {
						task=Task.RECORD_DETAIL;
					}
					else if(st[4].equals("recover")) {
						task=Task.RECOVER;
					}
					else {
					}
					inputhandler.createMonitor(st[1], tigger, task, summary, detail, old);//创建监控线程
				}
				
			}
			read.close();
		}
		catch(Exception e) {
			
		}
		
		try {
			int i;
			
			////////////////////////////////启动监控线程
			for(i=0;i<monitors.size();i++) {
				//System.out.println(monitors.get(i).monitor_obj);
				monitors.get(i).start();
			}
			///////////////////////////////////////////////
			
			
			time=System.currentTimeMillis();
			i=0;
			
			/////////////////////////////////////启动测试线程
			test1.start();
			//////////////////////////////////////////
			
			
			
			while(true) {
				if(System.currentTimeMillis()-time>3000L) {
					time=System.currentTimeMillis();
					i++;
					detail.output();
					summary.output();
					
				}
			}
		}
		catch(Exception e) {
			System.setOut(old);
			System.out.println("excption!!");
		}
		
		
	}

}
