package six;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

class Test_file {
	
	public boolean addFile(String file_name) {
		File file = new File(file_name);
		if(!file.exists()) {
			try {    
		        file.createNewFile();    
		    } catch (IOException e) {      
		        e.printStackTrace();    
		    } 
			return true;
		}else {
			return false;
		}
	}
	
	public boolean rename(String file_old, String file_new) {
		File file = new File(file_old);
		File file_2 = new File(file_new);
		if(file.isDirectory() || file_2.exists()) {
			return false;
		}
		else {
			file.renameTo(file_2);
			return true;
		}
	}
	
	public boolean move(String file_old, String file_new) {
		File file = new File(file_old);
		File file_2 = new File(file_new);
		if(file.getParent().equals(file_2.getParent()) || file_2.exists()) {
			return false;     //同一目录下不算是移动文件
		}
		else {
			file.renameTo(file_2);  //将file移动到file_2的地方，
			return true;
		}
		
	}
	
	public boolean changeSize(String file_old) {
		File file = new File(file_old);
		if(!file.exists()) {
			return false;
		}
		else {
			PrintStream ps3 = null;
			try {
				ps3 = new PrintStream(file.getPath());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			System.setOut(ps3);// 设置使用新的输出流
			System.out.println("修改了文件大小，此方法太蠢，大概只有我会用吧");
			return true;
		}
	}
	
	public boolean changeTime(String file_old) {
		File file = new File(file_old);
		if(!file.exists()) {
			return false;
		}
		else {
			file.setLastModified(System.currentTimeMillis());  //修改文件最后修改时间位当前时间
			return true;
		}
	}
	
	public boolean casetest() {
		if(!addFile("D:\\OO\\a.txt")) {
			return false;
		}
		if(!rename("D:\\OO\\b1.txt","D:\\OO\\b2.txt")) {
			return false;
		}
		return true;
	}
}
