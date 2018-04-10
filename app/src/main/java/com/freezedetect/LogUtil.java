package com.freezedetect;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

/**
 * Created by guohongcheng on 2018/3/31.
 */

public class LogUtil {
    private static final String TAG = "LogUtil";
    private static final long TIME_BLOCK = 1000L;
    private static LogUtil sInstance = new LogUtil();
    private static Runnable mLogRunnable = new Runnable() {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString() + "\n");
            }
            Log.e(TAG, sb.toString());
        }
    };
    private HandlerThread mLogThread = new HandlerThread("log");
    private Handler mIoHandler;
    private boolean isMonitor = false;

    private LogUtil() {
        mLogThread.start();
        mIoHandler = new Handler(mLogThread.getLooper());
    }

    public static LogUtil getInstance() {
        return sInstance;
    }

    public boolean isMonitor() {
        return isMonitor;
    }

    public void startMonitor() {
        isMonitor = true;
        mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK);
    }

    public void removeMonitor() {
        isMonitor = false;
        mIoHandler.removeCallbacks(mLogRunnable);
    }
}
