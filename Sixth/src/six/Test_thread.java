package six;

import java.util.Scanner;

class Test_thread implements Runnable {
	protected Test_file t_f = new Test_file();
	
	public void run() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String path_1=null, path_2=null;
		Scanner scanner = new Scanner(System.in);
		path_1 = "F:\\oo_code\\tey\\Elevator.java";
		path_2 = "F:\\oo_code\\tey\\try\\Elevator.java";
		if(t_f.move(path_1, path_2)) {
			//System.out.println("尝试正确");
		}
		//测试者完善代码的时候请把我写的例子删掉，谢谢
		//关于本线程的结束，可以定义标识符，我仅仅是给出例子，实际操作
		//后面的调用方法由测试者写，调用Test_file类中的方法实现对文件的操作
		scanner.close();
	}
}
