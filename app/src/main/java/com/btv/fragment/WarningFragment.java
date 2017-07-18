package com.btv.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.btv.Bean.WarningBean;
import com.btv.R;
import com.btv.Utils.HttpUtils;
import com.btv.Utils.Internet;
import com.btv.activity.WarningDetail;
import com.btv.swiprefresh.XListView;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/17.
 */

public class WarningFragment extends Fragment implements XListView.IXListViewListener {

    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.techan_xListView)
    XListView techanXListView;
    private SharedPreferences sp;
    private String token;
    private String warningLevel;
    private String timeMills;
    private String pageSize;
    private int[] yData = new int[6];
    private String[] xData = {"未知", "信息", "警告", "次要", "严重", "致命"};
    private ArrayList<WarningBean> warningBeenList;
    private MyAdapter adapter;
    private PieChart mChart;
    private View view2;
    private Handler mHandler = new Handler();
    ;
    private String currentPage = "1";
    private int length = 0;
    private CustomDialog dialog;
    private CustomPopwindow customPopwindow;
    private String userId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.warning, container, false);
        ButterKnife.inject(this, view);
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        ivTitleRight = (ImageView) view.findViewById(R.id.iv_title_right);
        ivTitleLeft = (ImageView) view.findViewById(R.id.iv_title_left);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        ivTitleLeft.setImageResource(R.drawable.timg);
        tvTitle.setText("告警");
        ivTitleRight.setImageResource(R.drawable.warning_select);
        ivTitleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new CustomDialog(getActivity(), R.style.customDialog);
                dialog.setTitle("选择告警对象");
                dialog.show();
            }
        });
        techanXListView.setPullLoadEnable(true);// 设置让它上拉，FALSE为不让上拉，便不加载更多数据
        techanXListView.setXListViewListener(this);
        initView();
        //设置扇行的布局
        LayoutInflater inflater1 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view2 = inflater1.inflate(R.layout.piechart_item, null);
        mChart = (PieChart) view2.findViewById(R.id.piechart);
        return view;
    }

    private void initView() {
        token = sp.getString("token", "");
        userId = sp.getString("userId", "");
        warningLevel = "0";
        timeMills = "1800000";
        pageSize = "5";
        currentPage = "1";
        Log.e("123", token);
        HashMap<String, String> param = new HashMap<>();
        param.put("token", token);
        param.put("warningLevel", warningLevel);
        param.put("timeMills", timeMills);
        param.put("pageSize", pageSize);
        param.put("currentPage", currentPage);
        Log.e("123", param.toString());
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
                        try {
                            Log.e("789", data);
                            JSONObject jsonObject = new JSONObject(data);
                            JSONObject data1 = jsonObject.getJSONObject("data");
                            //判断为空的时候绘制空圆环
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
                            warningBeenList = new ArrayList<WarningBean>();
                            for (JsonElement warning : jsonArray) {
                                WarningBean warningBean = gson.fromJson(warning, WarningBean.class);
                                warningBeenList.add(warningBean);
                            }
                            adapter = new MyAdapter(getActivity());
                            techanXListView.setAdapter(adapter);
                            initPieChart();
                            if (techanXListView.getHeaderViewsCount() == 0) {
                                techanXListView.addHeaderView(view2);
                            } else {
                                techanXListView.removeHeaderView(view2);
                                techanXListView.addHeaderView(view2);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * 停止刷新，
     */
    private void onLoad() {
        techanXListView.stopRefresh();
        techanXListView.stopLoadMore();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        techanXListView.setRefreshTime(str);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initView();
                onLoad();
            }
        }, 2000);
    }

    String page = currentPage;

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> param = new HashMap<>();
                int intCurrentPage = Integer.parseInt(page);
                intCurrentPage++;
                page = intCurrentPage + "";
                param.put("token", token);
                param.put("warningLevel", warningLevel);
                param.put("timeMills", timeMills);
                param.put("pageSize", pageSize);
                Log.e("123", intCurrentPage + "");
                param.put("currentPage", intCurrentPage + "");
                Log.e("123", param.toString());
                HttpUtils.doPost(Internet.WARNING, param, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String data = response.body().string();
                        Log.e("789", "data===========" + data);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    length = warningBeenList.size();
                                    JSONObject jsonObject = new JSONObject(data);
                                    JSONObject data1 = jsonObject.getJSONObject("data");
                                    JSONArray warningobj = data1.getJSONArray("Warning.obj");
                                    //Json的解析类对象
                                    JsonParser parser = new JsonParser();
                                    //将JSON的String 转成一个JsonArray对象
                                    JsonArray jsonArray = parser.parse(warningobj.toString()).getAsJsonArray();
                                    Gson gson = new Gson();
                                    for (JsonElement warning : jsonArray) {
                                        WarningBean warningBean = gson.fromJson(warning, WarningBean.class);
                                        warningBeenList.add(warningBean);
                                    }
                                    adapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if (length == warningBeenList.size()) {
                                    Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                                    onLoad();
                                } else {
                                    Toast.makeText(getActivity(), "刷新完成", Toast.LENGTH_SHORT).show();
                                    onLoad();
                                }
                            }
                        });

                    }
                });
            }
        }, 1000);
    }

    //自定义适配器,并且在适配器中添加侧滑按钮
    private class MyAdapter extends BaseSwipeAdapter {
        private String months[] = {"Jua", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        private Context mContext;
        private String month;
        private String day;
        private String hour;
        private String minute;
        private String hm;
        private String ipAddress;
        private String io;
        private String content;
        private WarningBean item;

        public MyAdapter(Context mContext) {
            this.mContext = mContext;
        }


        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }

        @Override
        public View generateView(final int position, ViewGroup parent) {

            item=warningBeenList.get(position);
            View v = LayoutInflater.from(mContext).inflate(R.layout.warning_item, null);
            SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
            LinearLayout ll_content = (LinearLayout) v.findViewById(R.id.ll_content);
            final TextView delet = (TextView) v.findViewById(R.id.delete);
            if ("true".equals(item.getIsAttention())) {
                delet.setText("取消关注");
            } else {
                delet.setText("关注");
            }
            //设置item的点击事件
            ll_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到详情界面
                    Intent intent = new Intent(getActivity(), WarningDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("warningBean", warningBeenList.get(position));
                    intent.putExtras(bundle);
                    getActivity().startActivity(intent);
                }
            });
            //设置item的长点击事件
            ll_content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopWindow(v, position);
                    return false;
                }
            });
            //关注警告的点击
            v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if ("关注".equals(delet.getText())) {
                        delet.setText("取消关注");
                        HashMap<String, String> param = new HashMap<String, String>();
                        param.put("token", token);
                        param.put("instanceId", warningBeenList.get(position).getInstanceId());
                        param.put("isAttention", "true");
                        param.put("userId", userId);
                        HttpUtils.doPost(Internet.ISATTENTION, param, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String data = response.body().string();
                                warningBeenList.get(position).setIsAttention("true");
                            }
                        });

                    } else {
                        delet.setText("关注");
                        HashMap<String, String> param = new HashMap<String, String>();
//                            token	true	string	令牌
//                            notificationId	true	string	告警ID（如有多个ID请以；分号分割传参）
//                            flag	true	string	确认取消确认告警（true：确认告警；false：取消确认告警）
                        param.put("token", token);
                        param.put("instanceId", warningBeenList.get(position).getInstanceId());
                        param.put("isAttention", "false");
                        param.put("userId", userId);
                        HttpUtils.doPost(Internet.ISATTENTION, param, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String data = response.body().string();
                                warningBeenList.get(position).setIsAttention("false");
                            }
                        });
                    }
                }
            });
            return v;
        }

        @Override
        public void fillValues(int position, View convertView) {
            item = warningBeenList.get(position);
            String occurDate = item.getOccurDate();
            String[] dateArray = occurDate.split(" ");
            String[] dayArray = dateArray[0].split("-");
            month = dayArray[1];
            // 获取天
            day = dayArray[2];
            //获取小时和分钟
            hour = (dateArray[1].split(":"))[0];
            minute = (dateArray[1].split(":"))[1];
            hm = hour + ":" + minute;
            ipAddress = item.getIpAddress();
            //ip+objectName
            io = ipAddress + "  " + item.getObjectName();
            //content
            content = item.getContent();
            LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll);
            TextView tvmonth = (TextView) convertView.findViewById(R.id.warmingitem_month);
            TextView tvday = (TextView) convertView.findViewById(R.id.warmingitem_day);
            TextView tvtime = (TextView) convertView.findViewById(R.id.warmingitem_time);
            TextView tvio = (TextView) convertView.findViewById(R.id.warmingitem_io);
//            TextView tvip = (TextView) convertView.findViewById(R.id.warmingitem_ip);
            TextView tvcontent = (TextView) convertView.findViewById(R.id.warmingitem_content);
            tvmonth.setText(month);
            tvday.setText(day);
            tvtime.setText(hm);
            tvio.setText(io);
//            tvip.setText(ipAddress);
            tvcontent.setText(content);
            switch (item.getLevel()) {
                case "严重":
                    ll.setBackgroundResource(R.drawable.shap_square_serious);
                    break;
                case "致命":
                    ll.setBackgroundResource(R.drawable.shap_square);
                    break;
                case "次要":
                    ll.setBackgroundResource(R.drawable.shap_square_secondary);
                    break;
                case "警告":
                    ll.setBackgroundResource(R.drawable.shap_square_ordinary);
                    break;
                case "信息":
                    ll.setBackgroundResource(R.drawable.shap_square_information);
                    break;
                case "未知":
                    ll.setBackgroundResource(R.drawable.shap_square_unknown);
                    break;
            }
        }

        @Override
        public int getCount() {
            return warningBeenList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    //初始化扇形
    public PieChart initPieChart() {
        //configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        //enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setUsePercentValues(false);
        // mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(40);
        mChart.setTransparentCircleRadius(10);
        // enable rotation of the chart by touch
        mChart.setRotation(0);
        mChart.setRotationEnabled(false);
        mChart.setDescription("");
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;
//                Toast.makeText(getActivity(), xData[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
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
        l.setForm(Legend.LegendForm.LINE);
        //设置图例不可见
        l.setEnabled(false);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        return mChart;
    }

    //扇形的数据源
    private PieData addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        int m = 0;
        for (int i = 0; i < yData.length; i++) {
            m += yData[i];
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
//        create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        //判断是不是只有一种数据,设置分开的间隙,
        if (xVals.size() == 1) {
            dataSet.setSliceSpace(0);
            dataSet.setSelectionShift(5);
        } else {
            dataSet.setSliceSpace(0);
            dataSet.setSelectionShift(5);
        }
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

    //自定义的搜索对话框
    class CustomDialog extends Dialog {
        int layoutRes;//布局文件
        Context context;

        public CustomDialog(Context context) {
            super(context);
            this.context = context;
        }

        /**
         * 自定义主题及布局的构造方法
         *
         * @param context
         * @param theme
         */
        public CustomDialog(Context context, int theme) {
            super(context, theme);
            this.context = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.setContentView(R.layout.gaojingobject_dialog);
            final EditText editText = (EditText) findViewById(R.id.editText);
            RadioGroup rg_time = (RadioGroup) findViewById(R.id.rg_time);
            RadioButton rb_time1 = (RadioButton) findViewById(R.id.rb_time1);
            RadioButton rb_time2 = (RadioButton) findViewById(R.id.rb_time1);
            RadioButton rb_time3 = (RadioButton) findViewById(R.id.rb_time1);
            RadioButton rb_time4 = (RadioButton) findViewById(R.id.rb_time1);
            RadioButton rb_time5 = (RadioButton) findViewById(R.id.rb_time1);
            Button btn_seek = (Button) findViewById(R.id.btn_seek);
            Log.e("123", timeMills);
            switch (timeMills) {
                case "1800000":
                    rg_time.check(R.id.rb_time1);
                    break;
                case 60 * 60 * 1000 + "":
                    rg_time.check(R.id.rb_time2);
                    break;
                case 6 * 60 * 60 * 1000 + "":
                    rg_time.check(R.id.rb_time3);
                    break;
                case 12 * 60 * 60 * 1000 + "":
                    rg_time.check(R.id.rb_time4);
                    break;
                case 7 * 24 * 60 * 60 * 1000 + "":
                    rg_time.check(R.id.rb_time5);
                    break;
            }

            btn_seek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                    dialog.dismiss();
                    techanXListView.removeHeaderView(view2);
                    //设置当前页面为1pager
                    page = "1";
                    String objectName = editText.getText().toString();
                    HashMap<String, String> param = new HashMap<>();
                    param.put("token", token);
                    param.put("objectName", objectName);
                    param.put("timeMills", timeMills);
                    param.put("pageSize", pageSize);
                    param.put("currentPage", currentPage);
                    Log.e("123", param.toString());
                    HttpUtils.doPost(Internet.WARSEARCH, param, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String data = response.body().string();
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //////
                                    try {
                                        Log.e("123", "data=" + data);
                                        JSONObject jsonObject = new JSONObject(data);
                                        JSONObject data1 = jsonObject.getJSONObject("data");
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
                                        warningBeenList = new ArrayList<WarningBean>();
                                        for (JsonElement warning : jsonArray) {
                                            WarningBean warningBean = gson.fromJson(warning, WarningBean.class);
                                            warningBeenList.add(warningBean);
                                        }
                                        initPieChart();
                                        //设置表头为扇形
                                        techanXListView.removeHeaderView(view2);
                                        techanXListView.addHeaderView(view2);
                                        techanXListView.setPullLoadEnable(true);
                                        adapter = new MyAdapter(getActivity());
                                        techanXListView.setAdapter(adapter);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    });
                }
            });
            rg_time.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.rb_time1:
                            timeMills = "1800000";
                            break;
                        case R.id.rb_time2:
                            timeMills = 60 * 60 * 1000 + "";
                            break;
                        case R.id.rb_time3:
                            timeMills = 6 * 60 * 60 * 1000 + "";
                            break;
                        case R.id.rb_time4:
                            timeMills = 12 * 60 * 60 * 1000 + "";
                            break;
                        case R.id.rb_time5:
                            timeMills = 7 * 24 * 60 * 60 * 1000 + "";
                            break;
                    }
                }
            });
        }
    }

    //显示弹框
    private void showPopWindow(View view, int position) {
        customPopwindow = new CustomPopwindow(getActivity(), position);
        customPopwindow.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    class CustomPopwindow extends PopupWindow {

        private View mView;

        public CustomPopwindow(Activity context, int position) {
            super(context);
            initView(context, position);
        }

        private void initView(final Activity context, final int position) {
            // TODO Auto-generated method stub
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = mInflater.inflate(R.layout.jinggao_popwindow, null);

            final TextView config = (TextView) mView.findViewById(R.id.pop_config);
            TextView allconfig = (TextView) mView.findViewById(R.id.pop_allconfig);
            TextView popSms = (TextView) mView.findViewById(R.id.pop_sms);
            TextView email = (TextView) mView.findViewById(R.id.pop_email);
            TextView copy = (TextView) mView.findViewById(R.id.pop_copy);
            Log.e("789", warningBeenList.get(position).toString());
            if ("false".equals(warningBeenList.get(position).getIsConfirm())) {
                config.setText("确认告警");
            } else {
                config.setText("取消告警");
            }
            //告警和取消告警
            config.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (config.getText().toString()) {
                        case "确认告警":
                            HashMap<String, String> param = new HashMap<String, String>();
//                            token	true	string	令牌
//                            notificationId	true	string	告警ID（如有多个ID请以；分号分割传参）
//                            flag	true	string	确认取消确认告警（true：确认告警；false：取消确认告警）
                            param.put("token", token);
                            param.put("notificationId", warningBeenList.get(position).getNotificationId());
                            param.put("flag", "true");
                            HttpUtils.doPost(Internet.ISCONFIRM, param, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    final String data = response.body().string();
                                    Log.e("789", data);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject jsonObject = new JSONObject(data);
                                                JSONObject data1 = jsonObject.getJSONObject("data");
                                                warningBeenList.get(position).setIsConfirm("true");
                                                Log.e("789", "data======" + data);
                                                config.setText("取消告警");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });
                            break;
                        case "取消告警":
                            HashMap<String, String> param2 = new HashMap<String, String>();
//                            token	true	string	令牌
//                            notificationId	true	string	告警ID（如有多个ID请以；分号分割传参）
//                            flag	true	string	确认取消确认告警（true：确认告警；false：取消确认告警）
                            param2.put("token", token);
                            param2.put("notificationId", warningBeenList.get(position).getNotificationId());
                            param2.put("flag", "true");
                            HttpUtils.doPost(Internet.ISCONFIRM, param2, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    final String data = response.body().string();
                                    Log.e("789", data);
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                JSONObject jsonObject = new JSONObject(data);
                                                JSONObject data2 = jsonObject.getJSONObject("data");
                                                warningBeenList.get(position).setIsConfirm("false");
                                                config.setText("确认告警");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });
                            break;
                    }
                }
            });
            //全部告警
            allconfig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < warningBeenList.size(); i++) {
                        if (i == 0) {
                            sb.append(warningBeenList.get(i).getNotificationId());
                        } else {
                            sb.append(";");
                            sb.append(warningBeenList.get(i).getNotificationId());
                        }
                    }
                    Log.e("789", sb.toString());
                    HashMap<String, String> param2 = new HashMap<String, String>();
                    param2.put("token", token);
                    param2.put("notificationId", sb.toString());
                    param2.put("flag", "true");
                    HttpUtils.doPost(Internet.ISCONFIRM, param2, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    customPopwindow.dismiss();
                                }
                            });
                        }
                    });
                }
            });
            //发送短信
            popSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri smsToUri = Uri.parse("smsto://" + warningBeenList.get(position).getMobile());
                    Intent mIntent = new Intent(Intent.ACTION_SENDTO, smsToUri);
                    startActivity(mIntent);
                }
            });
            //发送邮件
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(warningBeenList.get(position).getEmail());
                    Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO, uri);
                    mIntent.setType("message/rfc822");
                    startActivity(mIntent);
                }
            });
            //设置SelectPicPopupWindow的View
            this.setContentView(mView);
            //设置SelectPicPopupWindow弹出窗体的宽
            this.setWidth(WindowManager.LayoutParams.FILL_PARENT);
            //设置SelectPicPopupWindow弹出窗体的高
            this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
            //设置SelectPicPopupWindow弹出窗体可点击
            this.setFocusable(true);
            //设置PopupWindow可触摸
            this.setTouchable(true);
            //设置非PopupWindow区域是否可触摸
//        this.setOutsideTouchable(false);
            //设置SelectPicPopupWindow弹出窗体动画效果
            //实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x00000000);
            //设置SelectPicPopupWindow弹出窗体的背景
            this.setBackgroundDrawable(dw);
            backgroundAlpha(context, 0.5f);//0.0-1.0
            this.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss() {
                    // TODO Auto-generated method stub
                    backgroundAlpha(context, 1f);
                }
            });
        }

        /**
         * 设置添加屏幕的背景透明度
         *
         * @param bgAlpha
         */
        public void backgroundAlpha(Activity context, float bgAlpha) {
            WindowManager.LayoutParams lp = context.getWindow().getAttributes();
            lp.alpha = bgAlpha;
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            context.getWindow().setAttributes(lp);
        }
    }
}
