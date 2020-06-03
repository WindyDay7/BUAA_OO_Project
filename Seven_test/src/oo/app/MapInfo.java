package oo.app;

import java.io.File;
import java.util.Scanner;

public class MapInfo{
	int[][] map = new int[80][80];
	public void readMap(String path){
		Scanner scan = null;
		File file = new File(path);
		int i,j;
		
		if(file.exists()==false){
			this.printError();
			return;
		}
		
		try{
			scan=new Scanner(new File(path));
		}catch(Exception e){
		}
		
		for(i=0;i<80;i++){
			String[] strArray = null;
			try{
				strArray=scan.nextLine().replace("/s","").split("");
				if(strArray.length!=80){
					this.printError();
					return;
				}
			}catch(Exception e) {
				this.printError();
			}
			for(j=0;j<80;j++) {
				try {
					this.map[i][j]=Integer.parseInt(strArray[j]);
					if((this.map[i][j]>3||this.map[i][j]<0)||
						(i!=79&&j==79&&(this.map[i][j]!=2&&this.map[i][j]!=0))||
						(i==79&&j!=79&&(this.map[i][j]!=1&&this.map[i][j]!=0))||
						(i==79&&j==79&&(this.map[i][j]!=0)))
						this.printError();
				}catch(Exception e) {
					this.printError();
				}
			}
		}
		scan.close();
	}
	
	public void printError() {
		System.out.println("The map is invalid,and program exits.");
		System.exit(1);
		return;
	}
}
