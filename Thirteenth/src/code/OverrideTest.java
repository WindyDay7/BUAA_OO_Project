package code;

import static org.junit.Assert.*;

import org.junit.Test;

public class OverrideTest {
	private Override Override_test = new Override(); 

	@Test
	public void Test_deal_t() { 
		this.Override_test.on_way[0][0][0] = 0;   
		this.Override_test.station[1] = 2;
		this.Override_test.i = 0;
		this.Override_test.request_n[0][4] = 3; 
		long[] a={1,0,1,1,0};
		this.Override_test.request_n[1] = a;  
		this.Override_test.deal_t(); //测到47行
		this.Override_test.on_way[3][0][0] = 1;
		this.Override_test.on_way[0][0][0] = 0;
		this.Override_test.deal_t(); //arrive 54 lines  
		this.Override_test.station[1] = 1;
		this.Override_test.on_way[3][0][0] = 0;  
		this.Override_test.i = 0;
		this.Override_test.on_way[0][0][0] = 0;
		this.Override_test.deal_t(); 
		this.Override_test.on_way[3][0][0] = 1; 
		this.Override_test.on_way[0][0][0] = 0; 
		this.Override_test.deal_t();
		this.Override_test.i = 0;
		this.Override_test.station[1] = 0;   //最后一种情况
		this.Override_test.request_n[0][4] = 2;
		this.Override_test.deal_t();
		this.Override_test.on_way[0][0][0] = 0;
		this.Override_test.i = 0;
		long[] a1={1,0,1,2,0};
		this.Override_test.request_n[2] = a1;
		this.Override_test.deal_t();
		this.Override_test.on_way[0][0][0] = 0;    
		this.Override_test.i = 0;
		long[] a2={1,10,2,3,0};
		this.Override_test.request_n[3] = a2;  
		this.Override_test.stop_time = 7;
		this.Override_test.deal_t();
		this.Override_test.on_way[0][0][0] = 0;
	}

	@Test
	public void Test_deal_t_1() { 
		this.Override_test.i = 0;
		this.Override_test.count_num = 8;
		this.Override_test.on_way[0][0][0] = 2; 
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 1;
		this.Override_test.stop_time = 1;

		long[] b1 = {7,1,1,1,0};
		long[] b2 = {7,3,1,1,0};
		long[] b3 = {6,2,2,0,0};
		long[] b4 = {6,2,1,1,0};
		long[] b5 = {6,3,2,0,0};
		long[] b6 = {6,3,1,1,0};  
		long[] b7 = {9,4,2,0,0};
		long[] b8 = {10,4,1,2,0};

		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;
		this.Override_test.request_n[1] = b3;
		this.Override_test.request_n[2] = b4;  
		this.Override_test.request_n[3] = b5;  
		this.Override_test.request_n[4] = b6; 
		this.Override_test.request_n[5] = b7;  
		this.Override_test.request_n[6] = b8; 
		//this.Override_test.request_n[3] = b4;
		this.Override_test.deal_t();
	}

	@Test
	public void Test_deal_t_3() { 
		this.Override_test.i = 0;
		this.Override_test.count_num = 8;
		this.Override_test.on_way[0][0][0] = 2; 
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 1;
		this.Override_test.stop_time = 1;

		long[] b1 = {7,1,1,1,0};
		long[] b2 = {7,3,1,1,0};
		long[] b3 = {6,2,1,1,0};
		long[] b4 = {6,2,2,0,0};
		long[] b5 = {6,3,1,1,0};
		long[] b6 = {7,3,1,1,0};  
		long[] b7 = {6,3,2,0,0};  

		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;
		this.Override_test.request_n[1] = b3;
		this.Override_test.request_n[2] = b4;
		this.Override_test.request_n[3] = b5;  
		this.Override_test.request_n[4] = b6;  
		this.Override_test.request_n[5] = b7;  
		//this.Override_test.request_n[3] = b4;
		this.Override_test.deal_t();
	}

	@Test
	public void Test_deal_t_2() { 
		this.Override_test.i = 0;
		this.Override_test.count_num = 7;
		this.Override_test.on_way[0][0][0] = 2; 
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 10;
		this.Override_test.stop_time = 1;

		long[] b1 = {1,1,2,0,0};  //ER,1,1
		long[] b2 = {2,2,1,2,0};
		long[] b3 = {2,2,2,0,0};
		long[] b4 = {2,3,1,2,0};
		long[] b5 = {2,3,2,0,0};
		long[] b6 = {3,4,2,0,0};  

		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;
		this.Override_test.request_n[1] = b3;
		this.Override_test.request_n[2] = b4;  
		this.Override_test.request_n[3] = b5;  
		this.Override_test.request_n[4] = b6;    
		//this.Override_test.request_n[3] = b4;
		this.Override_test.deal_t();
	}

	@Test
	public void Test_deal_t_4() { 
		this.Override_test.i = 0;
		this.Override_test.count_num = 8;
		this.Override_test.on_way[0][0][0] = 2; 
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 10;
		this.Override_test.stop_time = 1;  

		long[] b1 = {1,1,2,0,0};  //ER,1,1
		long[] b2 = {2,2,2,0,0};
		long[] b3 = {2,2,1,2,0};
		long[] b4 = {2,3,2,0,0};
		long[] b5 = {2,3,1,2,0};
		long[] b6 = {1,3,2,0,0}; 
		long[] b7 = {1,4,2,0,0}; 

		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;  
		this.Override_test.request_n[1] = b3;
		this.Override_test.request_n[2] = b4;  
		this.Override_test.request_n[3] = b5;    
		this.Override_test.request_n[4] = b6;  
		this.Override_test.request_n[5] = b7; 
		//this.Override_test.request_n[3] = b4;
		this.Override_test.deal_t();
	}

	@Test
	public void Test_repOK() { 
		this.Override_test.on_way[0][0][0] = -1;
		this.Override_test.repOK();
		this.Override_test.on_way[0][0][0] = 1;
		this.Override_test.repOK();
		this.Override_test.station[0] = -1;
		this.Override_test.repOK();
		this.Override_test.station[0] = 1;
		this.Override_test.repOK();
		this.Override_test.pr_instr[0] = -1;
		this.Override_test.repOK();
		this.Override_test.pr_instr[0] = 1;
		this.Override_test.on_way[0][0][0] = 2;
		this.Override_test.old_instr[0] = 2;
		this.Override_test.occupy[0][0] = 2;
		this.Override_test.station[0] = 3;
		this.Override_test.count_num = -1;
		this.Override_test.repOK();
		this.Override_test.count_num = 1;
		this.Override_test.request_n[0][0] = -1;
		this.Override_test.repOK();
		this.Override_test.request_n[0][0] = 1;  
		this.Override_test.repOK();
	}

	@Test
	public void Test_others() { 
		this.Override_test.i = 0;
		this.Override_test.count_num = 4;
		this.Override_test.on_way[0][0][0] = 2;
		this.Override_test.on_way[1][0][0] = 2;
		this.Override_test.on_way[6][0][0] = 2;
		this.Override_test.on_way[6][0][4] = 8;
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 1;
		this.Override_test.stop_time = 1;
		long[] b1 = {7,1,1,1,0};
		long[] b2 = {5,2,2,0,0};
		long[] b3 = {6,2,2,0,0};

		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;
		this.Override_test.request_n[1] = b3;
		this.Override_test.deal_t();
	}

	@Test
	public void Test_others_2() { 
		this.Override_test.i = 0;
		this.Override_test.count_num = 4;
		this.Override_test.on_way[0][0][0] = 2;
		this.Override_test.on_way[1][0][0] = 2;
		this.Override_test.on_way[6][0][0] = 2;
		this.Override_test.on_way[6][0][4] = 8;
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 1;
		this.Override_test.stop_time = 1;
		long[] b1 = {7,1,1,1,0};
		long[] b2 = {5,2,2,0,0};
		long[] b3 = {6,2,1,1,0};

		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;
		this.Override_test.request_n[1] = b3;
		this.Override_test.deal_t();
	}

	@Test
	public void Test_others_3() { 
		this.Override_test.i = 0;
		this.Override_test.count_num = 4;
		this.Override_test.on_way[0][0][0] = 2;
		this.Override_test.on_way[10][0][0] = 2;
		this.Override_test.on_way[2][0][0] = 2;
		this.Override_test.on_way[2][0][4] = 8;
		this.Override_test.on_way[3][0][0] = 2;
		this.Override_test.on_way[3][0][4] = 8;
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 10;
		this.Override_test.stop_time = 1;
		long[] b1 = {1,1,1,1,0};
		long[] b2 = {3,2,2,0,0};
		long[] b3 = {2,3,1,2,0};

		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;
		this.Override_test.request_n[1] = b3;
		this.Override_test.deal_t();
	}

	@Test
	public void Test_others_4() { 
		this.Override_test.i = 0;
		this.Override_test.count_num = 4;
		this.Override_test.on_way[0][0][0] = 2;
		this.Override_test.on_way[10][0][0] = 2;
		this.Override_test.on_way[3][0][0] = 2;
		this.Override_test.on_way[3][0][4] = 8;
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 10;
		this.Override_test.stop_time = 1;
		long[] b1 = {1,1,1,1,0};
		long[] b2 = {2,2,2,0,0};
		long[] b3 = {3,2,1,2,0};

		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;
		this.Override_test.request_n[1] = b3;
		this.Override_test.deal_t();
	}

	@Test
	public void Test_others_5() { 
		this.Override_test.i = 7;
		this.Override_test.count_num = 4;
		long[] b1 = {1,1,1,1,0};
		this.Override_test.pr_instr = b1;
		this.Override_test.station[0] = 10;
		this.Override_test.stop_time = 1;
	}

	@Test
	public void Test_others_6() { 
		this.Override_test.i = 0;
		this.Override_test.count_num = 15;
		this.Override_test.on_way[0][0][0] = 2; 
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 10;
		this.Override_test.stop_time = 1;

		long[] b1 = {3,1,2,0,0};  //ER,1,1
		long[] b2 = {2,2,1,2,0};
		long[] b3 = {1,3,2,0,0};
		long[] b4 = {3,2,1,2,0};
		long[] b5 = {3,2,1,1,0};
		long[] b6 = {4,2,1,2,0};
		long[] b7 = {4,2,2,0,0};
		long[] b8 = {4,3,1,2,0};
		long[] b9 = {4,3,2,0,0};
		long[] b10 = {6,4,2,0,0}; 
		long[] b11 = {7,4,2,0,0};
		long[] b12 = {7,5,1,2,0};
		long[] b13 = {6,4,2,0,0};
		long[] b14 = {6,4,1,2,0};  
		long[] b15 = {6,14,1,2,0};  


		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;
		this.Override_test.request_n[1] = b3;
		this.Override_test.request_n[2] = b4;  
		this.Override_test.request_n[3] = b5;  
		this.Override_test.request_n[4] = b6;    
		this.Override_test.request_n[5] = b7;
		this.Override_test.request_n[6] = b8;
		this.Override_test.request_n[7] = b9;  
		this.Override_test.request_n[8] = b10; 
		this.Override_test.request_n[9] = b11; 
		this.Override_test.request_n[10] = b12;
		this.Override_test.request_n[11] = b13;  
		this.Override_test.request_n[12] = b14; 
		this.Override_test.request_n[13] = b15; 
		//this.Override_test.request_n[3] = b4;
		this.Override_test.deal_t();
	}

	@Test
	public void Test_deal_t_7() { 
		this.Override_test.i = 0;
		this.Override_test.count_num = 16;
		this.Override_test.on_way[0][0][0] = 2; 
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 1;
		this.Override_test.stop_time = 1;

		long[] b1 = {10,1,1,1,0};
		long[] b2 = {1,1,1,1,0}; 
		long[] b3 = {1,2,1,1,0}; 
		long[] b4 = {7,3,1,1,0};
		long[] b5 = {6,2,1,1,0};
		long[] b6 = {6,2,2,0,0};
		long[] b7 = {6,3,1,1,0};
		long[] b8 = {7,3,1,1,0};  
		long[] b9 = {6,3,2,0,0}; 
		long[] b10 = {5,3,1,2,0};  
		long[] b11 = {4,3,1,1,0};  
		long[] b12 = {9,4,5,1,0};
		long[] b13 = {10,5,2,0,0};  
		long[] b14 = {10,5,1,2,0};
		long[] b15 = {10,5,1,2,0};
		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;
		this.Override_test.request_n[1] = b3;
		this.Override_test.request_n[2] = b4;
		this.Override_test.request_n[3] = b5;  
		this.Override_test.request_n[4] = b6;  
		this.Override_test.request_n[5] = b7;  
		this.Override_test.request_n[6] = b8;
		this.Override_test.request_n[7] = b9;
		this.Override_test.request_n[8] = b10;
		this.Override_test.request_n[9] = b11;  
		this.Override_test.request_n[10] = b12;
		this.Override_test.request_n[11] = b13;
		this.Override_test.request_n[12] = b14;
		this.Override_test.request_n[13] = b15;  
		//this.Override_test.request_n[3] = b4;
		this.Override_test.deal_t();
	}

	@Test
	public void Test_bug1() {
		/*bug原本输入
		(FR,1,UP,0)
		(FR,7,UP,0)
		(FR,4,DOWN,1)
		(ER,10,4)
		RUN
		*/
		this.Override_test.i = 0;
		this.Override_test.count_num = 4;
		this.Override_test.on_way[0][0][0] = 2; 
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 1;
		this.Override_test.stop_time = 0;
		long[] b1 = {7,0,1,1,0};  //(FR,7,UP,0)
		long[] b2 = {4,1,1,2,0};  //(FR,4,DOWN,1)
		long[] b3 = {10,4,2,0,0};  //(ER,10,4)
		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;
		this.Override_test.request_n[1] = b3;
		this.Override_test.deal_t();
		System.out.println(this.Override_test.on_way[10][0][0]);   //之前的bug是由于10楼错误的捎带了，即(ER,10,4)请求进入了捎带队列
		//针对测试，构造相同的请求序列，(ER,10,4)请求不在捎带队列中了，所以bug被修复
	}

	@Test
	public void Test_bug2() {
		/*bug原本输入
		(FR,1,UP,0)
		(FR,8,UP,0)
		(FR,4,UP,2)
		(FR,7,UP,3)
		(FR,7,UP,4)
		RUN
		*/
		this.Override_test.i = 0;
		this.Override_test.count_num = 6;
		this.Override_test.on_way[0][0][0] = 2; 
		//this.Override_test.on_way[6][0][0] = 1;
		this.Override_test.station[0] = 1;
		this.Override_test.stop_time = 0;
		long[] b1 = {8,0,1,1,0};  //(FR,8,UP,0)
		long[] b2 = {4,2,1,1,0};  //(FR,4,UP,2)
		long[] b3 = {7,3,1,1,0};  //(FR,7,UP,3)
		long[] b4 = {7,4,1,1,0};   //(FR,7,UP,4)
		this.Override_test.pr_instr = b1;
		this.Override_test.request_n[0] = b2;
		this.Override_test.request_n[1] = b3;
		this.Override_test.request_n[2] = b4;
		this.Override_test.deal_t();
		//之前的bug是由于同质请求没有判断对，这个bug之后就de出来了
		//测试类的运行结果可以看到输出中的同质判断
	}

}
