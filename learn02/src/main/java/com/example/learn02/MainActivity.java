package com.example.learn02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.learn02_service.MusicService;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author WTCL
 */
public class MainActivity extends AppCompatActivity {

    public static final String CTL_ACTION = "com.example.learn02.update_action";
    public String[] musicNames = new String[]{"大风吹","好心分手","如果声音不记得","少年"};
    public String[] musicAuthors = new String[]{"江之遇","孟祥龙","多多","梦然"};
    //获取界面上显示的歌曲标题，歌手

    private TextView musicName;
    private TextView musicAuthor;
    private TextView music_currentTime;
    private TextView music_TotalTime;
    private ProgressBar progressBar;
    //获取上一首、下一首、播放/暂停按钮

    private Button past;
    private ImageButton play_pause;
    private Button next;
    private Button drop;
    private FrontReceiver frontReceiver;

    //当前播放歌曲

    int current = 0;

    private Timer timer;
    private TimerTask timerTask;

    //aidl服务

    private MusicService musicService;


    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = MusicService.Stub.asInterface(service);
            if (musicService==null) {
                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(MainActivity.this,"success",Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化组件
        setContentView(R.layout.activity_main);
        initView();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(frontReceiver);
        unbindService(conn);
    }

    //接受服务器广播改变播放暂停按钮

    class FrontReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int cur = intent.getIntExtra("current",-1);
            Log.d("广播","接收到广播:" + cur);
            int status = intent.getIntExtra("status",-1);
            musicName.setText(musicNames[cur]);
            musicAuthor.setText(musicAuthors[cur]);
            if (status == 0x12){
                play_pause.setBackground(ContextCompat.getDrawable(context, R.drawable.pause));
                /*timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        updateProgress();
                    }
                };
                timer.schedule(timerTask,0,800);*/
            }else if (status == 0x13){
                play_pause.setBackground(ContextCompat.getDrawable(context, R.drawable.play));
               /* if (timer != null){
                    timer.cancel();
                    timer = null;
                    timerTask.cancel();
                    timerTask = null;
                }*/
            }
        }
    }


    //初始化视图组件

    private void initView(){
        musicName = findViewById(R.id.music_name);
        musicAuthor = findViewById(R.id.music_author);
        progressBar = findViewById(R.id.music_progress);
        past = findViewById(R.id.past);
        play_pause = findViewById(R.id.play_pause);
        next = findViewById(R.id.next);
        drop = findViewById(R.id.drop);
        music_currentTime = findViewById(R.id.music_current_time);
        music_TotalTime = findViewById(R.id.music_total_time);
        //初始化为第一首歌曲信息
        musicName.setText(musicNames[current]);
        musicAuthor.setText(musicAuthors[current]);
        //为切换按钮设置事件监听器
        View.OnClickListener listener= e -> {
            Intent intent = new Intent("com.example.learn02_service.update_action");
            intent.setPackage("com.example.learn02_service");
            switch (e.getId()){
                case R.id.past:
                    intent.putExtra("next",0);
                    break;
                case R.id.next:
                    intent.putExtra("next",1);
                    break;
            }
            sendBroadcast(intent);
        };
        past.setOnClickListener(listener);
        next.setOnClickListener(listener);
        drop.setOnClickListener(view->{finish();});
        //动态注册广播
        frontReceiver = new FrontReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(CTL_ACTION);
        registerReceiver(frontReceiver,filter);
        Intent serviceIntent = new Intent();
        serviceIntent.setAction("com.example.learn02_service.action");
        serviceIntent.setPackage("com.example.learn02_service");
        //绑定远程service
        bindService(serviceIntent,conn, BIND_AUTO_CREATE);
        //为播放暂停按钮注册事件监听器
        play_pause.setOnClickListener(e->{
            try {
                if (musicService !=null){
                    musicService.play();

                }else{
                    Toast.makeText(MainActivity.this,"为空",Toast.LENGTH_SHORT).show();
                }
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });
        timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        updateProgress();
                    }
                };
        timer.schedule(timerTask,0,1000);
    }

    //更新进度条

    private void updateProgress(){
       try {
           if (musicService != null){
               int progress = (int)musicService.getProgress();
               Log.d("progress","progress:" + progress);
               progressBar.setProgress(progress);
           }else {
               Log.d("progress","服务为空");
           }
       }catch (RemoteException e){
           e.printStackTrace();
       }
    }
}