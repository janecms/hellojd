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
     * 获取并移除队首的元素, 或者返回null（如果队列不包含到达延迟时间的元素）
     *
     * @return 队首的元素, 或者null（如果队列不包含到达延迟时间的元素）
     */
    public E poll() {
        final ReentrantLock lock = this.lock;
        // 获得锁
        lock.lock();
        try {
            // 获取优先队列队首元素
            E first = q.peek();
            // 若优先队列队首元素为空，或者还没达到延迟时间，返回null
            if (first == null || first.getDelay(NANOSECONDS) > 0)
                return null;
            // 否则，返回并移除队首元素
            else
                return q.poll();
        } finally {
            // 释放锁
            lock.unlock();
        }
    }
```