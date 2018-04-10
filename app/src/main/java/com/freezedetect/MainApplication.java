package com.freezedetect;

import android.app.Application;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by guohongcheng on 2018/4/10.
 */

public class MainApplication extends Application {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        super.onCreate();
        // 在Application中注册该卡顿监控，两者都注册
        FreezeChoreogra.register();
        FreezeLooper.register();
    }
}
