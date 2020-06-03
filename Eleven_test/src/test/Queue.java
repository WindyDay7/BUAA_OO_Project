package test;

import java.util.ArrayList;

public class Queue {
	private ArrayList<Request> queue;
	private boolean full;
	boolean over;
	
	/**
	 * @REQUIRES: none;
	 * @MODIFIES: this;
	 * @EFFECTS:
	 * create a queue with no elements inside;
	 */
	public Queue(){
		queue = new ArrayList<Request>();
		full = false;
		over = false;
	}
	public boolean repOK() {
		return true;
	}
	
	/**
	 * @REQUIRES:(\exist Request req);
	 * @MODIFIES:queue;
	 * @EFFECTS:(queue.length == \old(queue)+1);
	 * (queue[length-1] == req);
	 */
	public synchronized void put(Request req){
		while(full == true){
			try{
				wait();
			}catch(InterruptedException e){
				;
			}
		}
		full = true;
		//System.out.println("# added");
		queue.add(req);
		notifyAll();
	}
	/**
	 * @REQUIRES:none;
	 * @MODIFIES:queue;
	 * @EFFECTS:(\old(queue).length == 0 ==> \result == null);
	 * (\old(queue).length > 0 ==> \result == queue[0] &&
	 * queue.length == \old(queue).length-1 &&
	 * (\all int i; 0<=i<queue.length)==>(queue[i] == \old(queue)[i+1]));
	 */
	public synchronized Request get(){
		Request temp;
		while(full == false) {
			try{
				wait();
			}catch(InterruptedException e) {
				;
			}
		}
		full = false;
		if(queue.isEmpty()) {
			notifyAll();
			return null;
		}
		else {
			temp = queue.get(0);
			queue.remove(0);
			notifyAll();
			return temp;
		}
	}
}
