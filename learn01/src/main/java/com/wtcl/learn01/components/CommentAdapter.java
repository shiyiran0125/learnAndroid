package com.wtcl.learn01.components;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wtcl.learn01.R;
import com.wtcl.learn01.entity.HotComments;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @project: LearnAndroid
 * @desc: this is ...
 * @author: WTCL
 * @date: 2021/7/8
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.HotCommentsViewHolder> {
    private List<HotComments> mList;
    private Context context;
    private static final String TAG = "CommentAdapter";
    public CommentAdapter(List<HotComments> list,Context context){
        this.mList = list;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public HotCommentsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new HotCommentsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HotCommentsViewHolder holder, int position) {
        loadImage(mList.get(position).getAvatar(),holder.avatar);
        holder.nickname.setText(mList.get(position).getNickname());
        holder.likedCount.setText(mList.get(position).getLikedCount());
        holder.content.setText(mList.get(position).getContent());
        holder.likedPic.setOnClickListener(view->{
            int pos = holder.getLayoutPosition();
            Toast.makeText(context,"" + pos,Toast.LENGTH_SHORT).show();
        });
        //点击显示大图
        holder.avatar.setOnClickListener(view->{
            LayoutInflater inflater = LayoutInflater.from(context);
            View imgEntryView = inflater.inflate(R.layout.dialog_photo, null);
            // 加载自定义的布局文件
            final AlertDialog dialog = new AlertDialog.Builder(context).create();
            ImageView img = (ImageView) imgEntryView.findViewById(R.id.large_image);
            Glide.with(context).load(mList.get(position).getAvatar()).into(img);
            dialog.setView(imgEntryView); // 自定义dialog
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
            // 点击大图关闭dialog
            imgEntryView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View paramView) {
                    dialog.cancel();
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class HotCommentsViewHolder extends RecyclerView.ViewHolder{
        public ImageView avatar;
        public TextView nickname;
        public TextView likedCount;
        public ImageView likedPic;
        public TextView content;
        public HotCommentsViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            nickname = itemView.findViewById(R.id.nickname);
            likedCount = itemView.findViewById(R.id.likedCount);
            likedPic = itemView.findViewById(R.id.likedPic);
            content = itemView.findViewById(R.id.content);
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
