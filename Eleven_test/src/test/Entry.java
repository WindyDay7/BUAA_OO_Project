package test;

public class Entry {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			// you can modify the path of the file
			MHandler m = new MHandler("D:\\test\\map.txt");
			m.scan();
			LHandler l = new LHandler("D:\\test\\light.txt");
			l.scan();
			/*
			boolean[][] lmap = new boolean[80][80];
			int i,j;
			for(i = 0; i < 80; i++) {
				for(j = 0; j < 80; j++) {
					lmap[i][j] = false;
				}
			}
			*/
			TaxiGUI gui = new TaxiGUI();
			gui.LoadMap(m.map, 80);
			Lctrl lc = new Lctrl(l.lmap, gui);
			lc.start();
			Taxi[] alltaxi = new Taxi[100];
			Queue rqueue = new Queue();
			
			Schedule sch = new Schedule(alltaxi, gui, rqueue, lc.stime);
			sch.start();
			CHandler ch = new CHandler(gui, alltaxi, rqueue, lc);
			ch.start();
			//TestThread th = new TestThread(alltaxi, rqueue);
			//th.start();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean repOK() {
		return true;
	}
}
