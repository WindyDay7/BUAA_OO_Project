package exp2;

public class Company {
	public static void main(String[] args) {
		
		Employee[] emp=new Employee[4];
		
		emp[0]=new Yanfa(1,1,1);
		Yanfa y1 = (Yanfa) emp[0];
		y1.getSalary();
		y1.baoxiao("����",1, 800,"������Ŷ",1);   //��ɫΪ1����ɫΪ2��AΪ1�� BΪ2�� CΪ3
		
		emp[1]=new Shichang(2);
		Shichang s1 = (Shichang) emp[1];
		s1.getSalary();
		s1.baoxiao("����",2, 20000,null,2); 
		
		emp[2]=new Caiwu();
		Caiwu c1 = (Caiwu) emp[2];
		c1.getSalary();
		c1.baoxiao(name, kind, money, way, cloor);
		
		
		
	}
}
