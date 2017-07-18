package com.btv.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btv.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
//// TODO: 2017/7/8 cc
public class ResDetailInfoFragment extends Fragment {


    private LineChart mLineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_res_detail_info, container, false);
        mLineChart = (LineChart) view.findViewById(R.id.linechart);
        LineData mLineData = getLineData(36, 100);
        showChart(mLineChart, mLineData, Color.WHITE);
        initView();
        return view;

    }

    //初始化界面
    private void initView() {

    }

    // 设置显示的样式
    private void showChart(LineChart lineChart, LineData lineData, int color) {
        YAxis leftYaxis = lineChart.getAxisLeft();
        leftYaxis.setGridColor(Color.parseColor("#AFECDD"));
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框
        lineChart.getXAxis().setGridColor(Color.TRANSPARENT);
        lineChart.getAxisRight().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        // no description text
        lineChart.setDescription("");// 数据描述
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable / disable grid background
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        // enable touch gestures
        lineChart.setTouchEnabled(true); // 设置是否可以触摸

        // enable scaling and dragging
        lineChart.setDragEnabled(false);// 是否可以拖拽
        lineChart.setScaleEnabled(false);// 是否可以缩放

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);//

        lineChart.setBackgroundColor(color);// 设置背景

        // add data
        lineChart.setData(lineData); // 设置数据

        // get the legend (only possible after setting data)
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
        mLegend.setEnabled(false);
        // modify the legend ...
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.WHITE);// 颜色
//      mLegend.setTypeface(mTf);// 字体

        lineChart.animateX(100); // 立即执行的动画,x轴
    }

    /**
     * 生成一个数据
     *
     * @param count 表示图表中有多少个坐标点
     * @param range 用来生成range以内的随机数
     * @return
     */
    private LineData getLineData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<String>();

        xValues.add("" + 1);
        xValues.add("" + 2);
        xValues.add("" + 3);
        xValues.add("" + 4);
        xValues.add("" + 5);
        xValues.add("" + 6);
        xValues.add("" + 7);
        xValues.add("" + 8);
        // y轴的数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();

        yValues.add(new Entry(50, 0));
        yValues.add(new Entry(60, 1));
        yValues.add(new Entry(40, 2));
        yValues.add(new Entry(70, 3));
        yValues.add(new Entry(60, 4));
        yValues.add(new Entry(65, 5));
        yValues.add(new Entry(80, 6));
        yValues.add(new Entry(50, 7));

        // create a dataset and give it a type
        // y轴的数据集合
        LineDataSet lineDataSet = new LineDataSet(yValues, "测试折线图");
        //用y轴的集合来设置参数
        lineDataSet.setLineWidth(1.75f); // 线宽
        lineDataSet.setCircleSize(0);// 显示的圆形大小
        lineDataSet.setColor(Color.parseColor("#FBCA69"));// 显示颜色
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawValues(false);
        lineDataSet.setFillColor(Color.parseColor("#F5DFA9"));//设置填充颜色
//        lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色
//        lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        lineDataSets.add(lineDataSet); // add the datasets

        // create a data object with the datasets
        LineData linedata = new LineData();
        linedata.setXVals(xValues);
        linedata.addDataSet(lineDataSet);
        return linedata;
    }
}
