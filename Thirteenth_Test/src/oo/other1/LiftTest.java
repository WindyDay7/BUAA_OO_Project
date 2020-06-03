package oo.other1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LiftTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Lift ele = new Lift();
		ele.toString();
		ele.setFloor_begin(1);
		ele.setFloor_now(1);
		ele.setFloor_target(1);
		ele.setState(1);
		ele.getFloor_begin();
		ele.getFloor_now();
		ele.getFloor_target();
		ele.getState();
		ele.getTime();
		
		assertTrue(ele.repOK());
		ele.setState(3);
		assertFalse(ele.repOK());
		ele.setState(-1);
		assertFalse(ele.repOK());
		ele.setState(0);
		ele.setTime(-1);
		assertFalse(ele.repOK());
		ele.setTime(0);
		ele.setFloor_begin(11);
		assertFalse(ele.repOK());
		ele.setFloor_begin(-1);
		assertFalse(ele.repOK());
		ele.setFloor_begin(2);
		ele.setFloor_now(11);
		assertFalse(ele.repOK());
		ele.setFloor_now(-1);
		assertFalse(ele.repOK());
		ele.setFloor_now(2);
		ele.setFloor_target(11);
		assertFalse(ele.repOK());
		ele.setFloor_target(-1);
		assertFalse(ele.repOK());
		
	}

}
