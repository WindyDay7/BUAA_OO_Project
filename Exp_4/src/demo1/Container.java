package demo1;

import java.util.ArrayList;
import java.util.List;


/**
 * 装产品的容器

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
     * 向容器里添加产品 
     * @param product
     */
    public synchronized void add(T product){
        list.add(product);
    }
    
    /**
     * 容器是否已满
     * @return
     */
    public synchronized boolean isFull(){
        if(this.list.size()==this.capacity) {
        	return true;
        }
        return false;
    }
    
    
    /**
     * 容器是否为空
     * @return
     */
    public synchronized boolean isEmpty(){
        if(this.list.size()==0) {
        	return true;
        }
        return false;
    }
    
    
    /**
     * 取容器里第一个对象
     * @return
     */
    public synchronized T get(){
    	T con;
    	con = list.get(0);
    	list.remove(0);
        return con;
    }
    
    
    
    /**
     * 容器目前大小，模拟目前总共有多少客户在等待
     * @return
     */
    public int getSize(){
        return this.list.size();
    }
    
    
    /**
     * 容器容量大小，模拟银行大厅总共可以容纳多少客户上限
     * @return
     */
    public int getCapacity(){
       return this.capacity;
    }
}
