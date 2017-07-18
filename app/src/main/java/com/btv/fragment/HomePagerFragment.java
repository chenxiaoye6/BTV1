package com.btv.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.btv.Bean.FuWuBean;
import com.btv.Bean.GongDanBean;
import com.btv.Bean.GongGaoBean;
import com.btv.Bean.GuanZhuBean;
import com.btv.Bean.JiFangBean;
import com.btv.Bean.WarningBean;
import com.btv.MyApplication;
import com.btv.R;
import com.btv.Utils.HttpUtils;
import com.btv.Utils.Internet;
import com.btv.activity.AboutActivity;
import com.btv.activity.GongGaoDetailActivity;
import com.btv.activity.NewestOrderDetail;
import com.btv.activity.NoticeActivity;
import com.btv.activity.SettingActivity;
import com.btv.activity.WarningDetail;
import com.btv.adapter.FuWuAdapter;
import com.btv.adapter.GongDanAdapter;
import com.btv.adapter.GongGaoAdapter;
import com.btv.adapter.JiFangAdapter;
import com.btv.adapter.ResourceAdapter;
import com.btv.adapter.WarningAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/17.
 */

public class HomePagerFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private View view;
    private PieChart mChart;
    private FrameLayout chartContainer;
    private String token, warningLevel, timeMills, pageSize, currentPage;
    //we are going to display the pie chart for smartphone market shares
//    private int[] yData = {1, 1, 1, 1, 1, 1};
    private int[] yData = new int[6];
    private String[] xData = {"未知", "信息", "警告", "次要", "严重", "致命"};
    private ListView shouye__guanzhu_lv, shouye_gongdan_lv, shouye__jinggao_lv, shouye__jifang_lv, shouye__fuwu_lv, shouye__gonggao_lv;
    private DrawerLayout drawerLayout;
    private SharedPreferences sp;
    private String userId;
    private ImageView iv_title_left;

    private WarningAdapter warningAdapter;
    private GongDanAdapter gongDanAdapter;
    private ResourceAdapter resourceAdapter;
    private JiFangAdapter jiFangAdapter;
    private FuWuAdapter fuWuAdapter;
    private ArrayList<WarningBean> homewarningBeenList;
    private ArrayList<GongDanBean> gongDanlist;
    private ArrayList<GuanZhuBean> guanZhuList;
    private ArrayList<JiFangBean> jiFangList;
    private ArrayList<FuWuBean> fuWuList;
    private ArrayList<GongGaoBean> gongGaolist;
    private LinearLayout ll_left_homepager;
    private DrawerLayout mDrawerLayout;
    private TextView left_setting, left_notice, left_about, left_huanfu, left_logout, left_exit, left_versions;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.homepager, container, false);
        //// TODO: 2017/7/8 cc
        initView();
        initEvents();
        mDrawerLayout.setScrimColor(0x00000000);
        //侧滑
        iv_title_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mDrawerLayout.isDrawerOpen(ll_left_homepager)) {
                    mDrawerLayout.openDrawer(ll_left_homepager);
                } else {
                    mDrawerLayout.closeDrawer(ll_left_homepager);
                }
            }
        });
        //扇形图的绘制
        chartContainer = (FrameLayout) view.findViewById(R.id.chartContainer);
        chartContainer = initPieChart();
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        userId = sp.getString("userId", "");
        Log.e("789", "token----------" + token);
        Log.e("789", "userId======" + userId);
        initWarningView();
        initGongDanView();
        initGuanZhuView();
        initJiFangView();
        initFuWuView();
        initGongGao();
        return view;
    }

    public void OpenRightMenu(View view) {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,
                Gravity.RIGHT);
    }

    private void initEvents() {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
                                            @Override
                                            public void onDrawerSlide(View drawerView, float slideOffset) {
                                                View mContent = mDrawerLayout.getChildAt(0);
                                                mContent.setAlpha(0.3f);
                                                //侧滑的动画效果
                                            }

                                            @Override
                                            public void onDrawerOpened(View drawerView) {

                                            }

                                            @Override
                                            public void onDrawerClosed(View drawerView) {
                                                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
                                                mDrawerLayout.getChildAt(0).setAlpha(1);
                                            }

                                            @Override
                                            public void onDrawerStateChanged(int newState) {
                                            }
                                        }
        );

    }

    //初始化控件
    private void initView() {
        //侧边栏的初始化控件
        ll_left_homepager = (LinearLayout) view.findViewById(R.id.ll_left_homepager);
        left_setting = (TextView) view.findViewById(R.id.left_setting);
        left_notice = (TextView) view.findViewById(R.id.left_notice);
        left_about = (TextView) view.findViewById(R.id.left_about);
        left_huanfu = (TextView) view.findViewById(R.id.left_huanfu);
        left_logout = (TextView) view.findViewById(R.id.left_logout);
        left_exit = (TextView) view.findViewById(R.id.left_exit);
        left_versions = (TextView) view.findViewById(R.id.left_versions);
        left_setting.setOnClickListener(this);
        left_notice.setOnClickListener(this);
        left_about.setOnClickListener(this);
        left_huanfu.setOnClickListener(this);
        left_logout.setOnClickListener(this);
        left_exit.setOnClickListener(this);
        //侧边栏的初始化控件
        iv_title_left = (ImageView) view.findViewById(R.id.iv_title_left);
        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.id_drawerLayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        shouye__jinggao_lv = (ListView) view.findViewById(R.id.shouye__jinggao_lv);
        shouye_gongdan_lv = (ListView) view.findViewById(R.id.shouye_gongdan_lv);
        shouye__guanzhu_lv = (ListView) view.findViewById(R.id.shouye__ziyuan_lv);
        shouye__jifang_lv = (ListView) view.findViewById(R.id.shouye__jifang_lv);
        shouye__fuwu_lv = (ListView) view.findViewById(R.id.shouye__fuwu_lv);
        shouye__gonggao_lv = (ListView) view.findViewById(R.id.shouye__gonggao_lv);
    }

    //初始化公告界面
    private void initGongGao() {
//        Log.e("123gg", "initgonggao============");
        HashMap<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("userId", userId);
        HttpUtils.doPost(Internet.GONGGAO, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                getActivity().runOnUiThread(new Runnable() {

                    private GongGaoBean gongGaoBean;

                    @Override
                    public void run() {
                        Log.e("789", "公告----------" + data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            JSONArray array = jsonObject.getJSONArray("data");
                            JsonParser jsonParser = new JsonParser();
                            JsonArray jsonArray = jsonParser.parse(array.toString()).getAsJsonArray();
                            Gson gson = new Gson();
                            gongGaolist = new ArrayList<GongGaoBean>();
                            for (JsonElement gonggao : jsonArray) {
                                gongGaoBean = gson.fromJson(gonggao, GongGaoBean.class);
                                gongGaolist.add(gongGaoBean);
                                //
                            }
                            Log.e("789", "公告" + gongGaolist.toString());
                            shouye__gonggao_lv.setAdapter(new GongGaoAdapter(getActivity(), gongGaolist));
                            shouye__gonggao_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(getActivity(), GongGaoDetailActivity.class);
                                    intent.putExtra("gongGaoBean", gongGaoBean);
                                    startActivity(intent);
                                }
                            });
                            setListViewHeight(shouye__gonggao_lv);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    //初始化服务一栏界面
    private void initFuWuView() {
//        Log.e("123fuwu", "initjifang============");
        HashMap<String, String> param = new HashMap<>();
        param.put("token", token);
//        Log.e("123fuwu", param.toString());
        HttpUtils.doPost(Internet.FUWU, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("789", e + "");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("789", "服务----------" + data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray ResourceObj = data.getJSONArray("ServiceView");
                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(ResourceObj.toString()).getAsJsonArray();
                            Gson gson = new Gson();
                            fuWuList = new ArrayList<FuWuBean>();
                            for (JsonElement fuWu : jsonArray) {
                                FuWuBean fuWuBean = gson.fromJson(fuWu, FuWuBean.class);
                                fuWuList.add(fuWuBean);
                            }
                            Log.e("789", "服务----------" + fuWuList.toString());
                            fuWuAdapter = new FuWuAdapter(getActivity(), fuWuList);
                            shouye__fuwu_lv.setAdapter(fuWuAdapter);
                            setListViewHeight(shouye__fuwu_lv);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    //初始化机房一栏界面
    private void initJiFangView() {
//        Log.e("123jifang", "initjifang============");
        HashMap<String, String> param = new HashMap<>();
        param.put("token", token);
//        Log.e("123", param.toString());
        HttpUtils.doPost(Internet.JIFANG, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("789", "机房=========" + data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray ResourceObj = data.getJSONArray("RoomView");
                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(ResourceObj.toString()).getAsJsonArray();
                            Gson gson = new Gson();
                            jiFangList = new ArrayList<JiFangBean>();
                            for (JsonElement jiFang : jsonArray) {
                                JiFangBean jiFangBean = gson.fromJson(jiFang, JiFangBean.class);
                                jiFangList.add(jiFangBean);
                            }
                            jiFangAdapter = new JiFangAdapter(getActivity(), jiFangList);
                            shouye__jifang_lv.setAdapter(jiFangAdapter);
                            setListViewHeight(shouye__jifang_lv);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    //初始化关注资源界面c
//  参数:  token	true	string	令牌
//    userId	true	string	会员编号
    private void initGuanZhuView() {
//        Log.e("123guanzhu", "initguanzhu============");
        HashMap<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("userId", userId);
        HttpUtils.doPost(Internet.GUANZHU, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("789", "关注资源===" + data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray ResourceObj = data.getJSONArray("ResourceObj");
                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(ResourceObj.toString()).getAsJsonArray();
                            Gson gson = new Gson();
                            guanZhuList = new ArrayList<GuanZhuBean>();
                            for (JsonElement guanZhu : jsonArray) {
                                GuanZhuBean guanZhuBean = gson.fromJson(guanZhu, GuanZhuBean.class);
                                guanZhuList.add(guanZhuBean);
                            }
                            resourceAdapter = new ResourceAdapter(getActivity(), guanZhuList);
                            shouye__guanzhu_lv.setAdapter(resourceAdapter);
                            setListViewHeight(shouye__guanzhu_lv);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    //初始化工单控件
//    token	true	string	令牌
//    queryStart	false	int	起始位置
//    queryLimit	false	int	数据条数(如果为空返回最少条数)
//    userId	true	string	用户UserID
    private void initGongDanView() {
//        Log.e("123gongdan", "initgongdan============");
        HashMap<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("queryStart", "1");
        param.put("queryLimit", "3");
        param.put("userId", userId);
        HttpUtils.doPost(Internet.GONGDAN, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("789", "工单----------" + data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray workFormList = data.getJSONArray("workFormList");
                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(workFormList.toString()).getAsJsonArray();
                            Gson gson = new Gson();
                            gongDanlist = new ArrayList<GongDanBean>();
                            for (JsonElement gongdan : jsonArray) {
                                GongDanBean gongDanBean = gson.fromJson(gongdan, GongDanBean.class);
                                gongDanlist.add(gongDanBean);
                            }
                            gongDanAdapter = new GongDanAdapter(getActivity(), gongDanlist);
                            shouye_gongdan_lv.setAdapter(gongDanAdapter);
                            shouye_gongdan_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(getActivity(), NewestOrderDetail.class);
                                    intent.putExtra("gongdan", gongDanlist.get(position));
                                    startActivity(intent);
                                }
                            });
                            setListViewHeight(shouye_gongdan_lv);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }


    //初始化告警
    private void initWarningView() {
        warningLevel = "0";
        timeMills = "1800000";
//        timeMills = "18";
        pageSize = "3";
        currentPage = "1";
//        Log.e("123", token);
        HashMap<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("warningLevel", warningLevel);
        param.put("timeMills", timeMills);
        param.put("pageSize", pageSize);
        param.put("currentPage", currentPage);

//        Log.e("123", param.toString());
        HttpUtils.doPost(Internet.WARNING, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("123", data + "=============");
                        parseJson(data);
                        if (homewarningBeenList == null) {
                            initPieChart();
                        } else {
                            initPieChart();
                            warningAdapter = new WarningAdapter(getActivity(), homewarningBeenList);
                            shouye__jinggao_lv.setAdapter(warningAdapter);
                            shouye__jinggao_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(getActivity(), WarningDetail.class);
                                    intent.putExtra("warningBean", homewarningBeenList.get(position));
                                    startActivity(intent);
                                }
                            });
                            setListViewHeight(shouye__jinggao_lv);
                        }
                    }

                    //解析告警数据
                    private void parseJson(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            JSONObject data1 = jsonObject.getJSONObject("data");
                            Log.e("123", data);
                            //在这里判断没有数据的时候绘制空的圆环
                            yData[0] = data1.getInt("Warning.unknown");
                            yData[1] = data1.getInt("Warning.information");
                            yData[2] = data1.getInt("Warning.ordinary");
                            yData[3] = data1.getInt("Warning.secondary");
                            yData[4] = data1.getInt("Warning.serious");
                            yData[5] = data1.getInt("Warning.deadly");
                            JSONArray warningobj = data1.getJSONArray("Warning.obj");
                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(warningobj.toString()).getAsJsonArray();
                            Gson gson = new Gson();
                            homewarningBeenList = new ArrayList<WarningBean>();
                            for (JsonElement warning : jsonArray) {
                                WarningBean warningBean = gson.fromJson(warning, WarningBean.class);
                                homewarningBeenList.add(warningBean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    //初始化扇形
    private FrameLayout initPieChart() {
        mChart = new PieChart(getActivity());
        chartContainer.addView(mChart);
        //configure pie chart
        mChart.setUsePercentValues(false);
        mChart.setDescription("");
        //enable hole and configure
        mChart.setDrawHoleEnabled(true);
        // mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(40);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotation(0);
        mChart.setRotationEnabled(false);
        //set a chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                //display message when value selected
                if (e == null)
                    return;
//                Toast.makeText(getActivity(), xData[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
//                mChart.setCenterText(e.getVal() + "");
            }

            @Override
            public void onNothingSelected() {
            }
        });
        // add Data
        addData();
        //customize legends
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        //设置图例不可见
        l.setEnabled(false);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        return chartContainer;
    }

    //扇形的数据源
    private PieData addData() {
        int m = 0;
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < yData.length; i++) {
            m = m + yData[i];
            if (yData[i] == 0) {

            } else {
                yVals1.add(new Entry(yData[i], i));
            }
        }
        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++) {

            if (yData[i] == 0) {
            } else {
                xVals.add(yData[i] + xData[i]);
            }
        }
        mChart.setCenterText(m + "");
        //create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        //判断是不是只有一种数据,设置分开的间隙,
        if (xVals.size() == 1) {
            dataSet.setSliceSpace(0);
            dataSet.setSelectionShift(5);
        } else {
            dataSet.setSliceSpace(0);
            dataSet.setSelectionShift(5);
        }

        // add many colors
        //"未知", "信息", "普通警告", "次要", "严重", "致命"
        int color[] = {Color.rgb(168, 168, 168), Color.rgb(0, 207, 255), Color.rgb(253, 220, 65), Color.rgb(248, 138, 103), Color.rgb(255, 124, 155), Color.rgb(252, 10, 10)};
        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < yData.length; i++) {
            if (yData[i] == 0) {
            } else {
                colors.add(color[i]);
            }
        }
        dataSet.setColors(colors);
        dataSet.setValueLinePart1OffsetPercentage(90f);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.8f);
        dataSet.setValueLineColor(R.color.hui4);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int n = (int) value;
                return "";
            }
        });
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);
        mChart.setData(data);
        //undo all higlights
        mChart.highlightValues(null);
        // update pie chart
        mChart.invalidate();
        return data;
    }

    // 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
    public void setListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Log.e("123", "veiw.getid" + view.getId() + position);
//        Log.e("123", "shouye__jinggao_lv" + R.id.shouye__jinggao_lv + position);
        switch (parent.getId()) {

            case R.id.shouye__jinggao_lv:
//                Toast.makeText(getActivity(), position + "被点击了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.shouye__ziyuan_lv:
                break;
            case R.id.shouye_gongdan_lv:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.left_exit:
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setIcon(0)
                        .setTitle("")
                        .setMessage("是否退出应用?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyApplication.getInstance().exit();
                            }
                        })
                        .setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

                break;
            case R.id.left_notice:
                //跳转到公告
                startActivity(new Intent(getActivity(), NoticeActivity.class));
                break;
            case R.id.left_about:
                //关于
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.left_huanfu:
                break;
            case R.id.left_logout:
                break;
            default:
                break;

        }
    }
}
