package six;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class Modified implements Runnable {
	private String path;
	private String obj_path;
	protected int in_kind, times;
	protected Sum_detail mf_sum = new Sum_detail();
	protected Test test_mo = new Test();
	public Modified(String path, String obj_path, int in_kind) {
		this.path = path;
		this.obj_path = obj_path;
		this.in_kind = in_kind;
	}
	
	public void run() {
		File file_2 = new File(obj_path);
		int chose = 0;
		if(file_2.isDirectory()) {
			ArrayList<File_all> FileList = new ArrayList<File_all>();
			test_mo.test(path,FileList);
			while(true) {
				chose = 0;
				ArrayList<File_all> FileList2 = new ArrayList<File_all>();
				test_mo.test(path,FileList2);
				for(int i=0; i<FileList2.size();i++) {
					for(int j=0;j<FileList.size();j++) {
						if(FileList2.get(i).l_t!=FileList.get(j).l_t && FileList2.get(i).name.equals(FileList.get(j).name)) {	
							//假装操作需要1s
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							times++;
							if(in_kind==1) {
								mf_sum.summary(FileList.get(j).name,times,"Modify");
							}
							else if(in_kind==2) {
								mf_sum.details(FileList.get(j).file, FileList2.get(i).file);
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
				test_mo.test(path,FileList);
				try {
					Thread.sleep(1000);  
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		else if(file_2.isFile()){
			File file;
			long l_t=0;
			l_t = file_2.lastModified();
			while(true) {
				file = new File(obj_path);
				if(file.lastModified()!=l_t) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					times++;
					if(in_kind==1) {
						mf_sum.summary(file_2.getName(),times,"Modify");
					}
					else if(in_kind==2) {
						mf_sum.details(file_2, file);
					}
					break;    //最后修改时间不同，线程结束
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

