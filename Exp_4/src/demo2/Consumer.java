package demo2;
/**�������̣߳�ģ��ͻ����ҵ�������,������Ա���о�������һλ�ͻ����������
 */
public class Consumer implements Runnable {  
    private Center center;  
  
    public Consumer(Center center) {  
        this.center = center;  
    }  
  
    @Override  
    public void run() {  
        while (!Thread.interrupted()) {  
            center.consume();  
        }  
    }  
  
}  

