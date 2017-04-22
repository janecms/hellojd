package com.hellojd.concurrent.collection.Delay;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

/**
 * Created by Administrator on 2017/4/22.
 */
public class DelayQueueConsumer {
    private BlockingQueue<DelayObject> queue;
    public DelayQueueConsumer(BlockingQueue<DelayObject> queue) {
        super();
        this.queue = queue;
    }
    class ConsumerThread  implements  Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    // Take elements out from the DelayQueue object.
                    DelayObject object = queue.take();
                    System.out.printf("[%s] - Take object = %s%n", Thread.currentThread().getName(), object);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void start(String threadName){
       new Thread(new ConsumerThread(),threadName).start();
//        this.consumerThread.start();
    }
}
