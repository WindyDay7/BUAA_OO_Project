package demo1;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
public class CalNum {

/** ���Գ������**/
	 
	    public static void main(String[] args){
	    	
	    	//�������������߳���
	        Object producerMonitor = new Object();
	        Object consumerMonitor = new Object();
	        
	        //�趨һ���㹻�������
	        Container<Customer> container = new Container<Customer>(10000);
	        
	        //�������߳�����
	        new Thread(new Producer(producerMonitor,consumerMonitor,container)).start();
	        //�������߳�������ģ��10����̨
	        for(int i=1;i<=10;i++){
	         { new Thread(new Consumer(producerMonitor,consumerMonitor,container,i)).start();
	         }
	 	  }
	   }  
     }
	