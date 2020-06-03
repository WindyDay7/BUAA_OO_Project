package OO3;
import java.util.regex.Matcher;
import java.lang.Double;
import java.util.regex.Pattern;

public class Request {
	private int direction = 0, destination = 0, num = 0;
	private double time = 0;
	private boolean exc = true;
	private boolean ifin = false;
	
	public Request(int a, int b, double c) {
		direction = b;
		destination = a;
		time = c;
		exc = true;
		num = 0;
		ifin = false;
	}
	
	public Request() {
		direction = 0;
		destination = 0;
		time = 0;
		exc = true;
		num = 0;
		ifin = false;
	}
	
	public int getdirection() {
		return direction;
	}
	
	public int getdestination() {
		return destination;
	}
	
	public double gettime() {
		return time;
	}
	
	public static boolean reMatch (String str) {
		//System.out.println(str);
		try {
		String pattern = "(^\\(((FR,\\+?0{0,50}((10)|[1-9]),((UP)|(DOWN)),\\+?\\d{1,50})|(ER,\\+?0{0,50}((10)|[1-9]),\\+?\\d{1,50}))\\)$)";

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		return (m.matches());
		}
		catch (StackOverflowError e) {
			return false;
		}
	}
	
	public static Request getRequest (String str) {
		String[] strs = str.split("[(),]");
		if (strs.length == 5) {
			if (strs[3].equals("UP")) {
				return (new Request(Integer.parseInt(strs[2]),1,Double.parseDouble(strs[4])));
			}
			else {
				return (new Request(Integer.parseInt(strs[2]),-1,Double.parseDouble(strs[4])));
			}
		}
		else return (new Request(Integer.parseInt(strs[2]),0,Double.parseDouble(strs[3])));
	}
	
	public boolean check(Elevator ele, Floor floor) {
		if (direction == 0) {
			//System.out.printf("floor:%d   type:ELE   button:%b\n", destination, ele.getbutton(destination));
			return (!ele.getbutton(destination));
		}
		else if (direction == 1) {
			//System.out.printf("floor:%d   type:UP   button:%b\n", destination, floor.getup(destination));
			return (!floor.getup(destination));
		}
		else if(direction == -1) {
			//System.out.printf("floor:%d   type:DOWN   button:%b\n", destination, floor.getdown(destination));
			return (!floor.getdown(destination));
		}
		else return false;
	}
	
	public void printr() {
		if (direction==0)
			System.out.printf("[ER,%d,%.0f]", destination,time);
		else if (direction==-1)
			System.out.printf("[FR,%d,DOWN,%.0f]", destination,time);
		else
			System.out.printf("[FR,%d,UP,%.0f]", destination,time);
	}
	
	public void printr2() {
		if (direction==0)
			System.out.printf("[(ER,%d,%.0f)]", destination,time);
		else if (direction==-1)
			System.out.printf("[(FR,%d,DOWN,%.0f)]", destination,time);
		else
			System.out.printf("[(FR,%d,UP,%.0f)]", destination,time);
	}
	
	public boolean getexc() {
		return exc;
	}
	
	public void setexc(boolean b) {
		exc = b;
	}
	
	public int getnum() {
		return num;
	}
	
	public void setnum(int n) {
		num = n;
	}
	
	public boolean getifin() {
		return ifin;
	}
	
	public void setifin(boolean b) {
		ifin = b;
	}
}
