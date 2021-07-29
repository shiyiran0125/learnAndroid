package com.wtcl.learn01.fragment;

import android.view.View;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * @author 石益然
 */
public interface FragmentInterface {
    /**
    *@desc 初始化视图
    *@author 石益然
    *
    */
    void initView(View view) throws IOException, XmlPullParserException;
}
