package com.btv.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btv.R;
import com.btv.Utils.Internet;
import com.btv.Utils.L;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class JFBaseInfoFragment extends Fragment {

    private View view;

    private String id;
    private String token;

    public JFBaseInfoFragment() {
    }

    public JFBaseInfoFragment(String roomId) {
        this.id = roomId;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_base_info_jf, container, false);
        token = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("token", "");
        initData();
        return view;
    }

    private void initData() {

        HashMap<String, String> params = new HashMap<>();
        params.put("roomId", id);
        params.put("command", "1");
        params.put("token", token);

        OkHttpUtils.post().url(Internet.JIFANG_BASE_INFO).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {

                L.e(response);
            }
        });
    }

}
