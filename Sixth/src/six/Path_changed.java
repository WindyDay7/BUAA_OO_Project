package six;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Path_changed implements Runnable {
	private String con_path;
	private String obj_path;
	protected Test test_pc = new Test();
	protected int in_kind, times;
	protected Sum_detail pc_sum = new Sum_detail();
	
	public Path_changed(String con_path, String obj_path, int in_kind) {
		this.con_path = con_path;
		this.obj_path = obj_path;
		this.in_kind = in_kind;
	}
	
	public void run() {
		File file_2 = new File(obj_path);
		int chose = 0;
		if(file_2.isDirectory()) {
			ArrayList<File_all> FileList = new ArrayList<File_all>();
			test_pc.test(obj_path,FileList);
			while(true) {
				chose = 0;
				ArrayList<File_all> FileList2 = new ArrayList<File_all>();
				test_pc.test(obj_path,FileList2);
				for(int i=0; i<FileList2.size();i++) {
					for(int j=0;j<FileList.size();j++) {
						if(FileList2.get(i).name.equals(FileList.get(j).name) && FileList2.get(i).l_t==FileList.get(j).l_t && FileList2.get(i).f_l==FileList.get(j).f_l && i!=j) {
							//假装改名需要一秒钟
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							times++;
							if(in_kind==1) {
								pc_sum.summary(FileList.get(j).name,times,"Path_changed");
							}
							else if(in_kind==2) {
								pc_sum.details(FileList.get(j).file, FileList2.get(i).file);
							}
							else {
								FileList2.get(i).file.renameTo(FileList.get(j).file);
							}
							chose = 1;
							break;   //表示找到
						}
					}
					if(chose==1)
						break;
				}
				FileList.clear();
				FileList2.clear();
				test_pc.test(obj_path,FileList);
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
				File file_3 = new File(obj_path);
				ArrayList<File_all> FileList3 = new ArrayList<File_all>();
				test_pc.test(file_2.getParent(),FileList3);
				if(!file_3.exists()) {
					for (int i=0;i<FileList3.size();i++) {
						if(FileList3.get(i).file.getName().equals(file_2.getName()) && FileList3.get(i).l_t==l_t && FileList3.get(i).f_l==f_l && FileList3.get(i).file.getPath()!=file_2.getPath()) {
							//假装改名需要一秒钟
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							times++;
							if(in_kind==1) {
								pc_sum.summary(file_2.getName(),times,"Path_changed");
							}
							else if(in_kind==2) {
								pc_sum.details(file_2, FileList3.get(i).file);
							}
							else {
								FileList3.get(i).file.renameTo(file_2);
							}
							obj_path = FileList3.get(i).file.getPath();
							chose = 1;
							break;   //表示路径发生变化的，线程还不能结束，要挺住啊
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
