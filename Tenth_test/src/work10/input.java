package work10;
/**
 * @overview 对输入处理，包括命令行的输入处理，文件处理等。
 *
 */
import java.util.Scanner;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;
import java.awt.Point;
public class input implements Runnable{
	private requireList customers;
	private Vector<taxi> tl;
	input(requireList rl, Vector<taxi> tl) {
		 /**
         * @REQUIRES:rl!=None;
         * 			tl!=None;
         * @MODIFIES:
         *      \this.customers;
         *      \this.tl;
         * @EFFECTS:
         *     \this.customers=rl;
         *      \this.tl=tl;
         */
		customers = rl;
		this.tl = tl;
	}
	public boolean repOK() {
		 /**
         * @REQUIRES:None
         * @MODIFIES:
         *     		None
         * @EFFECTS:
         *     \result =(customers!=null)&&(tl!=null);
         */
		return (customers!=null)&&
				(tl!=null);
	}
	public void require(String s,long t) {
		 /**
         * @REQUIRES:s!=None;
         * 			t!=None;
         * @MODIFIES:
         *      \this.customers;
         * @EFFECTS:
         *     满足输入条件的请求，并且不是同志请求，不是输入地点与目的地相同的地点的请求将会被加入到乘客的请求队列当中
         */
		String line=s;
		long time=t;
		int i = 5;
		char c = line.charAt(i);
		int j = i;
		while (line.charAt(j) != ',') {
			j++;
		}
		int begin1 = Integer.parseInt(line.substring(i, j));
		j++;
		i = j;
		while (line.charAt(j) != ')') {
			j++;
		}
		int begin2 = Integer.parseInt(line.substring(i, j));
		j = j + 3;
		i = j;
		while (line.charAt(j) != ',') {
			j++;
		}
		int end1 = Integer.parseInt(line.substring(i, j));
		j++;
		i = j;
		while (line.charAt(j) != ')') {
			j++;
		}
		int end2 = Integer.parseInt(line.substring(i, j));
		if (begin1 == end1 && begin2 == end2)
			System.out.println("输入的起始和终点相同");
		else {
			customer ct = new customer(begin1, begin2, end1, end2, time / 100);
			if (customers.issame(ct))// 判断是有相同的指令，最小的时间单位为100毫秒；
				customers.add(ct);
			else
				System.out.println("两条指令相同");
		}
	}
	public void information(String s) {
		 /**
         * @REQUIRES:s!=None;
         * @MODIFIES:
         *     None;
         * @EFFECTS:
         *     输出所有车辆的信息；
         */
		String line=s;
		if (line.equals("information")) {
			for (int i = 0; i < 100; i++)
				System.out.println(
						"查询出租车的位置为" + tl.get(i).location + "状态为" + tl.get(i).state + "时间为" + gv.getTime());
		} else {
			int num = Integer.parseInt(line.substring(11, line.length()));
			System.out.println(
					"查询出租车的位置为" + tl.get(num).location + "状态为" + tl.get(num).state + "时间为" + gv.getTime());
		}
	}
	public void open(String s)
	{
		 /**
         * @REQUIRES:s!=None;
         * @MODIFIES:
         *      \this.customers;
         * @EFFECTS:
         *     判断路径是否曾经存在过，然后执行关闭或者打开一条已经存在的路径的操作；
         */
		String line=s;
		char opreat=line.charAt(0);
		int x1=0,y1=0,x2=0,y2=0;
		int i=3,j=3;
		char c=line.charAt(i);
		while((c=line.charAt(i))!=',')
			i++;
		String sx1=line.substring(j, i);
		x1=Integer.parseInt(sx1);
		i++;
		j=i;
		while((c=line.charAt(i))!=')')
			i++;
		String sy1=line.substring(j, i);
		y1=Integer.parseInt(sy1);
		i=i+3;
		j=i;
		while((c=line.charAt(i))!=',')
			i++;
		String sx2=line.substring(j, i);
		x2=Integer.parseInt(sx2);
		i++;
		j=i;
		while((c=line.charAt(i))!=')')
			i++;
		String sy2=line.substring(j, i);
		y2=Integer.parseInt(sy2);
		boolean flag = false;
		for(int k=0;k<main.ov[x1*80+y1].size();k++) {
			if(main.ov[x1*80+y1].get(k) == x2*80+y2) flag = true;
		}
		if(!flag){System.out.println("原地图中不存在的道路不能开关，忽略请求");return;}
		
		if(line.charAt(0)=='c') {
			main.gui.SetRoadStatus(new Point(x1,y1),new Point(x2,y2),0);
			for(int m=0;m<main.v[x1*80+y1].size();m++) {
				if(main.v[x1*80+y1].get(m) == x2*80+y2) {
					main.v[x1*80+y1].remove(m);
				}
			}
			for(int m=0;m<main.v[x2*80+y2].size();m++) {
				if(main.v[x2*80+y2].get(m) == x1*80+y1) {
					main.v[x2*80+y2].remove(m);
				}
			}
			main.m.getmapnumber()[x1*80+y1][x2*80+y2]=0;
			main.m.getmapnumber()[x2*80+y2][x1*80+y1]=0;
			
		}
		else {
			main.gui.SetRoadStatus(new Point(x1,y1),new Point(x2,y2),1);
			main.v[x1*80+y1].add(x2*80+y2);
			main.v[x2*80+y2].add(x1*80+y1);
			main.m.getmapnumber()[x1*80+y1][x2*80+y2]=1;
			main.m.getmapnumber()[x2*80+y2][x1*80+y1]=2;
			
		}
	}
	public void file(String a) {
		 /**
         * @REQUIRES:a!=None;
         * @MODIFIES:
         *      \this.tl;
         *      \main.m;
         *      \this.customers;
         *      \main.light;
         * @EFFECTS:
         *     ；根据文件当中输入的内容进行对地图的更新操作，读入红绿灯文件，或者对每辆车的状态或者位置改变，或者改变车的信用，改变在上一个500ms的道路的流量，或者执行相应的请求。
         */
		String line=a;
		String s;
		s=line.substring(5, line.length());
		File f=new File(s);
		BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(f));
	            String l = null;
	            while ((l = reader.readLine()) != null) {
	                if(l.equals("#map")) {
	                	l=reader.readLine();
	                	if(!l.equals("#end_map")) {
	                		main.MakeMap(l);
	                	}
	                	continue;
	                }
	                else if(l.equals("#flow")) {
	                	l=reader.readLine();
	                	int x1=0,x2=0,y1=0,y2=0,n=0;
	                	if(!l.equals("#end_flow")) {
	                		//System.out.println(l);
		                	int i=0;
		                	//System.out.println(l.length());
		                		char c=l.charAt(i);
		                		while((c=l.charAt(i))!=',')
		                		{
		                			i++;
		                		}
		                		String sx1=l.substring(1, i);
		                		x1=Integer.parseInt(sx1);
		                		int j=i+1;
		                		while((c=l.charAt(i))!=')')
		                			i++;
		                		String sy1=l.substring(j, i);
		                		y1=Integer.parseInt(sy1);
		                		i=i+2;
		                		j=i+1;
		                		while((c=l.charAt(i))!=',')
		                			i++;
		                		String sx2=l.substring(j, i);
		                		x2=Integer.parseInt(sx2);
		                		j=i+1;
		                		while((c=l.charAt(i))!=')')
		                			i++;
		                		String sy2=l.substring(j, i);
		                		y2=Integer.parseInt(sy2);
		                		i=i+2;
		                		j=i;
		                		while(i<l.length())
		                			i++;
		                		String sn=l.substring(j, i);
		                		n=Integer.parseInt(sn);
		                	for(int y=0;y<n;y++) {
		                		guigv.AddFlow(x1, y1, x2, y2);
		                	}
	                	}
	                	else continue;
		            }
	                else if(l.equals("#taxi")) {
	                	l=reader.readLine();	                	
	                	while(!l.equals("#end_taxi")) {
	                		int num=0;
	                		int state=0;
	                		int x=0,y=0;
	                		int i=0;
	                		int cre;
	                		char c=l.charAt(i);
	                		while((c=l.charAt(i))!=' ')
	                			i++;
	                		int j=0;
	                		String snum=l.substring(j,i);
	                		num=Integer.parseInt(snum);
	                		j=i+1;
	                		i++;
	                		while((c=l.charAt(i))!=' ')
	                			i++;
	                		String sstate=l.substring(j,i);
	                		state=Integer.parseInt(sstate);
	                		j=i+1;
	                		i++;
	                		while((c=l.charAt(i))!=' ')
	                			i++;
	                		String scre=l.substring(j, i);
	                		cre=Integer.parseInt(scre);
	                		i++;//c=(
	                		if(i<l.length()) {
	                		j=i+1;
	                		while((c=l.charAt(i))!=',')
	                			i++;
	                		String sx=l.substring(j, i);
	                		x=Integer.parseInt(sx);
	                		j=i+1;
	                		while((c=l.charAt(i))!=')')
	                			i++;
	                		String sy=l.substring(j, i);
	                		y=Integer.parseInt(sy);
	                		}
	                		else {
	                			Random rand=new Random();
	                			x=rand.nextInt(79);
	                			y=rand.nextInt(79);
	                		}
	                		tl.get(num).state=state;
	                		tl.get(num).cre=cre;
	                		tl.get(num).location=x*80+y;
	                		l=reader.readLine();
	                	}
	                }
	                else if(l.equals("#request")) {
	                	l=reader.readLine();
	                	while(!l.equals("#end_request")) {
	                		long t = gv.getTime();
	                		this.require(l, t);
	                		l=reader.readLine();
	                	}
	                }
	                else if(l.equals("#light")) {
	                	l=reader.readLine();
	                	if(l.equals("#end_light"))
	                		;
//	                	System.out.println(l);
	                	else {
	                	File f1=new File(l);
	                	BufferedReader reader1 = null;
	        	        try {
	        	            reader1 = new BufferedReader(new FileReader(f1));
	        	            String l1 = null;
	        	            while ((l1= reader1.readLine()) != null) {
	//        	            	System.out.println(l1);
	        	            	for(int i=0;i<l1.length();i++) {
	        	            		main.light.add(l1.charAt(i)-'0');
	        	            	}
	        	            }
	        	         
	       // 	            for(int i=0;i<main.light.size();i++)
	        //	            System.out.println(main.light.get(i));
	        	        }
	        	        catch (IOException e) {
	        		            e.printStackTrace();
	        		        }
	        	        finally {
	        	            if (reader1 != null) {
	        	                try {
	        	                    reader1.close();
	        	                } catch (IOException e1) {
	        	                }
	        	            }
	        	        }
	                	}
	                }
	            }
	            reader.close();
	        } 
	        catch (IOException e) {
	            e.printStackTrace();
	        } 
	        finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	}
	public void run() {
		 /**
         * @REQUIRES:None;
         * @MODIFIES:
         *      None;
         * @EFFECTS:
         *     根据控制台的输入判断是哪个类型的指令，并进入相应的函数当中进行运算。若是输入不符合要求的命令则输出输入错误。
         */
		Scanner input = new Scanner(System.in);
		String p = "\\[CR,\\(([0-9]|[1-7][0-9]),([0-9]|[1-7][0-9])\\),\\(([0-9]|[1-7][0-9]),([0-9]|[1-7][0-9])\\)\\]";
		String information = "information";
		String open="(o|c) \\(([0-9]|[1-7][0-9]),([0-9]|[1-7][0-9])\\),\\(([0-9]|[1-7][0-9]),([0-9]|[1-7][0-9])\\)";
		String file="Load .+";
		String line = input.nextLine();
		while (true) {
			long time = gv.getTime();
			if(line.equals("END"))
				break;
			if (line.matches(p)) {
				this.require(line, time);
			} else if (line.matches(information)) {
				this.information(line);
			}else if(line.matches(open)){
				this.open(line);
			}
			else if(line.matches(file)) {
				this.file(line);
			}
			else 
				System.out.println("输入错误");
			line = input.nextLine();
		}
	}
}
