package demo2;
/*�������߳�,ģ�����й�����Ա�������һλ�ͻ��󣬷�����Դ�ͷţ���ʼ׼��������һλ�ͻ�*/

public class Producer implements Runnable {  
    private Center center;  
  
    public Producer(Center center) {  
        this.center = center;  
    }  
  
    @Override  
    public void run() {  
        while (!Thread.interrupted()) {  
        	
            center.produce();  
        }  
    }  
}  


