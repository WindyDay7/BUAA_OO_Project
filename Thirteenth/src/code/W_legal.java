package code;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class W_legal {
	/**Overview
	*get the inputs and judge whether the inputs is the right format and pass the wrong inputs
	*get the right inputs and construct the requests Lines.
	*/
	String input_r;
	protected String left = null;
	protected String[] in_put = new String[1000];
	protected long [][] num = new long[1000][5];
	protected long [] tem = new long[5]; //表是临时的一个
	protected String str1 = "(\\(FR,([+]?)(([0]{1,})?)(([0-9])|10),(DOWN|UP),[+]?[0-9]{1,10}\\))|(\\(ER,[+]?(([0]{1,})?)(([0-9])|10),[+]?[0-9]{1,10}\\))";
	protected String str3 = "[0-9]+";
	protected String str4 = "\\(FR,1,UP,0\\)";
	protected long num1 = 0;
	protected long num2 = 0;
	protected long max = 0;
	protected int count = 0;
	protected int count_n = 0;
	//时间顺序不对，层数不对，格式不对，不符合条件
	protected void travel() {
		/*
		Requires:none
		Modifies:this.num , count ,count_n
		Effects: (\all input[count_n].matches(str4) || input[count_n].matches(str1) ; input[] ==> output("Something Wrong"), count_n++)
			   : (\all judge(input[count_n].matches(str3)) : input[] ==> this.num=this.tem
		*/
		while(this.in_put[this.count_n]!=null && !(this.in_put[this.count_n].matches(str4))) {
			System.out.printf("INVALID");
			System.out.printf("[%s]\n",this.in_put[this.count_n]);
			this.count_n++;
		}
		while(this.in_put[this.count_n]!=null && this.count_n<=100) {
			if(!(this.in_put[this.count_n].matches(str1))) {
				System.out.printf("INVALID");
				System.out.printf("[%s]\n",this.in_put[this.count]);
				this.count_n++;
			}
			else {
				if(this.judge(this.in_put[this.count_n],str3)==1) {   //请求和数字，一个请求和数字
					this.num[this.count][0] = this.tem[0];
					this.num[this.count][1] = this.tem[1];
					this.num[this.count][2] = this.tem[2];
					this.num[this.count][3] = this.tem[3];
					this.num[this.count][4] = this.tem[4];
					this.count++;
					this.count_n++;
				}
				else {
					this.count_n++;  //未执行
				}
			}
		}
	}
	
	protected int judge(String str_3, String str_4) {  //请求和数字
		/*
		Requires:str_3, str_4
		Modifies:this.tem
		Effects: m_1.find() && (num2> long.size() || num2>max) ==> exceptional_behavior("INVALID") ;  
			   *: tem[0] = num1 && tem[1] = num2
			   *:left.equals("FR") ==> tem[2]=1  left!="FR"==>tem[2]=2
			   *:search_2(str)3,"UP")==>tem[3]=1  search_2(str_3,"DOWN")==>tem[3]=2  
			   *:!search_2(str)3,"UP") && !search_2(str_3,"DOWN")==>tem[3]=0
			   *:(this.tem[0]==1 && this.tem[3]==2) || (this.tem[0]==10 && this.tem[3]==1) ==> output "INVALID"
			   *:(this.count >= 1 && this.tem[1] < this.num[this.count-1][1]) ==> output "INVALID"
		*/
		max = Long.parseLong("4294967295");   //4294967295
		Pattern p_1 = Pattern.compile(str_4);   //短的，数字
		Matcher m_1 = p_1.matcher(str_3);    //长的
		if(m_1.find()) {
			num1 = Long.parseLong(m_1.group());
		}
				if(m_1.find()) {
					try {
						num2 = Long.parseLong(m_1.group());
					}
					catch(Exception e) {
						System.out.printf("INVALID");
						System.out.printf("[%s]\n",this.in_put[this.count]);
						return 0;
					}
					if(num2 > max) {
						System.out.printf("INVALID");
						System.out.printf("[%s]\n",this.in_put[this.count]);
						return 0;
					}
				}
				left = str_3.substring(1,3);
				this.tem[0] = num1;  //[0]表示楼层
				this.tem[1] = num2;  //[1]表示时间
				if(left.equals("FR")) {
					this.tem[2] = 1; //*表示在电梯外，也就是楼层接收的信号
				}
				else {
					this.tem[2] = 2;  //2表示电梯内
				}
				if(search_2(str_3,"UP")) {
					this.tem[3] = 1; //1表示上，2表示下
				}
				else if(search_2(str_3,"DOWN")){
					this.tem[3] = 2;  //3表示上下，其中2表示下，0表示的是电梯内，没有
				}
				else {
					this.tem[3] = 0; 
				}
				if((this.tem[0]==1 && this.tem[3]==2) || (this.tem[0]==10 && this.tem[3]==1))
				{
					System.out.printf("INVALID");
					System.out.printf("[%s]\n",this.in_put[this.count]);   //表示10楼不能上，1楼不能下
					return 0; 
				}
				if(this.count >= 1 && this.tem[1] < this.num[this.count-1][1]) { //表示时间不符合顺序
					System.out.printf("INVALID");
					System.out.printf("[%s]\n",this.in_put[this.count]);   
					return 0; 
				}
			
		return 1;
	}
	

	protected boolean search_2(String str_5, String str_6) {  //前面长后面短
		/**
		Requires:str_5, str_6
		Modifies:p_2, m_2
		Effects: \exist str_5 ;str6==> return m_2.find() 
		*/
		Pattern p_2 = Pattern.compile(str_6);
		Matcher m_2 = p_2.matcher(str_5);
		return m_2.find();
	}

	public boolean repOK() {
		if(this.input_r==null || this.in_put==null || this.left==null)  //
			return false;
		if(this.tem[0]<0 || this.num[0][0]<0) 
			return false;
		if(this.num2<0 || this.num1<0 || this.max<0 || this.count_n<0 || this.count<0)
			return false;
		return true;
	}
	
}
/*
 * 0表示楼层
 * 1表示时间
 * 2表示电梯内外
 * 3表示上或者下  [1,0,1,1,0]
 */


