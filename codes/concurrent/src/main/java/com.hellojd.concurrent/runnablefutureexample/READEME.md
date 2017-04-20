## FutureTask
 - 可取消的异步计算
 - 提供了Future的基本实现
 - 需要提供Callable实现
 - 只有任务处理完成状态，才会返回结果
 - 如果任务不是完成状态，会阻塞

 ### 状态
 ```
    private static final int NEW          = 0;
    private static final int COMPLETING   = 1;
    private static final int NORMAL       = 2;
    private static final int EXCEPTIONAL  = 3;
    private static final int CANCELLED    = 4;
    private static final int INTERRUPTING = 5;
    private static final int INTERRUPTED  = 6;
 ```