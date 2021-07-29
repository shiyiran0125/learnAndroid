package com.wtcl.learn01.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wtcl.learn01.R;
import com.wtcl.learn01.components.AdapterFragment;
import com.wtcl.learn01.fragment.ContactFragment;
import com.wtcl.learn01.fragment.FindFragment;
import com.wtcl.learn01.fragment.InforFragment;
import com.wtcl.learn01.fragment.ProfileFragment;

import java.util.ArrayList;
import java.util.List;
/**
 * @author WTCL
 */
public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";
    private RadioGroup radioGroup;
    private RadioButton information;
    private RadioButton contact;
    private RadioButton find;
    private RadioButton profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,TAG + ":onCreate");
        setContentView(R.layout.activity_index);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,TAG + ":onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,TAG + ":onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,TAG + ":onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,TAG + ":onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,TAG + ":onDestroy");
    }
    public void initView(){
        InforFragment fragment1 = new InforFragment();
        ContactFragment fragment2 = new ContactFragment();
        FindFragment fragment3 = new FindFragment();
        ProfileFragment fragment4 = new ProfileFragment();
        // 将要分页显示的View装入数组中
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);
        list.add(fragment4);
        ViewPager vp = findViewById(R.id.pager);

        //设置页面切换时的监听器(可选，用了之后要重写它的回调方法处理页面切换时候的事务)
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("index",position + "poffoset :" + positionOffset + "poff0Poxel" + positionOffsetPixels);
            }

            /**
             *@desc 根据滑动的fragment切换底部导航栏及顶部
             *@author 石益然
             *
             */
            @Override
            public void onPageSelected(int position) {
                Log.d("selected", "selected:" +  position);
                int id = radioGroup.getChildAt(position).getId();
                radioGroup.check(id);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        vp.setAdapter(new AdapterFragment(getSupportFragmentManager(), list));
        //为单选按钮组注册监听器
        radioGroup = findViewById(R.id.navigationGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(IndexActivity.this,group.indexOfChild(group.findViewById(checkedId))+"",Toast.LENGTH_SHORT).show();
                vp.setCurrentItem(group.indexOfChild(group.findViewById(checkedId)));
            }
        });

        //为底部几张图片设置大小
        information = (RadioButton) findViewById(R.id.radioButton1);
        contact = (RadioButton) findViewById(R.id.radioButton2);
        find = (RadioButton) findViewById(R.id.radioButton3);
        profile = (RadioButton) findViewById(R.id.radioButton4);
        RadioButton[] rbs = new RadioButton[4];
        rbs[0] =information;
        rbs[1] = contact;
        rbs[2] = find;
        rbs[3] = profile;
        for (RadioButton rb : rbs) {
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            Drawable[] drawables = rb.getCompoundDrawables();
            //获取drawables
            Rect r = new Rect(0, 0, drawables[1].getMinimumWidth()*1/5, drawables[1].getMinimumHeight()*1/5);
            //定义一个Rect边界
            drawables[1].setBounds(r);
            rb.setCompoundDrawables(null,drawables[1],null,null);
        }
    }
}