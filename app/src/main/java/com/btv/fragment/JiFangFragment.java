package com.btv.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.btv.Bean.SJifangBean;
import com.btv.R;
import com.btv.Utils.Internet;
import com.btv.Utils.L;
import com.btv.activity.JifangContentActivity;
import com.btv.adapter.AAdapter;
import com.btv.adapter.SJifangAdapter;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 *
 */
public class JiFangFragment extends Fragment {

    private View view;
    private PullToRefreshListView listView;
    private SJifangAdapter mAdapter;
    private AAdapter aAdapter;
    private List<SJifangBean.RoomListBean> mList = new ArrayList<>();

    //获取token
    private SharedPreferences sp;
    private String token;
    private SJifangBean sJifangBean;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ji_fang, container, false);
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        listView = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_list);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        initData();
        return view;
    }

    private void initData() {

        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("command","2");
        OkHttpUtils.post().url(Internet.MONITORING_RESOURCE).params(params).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                L.e(response);
                try {
                    JSONObject data = new JSONObject(response);
                    String string = data.getJSONObject("data").toString();
                    Gson gson = new Gson();
                    sJifangBean = gson.fromJson(string, SJifangBean.class);
//                    sJifangBean.getRoomList();
                    mList.addAll(sJifangBean.getRoomList());
                    mAdapter = new SJifangAdapter(mList,getActivity());
                    listView.setAdapter(mAdapter);
//                    aAdapter=new AAdapter(getActivity());
//                    listView.setAdapter(aAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {


            }
        });
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getActivity(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    jkbeans.remove(0);
//                                    adapter.notifyDataSetChanged();
                                    listView.onRefreshComplete();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getActivity(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            sleep(1000);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    jkbeans.add(new JianKongBean());
//                                    adapter.notifyDataSetChanged();
                                    listView.onRefreshComplete();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String roomId = mList.get(position-1).getRoomId();
                String state = mList.get(position-1).getRoomState();
                String name = mList.get(position-1).getRoomName();
                Intent intent = new Intent(getActivity(), JifangContentActivity.class);
                intent.putExtra("id",roomId);
                intent.putExtra("state",state);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }


}
