package watchFileTest;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Title: 文件监控之定时器
 */
public class FileScheduler {
    //定时器设置，设置定时器
    private final Timer timer;
    //构造方法
    public FileScheduler(){
        timer = new Timer();;
    }
    //设置守护线程
    public FileScheduler(boolean isDaemon){  
        // 是否为守护线程  
        timer = new Timer(isDaemon);  
    }
    //为定时器分配可执行任务 

    public void schedule(Runnable task,TimeStep step){
        Date time = step.next();  
        SchedulerTimerTask timeTask = new SchedulerTimerTask(task,step);  
        // 安排在指定的时间 time 执行指定的任务 timetask  
        timer.schedule(timeTask, time); 
    }
    /** 
     * MethodsTitle: 重新执行任务 
     */
    private void reSchedule(Runnable task,TimeStep step){  
        Date time = step.next();  
        SchedulerTimerTask timeTask = new SchedulerTimerTask(task,step);  
        // 安排在指定的时间 time 执行指定的任务 timetask  
        timer.schedule(timeTask, time);  
    }  
    /** 
     * MethodsTitle: 停止当前定时器
     */
    public void cancle(){  
        timer.cancel();  
    }  
    /** 
     * Title:停止当前定时器  
     */
    private class SchedulerTimerTask extends TimerTask{  
        private Runnable task;  
        private TimeStep step;  

        public SchedulerTimerTask(Runnable task,TimeStep step){  
            this.task = task;  
            this.step = step;  
        }  
        public void run() {  
            // 执行指定任务  
            task.run();  
            // 继续重复执行任务  
            reSchedule(task, step);  
        }  
    } 
}