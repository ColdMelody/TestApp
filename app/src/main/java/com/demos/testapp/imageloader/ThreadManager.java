package com.demos.testapp.imageloader;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by peng on 2016/7/5.
 */
public class ThreadManager {
    private static volatile ThreadManager threadManager;
    private ThreadPoolExecutor threadPoolExecutor;
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;


    private ThreadManager(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = keepAliveTime;
    }

    public void excute(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
            threadPoolExecutor = new ThreadPoolExecutor(
                    corePoolSize,
                    maximumPoolSize,
                    keepAliveTime,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<Runnable>(),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());
        }
        threadPoolExecutor.execute(runnable);
    }

    public static synchronized ThreadManager getManager() {
        if (threadManager == null) {
                    threadManager = new ThreadManager(5, 10, 5L);
                }

        return threadManager;
    }

}
