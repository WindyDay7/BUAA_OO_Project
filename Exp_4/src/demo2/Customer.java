package demo2;

public class Customer {
	//ģ��ȡ�Ż�������ÿ���Զ��������õ��ͻ���ţ������С��10��ǰ���0����01,02
	private final int id = counter++;  
    private static int counter = 1;  
    public String toString() {  
        if (id > 9) {  
            return "Customer [id=" + id + "]";  
        }  
        return "Customer [id=0" + id + "]";  
    }  
}
