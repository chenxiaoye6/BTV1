package com.btv.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btv.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TroubleTargetActivity extends AppCompatActivity {

    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_target);
        ButterKnife.inject(this);

        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("详细信息");
    }

    @OnClick({R.id.iv_title_left, R.id.iv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.iv_title_right:
                //暂时没操作
                break;
        }
    }

}
