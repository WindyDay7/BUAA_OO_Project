package exp2;

public class Company {
	public static void main(String[] args) {
		
		Employee[] emp=new Employee[4];
		
		emp[0]=new Yanfa(1,1,1);
		Yanfa y1 = (Yanfa) emp[0];
		y1.getSalary();
		y1.baoxiao("李四",1, 800,"麦子来哦",1);   //黄色为1，粉色为2，A为1， B为2， C为3
		
		emp[1]=new Shichang(2);
		Shichang s1 = (Shichang) emp[1];
		s1.getSalary();
		s1.baoxiao("张三",2, 20000,null,2); 
		
		emp[2]=new Caiwu();
		Caiwu c1 = (Caiwu) emp[2];
		c1.getSalary();
		c1.baoxiao(name, kind, money, way, cloor);
		
		
		
	}
}
