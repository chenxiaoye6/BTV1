package com.btv.Utils;

import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by Administrator on 2015/12/25.
 */
public class ResourceUtil {

    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.getStackTrace();
        }
        return verName;
    }

    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.getStackTrace();
        }
        return verCode;
    }

    public static String getConfString(Context context, String key) {
        int id = context.getResources().getIdentifier(key, "string", context.getPackageName());
        if(id > 0){
            return context.getResources().getString(id);
        }
        return null;
    }

    public static boolean getConfBoolean(Context context, String key) {
        int id = context.getResources().getIdentifier(key, "string", context.getPackageName());
        if(id > 0){
            return Boolean.parseBoolean(context.getResources().getString(id));
        }
        return false;
    }

}
