package com.btv.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.btv.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class JianKongSettingActivity extends AppCompatActivity {

    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_xiala)
    ImageView ivXiala;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.cb1)
    CheckBox cb1;
    @InjectView(R.id.cb2)
    CheckBox cb2;
    @InjectView(R.id.cb3)
    CheckBox cb3;
    @InjectView(R.id.cb4)
    CheckBox cb4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jian_kong_setting);
        ButterKnife.inject(this);
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("监控资源过滤");
        ivTitleRight.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.iv_title_left)
    public void onViewClicked() {
        finish();
    }
}
