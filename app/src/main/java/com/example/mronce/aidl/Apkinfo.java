package com.example.mronce.aidl;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/*
/   apk信息：名字和图标
 */
public class Apkinfo implements Serializable {//可序列化
    private String name;
    private Drawable drawable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }


    public Apkinfo(String name, Drawable drawable) {
        this.name = name;
        this.drawable = drawable;
    }


}
