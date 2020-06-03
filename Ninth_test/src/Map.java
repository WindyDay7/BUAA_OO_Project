import java.io.*;
import java.awt.Point;

public class Map {
    private final int SIZE = 80;
    private final String root = "map.txt";
    final int WINDOWS = 7500;

    private int[][] map = new int[SIZE][SIZE];

    final int up = 8;
    final int left = 4;
    final int down = 2;
    final int right = 1;
    private int[][] link = new int[SIZE][SIZE];
    private int[][] request = new int[SIZE][SIZE];
    static volatile int[] flow = new int[12640];        //src.x*159+src.y+0.5*(dst.y-src.y)+79.5*(dst.x-src.x)-0.5

    guiInfo guiInfo = new guiInfo();
    TaxiGUI taxiGUI;
//    int[][][][] alldis = new int[SIZE][SIZE][SIZE][SIZE];

    Map(TaxiGUI taxiGUI){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * \all int i,j;0<=i<SIZE，0<=j<SIZE;map[i][j]==link[i][j]==request[i][j]==0;
         */
        for (int i=0;i<SIZE;i++){
            for (int j=0;j<SIZE;j++){
                map[i][j] = 0;
                link[i][j] = 0;
                request[i][j] = 0;
//                for (int m=0;m<SIZE;m++){
//                    for (int n=0;n<SIZE;n++){
//                        alldis[i][j][m][n] = -1;
//                    }
//                }
            }
        }
        clearflow();
        this.taxiGUI = taxiGUI;
    }

    public void makemap(){
        /**@REQUIRES: None;
         * @MODIFIES: map;
         * @EFFECTS:
         * \exist int i,j;0<=i<SIZE，0<=j<SIZE;map[i][j]!=0;
         */
        File file = new File(root);
        Reader in = null;
        for (int i=0;i<SIZE;i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = 0;
                link[i][j] = 0;
            }
        }
        try{
            in = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            char temp;
            int i=0,j=0,count=0;
            boolean border = false;
            while((tempchar = in.read())!=-1){
                temp = (char) tempchar;
                if (border){
                    System.out.println("INVALID INPUT");
                    break;
                }
                if ((temp==' ')||(temp=='\t')){
                    continue;
                }
                if (temp=='\n'){
                    if (count!=80){
                        System.out.println("INPUT HAS WRONG '\\n'");
                        break;
                    }
                    count = 0;
                }
                else {
                    if ((temp>='0')&&(temp<'4')){
                        int t = temp - '0';
                        map[i][j] = t;
                        link[i][j] += t;
                        if ((t&1)==1){
                            link[i][j+1] += 4;
                        }
                        if ((t&2)==2){
                            link[i+1][j] += 8;
                        }
                        j++;
                        count++;
                        if (j==SIZE){
                            j = 0;
                            i++;
                        }
                        if (i==SIZE){
                            border = true;
                        }
                    }
                    else if (temp=='\r'){
                        continue;
                    }
                    else{
                        System.out.println("INPUT HAS WRONG CHAR");
                        break;
                    }
//                    System.out.println((i++)+":\t"+temp);
                }
//                if (temp=='\n'){
//                    System.out.println(i+":\t\\n");
//                }
            }
        }catch (IOException e){System.out.println("Map.make()");}
        guiInfo.map = map;
        guiInfo.initmatrix();
//        System.out.println("finish read map");
//        guiInfo.map = map;
//        guiInfo.initmatrix();
//        for (int i=0;i<80;i++){
//            for (int j=0;j<80;j++){
//                guiInfo.pointbfs((i*80+j));
//                alldis[i][j] = guiInfo.D;
//                System.out.println("finish "+i+","+j);
//            }
//        }
    }
    public void makemap(String path){
        /**@REQUIRES: None;
         * @MODIFIES: map;
         * @EFFECTS:
         * \exist int i,j;0<=i<SIZE，0<=j<SIZE;map[i][j]!=0;
         */
        File file = new File(path);
        for (int i=0;i<SIZE;i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = 0;
                link[i][j] = 0;
            }
        }
        if (!file.exists()){
            System.out.println("your map path is wrong");
            makemap();
        }
        Reader in = null;
        try{
            in = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            char temp;
            int i=0,j=0,count=0;
            boolean border = false;
            while((tempchar = in.read())!=-1){
                temp = (char) tempchar;
                if (border){
                    System.out.println("INVALID INPUT");
                    break;
                }
                if ((temp==' ')||(temp=='\t')){
                    continue;
                }
                if (temp=='\n'){
                    if (count!=80){
                        System.out.println("INPUT HAS WRONG '\\n'");
                        break;
                    }
                    count = 0;
                }
                else {
                    if ((temp>='0')&&(temp<'4')){
                        int t = temp - '0';
                        map[i][j] = t;
                        link[i][j] += t;
                        if ((t&1)==1){
                            link[i][j+1] += 4;
                        }
                        if ((t&2)==2){
                            link[i+1][j] += 8;
                        }
                        j++;
                        count++;
                        if (j==SIZE){
                            j = 0;
                            i++;
                        }
                        if (i==SIZE){
                            border = true;
                        }
                    }
                    else if (temp=='\r'){
                        continue;
                    }
                    else{
                        System.out.println("INPUT HAS WRONG CHAR");
                        break;
                    }
                }
            }
        }catch (IOException e){System.out.println("Map.make()");}
        guiInfo.map = map;
        guiInfo.initmatrix();
    }

//    public void printmap(){
//        int i=0,j=0;
//        for (i=0;i<SIZE;i++){
//            for (j=0;j<SIZE;j++){
////                System.out.print(map[i][j]);
//                System.out.print("@");
//                if ((map[i][j]&1)==1){
//                    System.out.print(" - ");
//                }
//                else {
//                    System.out.print("   ");
//                }
//            }
//            System.out.println();
//            for (j=0;j<SIZE;j++){
//                if ((map[i][j]&2)==2){
//                    System.out.print("|");
//                }
//                else {
//                    System.out.print(" ");
//                }
//                System.out.print("   ");
//            }
//            System.out.println();
//        }
//    }   //TaxiGUI也实现了
//    public void printmapinnumber(){
//        int i=0,j=0;
//        for (i=0;i<SIZE;i++) {
//            for (j = 0; j < SIZE; j++) {
//                System.out.print(map[i][j]+" ");
//            }
//            System.out.println();
//        }
//    }

    public int[][] getMap(){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * \result == map;
         */
        return map;
    }

    public int[][] getLink(){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * \result == link;
         */
        return link;
    }

    public boolean Linkup(Point point){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * \result == ((0<point.x<SIZE)&&(0<=point.y<SIZE))&&((map[point.x-1][point.y]==2)||(map[point.x-1][point.y]==3));
         */
        if ((point.x>=80)||(point.x<0)||(point.y<0)||(point.y>=80)){
            return false;
        }
        else if ((link[point.x][point.y]&up)==up){
            return true;
        }
        else return false;
    }
    public boolean Linkleft(Point point){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * \result == ((0<=point.x<SIZE)&&(0<point.y<SIZE))&&((map[point.x][point.y-1]==1)||(map[point.x][point.y-1]==3));
         */
        if ((point.x>=80)||(point.x<0)||(point.y<0)||(point.y>=80)){
            return false;
        }
        else if ((link[point.x][point.y]&left)==left){
            return true;
        }
        else return false;
    }
    public boolean Linkdown(Point point){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * \result == ((0<=point.x<SIZE-1)&&(0<=point.y<SIZE))&&((map[point.x][point.y]==2)||(map[point.x][point.y]==3));
         */
        if ((point.x>=80)||(point.x<0)||(point.y<0)||(point.y>=80)){
            return false;
        }
        else if ((link[point.x][point.y]&down)==down){
            return true;
        }
        else return false;
    }
    public boolean Linkright(Point point){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * \result == ((0<=point.x<SIZE)&&(0<=point.y<SIZE-1))&&((map[point.x][point.y]==1)||(map[point.x][point.y]==3));
         */
        if ((point.x>=80)||(point.x<0)||(point.y<0)||(point.y>=80)){
            return false;
        }
        else if ((link[point.x][point.y]&right)==right){
            return true;
        }
        else return false;
    }

    public int transfer(int x1,int y1,int x2,int y2){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * \result == (159*x1+y1+0.5*(y2-y1)+79.5*(x2-x1)-0.5);
         */
        int res = (int)(159*x1+y1+0.5*(y2-y1)+79.5*(x2-x1)-0.5);
        if ((res>=0)&&(res<12640)){
            return res;
        }
        else {
            System.out.println("transfer is wrong");
            return 0;
        }
    }

    static volatile boolean clear = false;

    public void addflow(int x1,int y1,int x2,int y2){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * flow[transfer(x1,y1,x2,y2)] == \old([transfer(x1,y1,x2,y2)]) + 1;
         */
        if (clear){
            try {
                wait();
            }catch (Exception e){}
        }
        flow[transfer(x1,y1,x2,y2)]++;
    }

    public void setFlow(int x1,int y1,int x2,int y2,int flow){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * (0<=x1<80)&&(0<=x2<80)&&(0<=y1<80)&&(0<=y2<80)==>(this.flow[transfer(x1, y1, x2, y2)] == flow);
         */
        if ((x1>=80)||(x1<0)||(y1<0)||(y1>=80)||(x2>=80)||(x2<0)||(y2<0)||(y2>=80)||(flow<0)) {
            System.out.println("your file input , #flow is wrong");
        }
        else {
            this.flow[transfer(x1, y1, x2, y2)] = flow;
        }
    }

    public static void clearflow(){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * \all int i;0<=i<12640;flow[i] == 0;
         */
        clear = true;
//        int j=0;
        for (int i=0;i<12640;i++){
//            j += flow[i];
            flow[i] = 0;
        }
//        System.out.println(j);
        clear = false;
    }

    public int getflow(int x,int y,int direction){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * (direction == 8)==>(\result == flow[transfer(x,y,x-1,y)]);
         * (direction == 4)==>(\result == flow[transfer(x,y,x,y-1)]);
         * (direction == 2)==>(\result == flow[transfer(x,y,x+1,y)]);
         * (direction == 1)==>(\result == flow[transfer(x,y,x,y+1)]);
         * (direction != 1)&&(direction != 2)&&(direction != 4)&&(direction != 8)==>(\result == 101);
         */
        if (clear){
            try {
                wait();
            }catch (Exception e){}
        }
        if (direction==up){
            return flow[transfer(x,y,x-1,y)];
        }
        else if (direction==down){
            return flow[transfer(x,y,x+1,y)];
        }
        else if (direction==left){
            return flow[transfer(x,y,x,y-1)];
        }
        else if (direction==right){
            return flow[transfer(x,y,x,y+1)];
        }
        else {
            return 101;
        }
    }

    public int getflow(Point point,int direction){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS: None;
         */
        return getflow(point.x,point.y,direction);
    }

    public static int getflow(int p1,int p2){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * \result == flow[(int)(159*x1+y1+0.5*(y2-y1)+79.5*(x2-x1)-0.5)];
         */
        int x1,y1,x2,y2;
        x1 = p1/80;
        y1 = p1%80;
        x2 = p2/80;
        y2 = p2%80;
        return flow[(int)(159*x1+y1+0.5*(y2-y1)+79.5*(x2-x1)-0.5)];
    }

    public Point mypointbfs2(Point start,Point aim){
        /**@REQUIRES: None;
         * @MODIFIES: None;
         * @EFFECTS:
         * \result == next;
         */
        int mapchange = this.mapchange;
        guiInfo.mypointbfs(aim.x*80+aim.y,start);
        int length = guiInfo.D[aim.x*80+aim.y][start.x*80+start.y];
//        Point[] path = new Point[length];
//        for (int i=0;i<path.length;i++){
//            path[i] = new Point();
//        }
        Point next = new Point();
        next.setLocation(start);
//        for (int i=length-1;i>=0;i--) {
//            path[i].setLocation(next);
        if (Linkup(next) && (guiInfo.D[aim.x*80+aim.y][start.x*80+start.y-80] == (length-1)) && (guiInfo.flow[aim.x*80+aim.y][start.x*80+start.y-80] == guiInfo.flow[aim.x*80+aim.y][start.x*80+start.y] - getflow(start,up))) {
            next.translate(-1, 0);
        } else if (Linkleft(next) && (guiInfo.D[aim.x*80+aim.y][start.x*80+start.y-1] == (length-1)) && (guiInfo.flow[aim.x*80+aim.y][start.x*80+start.y-1] == guiInfo.flow[aim.x*80+aim.y][start.x*80+start.y] - getflow(start,left))) {
            next.translate(0, -1);
        } else if (Linkdown(next) && (guiInfo.D[aim.x*80+aim.y][start.x*80+start.y+80] == (length-1)) && (guiInfo.flow[aim.x*80+aim.y][start.x*80+start.y+80] == guiInfo.flow[aim.x*80+aim.y][start.x*80+start.y] - getflow(start,down))) {
            next.translate(1, 0);
        } else if (Linkright(next) && (guiInfo.D[aim.x*80+aim.y][start.x*80+start.y+1] == (length-1)) && (guiInfo.flow[aim.x*80+aim.y][start.x*80+start.y+1] == guiInfo.flow[aim.x*80+aim.y][start.x*80+start.y] - getflow(start,right))) {
            next.translate(0, 1);
        } else if (Linkup(next) && (guiInfo.D[start.x*80+start.y][next.x*80+next.y-80] == (length-1))) {
            next.translate(-1, 0);
        } else if (Linkleft(next) && (guiInfo.D[start.x*80+start.y][next.x*80+next.y-1] == (length-1))) {
            next.translate(0, -1);
        } else if (Linkdown(next) && (guiInfo.D[start.x*80+start.y][next.x*80+next.y+80] == (length-1))) {
            next.translate(1, 0);
        } else if (Linkright(next) && (guiInfo.D[start.x*80+start.y][next.x*80+next.y+1] == (length-1))) {
            next.translate(0, 1);
        }
//        }
//        if (mapchange==this.mapchange) {
        System.out.println("mypointbfs length:\t" + length);
        System.out.println(start + "\t" + aim);
//        }
//        else {
//            path = mypointbfs(start,aim);
//        }
        return next;
    }

    int mapchange=0;

    public void setRoadStatus(Point p1, Point p2, int status){
        /**@REQUIRES: (status==0)||(status==1);
         * @MODIFIES: None;
         * @EFFECTS: None;
         */
        synchronized(map){
            mapchange++;
            if (status==0) {
                if (p1.x == p2.x) {
                    if (p1.y == p2.y + 1) {
                        map[p2.x][p2.y] -= 1;
                        link[p2.x][p2.y] -= right;
                        link[p1.x][p1.y] -= left;
                    } else if (p1.y == p2.y - 1) {
                        map[p1.x][p1.y] -= 1;
                        link[p2.x][p2.y] -= left;
                        link[p1.x][p1.y] -= right;
                    }
                } else if (p1.y == p2.y) {
                    if (p1.x == p2.x + 1) {
                        map[p2.x][p2.y] -= 2;
                        link[p2.x][p2.y] -= down;
                        link[p1.x][p1.y] -= up;
                    } else if (p1.x == p2.x - 1) {
                        map[p1.x][p1.y] -= 2;
                        link[p2.x][p2.y] -= up;
                        link[p1.x][p1.y] -= down;
                    }
                }
            }
            else if (status==1) {
                if (p1.x == p2.x) {
                    if (p1.y == p2.y + 1) {
                        map[p2.x][p2.y] += 1;
                        link[p2.x][p2.y] += right;
                        link[p1.x][p1.y] += left;
                    } else if (p1.y == p2.y - 1) {
                        map[p1.x][p1.y] += 1;
                        link[p2.x][p2.y] += left;
                        link[p1.x][p1.y] += right;
                    }
                } else if (p1.y == p2.y) {
                    if (p1.x == p2.x + 1) {
                        map[p2.x][p2.y] += 2;
                        link[p2.x][p2.y] += down;
                        link[p1.x][p1.y] += up;
                    } else if (p1.x == p2.x - 1) {
                        map[p1.x][p1.y] += 2;
                        link[p2.x][p2.y] += up;
                        link[p1.x][p1.y] += down;
                    }
                }
            }
        }
        taxiGUI.SetRoadStatus(p1,p2,status);
    }
}