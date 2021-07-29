package com.wtcl.learnandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.Xml;
import android.widget.Button;
import android.widget.Toast;

import com.example.learn02_service.MusicService;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);
        try {
            List<Person> list = getPersons(getAssets().open("person.xml"));
            int len = list.size();
            Log.d("ssssssssssssssss","size: " + len);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Person> getPersons(InputStream is) throws Exception{
        List<Person> persons = null;
        Person person = null;
        XmlPullParser pullParse = Xml.newPullParser();
        pullParse.setInput(is, "UTF-8");
        int event = pullParse.getEventType();
        while(event != XmlPullParser.END_DOCUMENT){
            switch (event) {
                case XmlPullParser.START_TAG:
                    if("persons".equals(pullParse.getName())){
                        persons = new ArrayList<Person>();
                    }
                    if("person".equals(pullParse.getName())){
                        person = new Person();
                        int id = Integer.parseInt(pullParse.getAttributeValue(0));
                        person.setId(id);
                    }
                    if("name".equals(pullParse.getName())){
                        String name = pullParse.nextText();
                        person.setName(name);
                    }
                    if("age".equals(pullParse.getName())){
                        int age = Integer.parseInt(pullParse.nextText());
                        person.setAge(age);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("person".equals(pullParse.getName())){
                        persons.add(person);
                        person = null;
                    }
                    break;
            }
            event = pullParse.next();
        }
        return persons;
    }
    public void init1(){
        Button send = findViewById(R.id.send);
        //Intent intent1 = new Intent(this,MyMusicService.class);
        //startService(intent1);

        Intent intent2 = new Intent();
        intent2.setAction("com.example.learn02_service.action");
        intent2.setPackage("com.example.learn02_service");
        //绑定远程service
        bindService(intent2,conn, BIND_AUTO_CREATE);
        send.setOnClickListener(e->{
            Intent intent = new Intent("com.example.learn02_service.update_action");
            intent.setPackage("com.example.learn02_service");
            sendBroadcast(intent);
            try {
                if (musicService != null){
                    musicService.pause();
                }else{
                    Toast.makeText(MainActivity.this,"服务为空",Toast.LENGTH_SHORT).show();
                }
            } catch (RemoteException remoteException) {
                remoteException.printStackTrace();
            }
        });
    }
}