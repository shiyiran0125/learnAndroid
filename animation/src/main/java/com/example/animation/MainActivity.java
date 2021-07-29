package com.example.animation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button btnAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAnimation = findViewById(R.id.btn_animation);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation);
        btnAnimation.startAnimation(animation);
    }
}