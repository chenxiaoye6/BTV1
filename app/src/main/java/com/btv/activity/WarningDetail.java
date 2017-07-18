package com.btv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.btv.Bean.WarningBean;
import com.btv.Bean.WarningDAffBusi;
import com.btv.MyApplication;
import com.btv.R;
import com.btv.adapter.WdAffectBusiAdapter;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/30.
 */
public class WarningDetail extends AutoLayoutActivity {
    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.warning_objectName)
    TextView warningObjectName;
    @InjectView(R.id.warning_ipAddress)
    TextView warningIpAddress;
    @InjectView(R.id.warning_objectTypeName)
    TextView warningObjectTypeName;
    @InjectView(R.id.warning_location)
    TextView warningLocation;
    @InjectView(R.id.warning_content)
    TextView warningContent;
    @InjectView(R.id.warning_level)
    TextView warningLevel;
    @InjectView(R.id.warning_platform)
    TextView warningPlatform;
    @InjectView(R.id.warning_occurDate)
    TextView warningOccurDate;
    @InjectView(R.id.warning_confirmUser)
    TextView warningConfirmUser;
    @InjectView(R.id.warning_confirmDate)
    TextView warningConfirmDate;
    @InjectView(R.id.lv_warningdetail_affecebusi)
    ListView lvWarningdetailAffecebusi;
    @InjectView(R.id.warning_cpu_name)
    TextView warningCpuName;
    @InjectView(R.id.warning_cpu_canshu)
    TextView warningCpuCanshu;
    private ArrayList<WarningDAffBusi> warningDAffBusisList;
    private WdAffectBusiAdapter wdAffectBusiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warningdetails);
        MyApplication.getInstance().addActivity(this);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("告警详细信息");
        ivTitleLeft.setImageResource(R.drawable.back);
        ivTitleRight.setVisibility(View.GONE);
        //获得传过来的对象
        Intent intent = this.getIntent();
        WarningBean warningBean = (WarningBean) intent.getSerializableExtra("warningBean");
        Log.e("123", warningBean.toString());
        warningObjectName.setText(warningBean.getObjectName());
        warningIpAddress.setText(warningBean.getIpAddress());
        warningObjectTypeName.setText(warningBean.getObjectTypeName());
        warningLocation.setText(warningBean.getLocation());
        warningContent.setText(warningBean.getContent());
        warningLevel.setText(warningBean.getLevel());
        warningPlatform.setText(warningBean.getPlatform());
        warningOccurDate.setText(warningBean.getOccurDate());
        warningConfirmUser.setText(warningBean.getConfirmUser());
        warningConfirmDate.setText(warningBean.getConfirmDate());
        warningCpuName.setText(warningBean.getMetricName()+":");
        warningCpuCanshu.setText(warningBean.getMetricValue()+" "+warningBean.getMetricUnit());
        Log.e("123wd", warningBean.getServiceInfoStr());
        String[] strArray = null;
        strArray = warningBean.getServiceInfoStr().split(",");
        //影响的业务
        warningDAffBusisList = new ArrayList<>();
        for (String str : strArray) {
            String[] infoArray = null;
            infoArray = str.split("__");
            WarningDAffBusi warningDAffBusi = new WarningDAffBusi();
//            warningDAffBusi.setName(infoArray[1]);
//            warningDAffBusi.setState(infoArray[2]);
//            warningDAffBusisList.add(warningDAffBusi);
        }
        Log.e("123wd", warningDAffBusisList.toString());
        wdAffectBusiAdapter = new WdAffectBusiAdapter(this, warningDAffBusisList);
        lvWarningdetailAffecebusi.setAdapter(wdAffectBusiAdapter);
        setListViewHeightBasedOnChildren(lvWarningdetailAffecebusi);
    }

    @OnClick(R.id.iv_title_left)
    public void onClick() {
        finish();
    }

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
}
