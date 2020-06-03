package com.gumx.advance;
/**
* @author gumx
* I'm glad to share my knowledge with you all.
* 模拟电梯的动作
*/
public class Client {
    public static void main(String[] args) {
        Context context = new Context();
        context.setLiftState(new ClosingState());
        context.open();
        context.close();
        context.run();
        context.stop();
    }
}