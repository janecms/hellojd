package com.hellojd.concurrent.collection.Delay;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;

/**
 * Created by Administrator on 2017/4/22.
 */
public class DelayQueueTest {

    public static void main(String[] args) {
        BlockingQueue<DelayObject> queue = new DelayQueue<>();
        DelayQueueProducer producer= new DelayQueueProducer(queue);
        producer.start();
        DelayQueueConsumer consumer= new DelayQueueConsumer(queue);
        consumer.start("DelayQueueConsumer-Thread-1");
        consumer.start("DelayQueueConsumer-Thread-2");
    }
}
