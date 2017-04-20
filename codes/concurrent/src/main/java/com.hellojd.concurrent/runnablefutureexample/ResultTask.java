package com.hellojd.concurrent.runnablefutureexample;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.logging.Logger;

/**
 * Created by zhaoguoyu on 2017/4/20.
 */
public class ResultTask extends  FutureTask<String> {
    private static final Logger logger = Logger.getLogger("ResultTask");
    private String name;
    public ResultTask(Callable<String> callable) {
        super(callable);
        this.name = ((ExecutableTask) callable).getName();
    }

    @Override
    protected void done() {
        if (this.isCancelled()) {
            logger.info(this.name + ": has been canceled");
        } else if (this.isDone()) {
            logger.info(this.name + ": has finished");
        }
    }
}
