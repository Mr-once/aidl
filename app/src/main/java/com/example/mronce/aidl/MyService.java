package com.example.mronce.aidl;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/*
    服务
 */

public class MyService extends Service {
    private List<Apkinfo> apkinfoList;//apk信息列
    private GetBinder getBinder=new GetBinder();//自定义Binder
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
       return getBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //自定义Binder方法
    class GetBinder extends Binder{
        //获取应用信息的方法
            public List<Apkinfo> getApkInfos(){
                PackageManager pm = getApplication().getPackageManager();
                List<PackageInfo>  packgeInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
                apkinfoList = new ArrayList<Apkinfo>();
                for(PackageInfo packgeInfo : packgeInfos){
                    String appName = packgeInfo.applicationInfo.loadLabel(pm).toString();
                    Drawable drawable = packgeInfo.applicationInfo.loadIcon(pm);
                    Apkinfo appInfo = new Apkinfo(appName,drawable);
                    apkinfoList.add(appInfo);
                }
                return apkinfoList;
            }
            public  void sendBroadcast(){//发送本地广播
                Intent intent=new Intent("com.example.mronce.aidl.LOCAL_BROADCAST");
                intent.putExtra("list", (Serializable) apkinfoList);//intent中只能传递序列化对象
                LocalBroadcastManager localBroadcastManager=LocalBroadcastManager.getInstance(MyService.this);
                localBroadcastManager.sendBroadcast(intent);

            }


        }
    }

