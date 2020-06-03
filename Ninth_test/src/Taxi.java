import java.awt.Point;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Taxi extends Thread {
    private final int MOVE = 500;
    private final int OPENDOOR = 1000;
    private final int REST = 1000;
    private final int WAITROUND = 100;
    private final int WAITROUNDTIME = 20000;

    private int status = -1;
    private volatile Point point;
    private int credit=0;
    Point[] thispath;
    private int remainder;
    private long faketime = 0;

    private int count2rest = 0;
    private long startrest;
    private static volatile int pvcount = 0;
    private static volatile int pvcount2 = 0;
    private static boolean pv = false;

    private Map map;
    private TaxiGUI taxiGUI;

    Taxi(String number,Map map,TaxiGUI taxiGUI){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        super(number);
//        point = new Point((int)(Math.random()*80),(int)(Math.random()*80));
        point = new Point(0,0);
        credit = 0;
        this.map = map;
        this.taxiGUI = taxiGUI;
        this.taxiGUI.SetTaxiStatus(Integer.valueOf(number),point,status);
    }

    public void set(int status,int credit,int x,int y){
        /**@REQUIRES: (0<=status<=3);
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        switch (status) {
            case 0: this.status = 2;   break;
            case 1: this.status = 1;   break;
            case 2: this.status = 3;   break;
            case 3: this.status = 0;   break;
        }
        updatetogui();
        this.credit = credit;
        point.setLocation(x,y);
    }

    public void updatetogui(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        taxiGUI.SetTaxiStatus(Integer.valueOf(getName()),point,status);
    }

    public int getStatus() {
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * \result == status;
         */
        return status;
    }

    public Point getPoint() {
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * \result == point;
         */
        return point;
    }

    public int getCredit() {
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * \result == credit;
         */
        return credit;
    }

    public boolean iswaitforservice(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * (status == 3)==>(\result == true);
         * (status != 3)==>(\result == false);
         */
        return (status==3);
    }

    public void jointhelist(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * this.credit == \old(this.credit)+1;
         */
        credit++;
    }

    public void finishservice(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * this.credit == \old(this.credit)+3;
         */
        credit += 3;
    }

    public void takearest(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * this.startrest == System.currentTimeMillis();
         * this.status == 3;
         */
        long start=System.currentTimeMillis();
        stopservice();
        try {
            Thread.sleep(REST-20);
            while ((System.currentTimeMillis()-start)<REST){
                Thread.sleep(1);
            }
        }catch (Exception e){}
        waitforservice();
        count2rest = 0;
        startrest = System.currentTimeMillis();
        remainder = (int)((startrest/100)%5);
    }

    public void moveup(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * this.point.x == \old(this.point.x)-1;
         * this.faketime == \old(this.faketime)+this.MOVE;
         */
        long t=System.currentTimeMillis();
        boolean waitstatus = iswaitforservice();
        if ((map.getLink()[point.x][point.y]&map.up)!=map.up){
//            System.out.println(getName()+" can't move up");
            return;
        }
        try {
            Thread.sleep(MOVE-20);
            while((System.currentTimeMillis()-t)<MOVE){
                Thread.sleep(1);
            }
        }catch (Exception e){}

        if (waitstatus&&(!iswaitforservice())){
            return;
        }

        point.translate(-1,0);
        updatetogui();
        map.addflow(point.x+1,point.y,point.x,point.y);
        count2rest++;
        faketime += MOVE;
//        moveonce();
        if ((status==2)||(status==1)) {
            fileout(toString());
        }
    }
    public void movedown(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * this.point.x == \old(this.point.x)+1;
         * this.faketime == \old(this.faketime)+this.MOVE;
         */
        long t=System.currentTimeMillis();
        boolean waitstatus = iswaitforservice();
        if ((map.getLink()[point.x][point.y]&map.down)!=map.down){
//            System.out.println(getName()+" can't move down");
            return;
        }
        try {
            Thread.sleep(MOVE -20);
            while((System.currentTimeMillis()-t)<MOVE){
                Thread.sleep(1);
            }
        }catch (Exception e){}

        if (waitstatus&&(!iswaitforservice())){
            return;
        }

        point.translate(1,0);
        updatetogui();
        map.addflow(point.x-1,point.y,point.x,point.y);
        count2rest++;
        faketime += MOVE;
//        moveonce();
        if ((status==2)||(status==1)) {
            fileout(toString());
        }
    }
    public void moveleft(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * this.point.y == \old(this.point.y)-1;
         * this.faketime == \old(this.faketime)+this.MOVE;
         */
        long t=System.currentTimeMillis();
        boolean waitstatus = iswaitforservice();
        if ((map.getLink()[point.x][point.y]&map.left)!=map.left){
//            System.out.println(getName()+" can't move left");
            return;
        }
        try {
            Thread.sleep(MOVE-20);
            while((System.currentTimeMillis()-t)<MOVE){
                Thread.sleep(1);
            }
        }catch (Exception e){}

        if (waitstatus&&(!iswaitforservice())){
            return;
        }

        point.translate(0,-1);
        updatetogui();
        map.addflow(point.x,point.y+1,point.x,point.y);
        count2rest++;
        faketime += MOVE;
//        moveonce();
        if ((status==2)||(status==1)) {
            fileout(toString());
        }
    }
    public void moveright(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * this.point.y == \old(this.point.y)+1;
         * this.faketime == \old(this.faketime)+this.MOVE;
         */
        long t=System.currentTimeMillis();
        boolean waitstatus = iswaitforservice();
        if ((map.getLink()[point.x][point.y]&map.right)!=map.right){
//            System.out.println(getName()+" can't move right");
            return;
        }
        try {
            Thread.sleep(MOVE-20);
            while((System.currentTimeMillis()-t)<MOVE){
                Thread.sleep(1);
            }
        }catch (Exception e){}

        if (waitstatus&&(!iswaitforservice())){
            return;
        }
        point.translate(0,1);
        updatetogui();
        map.addflow(point.x,point.y-1,point.x,point.y);
        count2rest++;
        faketime += MOVE;
//        moveonce();
        if ((status==2)||(status==1)) {
            fileout(toString());
        }
    }
    public void move(Point point){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        if (point.x==this.point.x){
            if (point.y>=this.point.y+1){
                while (point.y>this.point.y){
                    moveright();
                }
            }
            else if (point.y<=this.point.y-1){
                while (point.y<this.point.y) {
                    moveleft();
                }
            }
            else if (point.y==this.point.y){
                return;
            }
            else {
                System.out.print("Taxi.move is wrong 1\t");
                System.out.println(this.point.toString()+"\t"+point.toString());
                return;
            }
        }
        else if (point.y==this.point.y){
            if (point.x>=this.point.x+1){
                while (point.x>this.point.x) {
                    movedown();
                }
            }
            else if (point.x<=this.point.x-1){
                while (point.x<this.point.x) {
                    moveup();
                }
            }
            else {
                System.out.print("Taxi.move is wrong 2\t");
                System.out.println(this.point.toString()+"\t"+point.toString());
                return;
            }
        }
        else {
            System.out.print("Taxi.move is wrong 3\t");
            System.out.println(this.point.toString()+"\t"+point.toString());
            return;
        }
    }
    public void move(int i){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        if (iswaitforservice()) {
            switch (i) {
                case 0:
                    moveup();
                    break;
                case 1:
                    moveleft();
                    break;
                case 2:
                    movedown();
                    break;
                case 3:
                    moveright();
                    break;
            }
        }
    }
    public void move(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        if ((System.currentTimeMillis()-startrest)>=WAITROUNDTIME){
            stopservice();
            takearest();
            return;
        }
        int num = 0,mapchange = map.mapchange;
        int[] able = new int[4];
        int minflow = 101;
        if (((map.getLink()[point.x][point.y]&map.up)==map.up)&&(map.getflow(point,map.up)<=minflow)){
            if (map.getflow(point,map.up)<minflow){
                minflow = map.getflow(point,map.up);
                num = 0;
            }
            able[num++] = 0;
        }
        if (((map.getLink()[point.x][point.y]&map.left)==map.left)&&(map.getflow(point,map.left)<=minflow)){
            if (map.getflow(point,map.left)<minflow){
                minflow = map.getflow(point,map.left);
                num = 0;
            }
            able[num++] = 1;
        }
        if (((map.getLink()[point.x][point.y]&map.down)==map.down)&&(map.getflow(point,map.down)<=minflow)){
            if (map.getflow(point,map.down)<minflow){
                minflow = map.getflow(point,map.down);
                num = 0;
            }
            able[num++] = 2;
        }
        if (((map.getLink()[point.x][point.y]&map.right)==map.right)&&(map.getflow(point,map.right)<=minflow)){
            if (map.getflow(point,map.right)<minflow){
                num = 0;
            }
            able[num++] = 3;
        }
        if (mapchange==map.mapchange) {
            int branch = (int) (Math.random() * num);
            move(able[branch]);
        }
        else {
            move();
        }
    }
    public long move2(Point dst){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * \result == this.faketime;
         */
        int mapchange = map.mapchange;
//        thispath = findpathto(dst);
        int res = 0;
//        System.out.println(thispath.length);
//        if (thispath.length==1000000){
//            System.out.println(thispath[0]);
//            return 0;
//        }
        while (!point.equals(dst)){
//            thispath = findpathto(dst);
            Point next = findpathto2(dst);
            if (mapchange==map.mapchange) {
                move(next);
                res++;
            }
            else {
                mapchange = map.mapchange;
            }
        }
        if (status==2) {
            fileout("到达目的地时间:" + faketime);
        }
        else if (status==1) {
            fileout("到达乘客时间:" + faketime);
        }
        System.out.println("res:\t"+res);
        return faketime;
    }

    public Point findpathto2(Point dst){
        /**@REQUIRES: (0<=dst.x<80)&&(0<=dst.y<80);
         *@MODIFIES: None;
         *@EFFECTS:
         * \result == next;
         */
//        try {
//            Thread.sleep(500);
//        }catch (Exception e){}
        int mapchange = map.mapchange;
        Point next = map.mypointbfs2(point,dst);
        if (mapchange!=map.mapchange){
            do {
                mapchange = map.mapchange;
                next = map.mypointbfs2(point,dst);
            }while (mapchange!=map.mapchange);
        }
        return next;
    }

    public void waitforservice(){
        /**@REQUIRES: None;
         *@MODIFIES: this.status == 3;
         *@EFFECTS: None;
         */
        status = 3;
        updatetogui();
    }
    public void onservice(){
        /**@REQUIRES: None;
         *@MODIFIES: this.status == 2;
         *@EFFECTS: None;
         */
        status = 2;
        updatetogui();
    }
    public void gotoservice(){
        /**@REQUIRES: None;
         *@MODIFIES: this.status == 1;
         *@EFFECTS: None;
         */
        status = 1;
        updatetogui();
        System.out.println("taxi"+getName()+" comes to service");
    }
    public void stopservice(){
        /**@REQUIRES: None;
         *@MODIFIES: this.status == 0;
         *@EFFECTS: None;
         */
        status = 0;
        updatetogui();
    }

    public boolean aroundpoint(Point src){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * (point.x>=src.x-2)&&(point.x<=src.x+2)&&(point.y>=src.y-2)&&(point.y<=src.y+2)==>(\result == true);
         */
        if ((point.x>=src.x-2)&&(point.x<=src.x+2)&&(point.y>=src.y-2)&&(point.y<=src.y+2)){
            return true;
        }
        return false;
    }

    public void opendoor(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        try {
            Thread.sleep(OPENDOOR);
        }catch (Exception e){}
    }

//    static volatile boolean lock = true;
//    private synchronized void moveoneline(){
//        while (lock){
//            try{
//                wait();
//            }catch (Exception e){}
//        }
//        lock = true;
//        pvcount++;
//        System.out.println(pvcount);
//        lock = false;
//        notifyAll();
//    }

    private void moveonce(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
//        lock = false;
//        moveoneline();
        pvcount++;
        if (pvcount>=100){
            pvcount -= 100;
//            map.clearflow();
            pv = true;
        }
//        else {
//            while (!pv){
//                try {
//                    Thread.sleep(1);
//                } catch (Exception e) {}
//            }
//        }
    }

    public void run(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        startrest = System.currentTimeMillis();
        remainder = (int)((startrest/100)%5);
//        move(findpathto(new Point(1,3)));
        while (true) {
            if (iswaitforservice()) {
                long start = System.currentTimeMillis();
                move();
//            System.out.println(getName()+"\t"+(System.currentTimeMillis()-start));
            }
            else {
                try {
                    Thread.sleep(MOVE-20);
                } catch (Exception e) {}
                count2rest = 0;
                startrest = System.currentTimeMillis();
                remainder = (int)((startrest/100)%5);
            }
        }
    }

    File file;
    FileOutputStream fos;

    public void newfile(File file,FileOutputStream fos,long faketime){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        this.file = file;
        this.fos = fos;
        this.faketime = faketime;
    }

    public void fileout(String string){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        byte[] data = string.getBytes();
        try {
            fos.write(data);
            data = "\r\n".getBytes();
            fos.write(data);
        }catch (IOException e){}
    }

    public String toString(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS:
         * \result == "途径地点:("+point.x+","+point.y+")\t途径时间:"+faketime;
         */
        String res = "途径地点:(";
        res += point.x;
        res += ",";
        res += point.y;
        res += ")\t途径时间:";
        long time = System.currentTimeMillis();
        if ((time%100)!=0){
            time /= 100;
            time++;
            time *= 100;
        }

//        time -= 500;

        res += faketime;
        return res;
    }
}
