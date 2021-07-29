package com.wtcl.learn01.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.wtcl.learn01.R;
import com.wtcl.learn01.components.FixedEditText;
import com.wtcl.learn01.util.Users;

import java.io.File;

/**
 * @author WTCL
 */
public class LoginActivity extends AppCompatActivity {
    private Button login;
    private FixedEditText userName;
    private FixedEditText password;
    private CheckBox remember;
    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView(){
        login = findViewById(R.id.login);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        remember = findViewById(R.id.remember);
        constraintLayout = findViewById(R.id.father);
        userName.setFixedText("帐名");
        password.setFixedText("密码");

        //从preference初始化本地数据
        initUserPass();

        //添加监听器处理输入框失去焦点事件
        constraintLayout.setOnClickListener(view->{
            userName.clearFocus();//取消焦点
            password.clearFocus();
            // 隐藏输入法
            InputMethodManager imm = (InputMethodManager) getApplicationContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            // 显示或者隐藏输入法
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        });

        login.setOnClickListener(view->{
            //如果登录验证成功
            if (validLogin()){
                //将账号密码写入内部存储
                if (remember.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("userName",userName.getText().toString());
                    editor.putString("password",password.getText().toString());
                    editor.putBoolean("remember",remember.isChecked());
                    editor.commit();
                }else {
                    File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs","login.xml");
                    if(file.exists()){
                        file.delete();
                    }
                }
                Intent intent = new Intent(LoginActivity.this,IndexActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
            }
        });
    }


    //验证账号和密码

    private boolean validLogin(){
        String where = "userName = ? and password = ?";
        String[] args = new String[]{userName.getText().toString(),password.getText().toString()};
        Cursor cursor = getContentResolver().query(Users.User.USERS_CONTENT_URI,new String[]{"userName"},where,args,null);
        return cursor==null?false:cursor.moveToNext();
    }

    //初始化本地preference到账号密码框

    private void initUserPass(){
        SharedPreferences preferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        userName.setText(preferences.getString("userName",""));
        password.setText(preferences.getString("password",""));
        remember.setChecked(preferences.getBoolean("remember",false));
    }
}