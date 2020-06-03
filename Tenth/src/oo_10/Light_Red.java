package oo_10;

class Light_Red implements Runnable {
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 这个类写的不是很好，主要原因的是，没有对该类的属性进行分配
	 * 而是直接使用了guigv里面的全局变量，这样写对代码的结构性不是很好
	 * 这里说明了一下啦
	 */
	
	public void run() {
		while(true) {
			try {
				Thread.sleep(guigv.Light_time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int i=0; i<80; i++) {
				for(int j=0; j<80; j++) {
					Light_Change(i,j);
				}
			}
			guigv.Light_Still = System.currentTimeMillis() + guigv.Light_time;
		}
	}
	
	public void Light_Change(int a, int b) {
		if(guigv.lightmap[a][b]==0) {
			return ;
		}
		else if(guigv.lightmap[a][b]==1) {
			guigv.lightmap[a][b] = 2;
		}
		else {
			guigv.lightmap[a][b] = 1;
		}
	}
}
