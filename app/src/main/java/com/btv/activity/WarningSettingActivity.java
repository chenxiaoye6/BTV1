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

public class WarningSettingActivity extends AppCompatActivity {

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
    @InjectView(R.id.cb5)
    CheckBox cb5;
    @InjectView(R.id.cb6)
    CheckBox cb6;
    @InjectView(R.id.cb7)
    CheckBox cb7;
    @InjectView(R.id.cb8)
    CheckBox cb8;
    @InjectView(R.id.cb9)
    CheckBox cb9;
    @InjectView(R.id.cb10)
    CheckBox cb10;
    @InjectView(R.id.cb11)
    CheckBox cb11;
    @InjectView(R.id.cb12)
    CheckBox cb12;
    @InjectView(R.id.cb13)
    CheckBox cb13;
    @InjectView(R.id.cb14)
    CheckBox cb14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_setting);
        ButterKnife.inject(this);
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("告警接受范围");
        ivTitleRight.setVisibility(View.INVISIBLE);
        ivXiala.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.iv_title_left)
    public void onViewClicked() {
        finish();
    }
}
