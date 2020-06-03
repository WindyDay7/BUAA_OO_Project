package oo.other1;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StageTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Stage stage = new Stage();
		stage.getLight();
		
		assertTrue(stage.repOK());
		stage.light[0] = -1;
		assertFalse(stage.repOK());
		stage.light[0] = 0;
		stage.light[1] = -1;
		assertFalse(stage.repOK());
	}

}
