package code;

import java.util.Scanner;

public class Run {
	
	protected static boolean search(String str_out, String str_in) {
		return str_out.matches(str_in);
	}
	
	public static void main(String[] args) {
		W_legal legal_1 = new W_legal();  //构造一个判断是否合法的类
		//Scheduler sche = new Scheduler();
		Override drive = new Override();  //创建调度的对象
		Elevator elev = new Elevator();
		Queue qun = new Queue();

		
		Scanner scanner = new Scanner(System.in);
		String input = null;
		String str2 = null;
		int count_num = 0;
		str2 = "RUN";//判断的正则表达式
		
		input = scanner.nextLine();
		input = input.replaceAll(" ","");
		count_num++;   //输入第一条指令，count_num = 1
		
		while(!search(input,str2)) {
			qun.produce(input);
			input = scanner.nextLine();
			input = input.replaceAll(" ","");
			count_num++;
			if(count_num == 101 && !search(input,str2))
				System.exit(1);
		}
		legal_1.in_put = qun.num1;
		legal_1.travel();
		drive.count_num = legal_1.count;
		drive.request_n = legal_1.num;
		
		while(drive.request_n[drive.i][0]!=0) {
			drive.on_way = elev.in_way;
			drive.occupy = elev.button;
			drive.station = elev.state_in;
			drive.stop_time = elev.st_time;
			drive.deal_t();
			elev.in_way = drive.on_way;
			elev.button = drive.occupy;
			elev.move = drive.pr_instr;
			elev.change();
		}	
			scanner.close();
	}
}
