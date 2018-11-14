package com.example.lenovo.weather.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {
    private static final int THREAD_COUNT=3;

    private final Executor mDiskIO;
//    private final Executor mNetworkIO;
    private final Executor mUIThread;

    public AppExecutors() {
        // 串行线程池
        mDiskIO=Executors.newSingleThreadExecutor();
        // 固定线程数线程池
//        mNetworkIO=Executors.newFixedThreadPool(THREAD_COUNT);

        mUIThread=new UIThreadExecutor();
    }

    public Executor diskIO(){return mDiskIO;}

//    public Executor networkIO(){return mNetworkIO;}

    public Executor UIThread(){return mUIThread;}

    private static class UIThreadExecutor implements Executor{
        private Handler mMainThreadHandler=new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mMainThreadHandler.post(command);
        }
    }
}
