package code;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueueTest {

	Queue Test_Queue = new Queue();
	
	@Test
	public void Test_queue() {
		this.Test_Queue.count_n = 1;
		this.Test_Queue.produce("(FR,1,UP,0)");
	}


}
