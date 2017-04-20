package com.hellojd.concurrent.runnablefutureexample;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zhaoguoyu on 2017/4/20.
 */
public class ExecutableTask implements Callable<String> {
    private static final Logger logger = Logger.getLogger("ExecutableTask");
    private String name;
    public ExecutableTask(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    @Override
    public String call() throws Exception {
        try {
            long duration = (long) (Math.random() * 10);
            logger.info(this.name + ": Waiting " + duration + " seconds for results.");
            TimeUnit.SECONDS.sleep(duration);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, e.getLocalizedMessage());
            e.printStackTrace(System.err);
        }
        return "Hello, world. I'm " + this.name;
    }
}
