package oo.other1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RequestTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Request req1 = new Request("(FR,1,UP,0)");
		Request req1_test = new Request();
		req1_test.setIs_in(0);
		req1_test.setFloor(1);
		req1_test.setState(1);
		req1_test.setTime(0.0f);
		req1_test.setError(0);
		req1.getIs_in();
		req1.getError();
		req1.getFloor();
		req1.getState();
		req1.getTime();
		
		
		Request req2 = new Request("(ER,1,1)");
		Request req2_test = new Request();
		req2_test.setIs_in(1);
		req2_test.setFloor(1);
		req2_test.setState(0);
		req2_test.setTime(1.0f);
		req2_test.setError(0);
		
		Request req3 = new Request("(FR,10,UP,0)");
		
		Request req4 = new Request("(FR,2,DOWN,+0)");
		
		Request req5 = new Request("(FR,9,UP,8888888888)");
		
		Request req6 = new Request("(FR,1,DOWN,0)");
		
		Request req7 = new Request("(ER,1,8888888888)");
		
		Request req8 = new Request("dsahdjksahdjksa");
		
		
		assertTrue(req1.repOK());
		req1.setIs_in(222);
		assertFalse(req1.repOK());
		req1.setIs_in(-1);
		assertFalse(req1.repOK());
		req1.setIs_in(0);
		req1.setTime(-1);
		assertFalse(req1.repOK());
		req1.setTime(0.0f);
		req1.setError(233);
		assertFalse(req1.repOK());
		req1.setError(-1);
		assertFalse(req1.repOK());
		req1.setError(0);
		req1.setFloor(111);
		assertFalse(req1.repOK());
		req1.setFloor(-1);
		assertFalse(req1.repOK());
	}

}
