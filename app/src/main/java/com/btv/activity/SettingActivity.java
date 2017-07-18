package com.btv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btv.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {


    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_xiala)
    ImageView ivXiala;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("设置");
        ivXiala.setVisibility(View.INVISIBLE);
        ivTitleRight.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.iv_title_left, R.id.setting_news, R.id.setting_warning, R.id.setting_jiankong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.setting_news:
                startActivity(new Intent(this, NewsActivity.class));
                break;
            case R.id.setting_warning:
                startActivity(new Intent(this, WarningSettingActivity.class));
                break;
            case R.id.setting_jiankong:
                startActivity(new Intent(this, JianKongSettingActivity.class));
                break;
        }
    }
}
