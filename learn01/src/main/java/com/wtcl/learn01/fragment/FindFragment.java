package com.wtcl.learn01.fragment;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wtcl.learn01.R;
import com.wtcl.learn01.entity.Channel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author 石益然
 */
public class FindFragment extends Fragment implements FragmentInterface{
    private TextView xmlDoc;
    private TextView objDoc;
    private static final String TAG = "FindFragment11";
    public FindFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        try {
            initView(view);
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView(View view) throws IOException, XmlPullParserException {
        xmlDoc = view.findViewById(R.id.xmlDoc);
        objDoc = view.findViewById(R.id.objDoc);
        xmlDoc.setMovementMethod(ScrollingMovementMethod.getInstance());
        //xmlDoc.setScrollbarFadingEnabled(false);
        objDoc.setMovementMethod(ScrollingMovementMethod.getInstance());
        //objDoc.setScrollbarFadingEnabled(false);
        InputStream is = getContext().getAssets().open("weather.xml");
        InputStream is2 = getContext().getAssets().open("weather.xml");
        showXml(is);
        parseXmlByPull(is2);
    }

    //pull解析xml

    public void parseXmlByPull(InputStream is) throws XmlPullParserException, IOException {
        ArrayList<Channel> aList = null;
        Channel channel = null;
        // 获得xml解析类的引用
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");
        // 获得事件的类型
        int eventType = parser.getEventType();
        while (eventType!= XmlPullParser.END_DOCUMENT){
            switch (eventType){
                case XmlPullParser.START_DOCUMENT:
                    aList = new ArrayList<Channel>();
                    Log.d(TAG,"start_doc");
                    break;
                case XmlPullParser.START_TAG:
                    Log.d(TAG,"START_TAG");
                    if ("channel".equals(parser.getName())) {
                        channel = new Channel();
                        // 取出属性值
                        int id = Integer.parseInt(parser.getAttributeValue(0));
                        channel.setId(id);
                    } else if ("city".equals(parser.getName())) {
                        String name = parser.nextText();
                        channel.setCityName(name);
                    } else if ("temp".equals(parser.getName())) {
                        String temp = parser.nextText();
                        channel.setTemp(temp);
                    }else if ("wind".equals(parser.getName())){
                        int wind = Integer.parseInt(parser.nextText());
                        channel.setWind(wind);
                    }else if("pm250".equals(parser.getName())){
                        int pm= Integer.parseInt(parser.nextText());
                        channel.setPm250(pm);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    Log.d(TAG,"END_TAG");
                    if ("channel".equals(parser.getName())){
                        aList.add(channel);
                        channel = null;
                    }
                    break;
                default:break;
            }
            eventType = parser.next();
        }
        showObj(aList);
    }

    private void showObj(ArrayList<Channel> aList) {
        StringBuilder sb = new StringBuilder();
        sb.append("id  " + "city  " + "temp  " + "wind  " + "pm250\n");
        for (Channel obj:aList) {
            sb.append(obj.getId() + "  ")
              .append(obj.getCityName() + "   ").append(obj.getTemp() + "      ").append(obj.getWind() + "      ").append(obj.getPm250() + "\n");
        }
        objDoc.setText(sb.toString());
    }

    private void showXml(InputStream is) throws IOException {
        byte[] bytes = new byte[2048];
        int len = 0;
        String str = "";
        StringBuilder stringBuilder = new StringBuilder();
        while ((len = is.read(bytes,0,bytes.length))!= -1){
            str = new String(bytes);
            stringBuilder.append(str);
        }
        xmlDoc.setText(stringBuilder.toString());
    }
}