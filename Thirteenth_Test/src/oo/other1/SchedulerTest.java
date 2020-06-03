package oo.other1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SchedulerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Scheduler scheduler = new Scheduler();
//		
		
		Scheduler.count = 47;
		Scheduler.requestlist[0] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[1] = new Request("(ER,2,0)");
		Scheduler.requestlist[2] = new Request("(FR,3,DOWN,2)");
		Scheduler.requestlist[3] = new Request("(FR,5,DOWN,4)");
		Scheduler.requestlist[4] = new Request("(ER,9,4)");
		Scheduler.requestlist[5] = new Request("(ER,10,4)");
		Scheduler.requestlist[6] = new Request("(FR,9,DOWN,5)");
		Scheduler.requestlist[7] = new Request("(FR,9,UP,6)");
		Scheduler.requestlist[8] = new Request("(ER,6,7)");
		Scheduler.requestlist[9] = new Request("(FR,8,UP,7)");
		Scheduler.requestlist[10] = new Request("(FR,2,DOWN,8)");
		Scheduler.requestlist[11] = new Request("(FR,10,DOWN,8)");
		Scheduler.requestlist[12] = new Request("(FR,7,DOWN,10)");
		Scheduler.requestlist[13] = new Request("(ER,10,10)");
		Scheduler.requestlist[14] = new Request("(FR,7,DOWN,12)");
		Scheduler.requestlist[15] = new Request("(FR,4,DOWN,13)");
		Scheduler.requestlist[16] = new Request("(ER,4,15)");
		Scheduler.requestlist[17] = new Request("(ER,4,17)");
		Scheduler.requestlist[18] = new Request("(ER,5,18)");
		Scheduler.requestlist[19] = new Request("(FR,3,UP,19)");
		Scheduler.requestlist[20] = new Request("(FR,4,DOWN,20)");
		Scheduler.requestlist[21] = new Request("(FR,1,UP,20)");
		Scheduler.requestlist[22] = new Request("(ER,9,21)");
		Scheduler.requestlist[23] = new Request("(FR,3,UP,22)");
		Scheduler.requestlist[24] = new Request("(ER,9,25)");
		Scheduler.requestlist[25] = new Request("(ER,3,28)");
		Scheduler.requestlist[26] = new Request("(ER,5,29)");
		Scheduler.requestlist[27] = new Request("(FR,10,DOWN,30)");
		Scheduler.requestlist[28] = new Request("(ER,5,31)");
		Scheduler.requestlist[29] = new Request("(FR,5,UP,31)");
		Scheduler.requestlist[30] = new Request("(FR,2,UP,31)");
		Scheduler.requestlist[31] = new Request("(FR,8,DOWN,32)");
		Scheduler.requestlist[32] = new Request("(ER,7,34)");
		Scheduler.requestlist[33] = new Request("(FR,2,UP,35)");
		Scheduler.requestlist[34] = new Request("(ER,10,36)");
		Scheduler.requestlist[35] = new Request("(FR,7,UP,37)");
		Scheduler.requestlist[36] = new Request("(FR,6,UP,40)");
		Scheduler.requestlist[37] = new Request("(FR,8,DOWN,40)");
		Scheduler.requestlist[38] = new Request("(ER,5,41)");
		Scheduler.requestlist[39] = new Request("(ER,5,43)");
		Scheduler.requestlist[40] = new Request("(FR,1,UP,46)");
		Scheduler.requestlist[41] = new Request("(FR,9,UP,46)");
		Scheduler.requestlist[42] = new Request("(ER,3,48)");
		Scheduler.requestlist[43] = new Request("(ER,6,50)");
		Scheduler.requestlist[44] = new Request("(FR,2,UP,51)");
		Scheduler.requestlist[45] = new Request("(ER,6,51)");
		Scheduler.requestlist[46] = new Request("(ER,8,51)");
		scheduler.run();
		
	}
	
	@Test
	public void test1() {
		Scheduler scheduler = new Scheduler();
		Scheduler.count = 3;
		Scheduler.requestlist[0] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[1] = new Request("(ER,2,0)");
		Scheduler.requestlist[2] = new Request("(FR,2,DOWN,10)");
		
		scheduler.run();
	}
	
	@Test
	public void test2() {
		Scheduler scheduler = new Scheduler();
		Scheduler.count = 7;
		Scheduler.requestlist[0] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[1] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[2] = new Request("(FR,2,DOWN,0)");
		Scheduler.requestlist[3] = new Request("(FR,2,DOWN,0)");
		Scheduler.requestlist[4] = new Request("(FR,2,UP,0)");
		Scheduler.requestlist[5] = new Request("(FR,5,UP,1)");
		Scheduler.requestlist[6] = new Request("(FR,5,UP,2)");
		
		scheduler.run();
	}
	
	@Test
	public void test3() {
		Scheduler scheduler = new Scheduler();
		Scheduler.count = 4;
		Scheduler.requestlist[0] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[1] = new Request("(FR,9,DOWN,0)");
		Scheduler.requestlist[2] = new Request("(FR,7,DOWN,5)");
		Scheduler.requestlist[3] = new Request("(FR,7,DOWN,5)");
		
		scheduler.run();
	}
	
	@Test
	public void test4() {
		Scheduler scheduler = new Scheduler();
		Scheduler.count = 7;
		Scheduler.requestlist[0] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[1] = new Request("(FR,10,DOWN,0)");
		Scheduler.requestlist[2] = new Request("(FR,7,DOWN,5)");
		Scheduler.requestlist[3] = new Request("(ER,7,6)");
		
		
		Scheduler.requestlist[4] = new Request("(FR,5,DOWN,10)");
		Scheduler.requestlist[5] = new Request("(ER,4,10)");
		Scheduler.requestlist[6] = new Request("(ER,3,10)");
		
		
		scheduler.run();
	}
	
	@Test
	public void test5() {
		Scheduler scheduler = new Scheduler();
		Scheduler.count = 2;
		Scheduler.requestlist[0] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[1] = new Request("(FR,1,UP,1)");
		
		scheduler.run();
	}
	
	@Test
	public void test6() {
		Scheduler scheduler = new Scheduler();
		Scheduler.count = 4;
		Scheduler.requestlist[0] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[1] = new Request("(FR,2,UP,0)");
		Scheduler.requestlist[2] = new Request("(ER,3,0)");
		Scheduler.requestlist[3] = new Request("(ER,3,0)");
		
		scheduler.run();
	}
	
	@Test
	public void test7() {
		Scheduler scheduler = new Scheduler();
		Scheduler.count = 3;
		Scheduler.requestlist[0] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[1] = new Request("(FR,2,UP,0)");
		Scheduler.requestlist[2] = new Request("(FR,2,UP,0)");
		scheduler.run();
	}
	
	@Test
	public void test8() {
		Scheduler scheduler = new Scheduler();
		Scheduler.count = 3;
		Scheduler.requestlist[0] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[1] = new Request("(FR,2,UP,0)");
		Scheduler.requestlist[2] = new Request("(ER,2,0)");
		scheduler.run();
	}
	
	@Test
	public void test9() {
		Scheduler scheduler = new Scheduler();
		Scheduler.count = 4;
		Scheduler.requestlist[0] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[1] = new Request("(FR,4,DOWN,0)");
		Scheduler.requestlist[2] = new Request("(FR,3,DOWN,0)");
		Scheduler.requestlist[3] = new Request("(FR,3,DOWN,0)");
		scheduler.run();
	}
	
	@Test
	public void test_error() {
		Scheduler.is_error(new Request("hdjksahda"));
		Scheduler.count = 4;
		Scheduler.requestlist[0] = new Request("(FR,1,UP,0)");
		Scheduler.requestlist[1] = new Request("(FR,4,DOWN,0)");
		Scheduler.requestlist[2] = new Request("(FR,3,DOWN,1)");
		Scheduler.requestlist[3] = new Request("(FR,3,DOWN,0)");
		Scheduler.is_error(Scheduler.requestlist[0]);
		Scheduler.is_error(Scheduler.requestlist[2]);
		
		Scheduler.count = 0;
		Scheduler.is_error(new Request("(FR,1,UP,0)"));
		Scheduler.is_error(new Request("(ER,1,0)"));
		Scheduler.is_error(new Request("(FR,3,UP,0)"));
		Scheduler.is_error(new Request("(FR,1,DOWN,0)"));
		Scheduler.is_error(new Request("(FR,1,UP,1)"));
	}
	
	@Test
	public void test_reqOk() {
		Scheduler scheduler = new Scheduler();
		assertTrue(scheduler.repOK());
		scheduler.time_now = -1.0;
		assertFalse(scheduler.repOK());
	}
}
