package oo6;
import java.io.File;
import java.io.PrintStream;
import java.util.Vector;
public class Monitor extends Thread{
	PrintStream old;
	int i=0;
	int log=0;
	Tigger tigger;
	Vector<Task> task=new Vector<Task> ();
	String monitor_obj;
	private SafeFile monitor_area;
	private long time;
	private Snapshot snapshot;
	private Summary summary;
	private Detail detail;
	public Monitor(SafeFile f,Summary summary,Detail detail,Tigger tigger, Task task, PrintStream old){
		this.old=old;
		this.summary=summary;
		this.detail=detail;
		this.tigger=tigger;
		this.task.add(task);
		this.time=System.currentTimeMillis();
		this.monitor_obj=f.getAbsolutePath();
		//this.snapshot=firstshot;
		if(f.isDirectory()) {
			log=1;
			this.monitor_area=f;
		}
		else if(f.isFile()) {
			log=0;
			this.monitor_area=f.getParentFile();
		}
		this.snapshot=new Snapshot();
		this.snapshot.createSnapshot(this.monitor_area);
	}
	public void addTask(Task task) {
		this.task.add(task);
	}
	public void run() {
		while(true) {
			if(System.currentTimeMillis()-this.time>600L) {
				//i++;
				
				
				//System.setOut(old);
				//this.snapshot.output();
				
				
				this.time=System.currentTimeMillis();
				
				Snapshot newshot=new Snapshot();
				newshot.createSnapshot(this.monitor_area);
				
				FileShot shot=this.snapshot.compare(monitor_obj, monitor_area.getAbsolutePath(), this.snapshot,newshot, tigger, task, log, summary, detail, old);
				if(log==0&&this.tigger==Tigger.RENAMED&&shot!=null) {
					if(task.contains(Task.RECOVER)==false) {
						this.monitor_area=new SafeFile(shot.parent);
						this.monitor_obj=shot.absolutename;
					}
						
				}
				else if(log==0&&this.tigger==Tigger.PATH_CHANGED&&shot!=null) {
					if(task.contains(Task.RECOVER)==false) {
						this.monitor_area=new SafeFile(shot.parent);
						this.monitor_obj=shot.absolutename;
					}
				}
				if(task.contains(Task.RECOVER)) {
				
					
						//System.setOut(old);
						//System.out.println(shot.absolutename+"\n$$$$$$$$$$\n");
						newshot.createSnapshot(this.monitor_area);
						
					
					
				}
				
				this.snapshot=newshot;
				
					
			}
		}
		
	}
}
