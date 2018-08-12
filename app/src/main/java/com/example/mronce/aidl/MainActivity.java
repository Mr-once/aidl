package com.example.mronce.aidl;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/*
*   我不知道为什么要点击一下SearchView才能显示list_view出来了
*   在没设置搜索监听前是不要点击SearchView就会全部显示出来的
* */

public class MainActivity extends AppCompatActivity {

   private ListView listView;
    private List<Apkinfo> list=new ArrayList<Apkinfo>();//搜索结果咧
    private SearchView mSearchView;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.GetBinder binder= (MyService.GetBinder) service;
            binder.getApkInfos();
            binder.sendBroadcast();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
   private LocalBroadcastManager localBroadcastManager;
    private List<Apkinfo> apkinfoList=new ArrayList<Apkinfo>();//apk信息列
    private List<Apkinfo> apkinfoLists=new ArrayList<Apkinfo>();//apk信息列
    private LocalReceiver localReceiver;//自定义类
    private IntentFilter intentFilter;
    private MyApplication application= (MyApplication) getApplication();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
        unbindService(connection);
    }

    @Override//保存状态
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("listview", (Serializable) list);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       if(savedInstanceState!=null){

           apkinfoLists= (List<Apkinfo>) savedInstanceState.getSerializable("listview");

        }
        Intent intent=new Intent(this,MyService.class);
        startService(intent);
        bindService(intent,connection,BIND_AUTO_CREATE);
        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.mronce.aidl.LOCAL_BROADCAST");//添加过滤
        localReceiver=new LocalReceiver();
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
       localBroadcastManager.registerReceiver(localReceiver,intentFilter);//注册广播接收器
         mSearchView =  findViewById(R.id.searchView);
        listView=findViewById(R.id.list_apk);
        listView.setAdapter(new Adapter(MainActivity.this,R.layout.list_item,apkinfoLists));


        //搜索监听器
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list.clear();//清空上一次搜索
                for (Apkinfo apkinfo : apkinfoLists){
                    if (apkinfo.getName().contains(newText)){//模糊查询，有相同字符的item就进行添加
                        list.add(apkinfo);
                    }
                }
               //重新加载
                listView=findViewById(R.id.list_apk);
                listView.setAdapter(new Adapter(MainActivity.this,R.layout.list_item,list));
                return true;
            }
        });



    }
    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
        //从服务中接送数据，添加到apkinfoLists

            apkinfoList= (List<Apkinfo>) intent.getSerializableExtra("list");
             for(Apkinfo apkinfo : apkinfoList){
                 String appName = apkinfo.getName();
                 Drawable drawable = apkinfo.getDrawable();
                 Apkinfo appInfos = new Apkinfo(appName,drawable);
                apkinfoLists.add(appInfos);

            }

        }
    }

}
