package demo1;

import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * 生产者
 *
 *
 */
public class Producer implements Runnable {
    //生产容器，设置成final类型的话不允许再次赋值
    private final Container<Customer> container;
    
    //生产者线程监听器
    private final Object producerMonitor;
    
    //消费者线程监听器
    private final Object consumerMonitor;
    private final static int PRODUCERSLEEPSEED = 3000;  
    
   
    public Producer(Object producerMonitor,Object consumerMonitor,Container<Customer> container){
        this.producerMonitor = producerMonitor;
        this.consumerMonitor = consumerMonitor;
        this.container = container;
    }

    
    @Override
    public synchronized void run() {
        while(true){
        	while(container.isFull()) {   
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
        	produce();
        	notifyAll();
        }
    }
    //取号机生产等待服务的客户，注意模拟前后两个客户之间的时间间隔
    public void produce(){
    	Customer cus = new Customer();
    	container.add(cus);
    	System.out.println(cus.toString() + "is waiting");
    }
    
   
}