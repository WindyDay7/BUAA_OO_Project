package watchFileTest;
/**
 * Title: 测试类
 */
public class TestMainClass {
    public static void main(String[] args) {
        FileSchedulerTask task = new FileSchedulerTask("D:\\tmp");
        FileScheduler fileScheduler = new FileScheduler();
        fileScheduler.schedule(task, new TimeStep());
    }
}