package exp2;

public class TestPerson {
	public static void main(String[] args)
	{
		Person[] person=new Person[4];
		
		
		person[0]=new Student("Jin",'M',20,"201504135146","061501");//添加一条学生信息：Jin,Male,20岁，学号201504135146，班号061501
		Student stu2 = (Student) person[0];  
		//2018_03_30-16061106-no4
		//根据多态的向上转型，person[0]时父类的类型，所以要加一个Student表示向下转型为Student
		stu2.register();//学生注册
	    stu2.updateAge(23);//更新该生的年龄 
	    stu2.toString();//打印输出
	    
	    
	    person[1]=new Student("Kate",'F',21,"unknown","unknown");//添加一条学生信息，Kate,Female,21岁，学号班号暂时未知
	    //2018_03_30-16061106-no5
	    //学生的学号和班号为未知，new Student调用子类的构造方法
	    Student stu=(Student)person[1];
	    stu.register();//学生注册
	    stu.updateAge(25);//更新该生的年龄
	    stu.toString();//打印输出
	    stu.printBasicInfo();//打印输出基本信息
	    
	    person[2]=new Teacher("Rene",'M',35,"06","01452");//添加一条教师信息，Rene,Male,35岁，6系，工号为01452
	    Teacher te1=(Teacher)person[2];
	    //2018_03_30-16061106-no6
	    //向下转型为教师
	    te1.register();//完成教师的注册，记录注册时间并设定基本薪资
	    te1.toString();
	    	
	    person[3]=new Teacher("Jason",'M',41,"unknown","unknown");//添加一条教师信息，Jason,Male,41岁，院系和工号暂时未知
	    //2018_03_30-16061106-no7
	    //老师的院系和工号暂时为未知
	    Teacher te=(Teacher)person[3];
	    te.register();//教师注册
	    te.printDetailInfo();//打印输出
	    }
	}   
		
		
