package com.wtcl.learn01.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtcl.learn01.R;
import com.wtcl.learn01.components.CommentAdapter;
import com.wtcl.learn01.entity.HotComments;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * @author 石益然
 */
public class InforFragment extends Fragment implements FragmentInterface{
    private static final String TAG = "inforFragment";
    private List<HotComments> commentsList = new ArrayList<>();
    private RecyclerView recyclerView;

    public InforFragment() {
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
        return inflater.inflate(R.layout.fragment_infor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG,TAG + ":onViewCreated");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG,TAG + ":onActivityCreated");
        View view = getView();
        initView(view);
    }

    /*@Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,TAG + ":onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,TAG + ":onResume");
    }
*/
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
        commentsList.clear();
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

    private Handler handler = new HotCommentHandler(this);

    //静态内部类加 弱引用解决内存泄漏
    private static class HotCommentHandler extends Handler{
        private WeakReference<InforFragment> wR;
        public HotCommentHandler(InforFragment inforFragment){
            wR = new WeakReference<>(inforFragment);
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0x1){
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(wR.get().getContext());
                wR.get().recyclerView.setLayoutManager(layoutManager);
                wR.get().recyclerView.setAdapter(new CommentAdapter(wR.get().commentsList,wR.get().getContext()));
            }
        }
    }

    @Override
    public void initView(View view) {
        recyclerView = view.findViewById(R.id.commentsList);
        downLoadHotComments();
    }

    //下载N条网易云热评
    private static final String HOT = "https://api.muxiaoguo.cn/api/163reping?api_key=4097c4c840707714";
    public void downLoadHotComments(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(HOT).get().build();
        for (int i = 0; i < 5; i++) {
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(TAG,"下载失败");
                }
                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()){
                        String json = response.body().string();
                        try {
                            parseJsonStr(json);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    //解析json字符串
    private void parseJsonStr(String json) throws JSONException {
        HotComments comments = new HotComments();
        JSONObject jsonObject = new JSONObject(json);
        JSONObject data = jsonObject.getJSONObject("data");
        comments.setContent(data.getString("content"));
        comments.setSongPic(data.getString("songPic"));
        comments.setSongAutho(data.getString("songAutho"));
        comments.setSongName(data.getString("songName"));
        comments.setSongId(data.getString("songId"));
        comments.setNickname(data.getString("nickname"));
        comments.setAvatar(data.getString("avatar"));
        comments.setLikedCount(data.getString("likedCount"));
        comments.setTime(data.getString("time"));
        commentsList.add(comments);
        handler.sendEmptyMessage(0x1);
    }
}