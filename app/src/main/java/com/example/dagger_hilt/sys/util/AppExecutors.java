package com.example.dagger_hilt.sys.util;

import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;

public class AppExecutors {
    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;

    public AppExecutors() {
        this.diskIO = Executors.newSingleThreadExecutor();
        this.networkIO = Executors.newFixedThreadPool(3);
        this.mainThread = new MainThreadExecutor();
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public Executor getNetworkIO() {
        return networkIO;
    }

    public Executor getMainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
