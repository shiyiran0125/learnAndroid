package com.wtcl.learn01.util;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @project: LearnAndroid
 * @desc: this is ...
 * @author: WTCL
 * @date: 2021/7/9
 */
public class DownLoadAppUtil {
    private static DownLoadAppUtil downLoadAppUtil;
    private final OkHttpClient client;
    private DownLoadListener listener;
    private File file;
    private File downloadFile;
    private long startPosition;
    Call call;
    public DownLoadAppUtil() {
        this.client = new OkHttpClient();
    }

    public void setListener(DownLoadListener listener){
        this.listener = listener;
    }
    public File getDownloadFile(){
        return downloadFile;
    }

    public static DownLoadAppUtil getInstance(){
        if (downLoadAppUtil==null){
            downLoadAppUtil = new DownLoadAppUtil();
        }
        return downLoadAppUtil;
    }

    //初始化下载路径

    public void initDownload(String path){
        file = new File(path);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        if (!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
            }
        }

    }

    //开始下载
    public void startDownload(String url){
        if (TextUtils.isEmpty(url)){
            return;
        }
        if (url.contains(".")){
            String typeName = url.substring(url.lastIndexOf(".")+1,url.lastIndexOf("&"));
            if (url.contains("/")){
                String filename = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
                String fn = filename + "." +typeName;
                downloadFile = new File(this.file,fn);
            }
        }
        startPosition = 0;
        if (downloadFile.exists()){
            startPosition = downloadFile.length();
        }
        //构造一个范围请求头
        final Request request = new Request.Builder()
                .addHeader("RANGE","bytes=" + startPosition + "-")
                .url(url)
                .build();
        call = client.newCall(request);
        //发起异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                listener.startDownload();
                ResponseBody body = response.body();
                long totalLength = body.contentLength();
                InputStream is = body.byteStream();
                byte[] bytes = new byte[2048];
                int len = 0;
                long totalNum = startPosition;
                RandomAccessFile raf = new RandomAccessFile(downloadFile,"rw");
                while((len=is.read(bytes,0,bytes.length))!= -1){
                    raf.seek(totalNum);
                    raf.write(bytes,0,len);
                    totalNum += len;
                    listener.downloadProgress(totalNum * 100 / totalLength);
                }
                listener.finishDownload();
                body.close();
            }
        });
    }

    //停止下载

    public void stopDownLoad(){
        listener.stopDownload();
        if (call != null && call.isExecuted()){
            call.cancel();
        }
    }
}
