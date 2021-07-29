package com.example.filedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File fileSdcard= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        Log.d(TAG,"" + fileSdcard.getAbsolutePath());
        Log.d(TAG,"" + getExternalFilesDir(null));
        /*
        /storage/emulated/0/Android/data/com.example.filedemo/files
        */

        /*
        /storage/emulated/0/Android/data/com.example.filedemo/files/test
        **/
        Log.d(TAG,"" + getExternalFilesDir("test"));
    }
}