package com.btv;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.emm.sandbox.EMMSandboxUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17.
 */
public class MyApplication extends Application {

    private List<Activity> mList = new LinkedList();
    private static MyApplication instance;

    private MyApplication() {
    }

    public synchronized static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    @Override
    public void onCreate() {

        super.onCreate();
        //初始化沙箱
        EMMSandboxUtil.setEMMPackageName("com.ctvit.wisdommedia");//
        boolean isSnadBox = EMMSandboxUtil.initSandbox(this);
        Log.e("789", "初始化沙箱" + isSnadBox);
    }
    private String b;

    public String getB(){
        return this.b;
    }
    public void setB(String c){
        this.b= c;
    }

}
