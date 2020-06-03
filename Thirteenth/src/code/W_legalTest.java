package code;

import static org.junit.Assert.*;

import org.junit.Test;

public class W_legalTest {

private W_legal legal_test = new W_legal();

	
	@Test
	public void Test_travel() { 
		this.legal_test.in_put[0] = "re";
		this.legal_test.in_put[1] = "(FR,1,UP,0)"; 
		this.legal_test.in_put[2] = "v(FR,1,UP,0)";
		this.legal_test.in_put[3] = "(FR,4,DOWN,3)";
		this.legal_test.in_put[4] = "(FR,7,DOWN,5)";
		this.legal_test.in_put[5] = "(FR,1,DOWN,5)"; 
		this.legal_test.travel();
	}

	@Test
	public void Test_travel_2() { 
		this.legal_test.in_put[0] = "re";
		this.legal_test.in_put[1] = "435"; 
		this.legal_test.in_put[2] = "v(FR,1,UP,0)";
		this.legal_test.in_put[3] = "rer3)";
		this.legal_test.in_put[4] = "(FReteOWN,5)";
		this.legal_test.in_put[5] = "(FyrhDOWN,5)"; 
		this.legal_test.travel();
	}
	
	@Test
	public void Test_travel_3() { 
		this.legal_test.in_put[0] = "(FR,1,UP,0)"; 
		for(int i=1; i< 110; i++){
			this.legal_test.in_put[i] = "(FR,7,DOWN,5)"; 
		}
		this.legal_test.travel();
	}
	
	@Test
	public void Test_judge() {
	
		this.legal_test.judge("(FR,1,UP,4294967298)","[0-9]+");
		this.legal_test.judge("(FR,4294967298,UP,4)","[0-9]+");
		this.legal_test.judge("(FR,1,UP,429496729878786756455467587)","[0-9]+"); 
		this.legal_test.judge("(FR,3,UP,4)","[0-9]+");
		this.legal_test.judge("(FR,4,DOWN,5)","[0-9]+");
		this.legal_test.judge("(FR,10,UP,6)","[0-9]+"); 
		this.legal_test.judge("(FR,1,DOWN,7)","[0-9]+"); 
		this.legal_test.judge("(FR,10,UP,8)","[0-9]+");
		this.legal_test.judge("(ER,10,11)","[0-9]+"); 
		this.legal_test.count = 3;
		this.legal_test.num[2][1] = 5;
		this.legal_test.judge("(FR,10,DOWN,3)","[0-9]+"); 

	}
	
	@Test
	public void Test_search_2() {
		this.legal_test.search_2("(FR,1,DOWN,7)","FR");
	} 
	 
	@Test
	public void Test_repOK() {
		this.legal_test.input_r = "FR";
		this.legal_test.repOK();
		this.legal_test.left = "FR"; 
		this.legal_test.repOK();
		this.legal_test.in_put[0] = "(FR,10,UP,8)";
		this.legal_test.left = null; 
		this.legal_test.repOK();
		this.legal_test.input_r = null;
		this.legal_test.repOK();
		this.legal_test.input_r = "FR";
		this.legal_test.left = "fe";

		this.legal_test.tem[0] = -1;
		this.legal_test.repOK();
		this.legal_test.tem[0] = 1;
		this.legal_test.num[0][1] = -3;
		this.legal_test.repOK();
		this.legal_test.num[0][1] = 3;

		this.legal_test.num1 = -1;
		this.legal_test.repOK();
		this.legal_test.num1 = 3;
		this.legal_test.max = -1;
		this.legal_test.repOK();
		this.legal_test.max = 2;
		this.legal_test.count = -4;
		this.legal_test.repOK();
		this.legal_test.count = 5;
		this.legal_test.count_n = -5;
		this.legal_test.repOK();
		this.legal_test.count_n = 5;
		this.legal_test.num1 = 1;
		this.legal_test.repOK();
	}

}
