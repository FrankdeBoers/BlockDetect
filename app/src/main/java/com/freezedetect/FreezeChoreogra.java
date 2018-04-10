package com.freezedetect;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Choreographer;

import java.util.concurrent.TimeUnit;

/**
 * Created by guohongcheng on 2018/4/10.
 */

public class FreezeChoreogra {
    private static final String TAG = "FreezeChoreogra";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void register() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            long lastFrameTimeNanos = 0;
            long currentFrameTimeNanos = 0;

            @Override
            public void doFrame(long frameTimeNanos) {
                // 初始化时间
                if (lastFrameTimeNanos == 0) {
                    lastFrameTimeNanos = frameTimeNanos;
                }
                currentFrameTimeNanos = frameTimeNanos;
                long diffMs = TimeUnit.MILLISECONDS.convert(currentFrameTimeNanos - lastFrameTimeNanos, TimeUnit.NANOSECONDS);
                if (diffMs < 16.6f) {
                    int droppedCount = (int) (diffMs / 16.6);
                    Log.d(TAG, "droppedCount: " + droppedCount);
                    if (LogUtil.getInstance().isMonitor()) {
                        LogUtil.getInstance().removeMonitor();
                    }
                }
                LogUtil.getInstance().startMonitor();
                lastFrameTimeNanos = frameTimeNanos;
                // 注册下一帧回调
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }
}
