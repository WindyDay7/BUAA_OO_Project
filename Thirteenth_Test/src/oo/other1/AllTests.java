package oo.other1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LiftTest.class, RequestTest.class, SchedulerTest.class, StageTest.class })
public class AllTests {
	
}
