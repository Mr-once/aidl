package com.example.mronce.aidl;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class MyApplication extends Application {




    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override//屏幕发生改变时调用
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override//内存吃紧的时候调用
    public void onLowMemory() {
        super.onLowMemory();
    }
}
