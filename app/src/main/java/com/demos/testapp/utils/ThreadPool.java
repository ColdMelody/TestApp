package com.demos.testapp.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bill on 2016/11/29.
 * Email androidBaoCP@163.com
 */
public class ThreadPool {

    private static final Object lock=new Object();
    private static volatile ThreadPool threadPool;
    private ThreadPoolExecutor threadPoolExecutor;
    private int coreThreadSize;
    private int maxMumSize;
    private long keepAliveTile;

    private ThreadPool(int coreThreadSize, int maxMumSize, long keepAliveTile){
        this.coreThreadSize=coreThreadSize;
        this.maxMumSize=maxMumSize;
        this.keepAliveTile=keepAliveTile;
    }

    public static ThreadPool getThreadPool(){
        if (threadPool==null){
            synchronized (lock){
                if (threadPool==null){
                    return new ThreadPool(5,5,5L);
                }
            }
        }
        return threadPool;
    }

    public ThreadPoolExecutor execute(Runnable runnable){
        if (runnable==null){
            return null;
        }
        if (threadPoolExecutor==null||threadPoolExecutor.isShutdown()){
            threadPoolExecutor=new ThreadPoolExecutor(
                    coreThreadSize,
                    maxMumSize,
                    keepAliveTile,
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    Executors.defaultThreadFactory(),
                    new ThreadPoolExecutor.AbortPolicy());
            }
            return threadPoolExecutor;
        }
    public void cancel(Runnable runnable) {
        if (runnable != null && !threadPoolExecutor.isShutdown()) {
                threadPoolExecutor.getQueue().remove(runnable);
        }
    }

}
