package com.btv.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btv.Bean.GongGaoBean;
import com.btv.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/27.
 */

public class GongGaoDetailActivity extends AutoLayoutActivity {
    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.gongdandetail_title)
    TextView gongdandetailTitle;
    @InjectView(R.id.gongdandetail_time)
    TextView gongdandetailTime;
    @InjectView(R.id.gongdandetail_content)
    TextView gongdandetailContent;
    private GongGaoBean gongGaoBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gonggaodetail);
        ButterKnife.inject(this);
        ivTitleLeft.setImageResource(R.drawable.back);
        ivTitleRight.setVisibility(View.GONE);
        tvTitle.setText("公告");
        gongGaoBean = (GongGaoBean) getIntent().getSerializableExtra("gongGaoBean");
        initView();
    }

    private void initView() {
        gongdandetailTitle.setText(gongGaoBean.getDOCUMENT_TITLE());
        gongdandetailTime.setText(gongGaoBean.getPUB_TIME());
        gongdandetailContent.setText(gongGaoBean.getNOTICE_DETAIL_INFO());
    }

    @OnClick(R.id.iv_title_left)
    public void onViewClicked() {
        finish();
    }
}
