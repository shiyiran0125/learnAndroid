package com.wtcl.learn01.util;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * project: LearnAndroid
 * desc: this is ...
 * @author: WTCL
 * date: 2021/7/7
 */
public final class Users {
    //定义该CpntentProvider的Authorities

    public static final String AUTHORITY = "com.wtcl.learn01";
    //定义一个静态内部类，暴露所包含的数据列的列名

    public static final class User implements BaseColumns{
        //数据列
        public final static String _ID = "id";
        public final static String USERNAME = "userName";
        public final static String PASSWORD = "password";
        //该content提供的两个URI
        public final static Uri USERS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/users");
        public final static Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/user");
    }
}
