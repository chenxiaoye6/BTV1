package com.btv.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.btv.Bean.SServiceBean;
import com.btv.R;
import com.btv.Utils.Internet;
import com.btv.activity.SserviceDetailActivity;
import com.btv.adapter.SServiceAdapter;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 *
 */
public class ServicesFragment extends Fragment {


    @InjectView(R.id.service_fragment_pulllist)
    PullToRefreshListView serviceFragmentPulllist;
    private ArrayList<SServiceBean.BizSvcListBean> sserviceList;

    private SharedPreferences sp;
    private String token;
    private SServiceBean sServiceBean;
    private SServiceAdapter adapter;
    private int page = 0;
    int m = 0;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
            serviceFragmentPulllist.onRefreshComplete();
            m = 0;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service, container, false);
        ButterKnife.inject(this, view);
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        initData("1");
        initView();
        return view;
    }

    private void initData(String page) {
        sserviceList = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();
        params.put("command", "3");
        params.put("token", token);
        params.put("currentPage", page);
        params.put("pageSize", "8");
        OkHttpUtils.post().url(Internet.MONITORING_RESOURCE).params(params).build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    Log.e("aaa",
                        "(ServicesFragment.java:96)"+response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Gson gson = new Gson();
                    sServiceBean = gson.fromJson(data.toString(), SServiceBean.class);
                    sserviceList.addAll(sServiceBean.getBizSvcList());
                    if (m == 0) {
                        adapter = new SServiceAdapter(sserviceList, getActivity());
                        serviceFragmentPulllist.setAdapter(adapter);
                    }
                    {
                        mhandler.sendEmptyMessageDelayed(1, 2000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {

        serviceFragmentPulllist.setMode(PullToRefreshBase.Mode.BOTH);

        serviceFragmentPulllist.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
//                sserviceList.add(new SServiceBean());
                page = 0;
                m = 1;
                initData(page + "");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                m = 1;
                initData(page + "");
            }
        });
        serviceFragmentPulllist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击跳转到详情页面
                Log.e("aaa",
                        "(ServicesFragment.java:139)" + sserviceList.get(position - 1).toString());
                startActivity(new Intent(getActivity(), SserviceDetailActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
