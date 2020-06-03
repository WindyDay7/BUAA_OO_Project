package oo6;

import java.util.Vector;
import java.io.PrintStream;
public class InputHandler {
	int count=0;
	String[] monitor_obj=new String[10];
	private Vector<Monitor> monitors=new Vector<Monitor> ();
	public InputHandler(Vector<Monitor> m) {
		this.monitors=m;
	}
	synchronized public void createMonitor(String s, Tigger tigger, Task task, Summary summary, Detail detail, PrintStream old) {
		int i;
		int log=0;
		if(count==10) {
			System.out.println("already 10 monitorObjects");
			return;
		}
			
		if(tigger==null||task==null) {
			System.out.println("format error");
			return;
		}
		if(task==Task.RECOVER&&(tigger==Tigger.MODIFIED||tigger==Tigger.SIZE_CHANGED)) {
			System.out.println("format error");
			return;
		}
		for(i=0;i<this.monitors.size();i++) {
			Monitor m=monitors.get(i);
			SafeFile file=new SafeFile(s);
			s=file.getAbsolutePath();
			
			if(s.equals(m.monitor_obj)&&tigger==m.tigger&&m.task.contains(task)) {
				log=1;
				break;
			}
			if(s.equals(m.monitor_obj)&&tigger==m.tigger&&(m.task.contains(task)==false)) {
				log=1;
				monitors.get(i).addTask(task);
			}
			else {
				//System.out.println("newwork!");
			}
		}
		if(log==0) {
			SafeFile f=new SafeFile(s);
			if(f.exists()==true) {
				Monitor m=new Monitor(f,summary, detail, tigger, task, old);
				this.monitors.add(m);
				int temp=0;
				for(i=0;i<this.count;i++) {
					if(s.equals(this.monitor_obj[i])) {
						temp=1;
						break;
					}
				}
				if(temp==0) {
					this.monitor_obj[this.count]=s;
					this.count++;
				}
			}
			else {
				System.out.println("file not find");
			}
			
		}
	}
}
