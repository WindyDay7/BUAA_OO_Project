package oo6;
import java.util.Vector;
//import java.io.File;
import java.io.PrintStream;
public class Snapshot {
	FileShot head;
	
	synchronized public void output() {
		System.out.println("name:"+this.head.name+" children:"+this.head.children+" size:"+this.head.size);
		int i;
		for(i=0;i<this.head.children;i++) {
			this.output(this.head.next[i]);
		}
	}
	
	synchronized public void output(FileShot f) {
		if(f.log==0) {
			System.out.println("absolutename:"+f.absolutename+" name:"+f.name+" size:"+f.size+" modify:"+f.lastmodify+" renamed?"+f.renamed);
			return ;
		}
		else {
			System.out.println("absolutename:"+f.absolutename+" name:"+f.name+" size:"+f.size+" children:"+f.children+" renamed?"+f.renamed);
			int i;
			for(i=0;i<f.children;i++) {
				output(f.next[i]);
			}
		}
	}
	//对监控范围f建立快照
	synchronized public void createSnapshot(SafeFile f) {
		int i;
		long size;
		SafeFile[] children = f.listFiles();
		i=children.length;
		size=f.getSize();
		this.head=new FileShot(1, i, size, 0, f.getAbsolutePath(), f.getName(),"no parent");
		if(children!=null) {
			for(i=0;i<children.length;i++) {
				this.createSnapshot(children[i], this.head, i);
			}
		}
	}
	
	synchronized public void createSnapshot(SafeFile f, FileShot father, int position) {
		if(f.isFile()) {
			FileShot fileshot=new FileShot(0, 0, f.getSize(), f.lastModified(), f.getAbsolutePath(), f.getName(), f.getParent());
			father.next[position]=fileshot;
		}
		else if(f.isDirectory()) {
			SafeFile[] children=f.listFiles();
			int i=children.length;
			int j;
			FileShot fileshot=new FileShot(1, i, f.getSize(), 0, f.getAbsolutePath(), f.getName(), f.getParent());
			father.next[position]=fileshot;
			for(j=0;j<i;j++) {
				this.createSnapshot(children[j], fileshot, j);
			}
		}
	}
	
	synchronized public FileShot compare(String monitor_obj, String monitor_area, Snapshot oldshot,Snapshot newshot, Tigger tigger, Vector<Task> task, int log, Summary summary, Detail detail, PrintStream old) {
		if(tigger==Tigger.RENAMED) {
			FileShot renamed=this.renamedDetect(monitor_obj, monitor_area,  oldshot.head,newshot.head, task, log, summary, detail, old);
			return renamed;
		}
		else if(tigger==Tigger.MODIFIED){
			//System.setOut(old);
			//System.out.println("############################");
			this.modifiedDetect(monitor_obj, monitor_area, oldshot.head, newshot.head, task, log, summary, detail, old);
		}
		else if(tigger==Tigger.PATH_CHANGED) {
			FileShot findshot=this.pathChangedDetect(monitor_obj, monitor_area, oldshot.head, newshot.head, task, log, summary, detail, old);
			return findshot;
		}
		else if(tigger==Tigger.SIZE_CHANGED) {
			if(log==0) {
				FileShot objshot1=this.fileExist(this.head, monitor_obj);
				FileShot objshot2=this.fileExist(newshot.head, monitor_obj);
				if(objshot1!=null&&objshot2!=null) {
					if(objshot1.size!=objshot2.size) {
						if(task.contains(Task.RECORD_SUMMARY)) {
							summary.addSizeChanged();
						}
						if(task.contains(Task.RECORD_DETAIL)) {
							String s=objshot1.absolutename+": "+objshot1.size+" --sizeChanged--> "+objshot2.size;
							String s1="(absolutePath:"+objshot1.absolutename+"-->"+objshot2.absolutename+", ";
							String s2="modified:"+objshot1.lastmodify+"-->"+objshot2.lastmodify+", ";
							String s3="name:"+objshot1.name+"-->"+objshot2.name+")";
							s=s+s1+s2+s3;
							detail.addSizeChanged(s);
							//System.out.println(s);
						}
						//System.out.println(objshot1.absolutename+"size changed: "+objshot1.size+" ---> "+objshot2.size);
					}
						
					else {
						//System.out.println("no size changed");
					}
				}
			}
			else {
				if(oldshot.head.size!=newshot.head.size) {
					if(task.contains(Task.RECORD_SUMMARY)) {
						summary.addSizeChanged();
					}
					if(task.contains(Task.RECORD_DETAIL)) {
						String s=monitor_obj+": "+oldshot.head.size+" --sizeChanged--> "+newshot.head.size;
						detail.addSizeChanged(s);
					}
					
					//System.out.println(s);
					//System.out.println(monitor_obj+"  size changed: "+oldshot.head.size+" ---> "+newshot.head.size);
				}
				else {
					//System.out.println("no size changed");
				}
			}
		}
		return null;
	}
	
	synchronized public FileShot pathChangedDetect(String monitor_obj, String monitor_area, FileShot oldshot, FileShot newshot, Vector<Task> task, int log, Summary summary, Detail detail,PrintStream old) {
		if(log==0) {
			//System.setOut(old);
			//System.out.println("############################");
			FileShot objshot1=this.fileExist(oldshot, monitor_obj);
			FileShot objshot2=this.fileExist(newshot, monitor_obj);
			if(objshot1!=null&&objshot2==null) {
				objshot2=this.pathChangedDetect(newshot, objshot1.name, objshot1.size, objshot1.lastmodify);
				if(objshot2!=null) {
					//System.setOut(old);
					//System.out.println("############################");
					if(task.contains(Task.RECORD_SUMMARY)) {
						//System.out.println("############################");
						summary.addPathChanged();
					}
					if(task.contains(Task.RECORD_DETAIL)) {
						String s= objshot1.absolutename+" --pathChanged--> "+objshot2.absolutename;
						//System.setOut(old);
						//System.out.println(s);
						String s1="(name:"+objshot1.name+"-->"+objshot2.name+", ";
						String s2="modified:"+objshot1.lastmodify+"-->"+objshot2.lastmodify+", ";
						String s3="size:"+objshot1.size+"-->"+objshot2.size+")";
						s=s+s1+s2+s3;
						detail.addPathChanged(s);
						//System.out.println(s);
						//System.setOut(System.out);
						//System.out.println("####################+"+s);
					}
					if(task.contains(Task.RECOVER)) {
						System.out.println("!!!!!!!!");
						SafeFile f=new SafeFile(objshot2.absolutename);
						f.move(objshot1.absolutename);
					}
					return objshot2;
					//System.out.println(objshot1.absolutename+"---->pathchanged---->"+objshot2.absolutename);
				}
				return null;
			}
			else {
				return null;
				//System.out.println("no pathchanged!!!!!!!!!");
			}
		}
		//监控的是目录
		else {
			int i;
			for(i=0;i<oldshot.children;i++) {
				if(oldshot.next[i].log==0) {
					this.pathChangedDetect(oldshot.next[i].absolutename, oldshot.next[i].parent, oldshot, newshot, task, 0, summary, detail, old);
				}
				else {
					this.pathChangedDetect(oldshot.next[i].absolutename, oldshot.next[i].parent, oldshot.next[i], newshot, task, 1, summary, detail, old);
				}
			}
			return null;
		}
		
	}
	
	synchronized public FileShot pathChangedDetect(FileShot head, String name, long size, long lastmodify) {
		if(head.log==0) {
			if(head.name.equals(name)&&head.size==size&&head.lastmodify==lastmodify) {
				if(this.fileExist(this.head, head.absolutename)==null)
					return head;
			}
		}
		else {
			int i;
			for(i=0;i<head.children;i++) {
				FileShot findshot;
				findshot=this.pathChangedDetect(head.next[i], name, size, lastmodify);
				if(findshot!=null)
					return findshot;
			}
		}
		return null;
	}
	
	synchronized public void modifiedDetect(String monitor_obj, String monitor_area, FileShot oldshot, FileShot newshot, Vector<Task> task, int log, Summary summary, Detail detail, PrintStream old) {
		if(log==0) {
			//System.setOut(old);
			//System.out.println("%%%%%%%%%%%%%%%%%%%");
			FileShot objshot=this.fileExist(oldshot, monitor_obj);
			FileShot findshot=this.fileExist(newshot, monitor_obj);
			if(objshot!=null&&findshot!=null) {
				if(objshot.lastmodify!=findshot.lastmodify) {
					if(task.contains(Task.RECORD_SUMMARY)) {
						summary.addModified();
					}
					if(task.contains(Task.RECORD_DETAIL)) {
						
						String s=objshot.absolutename+": "+objshot.lastmodify+" --modified--> "+findshot.lastmodify;
						
						//System.setOut(old);
						//System.out.println(s+"%%%%%%%%%%%%%%%%%%%");
						
						String s1="(absolutePath:"+objshot.absolutename+"-->"+findshot.absolutename+", ";
						String s2="name:"+objshot.name+"-->"+findshot.name+", ";
						String s3="size:"+objshot.size+"-->"+findshot.size+")";
						s=s+s1+s2+s3;
						detail.addModified(s);
						
					}
				}
			}
		}
		else {
			int i;
			for(i=0;i<oldshot.children;i++) {
				if(oldshot.next[i].log==0) {
					this.modifiedDetect(oldshot.next[i].absolutename, oldshot.next[i].parent, oldshot, newshot, task, 0, summary, detail, old);
				}
				else {
					this.modifiedDetect(oldshot.next[i].absolutename, oldshot.next[i].parent, oldshot.next[i], newshot, task, 1, summary, detail, old);
				}
			}
		}
	}
	
	
	
	
	synchronized public FileShot renamedDetect(String monitor_obj, String monitor_area, FileShot oldshot, FileShot newshot, Vector<Task> task, int log, Summary summary, Detail detail, PrintStream old) {
		if(log==0) {
			FileShot objshot=this.fileExist(oldshot,monitor_obj);
			if(objshot!=null&&this.fileExist(newshot,monitor_obj)==null) {
				//System.out.println("##############find:"+objshot.absolutename);
				long size=objshot.size;
				long lastmodify=objshot.lastmodify;
				FileShot findshot=this.findRenamedFile(newshot, monitor_area, size, lastmodify);
				if(findshot!=null) {
					if(task.contains(Task.RECORD_SUMMARY)) {
						summary.addRenamed();
					}
					if(task.contains(Task.RECORD_DETAIL)) {
						String s=objshot.name+" --renamed--> "+findshot.name;
						String s1="(absolutePath:"+objshot.absolutename+"-->"+findshot.absolutename+", ";
						String s2="modified:"+objshot.lastmodify+"-->"+findshot.lastmodify+", ";
						String s3="size:"+objshot.size+"-->"+findshot.size+")";
						s=s+s1+s2+s3;
						detail.addRnamed(s);
						//System.setOut(System.out);
						//System.out.println("####################+"+s);
					}
					if(task.contains(Task.RECOVER)) {
						SafeFile renamedfile=new SafeFile(findshot.absolutename);
						renamedfile.rename(objshot.absolutename);
					}
					return findshot;
				}
				return null;
			}
			else {
				return null;
			}
		}
		else {
			int i;
			for(i=0;i<oldshot.children;i++) {
				if(oldshot.next[i].log==0) {
					this.renamedDetect(oldshot.next[i].absolutename, oldshot.next[i].parent, oldshot ,newshot, task, 0, summary, detail, old);
				}
				else {
					this.renamedDetect(oldshot.next[i].absolutename, oldshot.next[i].parent, oldshot.next[i], newshot, task, 1, summary, detail, old);
				}
			}
			return null;
		}
	}
	
	//synchronized public 
	synchronized public FileShot findRenamedFile(FileShot head, String parent, long size, long lastmodify) {
		if(head.absolutename.equals(parent)) {
			int i;
			for(i=0;i<head.children;i++) {
				if(head.next[i]!=null&&head.next[i].log==0&&head.next[i].size==size&&head.next[i].lastmodify==lastmodify) {
					if(head.next[i].renamed==0) {
						if(this.fileExist(this.head, head.next[i].absolutename)==null) {
							//System.out.println("!!!!!!!!!!!!1"+head.next[i].absolutename);
							head.next[i].renamed=1;
							return head.next[i];
						}
						
					}
					else {
						
					}
					
				}
					
			}
			return null;
		}
		else {
			int i;
			for(i=0;i<head.children;i++) {
				if(head.next[i].log==1) {
					FileShot renamed=findRenamedFile(head.next[i], parent, size, lastmodify);
					return renamed;
				}
			}
		}
		return null;
	}
	
	synchronized public FileShot fileExist(FileShot head ,String file) {
		
		int i;
		FileShot find;
		if(head.log==0) {
			
			if(head.absolutename.equals(file)) {
				
				return head;
			}
		}
		else {
			for(i=0;i<head.children;i++) {
				find=this.fileExist(head.next[i], file);
				if(find!=null)
					return find;
			}
		}
		return null;
	}

}
