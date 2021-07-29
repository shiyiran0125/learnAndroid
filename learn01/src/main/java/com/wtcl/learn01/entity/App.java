package com.wtcl.learn01.entity;

import java.io.Serializable;

/**
 * @project: LearnAndroid
 * @desc: this is ...
 * @author: WTCL
 * @date: 2021/7/9
 */
public class App implements Serializable {
    /**
     * name : QQ
     * size : 91.6M
     * icon : http://pp.myapp.com/ma_icon/0/icon_6633_1597321962/256
     * userCount : 9343809438
     * editorIntro : 乐在沟通21年，聊天欢乐9亿人！
     * versionName : 8.4.5
     * downloadurl : http://imtt.dd.qq.com/16891/apk/D3DA805E44A86F1F197A89EA56055045.apk?fsname=com.tencent.mobileqq_8.4.5_1468.apk&csr=97c2
     */

    //App名称
    private String name;
    //App大小
    private String size;
    //App图标
    private String icon;
    //下载次数
    private long userCount;
    //软件描述
    private String editorIntro;
    //最新版本号
    private String versionName;
    //下载链接
    private String downloadurl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getUserCount() {
        return userCount;
    }

    public void setUserCount(long userCount) {
        this.userCount = userCount;
    }

    public String getEditorIntro() {
        return editorIntro;
    }

    public void setEditorIntro(String editorIntro) {
        this.editorIntro = editorIntro;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }
}
