package com.btv.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.btv.Bean.JianKongBean;
import com.btv.R;
import com.btv.Utils.HttpUtils;
import com.btv.Utils.Internet;
import com.btv.Utils.Utils;
import com.btv.activity.SRescourceDetailActivity;
import com.btv.adapter.JianKongAdapter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 *
 *///// TODO: 2017/7/6 cc
public class ResourceFragment extends Fragment {

    @InjectView(R.id.barchart)
    BarChart barchart;
    @InjectView(R.id.tv_resource_zhuji)
    TextView tvResourceZhuji;
    @InjectView(R.id.view1)
    View view1;
    @InjectView(R.id.ll_resource_zhuji)
    LinearLayout llResourceZhuji;
    @InjectView(R.id.tv_resource_netdevice)
    TextView tvResourceNetdevice;
    @InjectView(R.id.view2)
    View view2;
    @InjectView(R.id.ll_resource_netdevice)
    LinearLayout llResourceNetdevice;
    @InjectView(R.id.tv_resource_savedevice)
    TextView tvResourceSavedevice;
    @InjectView(R.id.view3)
    View view3;
    @InjectView(R.id.ll_resource_savedevice)
    LinearLayout llResourceSavedevice;
    @InjectView(R.id.tv_resource_yingyong)
    TextView tvResourceYingyong;
    @InjectView(R.id.view4)
    View view4;
    @InjectView(R.id.ll_resource_yingyong)
    LinearLayout llResourceYingyong;
    @InjectView(R.id.tv_resource_other)
    TextView tvResourceOther;
    @InjectView(R.id.view5)
    View view5;
    @InjectView(R.id.ll_resource_other)
    LinearLayout llResourceOther;
    @InjectView(R.id.pull_refresh_list)
    PullToRefreshListView pullRefreshList;
    private View view;
    public ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
    public BarDataSet dataset;
    public ArrayList<String> labels = new ArrayList<String>();
    private ArrayList<JianKongBean> jkbeans = new ArrayList<>();
    private JianKongAdapter adapter;

    private SharedPreferences sp;
    private String token;
    private float redNum, greenNum, yellowNum, grayNum;//条形图的数据

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_resource, container, false);
        ButterKnife.inject(this, view);
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        initEntriesData();
        initData("1", "0");
        initLableData();
        show();
        initListData();
        return view;
    }

    private void initData(String resourceNum, String currentPage) {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("resourceNum", resourceNum);
        params.put("command", "1");
        params.put("stateType", "null");
        params.put("pageSize", "8");
        params.put("currentPage", currentPage);
        HttpUtils.doPost(Internet.MONITORING_RESOURCE, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String data = response.body().string();
                    Log.e("aaa", "onResponse: 监控资源返回 " + data);
                    JSONObject jsonObject =new JSONObject(data).getJSONObject("data");
                    JSONObject resourceNumCount = jsonObject.getJSONArray("ResourceNumCount").getJSONObject(0);

                    redNum = Utils.convertToFloat(resourceNumCount.getString("redNum"),0f);
                    greenNum = Utils.convertToFloat(resourceNumCount.getString("greenNum"),0f);
                    yellowNum = Utils.convertToFloat(resourceNumCount.getString("yellowNum"),0f);
                    grayNum = Utils.convertToFloat(resourceNumCount.getString("grayNum"),0f);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                    Log.e("aaa", "onResponse: 获取的数据" + redNum + "   " + greenNum + "   " + yellowNum + "   " + grayNum + "   ");

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        entries.clear();
                        entries.add(new BarEntry(redNum,0));
                        entries.add(new BarEntry(greenNum,1));
                        entries.add(new BarEntry(yellowNum,2));
                        entries.add(new BarEntry(grayNum,3));
                        show();
                    }
                });
            }
        });
    }

    //初始化list数据
    private void initListData() {
        jkbeans.add(new JianKongBean());
        jkbeans.add(new JianKongBean());
        jkbeans.add(new JianKongBean());
        pullRefreshList.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new JianKongAdapter(jkbeans, getActivity());
        pullRefreshList.setAdapter(adapter);
        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        pullRefreshList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
                                    jkbeans.remove(0);
                                    adapter.notifyDataSetChanged();
                                    pullRefreshList.onRefreshComplete();
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
                                    jkbeans.add(new JianKongBean());
                                    adapter.notifyDataSetChanged();
                                    pullRefreshList.onRefreshComplete();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        pullRefreshList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getActivity(), SRescourceDetailActivity.class));
            }
        });
    }


    //初始化条形图数据
    public void initEntriesData() {
        entries.add(new BarEntry(0, 0));
        entries.add(new BarEntry(0, 1));
        entries.add(new BarEntry(0, 2));
        entries.add(new BarEntry(0, 3));
    }

    public void initLableData() {
        labels.add("在线严重");
        labels.add("性能状态正常");
        labels.add("在线警告");
        labels.add("未知");
    }

    public void show() {
        barchart.getXAxis().setGridColor(Color.TRANSPARENT);//去掉网格中竖线的显示
        barchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barchart.getXAxis().setValueFormatter(new XAxisValueFormatter() {
            @Override
            public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
                return "";
            }
        });

        YAxis axisLeft = barchart.getAxisLeft(); //y轴左边标示
        axisLeft.setEnabled(true);
        axisLeft.setTextColor(Color.GRAY); //字体颜色
        axisLeft.setTextSize(10f); //字体大小
        axisLeft.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                return "" + (int) value;
            }
        });
        axisLeft.setAxisMaxValue(5); //最大值
        axisLeft.setAxisMinValue(0);
        axisLeft.setLabelCount(6, true); //显示格数
        barchart.setDragEnabled(false);// 是否可以拖拽
        barchart.setScaleEnabled(false);// 是否可以缩放
        barchart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
        dataset = new BarDataSet(entries, "");
        dataset.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "" + (int) value;
            }
        });
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.parseColor("#D6D6D6"));
        dataset.setColors(colors);
        dataset.setBarSpacePercent(70f);
        BarData data = new BarData(labels, dataset);
        barchart.setData(data);
        //设置图例
        Legend l = barchart.getLegend();
        l.setEnabled(true);
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setForm(Legend.LegendForm.CIRCLE);// 样式
        l.setFormSize(6f);// 字体
        l.setTextColor(Color.BLACK);// 颜色
        l.setCustom(colors, labels);
        barchart.animateY(1000);
        barchart.setDescription("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void inittab() {
        view1.setBackgroundColor(Color.parseColor("#ffffff"));
        view2.setBackgroundColor(Color.parseColor("#ffffff"));
        view3.setBackgroundColor(Color.parseColor("#ffffff"));
        view4.setBackgroundColor(Color.parseColor("#ffffff"));
        view5.setBackgroundColor(Color.parseColor("#ffffff"));
        tvResourceZhuji.setTextColor(Color.parseColor("#000000"));
        tvResourceNetdevice.setTextColor(Color.parseColor("#000000"));
        tvResourceSavedevice.setTextColor(Color.parseColor("#000000"));
        tvResourceYingyong.setTextColor(Color.parseColor("#000000"));
        tvResourceOther.setTextColor(Color.parseColor("#000000"));
    }

    @OnClick({R.id.ll_resource_zhuji, R.id.ll_resource_netdevice, R.id.ll_resource_savedevice, R.id.ll_resource_yingyong, R.id.ll_resource_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_resource_zhuji:
                inittab();
                view1.setBackgroundColor(Color.parseColor("#62D6C8"));
                tvResourceZhuji.setTextColor(Color.parseColor("#62D6C8"));
                initData("1","0");
                break;
            case R.id.ll_resource_netdevice:
                inittab();
                view2.setBackgroundColor(Color.parseColor("#62D6C8"));
                tvResourceNetdevice.setTextColor(Color.parseColor("#62D6C8"));
                initData("2","0");
                break;
            case R.id.ll_resource_savedevice:
                inittab();
                view3.setBackgroundColor(Color.parseColor("#62D6C8"));
                tvResourceSavedevice.setTextColor(Color.parseColor("#62D6C8"));
                initData("3","0");
                break;
            case R.id.ll_resource_yingyong:
                inittab();
                view4.setBackgroundColor(Color.parseColor("#62D6C8"));
                tvResourceYingyong.setTextColor(Color.parseColor("#62D6C8"));
                initData("4","0");
                break;
            case R.id.ll_resource_other:
                inittab();
                view5.setBackgroundColor(Color.parseColor("#62D6C8"));
                tvResourceOther.setTextColor(Color.parseColor("#62D6C8"));
                initData("5","0");
                break;
        }
    }
}
