package com.wtcl.learn01.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wtcl.learn01.R;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        initView();
    }
    public void initView(){
        LinearLayout linearLayout = findViewById(R.id.user_showBalance);
        linearLayout.setOnClickListener(e->{
            Intent intent = new Intent(UserDetailActivity.this, MoneyActivity.class);
            startActivity(intent);
        });
    }

}