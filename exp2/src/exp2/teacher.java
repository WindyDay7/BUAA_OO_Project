package exp2;

import java.util.Date;

class Teacher extends Person  implements PrintInfo{

	Teacher(String name, char sex, int age, String dep, String tno) {
		//2018_03_30-16061106-nozai2
		//������๹�췽���м�û����ʽ���ø���Ĺ��췽������������û���޲����Ĺ��췽������������
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
    	//������ø���ķ���ʱ������ķ����ķ�ΧҪ���ڸ���
    	this.salary =6000;
       	this.hiredate=new Date();
	    				
		}    
 }