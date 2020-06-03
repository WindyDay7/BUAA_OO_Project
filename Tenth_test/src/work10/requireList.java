package work10;

import java.util.concurrent.LinkedBlockingQueue;
/**
 * @overview 请求队列函数，将所有的符合的请求加入到请求队列当中。
 *
 */
public class requireList {
	private LinkedBlockingQueue<customer> customers;
	private customer ct;// 记录上一个添加的用户，用来判断是不是相同的

	requireList() {
		/**
         * @REQUIRES:None;
         * @MODIFIES:
         *      this.ct;
         *      this.customers;
         * @EFFECTS:
         *      this.ct=null;
         */
		ct = null;
		customers = new LinkedBlockingQueue<customer>();
	}
	public boolean repOK() {
		/**
         * @REQUIRES:None;
         * @MODIFIES:
         *     None
         * @EFFECTS:
         *      /result=true;
         * 
         */
		return true;
	}
	public synchronized void add(customer c) {
		/**
         * @REQUIRES:c!=None;
         * @MODIFIES:this.customers;
         * 			this.ct	;
         * @EFFECTS:
         *      customers.contains(c);
         *      ct=c;
         * @ THREAD_REQUIRES: \locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		customers.offer(c);
		ct = c;
	}

	public synchronized boolean isEmpty() {
		/**
         * @REQUIRES:None;
         * @MODIFIES:None;
         * @EFFECTS:
         *     (customers.isEmpty)==>\result = true;
         *     (!customers.isEmpty)==>\result = false;
         *     判断数组的长度是否为空
         * @ THREAD_REQUIRES: \locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		return customers.isEmpty();
	}

	public synchronized customer poll() {
		/**
         * @REQUIRES:customers.isEmpty()!=false;
         * @MODIFIES:None;
         * @EFFECTS:
         *     \result = customers.poll();
         *     返回数组的第一个元素，并将此数组的第一个元素从数组中拿出。
         * @ THREAD_REQUIRES: \locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		return customers.poll();
	}

	public synchronized boolean issame(customer c) {
		/**
         * @REQUIRES:c!=None;
         * @MODIFIES:None;
         * @EFFECTS:
         * 		(c==ct)==>\result=false;
         *      (c!=ct)==>\result=true;
         *     判断是否是同质的指令，若是则返回false，不是则返回true;
         * @ THREAD_REQUIRES: \locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		if (c == ct)
			return false;
		else
			return true;
	}
}
