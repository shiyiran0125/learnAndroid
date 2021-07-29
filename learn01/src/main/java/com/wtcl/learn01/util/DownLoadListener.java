package com.wtcl.learn01.util;

/**
 * @author WTCL
 */
public interface DownLoadListener {
        //开始下载

        void startDownload();
        //暂停下载
        void stopDownload();
        //完成下载
        void finishDownload();
        //下载进度
        void downloadProgress(long progress);
}
