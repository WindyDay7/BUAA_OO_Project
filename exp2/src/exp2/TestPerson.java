package exp2;

public class TestPerson {
	public static void main(String[] args)
	{
		Person[] person=new Person[4];
		
		
		person[0]=new Student("Jin",'M',20,"201504135146","061501");//���һ��ѧ����Ϣ��Jin,Male,20�꣬ѧ��201504135146�����061501
		Student stu2 = (Student) person[0];  
		//2018_03_30-16061106-no4
		//���ݶ�̬������ת�ͣ�person[0]ʱ��������ͣ�����Ҫ��һ��Student��ʾ����ת��ΪStudent
		stu2.register();//ѧ��ע��
	    stu2.updateAge(23);//���¸��������� 
	    stu2.toString();//��ӡ���
	    
	    
	    person[1]=new Student("Kate",'F',21,"unknown","unknown");//���һ��ѧ����Ϣ��Kate,Female,21�꣬ѧ�Ű����ʱδ֪
	    //2018_03_30-16061106-no5
	    //ѧ����ѧ�źͰ��Ϊδ֪��new Student��������Ĺ��췽��
	    Student stu=(Student)person[1];
	    stu.register();//ѧ��ע��
	    stu.updateAge(25);//���¸���������
	    stu.toString();//��ӡ���
	    stu.printBasicInfo();//��ӡ���������Ϣ
	    
	    person[2]=new Teacher("Rene",'M',35,"06","01452");//���һ����ʦ��Ϣ��Rene,Male,35�꣬6ϵ������Ϊ01452
	    Teacher te1=(Teacher)person[2];
	    //2018_03_30-16061106-no6
	    //����ת��Ϊ��ʦ
	    te1.register();//��ɽ�ʦ��ע�ᣬ��¼ע��ʱ�䲢�趨����н��
	    te1.toString();
	    	
	    person[3]=new Teacher("Jason",'M',41,"unknown","unknown");//���һ����ʦ��Ϣ��Jason,Male,41�꣬Ժϵ�͹�����ʱδ֪
	    //2018_03_30-16061106-no7
	    //��ʦ��Ժϵ�͹�����ʱΪδ֪
	    Teacher te=(Teacher)person[3];
	    te.register();//��ʦע��
	    te.printDetailInfo();//��ӡ���
	    }
	}   
		
		
