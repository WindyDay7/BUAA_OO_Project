package txz;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;

public class PushBoxTest {
	PushBox Pb_Test = new PushBox();
	
	@Test
	public void Test_readm_map() {
		ByteArrayInputStream in = new ByteArrayInputStream("5 5 1 3 1 1 1 1 1 1 4 1 1 1 1 1 1 1 12 1 1 1 1 1 1 1".getBytes());
		System.setIn(in);  
		assertEquals(false,Pb_Test.readmap());
		assertEquals(true,Pb_Test.check(0, 1));
		assertEquals(false,Pb_Test.check(3, 0));
		assertEquals(false,Pb_Test.check(2, 6)); 
		assertEquals(false,Pb_Test.check(5, 2));
	}
	
	@Test
	public void Test_readm_map_2 () {
		
		ByteArrayInputStream in=new ByteArrayInputStream("5 5 0 3 0 0 0 1 0 1 4 0 0 0 1 0 0 1 0 2 0 0 0 0 0 0 0".getBytes());
		System.setIn(in);
		assertEquals(true,Pb_Test.readmap());
		 
		in=new ByteArrayInputStream("5 5 0 3 0 0 0 1 0 1 0 0 0 0 1 0 0 1 0 2 0 0 0 0 0 0 0".getBytes());
		System.setIn(in);
		assertEquals(false,Pb_Test.readmap());
		
		in=new ByteArrayInputStream("5 5 0 0 0 0 0 1 0 1 4 0 0 0 1 0 0 1 0 2 0 0 0 0 0 0 0".getBytes());
		System.setIn(in);
		assertEquals(false,Pb_Test.readmap());
		
		in=new ByteArrayInputStream("5 5 0 3 0 0 0 1 0 1 4 0 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0".getBytes());
		System.setIn(in);
		assertEquals(false,Pb_Test.readmap());
		
		in=new ByteArrayInputStream("5 5 0 3 0 0 3 1 0 1 4 0 0 0 1 0 0 1 0 2 0 0 0 0 0 0 0".getBytes());
		System.setIn(in);
		assertEquals(false,Pb_Test.readmap());
		
		in=new ByteArrayInputStream("5 5 0 3 0 0 0 1 0 1 4 4 0 0 1 0 0 1 0 2 0 0 0 0 0 0 0".getBytes());
		System.setIn(in);
		assertEquals(false,Pb_Test.readmap());
		
		in=new ByteArrayInputStream("5 5 0 3 0 0 0 1 0 1 4 0 2 0 1 0 0 1 0 2 0 0 0 0 0 0 0".getBytes());
		System.setIn(in);
		assertEquals(false,Pb_Test.readmap());
		
		in=new ByteArrayInputStream("5 5 1 3 1 1 1 6 1 1 4 1 1 1 1 1 1 1 1 2 1 1 1 1 1 1 1".getBytes());
		System.setIn(in); 
		assertEquals(false,Pb_Test.readmap());
		
		in=new ByteArrayInputStream("5 5 1 -3 1 1 1 -6 1 1 84 1 1 1 1 1 1 1 1 2 1 1 1 1 1 1 1".getBytes());
		System.setIn(in); 
		assertEquals(false,Pb_Test.readmap());
		
		in=new ByteArrayInputStream("5 5 1 -3 1 1 1 6 1 1 4 1 1 1 1 1 1 1 1 2 1 1 1 1 1 1 1".getBytes());
		System.setIn(in); 
		assertEquals(false,Pb_Test.readmap());
	} 

	@Test
	public void Test_Bfs() {
		ByteArrayInputStream in=new ByteArrayInputStream("5 5 0 3 0 0 0 1 0 1 4 0 0 0 1 0 0 1 0 2 0 0 0 0 0 0 0".getBytes());
		System.setIn(in);
		assertEquals(true,Pb_Test.readmap());
		Pb_Test.bfs(); 
		this.Pb_Test.q=null;
	}
	
	@Test
	public void Test_Bfs_2() {
		assertEquals(-1,Pb_Test.bfs());
		ByteArrayInputStream in = new ByteArrayInputStream("5 5 1 3 1 0 0 1 1 1 4 0 0 0 1 0 0 1 0 2 0 0 0 0 0 0 0".getBytes());
		System.setIn(in);
		Pb_Test.readmap();
		assertEquals(-1,Pb_Test.bfs());
	}
	
	@Test
	public void testPre() {
		Queue<node> q = new LinkedList<node>();
		q.add(new node(0, 0, 0, 0, 0, null));
		PushBox.pre(q);
	}
	
	@Test
	public void TestBfs_man() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (PushBox.map[i][j] != 1 && (i != 3 && j != 2)) {
					assertEquals(false, Pb_Test.bfs_man(i, j, 3, 3, 3, 2));
				}
			}
		}
	}

}
