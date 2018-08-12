package com.example.mronce.aidl;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
/*
    listview适配器
 */

public class Adapter extends ArrayAdapter<Apkinfo> {
    private int resourceId;
    public Adapter(@NonNull Context context, int resource, @NonNull List<Apkinfo> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Apkinfo apkinfo=getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView==null){
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.iconImage=view.findViewById(R.id.imageView);
            viewHolder.nameText=view.findViewById(R.id.textView);
            view.setTag(viewHolder);
        }else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.nameText.setText(apkinfo.getName());
        viewHolder.iconImage.setBackground(apkinfo.getDrawable());
        return view;
    }
    class ViewHolder{
        ImageView iconImage;
        TextView nameText;
    }

}
