package watchFileTest;

import java.util.Calendar;
import java.util.Date;

/**
 * Title: 文件监控之时间之实体类设置,将时间作为实体类
 */
public class TimeStep {
    //获取时间
    private Calendar calendar = Calendar.getInstance();
    //设置单位
    private int feild = Calendar.SECOND;
    //最大数
    private int amount = 10;

    public int getFeild() {
        return feild;
    }
    public void setFeild(int feild) {
        this.feild = feild;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    /**
     * MethodsTitle: 获取时间
     */
    //设置单位和数目
    public Date next(){
        calendar.add(feild, amount);  
        return calendar.getTime();
    }

}