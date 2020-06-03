package work10;

import java.awt.Point;
/**
 * @overview 统计每一条符合要求的指令，将他们加入到请求队列中。
 *
 */
public class customer {
	private int begin1;
	private int begin2;
	private int end1;
	private int end2;
	private long time;

	customer(int b1, int b2, int e1, int e2, long t) {
		 /**
         * @REQUIRES:None
         * @MODIFIES:
         *      \this.begin1;
         *      \this.begin2;
         *      \this.end1;
         *      \this.end2;
         *      \this.time;
         * @EFFECTS:
         *      \this.begin1 = b1;
         *      \this.begin2 = b2;
         *      \this.end1 = e1;
         *      \this.end2 = e2;
         *      \this.time = t * 100;
         */
		this.begin1 = b1;
		this.begin2 = b2;
		this.end1 = e1;
		this.end2 = e2;
		this.time = t * 100;
	}
	public boolean repOK() {
		 /**
         * @REQUIRES:None
         * @MODIFIES:
         *      None;
         * @EFFECTS:
         *      \result==(begin1<80)&&(begin2<80)&&(end1<80)&&(end2<80)&&(time>0);
         */
		return (begin1<80)&&
				(begin2<80)&&
				(end1<80)&&
				(end2<80)&&
				(time>0);
	}
	public synchronized int getsrc() {
		/**
         * @REQUIRES:begin1!=None;
         * 			begin2!=None;
         * @MODIFIES:None;
         * @EFFECTS:
         *      \result==(begin1 * 80 + begin2);
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS:  \locked();
         */
		return begin1 * 80 + begin2;
	}

	public synchronized int getdrc() {
		/**
         * @REQUIRES:end1!=None;
         * 			end2!=None;
         * @MODIFIES:None;
         * @EFFECTS:
         *      \result==(end1 * 80 + end2);
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS: \locked();
         */
		return end1 * 80 + end2;
	}

	public synchronized Point getpsrc() {
		/**
         * @REQUIRES:begin1!=None;
         * 			begin2!=None;
         * @MODIFIES:None;
         * @EFFECTS:
         *      \result==Point(begin1,begin2);
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS: \locked();
         */
		Point p = new Point(begin1, begin2);
		return p;
	}

	public synchronized Point getpdrc() {
		/**
         * @REQUIRES:end1!=None;
         * 			end2!=None;
         * @MODIFIES:None;
         * @EFFECTS:
         *      \result==Point(end1,end2);
         * @ THREAD_REQUIRES:\locked(\this);
		 * @ THREAD_EFFECTS: \locked();
         */
		Point p = new Point(end1, end2);
		return p;
	}

	public synchronized long gettime() {
		/**
         * @REQUIRES:time!=None;
         * @MODIFIES:None;
         * @EFFECTS:
         *      \result==time;
         * @ THREAD_REQUIRES: \locked(\this);
		 * @ THREAD_EFFECTS: \locked();
         */
		return time;
	}

}
