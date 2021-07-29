package com.wtcl.learn01.activitys;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.wtcl.learn01.R;

/**
 * @author 石益然
 */
public class MainActivity extends AppCompatActivity {

    private Button toLoginButton;
    private Button toRegisterButton;
    /**
    *启动首页的回调接口
    *author
    *
    */
    public interface ICallBack{
        /**
         * 进入登录界面
         * @author 石益然
         */
        void toLogin();
        /**
         * 进入注册界面
         * @author 石益然
         */
        void toRegister();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /* super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IndexFragment indexFragment = new IndexFragment();
        indexFragment.setCallBack(new ICallBack() {
            @Override
            public void toLogin() {
                Toast.makeText(MainActivity.this,"登录",Toast.LENGTH_SHORT).show();
                replaceFragment(new LoginFragment());
            }

            @Override
            public void toRegister() {
                Toast.makeText(MainActivity.this,"注册",Toast.LENGTH_SHORT).show();
            }
        });
        replaceFragment(indexFragment);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        toLoginButton = findViewById(R.id.login_bt);
        toRegisterButton = findViewById(R.id.register_bt);
        toLoginButton.setOnClickListener(view->{
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        });
        toRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}