package oo_5;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class W_legal {
	protected String left = null;
	protected int [][] tem_num = new int[20][5];
	protected long [][] line = new long[1000][5];
	protected int [] tem = new int[5]; //表是临时的一个
	protected String str1 = "(\\(ER,#[123],(10|20|[1]?[1-9])\\))|(\\(FR,(10|20|[1]?[1-9]),(DOWN|UP)\\))";
	protected String str3 = "[0-9]+";
	protected int num1 = 0;
	protected int num2 = 0;
	protected double input_time = 0, first_time = 0;
	protected int count = 0;
	protected int sign = 0;
	protected int has_in = 0;
	protected String str_in = null;
	protected int end = 0;
	protected int many = 0;
	//时间顺序不对，层数不对，格式不对，不符合条件

	protected void travel() {
		String[] StrArray = str_in.split(";");
		add_in(StrArray,str1);
		sign=1;
		notifyAll();
	}
	
	public synchronized void in_put() {
		Scanner scanner = new Scanner(System.in);	
		while(true) {
			while(this.sign!=0) {   //表示队列不为空
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			str_in = scanner.nextLine();
			many++;
			str_in = str_in.replaceAll(" ","");
			if(has_in==0) {//读入第一条指令的时间
				this.first_time = (System.currentTimeMillis()/1000.0);
				has_in = 1;
			}
			if(str_in.equals("END") || many>=50) {
				end=1;
				sign=1;
				notifyAll();
				break;
			}
			travel();
		}
		scanner.close();
	}
	
	protected void add_in(String[] str_3, String str_1) {    //将匹配的指令加入到队列中去
		count = 0;
		int count_num = 0;
		for(count_num=0;count_num<str_3.length;count_num++) {
			this.input_time = (System.currentTimeMillis()/1000) - this.first_time;
			if(str_3[count_num].matches(str_1)) {
				if(this.judge(str_3[count_num])==1) {
					this.tem_num[count][0] = this.tem[0];   //0表示电梯内外
					this.tem_num[count][1] = this.tem[1];   //1表示楼层
					this.tem_num[count][2] = this.tem[2];   //2表示哪个电梯
					this.tem_num[count][3] = this.tem[3];   //3表示电梯请求的方向
					this.tem_num[count][4] = this.tem[4];
					
					count++;
					//System.out.println("加入调度   " + tem_num[0][1]);
					//System.out.println(input_time);
				}
			}
			else {
				System.out.printf("%d:INVALID[%s,%.1f]\n",System.currentTimeMillis(),str_3[count_num],input_time);
			}
		}
	}
	
	protected int judge(String str_4) {
		//if(str_4.equals("END")) {
			//return 0;
		//}
		Pattern p_1 = Pattern.compile(this.str3);
		Matcher m_1 = p_1.matcher(str_4);
		if(m_1.find()) {
			num1 = Integer.parseInt(m_1.group());
		}
		if(m_1.find()) {
			num2 = Integer.parseInt(m_1.group());
		}
		//找出两个数字，然后开始找字符串
		left = str_4.substring(1,3); 
		if(left.equals("ER")) {
			this.tem[0] = 1; //0表示在电梯内还是在电梯外
			this.tem[2] = num1;  //2表示哪个电梯
			this.tem[1] = num2;  //1表示楼层
			this.tem[3] = 3;
		}
		else {
			this.tem[0] = 2;
			this.tem[1] = num1;
			if(search_2(str_4,"UP")) {
				this.tem[3] = 1;   //3表示电梯请求的方向
			} 
			else if(search_2(str_4,"DOWN")) {
				this.tem[3] = 2;
			}
			else {
				this.tem[3] = 0;
			}
		}
		

		if((this.tem[1]==1 && this.tem[3]==2) || (this.tem[1]==20 && this.tem[3]==1))
		{
			System.out.printf("%d:INVALID[%s,%.1f]\n",System.currentTimeMillis(),str_4,input_time);  //表示10楼不能上，1楼不能下
			return 0;
		}
		return 1;
	}
	
	protected boolean search_2(String str_5, String str_6) {
		Pattern p_2 = Pattern.compile(str_6);
		Matcher m_2 = p_2.matcher(str_5);
		return m_2.find();
	}
	
	protected synchronized void clean() {
		int count_2 = 0;
		this.count = 0;
		//System.out.println(input_time);
		//this.input_time = 0;
		for(count_2=0;count_2<20;count_2++) {
			this.tem_num[count_2][0] = 0;   //0表示电梯内外
			this.tem_num[count_2][1] = 0;   //1表示楼层
			this.tem_num[count_2][2] = 0;   //2表示哪个电梯
			this.tem_num[count_2][3] = 0;   //3表示电梯请求的方向
			this.tem_num[count_2][4] = 0;
		}
		
		this.sign = 0;
		notifyAll();
	}
	
	protected synchronized void release() {
		while(this.sign==0) {   //表示队列为空
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}



