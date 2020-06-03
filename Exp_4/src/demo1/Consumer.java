package demo1;

import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * ������
*/
public class Consumer implements Runnable{
	 private int windowId;
    
    //ģ���������������ó�final���͵Ļ��������ٴθ�ֵ
    private final Container<Customer> container;
    //�������̼߳�����
    private final Object producerMonitor;
    //�������̼߳�����
    private final Object consumerMonitor;
    
    
    private final static int CONSUMERSLEEPSEED = 100000;  
    
    public Consumer(Object producerMonitor,Object consumerMonitor,Container<Customer> container,int id){
        this.producerMonitor = producerMonitor;
        this.consumerMonitor = consumerMonitor;
        this.container = container;
        this.windowId=id;
    }

    @Override
    public synchronized void run() {
        while(true){
        	while(container.isEmpty()) {   
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
            consume();
            notify();
        }
    }
    
    //����
    public void consume(){
        	container.get();
        }       
    }
