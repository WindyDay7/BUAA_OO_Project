package six;

import java.io.File;
import java.util.ArrayList;

class Test {
	
	protected void test(String fileDir, ArrayList<File_all> fileList) {
		// = new ArrayList<File_all>();
		File file = new File(fileDir);
		
		File[] files = file.listFiles();// 获取目录下的所有文件或文件夹
		if (files == null) {// 如果目录为空，直接退出
			return ;
		}
		// 遍历，目录下的所有文件，将文件属性存起来
		for (File f : files) {
			File_all tem_file = new File_all();
			if (f.isFile()) {
				tem_file.file = f;
				tem_file.name = f.getName();
				tem_file.f_l = f.length();
				tem_file.l_t = f.lastModified();
				fileList.add(tem_file);
			} else if (f.isDirectory()) {
				test(f.getAbsolutePath(),fileList);
			}
		}
		return ;
	}
}


