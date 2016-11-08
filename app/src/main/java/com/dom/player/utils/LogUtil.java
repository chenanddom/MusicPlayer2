package com.dom.player.utils;

import android.util.Log;

/**
 * Created by chendom on 16-11-7.
 */

public class LogUtil {
    private static boolean flag=true;
    public static void showLog(String tag,String msg){
        if (flag) {
            Log.i("--tag--"+tag, "-----------------------" + msg);
        }
    }
}
