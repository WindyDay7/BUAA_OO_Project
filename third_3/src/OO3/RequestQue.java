package OO3;

public class RequestQue {
	private Request[] rlist = new Request[100];
	private int len = 0;
	
	public void setr(Request r) {
		double max = 4294967295.0;
		if (len == 0 && !(r.gettime()==0 && r.getdestination()==1 && r.getdirection()==1)) {
			System.out.printf("INVALID");
			r.printr2();
			System.out.printf("\n");
	//		System.out.printf("ERROR\n");
		}
		else if(r.getdestination()==1 && r.getdirection()==-1) {
			System.out.printf("INVALID");
			r.printr2();
			System.out.printf("\n");
//			System.out.printf("ERROR\n");
		}
		else if(r.getdestination()==10 && r.getdirection()==1) {
			System.out.printf("INVALID");
			r.printr2();
			System.out.printf("\n");
//			System.out.printf("ERROR\n");
		}
		else if(len>0 && r.gettime()<rlist[len-1].gettime()) {
			System.out.printf("INVALID");
			r.printr2();
			System.out.printf("\n");
//			System.out.printf("ERROR\n");
		}
		else if (r.gettime()>max) {
			System.out.printf("INVALID");
			r.printr2();
			System.out.printf("\n");
//			System.out.printf("ERROR\n");
		}
		else {
			rlist[len] = r;
			r.setnum(len);
			len++;
		}
		
		
	}
	
	public Request getr(int i) {
		return rlist[i];
	}
	
	public int getlen() {
		return len;
	}
	
	public int getdestination(int i) {
		return rlist[i].getdestination();
	}
	
	public int getdirection(int i) {
		return rlist[i].getdirection();
	}

	public double gettime(int i) {
		return rlist[i].gettime();
	}
	
	public boolean getexc(int i) {
		return rlist[i].getexc();
	}
	
	public void setexc(int i, boolean b) {
		rlist[i].setexc(b);
	}
	
	public boolean getifin(int i) {
		return rlist[i].getifin();
	}
	
	public void setifin(int i, boolean b) {
		rlist[i].setifin(b);
	}
}
