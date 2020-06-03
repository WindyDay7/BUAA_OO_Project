package oo.app;

import java.awt.Point;

import oo.app.GraphInfo.GraphCal;

public class Main {
	public static void main(String[] args) {
		try {
			int i;
			Taxi [] taxiarr =new Taxi[100];
			int [][] mapgraph;
			
			MapInfo mymap = new MapInfo();
			mymap.readMap("map.txt");
			TaxiGUI mygui = new TaxiGUI();
			mygui.LoadMap(mymap.map,80);
			mapgraph = GraphCal.initMatrix(mymap.map);
			
			for(i=0;i<100;i++) {
				Taxi tmptaxi = new Taxi(i,mymap.map,mygui,mapgraph);
				taxiarr[i] = tmptaxi;
				mygui.SetTaxiStatus(i, new Point(tmptaxi.getXAxis(), tmptaxi.getYAxis()),2);
				new Thread(tmptaxi).start();
			}
			
			InputHandler inhandler = new InputHandler(taxiarr,mygui,"Information.txt");
			inhandler.formatRequest();
		}catch(Exception e) {
			System.exit(1);
		}
	}
}
