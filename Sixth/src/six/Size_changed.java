package six;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Size_changed implements Runnable{
	private String path;
	private String obj_path;  //分别表示监控范围与监控对象的路径
	protected Test test_sc = new Test();
	protected int times, in_kind;
	protected Sum_detail sc_sum = new Sum_detail();
	
	public Size_changed(String con_path, String obj_path, int in_kind) {
		this.path = con_path;
		this.obj_path = obj_path;
		this.in_kind = in_kind;
	}
	
	public void run() {
		File file_2 = new File(obj_path);
		int chose = 0;
		if(file_2.isDirectory()) {
			chose = 0;
			ArrayList<File_all> FileList = new ArrayList<File_all>();
			test_sc.test(obj_path,FileList);
			while(true) {
				ArrayList<File_all> FileList2 = new ArrayList<File_all>();
				test_sc.test(obj_path,FileList2);
				for(int i=0; i<FileList2.size();i++) {
					for(int j=0;j<FileList.size();j++) {
						if(FileList2.get(i).name.equals(FileList.get(j).name) && FileList2.get(i).l_t!=FileList.get(j).l_t && FileList2.get(i).f_l!=FileList.get(j).f_l) {
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							times++;
							if(in_kind==1) {
								sc_sum.summary(FileList.get(j).name,times,"size_change");
							}
							else if(in_kind==2) {
								sc_sum.details(FileList.get(j).file, FileList2.get(i).file);
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
				test_sc.test(obj_path,FileList);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		else if(file_2.isFile()) {
			long l_t=0, f_l=0;
			l_t = file_2.lastModified();
			f_l = file_2.length();
			while(true) {
				File file_3 = new File(obj_path);
				if(file_3.exists()) {
					if(file_3.lastModified()!=l_t && file_3.length()!=f_l) {
						//假装听了1s
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						times++;
						if(in_kind==1) {
							sc_sum.summary(file_2.getName(),times,"size_change");
						}
						else if(in_kind==2) {
							sc_sum.details(file_2, file_3);
						}
						break;
						   //表示文件规模发生变化
					}
				}
				//file_2 = new File(obj_path);
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
