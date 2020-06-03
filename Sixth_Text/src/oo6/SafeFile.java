package oo6;
import java.io.File;
import java.io.FileWriter;
public class SafeFile {
	private File f;
	
	public SafeFile(String name) {
		this.f=new File(name);
	}
	public synchronized boolean changeSize() {
		try {
			if(this.exists()) {
				FileWriter fw=new FileWriter(this.f, true);
				fw.write("c");
				fw.flush();
				fw.close();
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}
	public synchronized boolean changeTime() {
		try {
			if(this.exists()) {
				long size=f.length();
				//System.out.println(size);
				String s="";
				for(long i=0;i<size;i++) {
					s+="$";
				}
				//System.out.println(s);
				FileWriter fw=new FileWriter(this.f);
				
				fw.write(s);
				fw.flush();
				fw.close();
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}
	public synchronized boolean rename(String newname) {
		if(this.exists()==false)
			return false;
		if(new File(newname).exists()) {
			return false;
		}
		if(this.getParent().equals(new File(newname).getParent())==false) {
			return false;
		}
		if(this.getAbsolutePath().equals(new File(newname).getAbsolutePath())==true) {
			return false;
		}
		if(this.f.renameTo(new File(newname))) {
			return true;
		}
		else {
			return false;
		}
		
	}
	public synchronized boolean move(String newname) {
		if(this.exists()==false)
			return false;
		if(new File(newname).exists()) {
			return false;
		}
		if(this.getParent().equals(new File(newname).getParent())) {
			return false;
		}
		if(this.getName().equals(new File(newname).getName())==false) {
			return false;
		}
		if(this.f.renameTo(new File(newname))) {
			return true;
		}
		else {
			return false;
		}
	}
	public synchronized boolean addFile() {
		if(this.exists())
			return false;
		try {
			if(this.f.createNewFile())
				return true;
			else
				return false;
		}
		catch(Exception e) {
			return false;
		}
	}
	public synchronized String getAbsolutePath() {
		return this.f.getAbsolutePath();
	}
	
	public synchronized SafeFile getParentFile() {
		return (new SafeFile(this.f.getParent()));
	}
	public synchronized boolean exists() {
		return this.f.exists();
	}
	public synchronized boolean mkdirs() {
	
			return this.f.mkdirs();
		
	}
	public synchronized boolean isFile() {
		return this.f.isFile();
	}
	
	public synchronized boolean isDirectory() {
		return this.f.isDirectory();
	}
	
	public synchronized String getName() {
		return this.f.getName();
	}
	
	public synchronized String getParent() {
		return this.f.getParent();
	}
	public synchronized long lastModified() {
		return this.f.lastModified();
	}
	public synchronized SafeFile[] listFiles() {
		int i,j;
		File filelist[]=this.f.listFiles();
		i=filelist.length;
		SafeFile[] safefilelist=new SafeFile[i];
		for(j=0;j<i;j++) {
			safefilelist[j]=new SafeFile(filelist[j].getAbsolutePath());
		}
		return safefilelist;
	}
	
	public synchronized long length() {
		return this.f.length();
	}
	
	public synchronized long getSize() {
		if(this.isFile()) {
			return this.f.length();
		}
		else if(this.isDirectory()) {
			long size=0;
			SafeFile[] children=this.listFiles();
			if(children!=null) {
				for(SafeFile child : children) {
					size+=child.getSize();
				}
			}
			return size;
		}
		else {
			return 0;
		}
	}
	public synchronized boolean delete() {
		return this.f.delete();
	}
}
