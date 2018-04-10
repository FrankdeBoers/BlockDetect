package com.freezedetect;

import android.os.Looper;
import android.util.Printer;

/**
 * Created by guohongcheng on 2018/3/31.
 */

public class FreezeLooper {
    public static void register() {
        Looper.getMainLooper().setMessageLogging(new Printer() {
            private static final String START = ">>>>> Dispatching";
            private static final String END = "<<<<< Finished";

            @Override
            public void println(String x) {
                if (x.startsWith(START)) {
                    LogUtil.getInstance().startMonitor();
                }
                if (x.startsWith(END)) {
                    LogUtil.getInstance().removeMonitor();
                }
            }
        });
    }
}
