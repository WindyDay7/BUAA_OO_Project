package demo2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * 模拟银行叫号系统
 */
public class CallNum {
	public static void main(String[] args) throws InterruptedException {  
		//创建服务中心,如一个银行的营业厅
		Center center = new Center(10);  
        ExecutorService exec = Executors.newCachedThreadPool();  
      
        Producer producer = new Producer(center);  
        Consumer consumer = new Consumer(center);  

         //模拟产生客户 
        exec.execute(producer);  
        //模拟10个柜台
        for (int i = 0; i < 10; i++) {  
            exec.execute(consumer);  
        }  
       
        exec.shutdown();  
    
    }
}
