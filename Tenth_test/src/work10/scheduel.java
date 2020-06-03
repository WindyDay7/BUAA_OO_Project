package work10;

import java.util.HashMap;
import java.util.Vector;
/**
 * @overview 调度线程，根据命令队列，选择车辆去完成相应的命令。
 *
 */
public class scheduel implements Runnable {
	private Vector<taxi> taxis;// taxilist
	private Vector<taxi> tc = new Vector<taxi>(100);// taxi-canbecalled
	private boolean[] ifin = new boolean[105];
	private customer c;
	private TaxiGUI taxiGUI;
	private HashMap<Integer, Integer> range = new HashMap<Integer, Integer>(26);
	private long startTime;
	private long time;

	scheduel(Vector<taxi> t, customer c, TaxiGUI g) {
	       /**
         * @REQUIRES:t!=None;
         * 			c!=None;
         * 			g!=None;
         * @MODIFIES:
         *      \this.taxis;
         *      \this.c;
         *      \this.taxiGUI;
         * @EFFECTS:
         *      \this.taxis = t;
         *		\this.c = c;
         *		\this.taxiGUI = g;
         */
		this.taxis = t;
		this.c = c;
		this.taxiGUI = g;
		for (int i = 0; i < 105; i++)
			ifin[i] = false;
	}
	public boolean repOK() {
	    /**
         * @REQUIRES:None;
         * @MODIFIES:
         *      None;
         * @EFFECTS:
         *      \result=taxis!=null&&c!=null&&tc!=null&&ifin!=null&&taxiGUI!=null&&range!=null;
         */
		return taxis!=null&&c!=null&&tc!=null&&ifin!=null&&taxiGUI!=null&&range!=null;
	}
	@Override
	public void run() {
		/**
		 * @Require: tc!=None;
		 * 			taxis!=None;
		 * 			c!=None;
		 * 			t!=None;
		 * @Modify:	 this.tc;
		 * 			this.taxis;
		 * @Effect: 
		 * 			读入需要处理的乘客请求，在起始点周围5*5的方格创立一个hash表，记录下3秒钟内所有从这个5*5方格中经过的车。
		 * 			从这些车中选择等待服务类型的车，对所有车的信用进行比较，选择信用最高的车，让他遵从最短路径的原则来接乘客。
		 */			
		int flag = 0;
		// TODO Auto-generated method stub
		int src = c.getsrc();
		int drc = c.getdrc();
		taxiGUI.RequestTaxi(c.getpsrc(), c.getpdrc());
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++)
				range.put(src - 80 * 2 - 2 + i * 80 + j, 0);
		}
		startTime = gv.getTime();
		while ((gv.getTime() - startTime) < 3000) {
			for (int i = 0; i < 100; i++) {
				taxi t = taxis.get(i);
				if (!ifin[t.number] && t.state == 2) {
					if (range.containsKey(t.location)) {
						flag = 1;
					} else
						flag = 0;
				}
				if (flag == 1) {
					tc.add(t);
					ifin[t.number] = true;
					t.cre++;
				}
			}
		}
		if (tc.isEmpty()) {
			System.out.println("没有出租车可用，结束处理");
		} else {
			int max = 0;// 找信誉最大
			int maxlo[] = new int[1000];
			int m = 0;
			for (int i = 0; i < tc.size(); i++) {
				if (tc.get(i).cre > max) {
					max = tc.get(i).cre;
					maxlo[m] = i;
					m++;
				}
			}
			int minpath = 100000;// 找距离最短
			int minlo = 0;//
			for (int i = 0; i < m; i++)// 所有信誉相同的车比较距离最近的；
			{
				int a = (tc.get(maxlo[i]).findpath(123, src)).length();
				if (a < minpath) {
					minpath = a;
					minlo = maxlo[i];
				}
			}
			for (int j = 0; j < tc.size(); j++)
				System.out.println("参与抢单的车量名字" + tc.get(j).number + ",信用：" + tc.get(j).cre + "位置：" + tc.get(j).location
						+ ",状态：" + tc.get(j).state);
			System.out.println(
					"抢到车的编号" + tc.get(minlo).number + "接单时位置" + tc.get(minlo).location + "接单时间" + gv.getTime());
			tc.get(minlo).ifcall(src, drc);// 抢到单的车。
		}
		System.out.println("乘客请求发出时间:" + c.gettime() + ",乘客请求发出地址:(" + c.getsrc() / 80 + "," + c.getsrc() % 80
				+ "),乘客请求目的地:(" + c.getdrc() / 80 + "," + c.getdrc() % 80 + ")");

	}

}
