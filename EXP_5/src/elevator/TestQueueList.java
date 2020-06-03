package elevator;

import elevator.Query.Direction;

public class TestQueueList {
	public static void main(String[] args) throws UnsortedException,InvalidRemoveException {
		
	QueryList ql=new QueryList(10,1);
	
	//测试不合法的remove
	try {
	System.out.println("remove from empty queue:"+ql.remove(3));
	} catch (InvalidRemoveException e) {
		System.out.println(e.getMessage());
	}
	
	ql.append(new Query(10,0.0));
	ql.append(new Query(8,1.0,Query.Direction.UP));
	ql.append(new Query(5,1.5,Query.Direction.DOWN));
	
	//测试合法的remove
	System.out.println("remove the first element from the queue:"+ql.remove(0));
	
	//测试不合法的remove
	try {
	System.out.println("remove an nonexistent element from the queue:"+ql.remove(-1));
	System.out.println("remove an exceeded element from the queue:"+ql.remove(4));
	} catch (InvalidRemoveException e) {
		System.out.println(e.getMessage());
	}
	
	//测试puckupQuery
	Elevator e3 = new Elevator(10, 1);
	e3.pickupQuery(new Query(5, 1));
	System.out.println("\n");
	boolean check = e3.checkFinishedQuery();
	System.out.println("检查是否有已经完成的请求: " + check);
	
	
	//测试append
	Query qqq = new Query(5,2,Direction.UP);
	ql.append(qqq);
	ql.append(new Query(1,4,Direction.DOWN));
	ql.append(new Query(10,5,Direction.UP));
	
	//测试checkFinishedQuery
	Elevator e4 = new Elevator(11, 2);
	e3.pickupQuery(new Query(3, 6));
	e3.pickupQuery(new Query(4, 7));
	e3.pickupQuery(new Query(5, 5));
	System.out.println("***************\n");
	boolean check4 = e4.checkFinishedQuery();
	System.out.println("检查是否有已经完成的请求: " + check4);
	
 }
}
