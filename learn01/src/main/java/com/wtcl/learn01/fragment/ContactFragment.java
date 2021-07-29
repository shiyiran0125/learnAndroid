package com.wtcl.learn01.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wtcl.learn01.R;
import com.wtcl.learn01.components.AppListAdapter;
import com.wtcl.learn01.entity.App;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 石益然
 */
public class ContactFragment extends Fragment implements FragmentInterface{
    public static final String TAG = "ContactFragment";
    private RecyclerView recyclerView;
    private Button btn_searchApp;
    private EditText searchBox;
    private List<App> appList;
    private ConstraintLayout layout;
    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG,TAG + ":attach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,TAG + ":onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i(TAG,TAG + ":onCreateView");
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG,TAG + ":onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        initView(view);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,TAG + ":onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,TAG + ":onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,TAG + ":onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,TAG + ":onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        appList = null;
        Log.i(TAG,TAG + ":onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,TAG + ":onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG,TAG + ":onDetach");
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.app_list);
        btn_searchApp = view.findViewById(R.id.btn_searchApp);
        searchBox = view.findViewById(R.id.search_box);
        layout = view.findViewById(R.id.contact_layout);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        appList = new ArrayList<>();

        //为搜索按钮添加事件
        btn_searchApp.setOnClickListener(v->{
            if (!searchBox.getText().toString().equals("")){
                appList.clear();
                searchApp(searchBox.getText().toString());
            }else{
                Toast.makeText(getContext(), "输入应用名称", Toast.LENGTH_SHORT).show();
            }
        });

        //添加监听器处理输入框失去焦点事件
        layout.setOnClickListener(v->{
            searchBox.clearFocus();//取消焦点
            // 隐藏输入法
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            // 显示或者隐藏输入法
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0x11){
                recyclerView.setAdapter(new AppListAdapter(appList,getContext()));
            }
        }
    };

    private static final String APP_URL = "https://api.muxiaoguo.cn/api/appstore";

    //搜索app

    public void searchApp(String appName){
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(APP_URL).newBuilder();
        urlBuilder.setQueryParameter("appname",appName);
        Request request = new Request.Builder().url(urlBuilder.build()).get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    parseJson(response.body().string());
                    handler.sendEmptyMessage(0x11);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //解析下载的json数据

    public void parseJson(String json) throws JSONException {
        App app;
        JSONObject object = new JSONObject(json);
        JSONArray jsonArray = object.getJSONArray("data");
        for (int i = 0; i < jsonArray.length(); i++) {
            if (!jsonArray.getJSONObject(i).getString("name").equals("null")){
                app = new App();
                app.setName(jsonArray.getJSONObject(i).getString("name"));
                app.setSize(jsonArray.getJSONObject(i).getString("size"));
                app.setIcon(jsonArray.getJSONObject(i).getString("icon"));
                app.setUserCount(jsonArray.getJSONObject(i).getLong("userCount"));
                app.setEditorIntro(jsonArray.getJSONObject(i).getString("editorIntro"));
                app.setVersionName(jsonArray.getJSONObject(i).getString("versionName"));
                app.setDownloadurl(jsonArray.getJSONObject(i).getString("downloadurl"));
                appList.add(app);
            }
        }
    }
}