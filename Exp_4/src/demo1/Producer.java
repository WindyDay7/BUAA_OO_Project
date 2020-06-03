package demo1;

import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * ������
 *
 *
 */
public class Producer implements Runnable {
    //�������������ó�final���͵Ļ��������ٴθ�ֵ
    private final Container<Customer> container;
    
    //�������̼߳�����
    private final Object producerMonitor;
    
    //�������̼߳�����
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
    //ȡ�Ż������ȴ�����Ŀͻ���ע��ģ��ǰ�������ͻ�֮���ʱ����
    public void produce(){
    	Customer cus = new Customer();
    	container.add(cus);
    	System.out.println(cus.toString() + "is waiting");
    }
    
   
}