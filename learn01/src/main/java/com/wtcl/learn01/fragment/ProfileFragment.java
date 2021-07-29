package com.wtcl.learn01.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wtcl.learn01.R;
import com.wtcl.learn01.activitys.UserDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements FragmentInterface{

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String TAG = "ProfileFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
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
        LinearLayout linearLayout = view.findViewById(R.id.profile_head_toDetail);
        linearLayout.setOnClickListener(e->{
            Intent intent = new Intent(getActivity(), UserDetailActivity.class);
            startActivity(intent);
        });
    }
}