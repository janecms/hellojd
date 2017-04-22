## DelayQueue
 - DelayQueue是一个无界的阻塞队列
 - 可重入锁
 - 不允许输入Null
 - 用于根据delay时间排序的优先级队列
 - 用于优化阻塞通知的线程元素leader

##  Leader-Follower
所有的工作线程分为Leader和Follower两种角色，只有一个Leader线程会处理任务，其他线程都在排队等待，称为Follower，当Leader获取到任务之后，通知其他Follower晋升为Leader，完成任务后等待下一次晋升为Leader。

```java
   /**
     * 获取并移除队首元素，该方法将阻塞，直到队列中包含达到延迟时间的元素
     *
     * @return 队首元素
     * @throws InterruptedException 阻塞时被打断，抛出打断异常
     */
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        // 获得锁，该锁可被打断
        lock.lockInterruptibly();
        try {
            // 循环处理
            for (;;) {
                // 获取队首元素
                E first = q.peek();
                // 若元素为空，等待条件，在offer方法中会调用条件对象的通知方法
                // 并重新进入循环
                if (first == null)
                    available.await();
                // 若元素不为空
                else {
                    // 获取延迟时间
                    long delay = first.getDelay(NANOSECONDS);
                    // 若达到延迟时间，返回并移除队首元素
                    if (delay <= 0)
                        return q.poll();
                    // 否则，需要进入等待
                    first = null; // 在等待时，不持有引用
                    // 若leader不为空，等待条件
                    if (leader != null)
                        available.await();
                    // 否则，设置leader为当前线程，并超时等待延迟时间
                    else {
                        Thread thisThread = Thread.currentThread();
                        leader = thisThread;
                        try {
                            available.awaitNanos(delay);
                        } finally {
                            if (leader == thisThread)
                                leader = null;
                        }
                    }
                }
            }
        } finally {
            // 通知其他线程条件得到满足
            if (leader == null && q.peek() != null)
                available.signal();
             // 释放锁
            lock.unlock();
        }
    }
```