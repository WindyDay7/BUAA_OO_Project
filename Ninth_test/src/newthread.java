public class newthread extends Thread {
    newthread(){
        /**@REQUIRES: None;
         *@MODIFIES: None;
         *@EFFECTS: None;
         */
        super("newthread");
        start();
    }
    public void run(){
        /**@REQUIRES: None;
         *@MODIFIES: Map.flow;
         *@EFFECTS:
         * \all int i;0<=i<12640;Map.flow[i]==0;
         */
        long time;
        while(true){
            time = System.currentTimeMillis();
            try {
                Thread.sleep(500-20);
                while ((System.currentTimeMillis()-time)<500){
                    Thread.sleep(1);
                }
            }catch (Exception e){}
            Map.clearflow();
        }
    }
}
