import java.awt.Point;
import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String args[]){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        TaxiGUI taxiGUI = new TaxiGUI();
        Map map = new Map(taxiGUI);
        Taxi[] taxis = new Taxi[100];
        Guest[] guests = new Guest[1000];
        Test test = new Test(taxis,map);
        int i=0;
        for (i=0;i<100;i++) {
            taxis[i] = new Taxi(String.valueOf(i),map,taxiGUI);
        }
        for (i=0;i<100;i++) {
            taxis[i].waitforservice();
        }
        map.makemap();

        Scanner in = new Scanner(System.in);
        String string = in.nextLine();
        String reg = "Load .*";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(string);
        if (m.matches()){
            String Path = string.split(" ")[1];
            File file = new File(Path);
            try {
                FileReader reader = new FileReader(file.getName());
                BufferedReader br = new BufferedReader(reader);
                String str = null;
                while ((str = br.readLine()) != null) {
                    str = str.replaceAll("\\\\r|\\\\n","");
                    if (str.equals("#map")){
                        str = br.readLine();
                        str = str.replaceAll("\\\\r|\\\\n","");
                        map.makemap(str);
                    }
                    else if (str.equals("#flow")){
                        str = br.readLine();
                        do {
                            str = str.replaceAll("\\\\r|\\\\n", "");
                            str = str.replaceAll(" ", "");
                            String[] buffer = str.split(",|\\(|\\)");
                            int x1 = 0, y1 = 0, x2 = 0, y2 = 0, flow = 0;
                            try {
                                x1 = Integer.parseInt(buffer[1]);
                                y1 = Integer.parseInt(buffer[2]);
                                x2 = Integer.parseInt(buffer[4]);
                                y2 = Integer.parseInt(buffer[5]);
                                flow = Integer.parseInt(buffer[6]);
                            } catch (Exception e) {
                            }
                            map.setFlow(x1, y1, x2, y2, flow);
                            str = br.readLine();
                            str = str.replaceAll("\\\\r|\\\\n", "");
                        }while(!str.equals("#end_flow"));
                    }
                    else if (str.equals("#taxi")){
                        str = br.readLine();
                        do {
                            str = str.replaceAll("\\\\r|\\\\n", "");
                            String[] buffer = str.split(",|\\(|\\)| ");
                            int no = 0, status = 0, credit = 0, x1 = 0, y1 = 0;
                            try {
                                no = Integer.parseInt(buffer[0]);
                                status = Integer.parseInt(buffer[1]);
                                credit = Integer.parseInt(buffer[2]);
                                x1 = Integer.parseInt(buffer[4]);
                                y1 = Integer.parseInt(buffer[5]);
                            } catch (Exception e) {
                            }
                            taxis[no].set(status,credit,x1,y1);
                            str = br.readLine();
                            str = str.replaceAll("\\\\r|\\\\n", "");
                        }while(!str.equals("#end_taxi"));
                    }
                    else if (str.equals("#request")){
                        i = 0;
                        str = br.readLine();
                        long time = System.currentTimeMillis();
                        do {
                            str = str.replaceAll("\\\\r|\\\\n", "");
                            str = str.replaceAll(" ", "");
                            guests[i] = new Guest(i, taxis, map, taxiGUI);
                            guests[i++].getrequest(str,time);
                            for (int j=0;j<i;j++){
                                if (guests[i].equal(guests[j])){
                                    System.out.println("request "+i+" is the same with request "+j);
                                    i--;
                                    break;
                                }
                            }
                            str = br.readLine();
                            str = str.replaceAll("\\\\r|\\\\n", "");
                        }while(!str.equals("#end_request"));
                    }
                }
                br.close();
                reader.close();
            }catch (Exception e){}
        }
        taxiGUI.LoadMap(map.getMap(),80);
        for (int j=0;j<100;j++) {
            taxis[j].start();
        }
        for (int j=0;j<i;j++) {
            guests[j].start();
        }

        test.start();
        newthread newthread = new newthread();

        for (;;i++) {
            Guest guest = new Guest(i, taxis, map, taxiGUI);
            guests[i] = guest;
            while (true) {
                if (guest.getrequest()) {
                    int j=0;
                    for (j=0;j<i;j++){
                        if (guest.equal(guests[j])){
                            break;
                        }
                    }
                    if (j<i){
                        System.out.println("SAME REQUEST");
                        continue;
                    }
                    long time = System.currentTimeMillis();
                    guest.setTime(time);
                    guest.start();
                    break;
                }
                if (Guest.end){
                    if (guest.file.exists()&&(guest.file.length()==0)){
                        guest.file.delete();
                    }
                    break;
                }
            }
            if (Guest.end){
                break;
            }
        }
    }
}