package work10;

import java.util.Vector;
import java.awt.Point;
import java.util.Random;
/**
 * @overview 红绿灯线程，根据数组的情况来构建各个点的红绿灯情况。
 *
 */
public class trafficLight implements Runnable {
	public static long rtime;//变化间隔
	private int dir;
	public boolean repOK() {
		/**
         * @REQUIRES:None;
         * @MODIFIES:
         *      None;
         * @EFFECTS:
         *     \result=(rtime>500&&rtime<1000)&&(dir==2||dir==1);
         */
		return (rtime>500&&rtime<1000)&&(dir==2||dir==1);
	}
	public void run() {
		 /**
         * @REQUIRES:None;
         * @MODIFIES:
         *      \main.gui.SetLightStatus;
         *      \this.rtime;
         *      \this.dir;
         * @EFFECTS:
         *     对于light数组的每一个数字进行判断，若是1则修改main.gui.SetLightStatus读入红绿灯的信息。执行一遍后线程睡红绿灯变化的时间，然后将红绿灯的状态改变，继续读入红绿灯信息。
         */
		// TODO Auto-generated method stub
		Random rand = new Random();
		dir=1+rand.nextInt(2);//决定初始的方向
		rtime=500+rand.nextInt(500);//决定灯变化的时间间隔
		while(true) {
			for(int i=0;i<main.light.size();i++) {
				if(main.light.get(i)!=0) {
					main.gui.SetLightStatus(new Point(i / 80, i % 80), dir);
				}
				else {
					main.gui.SetLightStatus(new Point(i / 80, i % 80), 0);
				}
			}
			try {
				Thread.sleep(rtime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
			if(dir==1) {
				dir=2;
			}
			else {
				dir=1;
			}
		}
	}
	
}
