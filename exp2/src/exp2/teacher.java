package exp2;

import java.util.Date;

class Teacher extends Person  implements PrintInfo{

	Teacher(String name, char sex, int age, String dep, String tno) {
		//2018_03_30-16061106-nozai2
		//如果子类构造方法中既没有显式调用父类的构造方法，而父类又没有无参数的构造方法，则编译出错。
		super(name, sex, age);  
		this.departmentno = dep;
		this.tno = tno;
	}
	
	public String departmentno,tno;
	double salary;
	public Date hiredate;
	
	
	public String printBasicInfo(){
        System.out.println(name+"  "+tno+"     "+departmentno);
        return name+"  "+tno+"     "+departmentno;
    				
	}
	
	
	public String printDetailInfo(){
        System.out.println(name+"  "+sex+"    "+age+"  "+tno+"     "+departmentno+"          "+hiredate);
        return name+"  "+sex+"    "+age+"  "+tno+"     "+departmentno+"          "+hiredate;
    				
	}
	
    public void register(){   
    	//2018_03_30-16061106-no3
    	//子类调用父类的方法时，子类的方法的范围要大于父类
    	this.salary =6000;
       	this.hiredate=new Date();
	    				
		}    
 }