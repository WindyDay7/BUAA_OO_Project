package demo1;

import java.util.ArrayList;
import java.util.List;


/**
 * װ��Ʒ������

 *
 */
public class Container<T> {
    
    private final int capacity;
    
    private final List<T> list;
    
    public Container(int capacity){
        this.capacity = capacity;
        list = new ArrayList<T>(capacity);
    }
    
    public List<T> getList(){
        return list;
    }
    
    
    /**
     * ����������Ӳ�Ʒ 
     * @param product
     */
    public synchronized void add(T product){
        list.add(product);
    }
    
    /**
     * �����Ƿ�����
     * @return
     */
    public synchronized boolean isFull(){
        if(this.list.size()==this.capacity) {
        	return true;
        }
        return false;
    }
    
    
    /**
     * �����Ƿ�Ϊ��
     * @return
     */
    public synchronized boolean isEmpty(){
        if(this.list.size()==0) {
        	return true;
        }
        return false;
    }
    
    
    /**
     * ȡ�������һ������
     * @return
     */
    public synchronized T get(){
    	T con;
    	con = list.get(0);
    	list.remove(0);
        return con;
    }
    
    
    
    /**
     * ����Ŀǰ��С��ģ��Ŀǰ�ܹ��ж��ٿͻ��ڵȴ�
     * @return
     */
    public int getSize(){
        return this.list.size();
    }
    
    
    /**
     * ����������С��ģ�����д����ܹ��������ɶ��ٿͻ�����
     * @return
     */
    public int getCapacity(){
       return this.capacity;
    }
}
