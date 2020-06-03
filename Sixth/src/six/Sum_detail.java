package six;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

class Sum_detail {
		
	public void summary(String object, int times, String kind) {
		PrintStream ps1 = null;
		try {
			ps1 = new PrintStream("F:/summary.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(ps1);// 设置使用新的输出流，如果想在控制台看输出，注释此语句
		System.out.println(object + " " + times + " " + kind);
		
	}
	
	public void details(File file_old, File file_new) {

		PrintStream ps2 = null;
		try {
			ps2 = new PrintStream("F:/details.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(ps2);// 设置使用新的输出流,如果想在控制台看输出则可以注释掉此语句
		System.out.printf("更改之前的文件规模%d  更改之后的文件规模%d\n更改文件之前的名称%s  更改文件之后的名称%s\n更改文件之前的路径%s  更改文件之后的路径%s\n更改文件之前的最后修改时间%d  更改文件之后的最后修改时间%d\n", file_old.length(), file_new.length(), file_old.getName(), file_new.getName(), file_old.getPath(), file_new.getPath(), file_old.lastModified(), file_new.lastModified());
		
	}
}
