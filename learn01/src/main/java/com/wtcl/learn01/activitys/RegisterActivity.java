package com.wtcl.learn01.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wtcl.learn01.R;
import com.wtcl.learn01.util.MyContentProvider;
import com.wtcl.learn01.util.Users;

/**
 * @author WTCL
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private Button btn_register;
    private ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    public void initView(){
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);
        constraintLayout = findViewById(R.id.register_father);

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

        btn_register.setOnClickListener(view->{
            String name = userName.getText().toString();
            String passwordStr = password.getText().toString();
            String res = "";
            boolean registerSuccess = false;
            if (validUserName(name)){
                if (validPassword(passwordStr)){
                        ContentValues values = new ContentValues();
                        values.put("userName",name);
                        values.put("password",passwordStr);
                        getContentResolver().insert(Users.User.USERS_CONTENT_URI,values);
                        res = "注册成功";
                        registerSuccess = true;
                }else {
                    res = "密码不符合规范";
                }
            }else {
                res = "用户名不正确";
            }
            Toast.makeText(RegisterActivity.this,res,Toast.LENGTH_SHORT).show();
            if(registerSuccess){
                finish();
            }
        });
    }

    private boolean validUserName(String userName){
        if (userName.length() < 6 || userName.length() > 8){
            return false;
        }
        return true;
    }

    private boolean validPassword(String password){
        if (password.length() < 6 || password.length() > 8){
            return false;
        }
        return true;
    }
}