package demo2;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
/**
 ��������
 
 */
public class Center extends Thread {  
    private BlockingQueue<Waiter> waiters;  
    private BlockingQueue<Customer> customers;  
  
   
    private final static int PRODUCERSLEEPSEED = 3000;  
    private final static int CONSUMERSLEEPSEED = 100000;  
  
    public Center(int num) {  
    	//�����ṩ����Ĺ�̨���к�ȡ�úŵĿͻ�����
       
    }  
       //ȡ�Ż������ȴ�����Ŀͻ���ע��ģ��ǰ�������ͻ�֮���ʱ����
    public void produce() {  
       
        
    }  
  //�ͻ���÷�����ע���̰߳�ȫ��ʵ��
    public void consume() {  
        
    }  
} 