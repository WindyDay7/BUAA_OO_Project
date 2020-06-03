package work10;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Vector;
/**
 * @overview 主函数，创建出租车线程，调度线程，红绿灯线程，输入线程。
 *
 */
public class main {
	private static Vector<taxi> tl = new Vector();
	private static requireList rl = new requireList();
	public static Vector<Integer>[] ov=new Vector[6400];
	public static Vector<Integer>light=new Vector();
	static Vector<Integer>[] v;
	static Map m ;
	static TaxiGUI gui =  new TaxiGUI();
	static void MakeMap(String s) {
		 m =  new Map(s);
		 if(m.set()){
			 int[][] map = m.getmapnumber();
			 v = m.getlattice();
			 gui.LoadMap(map, 80);
		 }
		 for(int i=0;i<v.length;i++) {
				ov[i] = new Vector<Integer>();
				for(int j=0 ;j<v[i].size();j++) {
					ov[i].add(v[i].get(j));
				}
			}
	   	     
	}
	
	public boolean repOK() {
		/**
         * @REQUIRES:None;
         * @MODIFIES:
         *      None;
         * @EFFECTS:
         *      \result=(tl!=null)&&(rl!=null)&&(ov!=null)&&(light!=null)&&(v!=null)&&(m!=null)&&(gui!=null);
         */
		return (tl!=null)&&
				(rl!=null)&&
				(ov!=null)&&
				(light!=null)&&
				(v!=null)&&
				(m!=null)&&
				(gui!=null);
	}
	public static void main(String[] args) {
		try {
			MakeMap("map.txt");
			PrintStream ps;
			ps = new PrintStream("out.txt");
			System.setOut(ps); // 重定项屏幕输出到ps对象中
			// TODO Auto-generated method stub
			//TaxiGUI gui = new TaxiGUI();
			//Vector[] v = m.getlattice();
			
			// mapInfo mi = new mapInfo();
			// mi.readmap("map.txt");// 在这里设置地图文件路径

			//if (m.set()) {
				//int[][] map = m.getmapnumber();
				//gui.LoadMap(map, 80);
				for (int k = 0; k < 100; k++) {
					taxi t = new taxi(k, v, gui);
					tl.add(t);
					new Thread(t, "Thread" + k).start();
				}      
			//}
			input in = new input(rl, tl);
			new Thread(in,"Threadin").start();
			trafficLight tfl=new trafficLight();
			new Thread(tfl,"Threadtfl").start();
			int i = 0;
			while (true) {
				if(!rl.isEmpty()){
				customer cs = rl.poll();
				scheduel sh = new scheduel(tl, cs, gui);
				new Thread(sh, "Thread" + i).start();
				i++;
				}
			}
		} catch (FileNotFoundException e) {
				System.out.println("error");
		} // 新建一个打印对象
	}

}
