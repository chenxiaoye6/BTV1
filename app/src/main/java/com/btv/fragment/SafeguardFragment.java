package com.btv.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.btv.Bean.GongDanBean;
import com.btv.R;
import com.btv.Utils.HttpUtils;
import com.btv.Utils.Internet;
import com.btv.activity.NewGongDan;
import com.btv.activity.NewestOrderDetail;
import com.btv.adapter.GongDanAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
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
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.btv.R.id.iv_title_left;

/**
 * Created by Administrator on 2016/11/17.
 */

public class SafeguardFragment extends Fragment {
    @InjectView(iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.ll_runcheck_notdo)
    LinearLayout llRuncheckNotdo;
    @InjectView(R.id.ll_runcheck_done)
    LinearLayout llRuncheckDone;
    @InjectView(R.id.ll_runcheck_check)
    LinearLayout llRuncheckCheck;
    @InjectView(R.id.ll_runcheck_newadd)
    LinearLayout llRuncheckNewadd;
    @InjectView(R.id.lv_runcheck_newestorder)
    ListView lvRuncheckNewestorder;
    @InjectView(R.id.mpiechart1)
    PieChart mpiechart1;
    @InjectView(R.id.mpiechart2)
    PieChart mpiechart2;
    private View view;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private List<GongDanBean> gongDanBeanList = new ArrayList<>();
    private String token;
    private SharedPreferences sp;
    private GongDanAdapter gongDanAdapter;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.safeguard, container, false);
        ButterKnife.inject(this, view);
        //初始化数据
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        userId = sp.getString("userId", "");
        initView();
        //初始化扇形
        initPiechart(mpiechart1);
        initPiechart(mpiechart2);
        initGongDan();

        return view;
    }

    //初始化扇形
    private void initPiechart(PieChart piec) {
        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据
        ArrayList<Integer> colors = new ArrayList<Integer>();
        if (piec == mpiechart1) {
            xValues.add("营运部");
            xValues.add("财务部");
            xValues.add("网络故障");
            xValues.add("市场部");

            // 饼图颜色
            colors.add(Color.parseColor("#FACDAE"));
            colors.add(Color.parseColor("#FF4266"));
            colors.add(Color.parseColor("#FC9D99"));
            colors.add(Color.parseColor("#88C3B7"));
            // 饼图数据
            /**
             * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
             * 所以 14代表的百分比就是14%
             */
            float quarterly1 = 1;
            float quarterly2 = 1;
            float quarterly3 = 1;
            float quarterly4 = 1;
            yValues.add(new Entry(quarterly1, 0));
            yValues.add(new Entry(quarterly2, 1));
            yValues.add(new Entry(quarterly3, 2));
            yValues.add(new Entry(quarterly4, 3));
        } else {
            xValues.add("机器故障");
            xValues.add("数据库故障");
            xValues.add("网络故障");


            // 饼图颜色
            colors.add(Color.parseColor("#FACDAE"));
            colors.add(Color.parseColor("#FF4266"));
            colors.add(Color.parseColor("#FC9D99"));
            // 饼图数据
            /**
             * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
             * 所以 14代表的百分比就是14%
             */
            float quarterly1 = 1;
            float quarterly2 = 1;
            float quarterly3 = 1;
            yValues.add(new Entry(quarterly1, 0));
            yValues.add(new Entry(quarterly2, 1));
            yValues.add(new Entry(quarterly3, 2));
        }
        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "");
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return ((int) value) + "";
            }
        });
        pieDataSet.setValueTextSize(10f);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 100f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度
        //指示线外出
        pieDataSet.setValueLinePart1OffsetPercentage(100f);
        pieDataSet.setValueLinePart1Length(0.5f);
        pieDataSet.setValueLinePart2Length(0.2f);
        pieDataSet.setHighlightEnabled(true);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);//标签显示在外面，关闭显示在饼图里面
        pieDataSet.setValueLineColor(0xff000000);  //设置指示线条颜色,必须设置成这样，才能和饼图区域颜色一致
        PieData pieData = new PieData(xValues, pieDataSet);
        showChart(piec, pieData);
    }

    //初始化工单
    private void initGongDan() {
        HashMap<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("queryStart", "1");
        param.put("queryLimit", "10");
        param.put("userId", userId);
        HttpUtils.doPost(Internet.GONGDAN, param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                Log.e("789", "data========" + data);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(data);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray workFormList = data.getJSONArray("workFormList");
                            //Json的解析类对象
                            JsonParser parser = new JsonParser();
                            //将JSON的String 转成一个JsonArray对象
                            JsonArray jsonArray = parser.parse(workFormList.toString()).getAsJsonArray();
                            Gson gson = new Gson();
                            for (JsonElement gongdan : jsonArray) {
                                GongDanBean gongDanBean = gson.fromJson(gongdan, GongDanBean.class);
                                gongDanBeanList.add(gongDanBean);
                                Map map = new HashMap<String, Object>();
                                map.put("news", gongDanBean.getDocTitle());
                                list.add(map);
                            }
                            lvRuncheckNewestorder.setAdapter(new SimpleAdapter(getActivity(), list, R.layout.item_runcheck_neworder,
                                    new String[]{"news"}, new int[]{R.id.tv_neworderitem}));
                            lvRuncheckNewestorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(getActivity(), NewestOrderDetail.class);
                                    intent.putExtra("gongdan", gongDanBeanList.get(position));
                                    startActivity(intent);
                                }
                            });
                            setListViewHeightBasedOnChildren(lvRuncheckNewestorder);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        ivTitleLeft.setVisibility(View.GONE);
        tvTitle.setText("运维");
        ivTitleRight.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.ll_runcheck_notdo, R.id.ll_runcheck_done, R.id.ll_runcheck_check, R.id.ll_runcheck_newadd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_runcheck_notdo:

                break;
            case R.id.ll_runcheck_done:

                break;
            case R.id.ll_runcheck_check:
                break;
            case R.id.ll_runcheck_newadd:
                //跳转到新建工单
                getActivity().startActivity(new Intent(getActivity(), NewGongDan.class));
                break;
        }
    }

    //解决listview和scrollview滑动冲突问题
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;//上面的分割线设置为5dp,所以这里每次加5个像素
        listView.setLayoutParams(params);
    }

    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setDrawHoleEnabled(true);

        pieChart.setHoleRadius(40f);  //半径
        pieChart.setTransparentCircleRadius(45f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        pieChart.setDescription("");

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(false);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("");  //饼状图中间的文字
        //设置数据
        pieChart.setData(pieData);
        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();
        Legend mLegend = pieChart.getLegend(); //设置比例图
        mLegend.setEnabled(false);//禁止图例
//        Legend mLegend = pieChart.getLegend();  //设置比例图
//        mLegend.setPosition(LegendPosition.RIGHT_OF_CHART);  //最右边显示
////      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
//        mLegend.setXEntrySpace(7f);
//        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }
}
