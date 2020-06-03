package oo6;
//在该类的RUN方法里使用README中提到的方法编写测试线程
public class Test extends Thread{
	
	public void run() {
		try{
			sleep(1000);
		}
		catch(Exception e) {
			
		}
		
		for(int i=242;i<245;i++) {
			new SafeFile("C:/test/"+i+".txt").rename("C:/test/"+(i+10)+".txt");
		}
		try{
			sleep(200);
			
		}
		catch(Exception e) {
			
		}
		
	}
	
}
