package com.hellojd.concurrent.collection.Delay;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * 生产者
 */
public class DelayQueueProducer {
    private BlockingQueue queue;

    private final Random random = new Random();

    public DelayQueueProducer(BlockingQueue queue) {
        super();
        this.queue = queue;
    }
    private Thread producerThread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    // Put some Delayed object into the DelayQueue.
                    int delay = random.nextInt(10000);
                    DelayObject object = new DelayObject(
                            UUID.randomUUID().toString(), delay);

                    System.out.printf("Put object = %s%n", object);
                    queue.put(object);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }, "Producer Thread");

    public void start(){
        this.producerThread.start();
    }
}
