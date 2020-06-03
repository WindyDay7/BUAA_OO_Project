import java.awt.*;

public class Test extends Thread {
    Taxi[] taxis;
    Map map;
    Test(Taxi[] taxis,Map map){
        /**@REQUIRES: \all int i;0<=i<100;taxis[i]!=null;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        super("Test");
        this.taxis = taxis;
        this.map = map;
    }
    public void catchtaxi(int index){
        /**@REQUIRES: (0<=index<100);
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        long time = System.currentTimeMillis();
        System.out.println("查询时刻:"+time);
        System.out.println("出租车当前坐标:("+taxis[index].getPoint().x+","+taxis[index].getPoint().x+")");
        System.out.println("出租车当前状态:"+taxis[index].getStatus());
    }
    public void catchstatus(int status){
        /**@REQUIRES: (0<=status<=3);
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        if ((status<0)||(status>3)){
            System.out.println("your status is out of range");
            return;
        }
        System.out.println("当前状态的出租车有:");
        for (int i=0;i<100;i++){
            if (taxis[i].getStatus()==status){
                System.out.println(i);
            }
        }
    }
    public void setRoadStatus(Point p1, Point p2, int status){
        /**p1 is next to p2
         * @REQUIRES: (status==0)||(status==1);
         * @MODIFIES: None;
         * @EFFECTS: None;
         */
        map.setRoadStatus(p1,p2,status);
    }
    public void run(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
//        catchstatus(2);
//        catchtaxi(4);
    }
}
