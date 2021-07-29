package com.wtcl.learn01.components;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wtcl.learn01.R;
import com.wtcl.learn01.activitys.DownloadActivity;
import com.wtcl.learn01.entity.App;
import com.wtcl.learn01.util.DownLoadAppUtil;
import com.wtcl.learn01.util.DownLoadListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

/**
 * @project: LearnAndroid
 * @desc: this is ...
 * @author: WTCL
 * @date: 2021/7/9
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppHolder> {
    private List<App> aList;
    private Context context;

    public AppListAdapter(List<App> aList, Context context) {
        this.aList = aList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AppHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new AppHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item, parent, false));
    }

    @Override
    public int getItemCount() {
        return aList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AppHolder holder, int position) {
        holder.appName.setText(aList.get(position).getName());
        holder.appSize.setText(aList.get(position).getSize());
        loadImage(aList.get(position).getIcon(),holder.appIcon);
        long counts = aList.get(position).getUserCount() / 10000;
        String countStr = "" + counts;
        if (counts == 0){
            countStr = "小于10000次";
        }
        holder.appUserCount.setText(countStr + "万 | ");
        holder.appEditorIntro.setText(aList.get(position).getEditorIntro());
        holder.appVersionName.setText("  V" + aList.get(position).getVersionName());
        holder.btn_download.setOnClickListener(view->{
            Intent intent = new Intent(context, DownloadActivity.class);
            Bundle data = new Bundle();
            data.putSerializable("appInfo",aList.get(position));
            intent.putExtras(data);
            context.startActivity(intent);
        });
    }

    //复用组件

    public class AppHolder extends RecyclerView.ViewHolder{
        //App名称
        public TextView appName;
        //App大小
        public TextView appSize;
        //App图标
        public ImageView appIcon;
        //下载次数
        public TextView appUserCount;
        //软件描述
        public TextView appEditorIntro;
        //最新版本号
        public TextView appVersionName;
        //下载链接
        public Button btn_download;
        public AppHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.appName);
            appSize = itemView.findViewById(R.id.appSize);
            appIcon = itemView.findViewById(R.id.appIcon);
            appUserCount = itemView.findViewById(R.id.appUserCount);
            appEditorIntro = itemView.findViewById(R.id.appEditorIntro);
            appVersionName = itemView.findViewById(R.id.appVersionName);
            btn_download = itemView.findViewById(R.id.btn_download);
        }
    }

    public void loadImage(String imgUrl,ImageView imageView){
        //this 是上下文 activity/fragment

        Glide.with(context)
                //根据地址下载图片
                .load(imgUrl)
                //缩放
                .apply(RequestOptions.circleCropTransform())
                //显示
                .into(imageView);
    }



}
