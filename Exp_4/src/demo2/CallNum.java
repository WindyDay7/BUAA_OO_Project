package demo2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * ģ�����нк�ϵͳ
 */
public class CallNum {
	public static void main(String[] args) throws InterruptedException {  
		//������������,��һ�����е�Ӫҵ��
		Center center = new Center(10);  
        ExecutorService exec = Executors.newCachedThreadPool();  
      
        Producer producer = new Producer(center);  
        Consumer consumer = new Consumer(center);  

         //ģ������ͻ� 
        exec.execute(producer);  
        //ģ��10����̨
        for (int i = 0; i < 10; i++) {  
            exec.execute(consumer);  
        }  
       
        exec.shutdown();  
    
    }
}
