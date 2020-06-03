package six;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class In_put {
	
	Scanner scanner = new Scanner(System.in);
	String str_in=null;
	public void input() {
		String path1 = null;
		String path2 = null;
		
		str_in = scanner.nextLine();
		while(!str_in.equals("END")) {
			String[] StrArray = str_in.split(" ");
			if(StrArray.length<5) {
				System.out.println("something wrong in input");
				str_in = scanner.nextLine();
				continue;
			}
			//StrArray[1] = "F:\\oo_code\\tey";
			File file = new File(StrArray[1]);
			if(file.isFile()) {
				path1 = file.getPath();
				path2 = file.getParent();
			}
			else if(file.isDirectory()) {
				path1 = StrArray[1];
				path2 = StrArray[1];
			}
			if(file.exists()) {
				
				if(StrArray[2].equals("renamed") && StrArray[4].equals("record-summary")) {
					Renamed Rename_try = new Renamed(path2,path1,1);
					new Thread(Rename_try).start();
				}
				else if(StrArray[2].equals("renamed") && StrArray[4].equals("record-detail")) {
					Renamed Rename_try = new Renamed(path2,path1,2);
					new Thread(Rename_try).start();
				}
				else if(StrArray[2].equals("renamed") && StrArray[4].equals("recover")) {
					Renamed Rename_try = new Renamed(path2,path1,3);
					new Thread(Rename_try).start();
				}
				else if(StrArray[2].equals("modified") && StrArray[4].equals("record-summary")) {
					Modified Rename_try = new Modified(path2,path1,1);
					new Thread(Rename_try).start();
				}
				else if(StrArray[2].equals("modified") && StrArray[4].equals("record-detail")) {
					Modified Rename_try = new Modified(path2,path1,2);
					new Thread(Rename_try).start();
				}
				else if(StrArray[2].equals("path_change") && StrArray[4].equals("record-summary")) {
					Path_changed Rename_try = new Path_changed(path2,path1,1);
					new Thread(Rename_try).start();
				}
				else if(StrArray[2].equals("path_change") && StrArray[4].equals("record-detail")) {
					Path_changed Rename_try = new Path_changed(path2,path1,2);
					new Thread(Rename_try).start();
				}
				else if(StrArray[2].equals("path_change") && StrArray[4].equals("recover")) {
					Path_changed Rename_try = new Path_changed(path2,path1,3);
					new Thread(Rename_try).start();
				}
				else if(StrArray[2].equals("size_change") && StrArray[4].equals("record-summary")) {
					Size_changed Renam_try = new Size_changed(path2,path1,1);
					new Thread(Renam_try).start();
				}
				else if(StrArray[2].equals("size_change") && StrArray[4].equals("record-detail")) {
					Size_changed Rename_try = new Size_changed(path2,path1,2);
					new Thread(Rename_try).start();
				}
				else {
					System.out.println("something wrong in input");
				}
			}
			
			else {
				if(!file.exists()) {
					try {    
				        file.createNewFile();  
				    } catch (IOException e) {      
				        e.printStackTrace();    
				    }    
				} //文件不
				continue;
			}
			str_in = scanner.nextLine();
		}
	}
	
}
