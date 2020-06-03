package six;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Renamed implements Runnable {
	
	private String path;
	private String obj_path;  //分别表示监控范围与监控对象的路径
	protected Test test_rn = new Test();
	protected int times = 0;
	protected Sum_detail rn_sum = new Sum_detail();
	protected int in_kind;
	
	public Renamed (String con_path, String obj_path, int in_kind) {
		this.path = con_path;
		this.obj_path = obj_path;
		this.in_kind = in_kind;
	}
	
	public void run() {
		File file_2 = new File(obj_path);
		
		int chose = 0;
		//System.out.println("Renamed开始");
		if(file_2.isDirectory()) {
			ArrayList<File_all> FileList = new ArrayList<File_all>();
			test_rn.test(obj_path,FileList);
			while(true) {
				ArrayList<File_all> FileList2 = new ArrayList<File_all>();
				chose = 0;
				test_rn.test(obj_path,FileList2);
				for(int i=0;i<FileList2.size();i++) {
					for(int j=0;j<FileList.size();j++) {
						//System.out.println(FileList.get(j).name);
						//System.out.println(FileList2.get(i).name);
						if(!(FileList2.get(i).name.equals(FileList.get(j).name)) && FileList2.get(i).l_t==FileList.get(j).l_t && FileList2.get(i).f_l==FileList.get(j).f_l) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							times++;
							if(in_kind==1) {
								rn_sum.summary(FileList.get(j).name,times,"Rename");
							}
							else if(in_kind==2) {
								rn_sum.details(FileList.get(j).file, FileList2.get(i).file);
							}
							else {
								FileList2.get(i).file.renameTo(FileList.get(j).file);
							}
							//System.out.println("目录下文件改名了");
							//表示改名了，需要调用改回去函数，以及summary
							chose=1;
							break;
						}
					}
					if(chose==1)
						break;
				}
				FileList.clear();
				FileList2.clear();
				test_rn.test(obj_path,FileList);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
			}
			
		}
		else if(file_2.isFile()){
			long l_t=0, f_l=0;
			l_t = file_2.lastModified();
			f_l = file_2.length();
			
			while(true) {
				File file_3 = new File(obj_path);  //文件
				File file = new File(path);   //目录
				File[] files = file.listFiles();
				//System.out.println(file_2.length());
				//System.out.println(file_2.lastModified());
				
				
				if(!file_3.exists()) {
					for (File f2 : files) {
						if(f2.lastModified()==l_t && f2.length()==f_l) {
							//假装各种操作1s完成
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							times++;
							if(in_kind==1) {
								rn_sum.summary(file_2.getName(),times,"Rename");
							}
							else if(in_kind==2) {
								rn_sum.details(file_2, f2);
							}
							else {
								f2.renameTo(file_2);
							}
							chose=1;
							break;
						}
					}
					if(chose==1)
						break;
				}
				try {
					Thread.sleep(1000);  
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			if(!file_2.exists()) {
				try {    
			        file_2.createNewFile();    
			    } catch (IOException e) {      
			        e.printStackTrace();    
			    }    
			} //文件不存在新建一个文件
		}
		
	}
}

class File_all {
	protected File file=null;
	protected String name=null;
	protected long f_l=0; 
	protected long l_t=0; 
	protected String path=null;
}


