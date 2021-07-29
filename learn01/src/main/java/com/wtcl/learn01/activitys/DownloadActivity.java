package com.wtcl.learn01.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wtcl.learn01.R;
import com.wtcl.learn01.entity.App;
import com.wtcl.learn01.util.DownLoadAppUtil;
import com.wtcl.learn01.util.DownLoadListener;

import java.io.File;

/**
 * @author WTCL
 */
public class DownloadActivity extends AppCompatActivity {
    private DownLoadAppUtil util;
    //App名称
    private TextView appName;
    //App大小
    private TextView appSize;
    //App图标
    private ImageView appIcon;
    //下载次数
    private TextView appUserCount;
    //软件描述
    private TextView appEditorIntro;
    //最新版本号
    private TextView appVersionName;
    //下载进度
    private ProgressBar progressBar;
    //暂停继续下载
    private Button btn_StartOrPause;
    //下载状态，0x11代表下载中，0x12代表暂停下载中
    private static int status = 0x11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        initView();
    }

    //初始化视图
    public void initView(){
        util = DownLoadAppUtil.getInstance();
        appName = findViewById(R.id.appName);
        appSize = findViewById(R.id.appSize);
        appIcon = findViewById(R.id.appIcon);
        appUserCount = findViewById(R.id.appUserCount);
        appEditorIntro = findViewById(R.id.appEditorIntro);
        appVersionName = findViewById(R.id.appVersionName);
        progressBar = findViewById(R.id.progressBar);
        btn_StartOrPause = findViewById(R.id.btn_start_pause);
        //取出数据为组件赋值
        Bundle data = getIntent().getExtras();
        App app = (App) data.getSerializable("appInfo");
        appName.setText(app.getName());
        appSize.setText(app.getSize());
        loadImage(app.getIcon(),appIcon);
        long counts = app.getUserCount() / 10000;
        String countStr = "" + counts;
        if (counts == 0){
            countStr = "小于10000次";
        }
        appUserCount.setText(countStr + "万 | ");
        appEditorIntro.setText(app.getEditorIntro());
        appVersionName.setText(app.getVersionName());
        //开始下载
        downLoadApp(app.getDownloadurl());
        //为按钮注册事件
        btn_StartOrPause.setOnClickListener(view->{
            if (status == 0x11){
                util.stopDownLoad();
                btn_StartOrPause.setText("继续下载");
                status = 0x12;
            }else if (status == 0x12){
                util.startDownload(app.getDownloadurl());
                status = 0x11;
                btn_StartOrPause.setText("暂停下载");
            }
        });
    }

    //加载网络图片
    public void loadImage(String imgUrl,ImageView imageView){
        //this 是上下文 activity/fragment
        Glide.with(this)
                //根据地址下载图片
                .load(imgUrl)
                //缩放
                .apply(RequestOptions.circleCropTransform())
                //显示
                .into(imageView);
    }

    //开始下载app
    private void downLoadApp(String downloadurl) {
        File appPath = getExternalFilesDir("/shiyiran");
        util.initDownload(appPath.getAbsolutePath());
        util.setListener(new DownLoadListener() {
            @Override
            public void startDownload() {
            }
            @Override
            public void stopDownload() {
            }

            @Override
            public void finishDownload() {
                File downloadFile = DownLoadAppUtil.getInstance().getDownloadFile();
                installApk(downloadFile);
            }

            @Override
            public void downloadProgress(long progress) {
                //更新进度
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress((int)progress);
                    }
                });
            }
        });
        util.startDownload(downloadurl);
    }

    /**
     * 安装apk
     * @param file
     */
    private void installApk(File file) {
        if (!file.exists()){
            Toast.makeText(this, "应用软件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }
}