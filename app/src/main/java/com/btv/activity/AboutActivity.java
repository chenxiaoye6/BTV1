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

public class AboutActivity extends AppCompatActivity {

    @InjectView(R.id.tv_version)
    TextView tvVersion;
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
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("关于");
        ivXiala.setVisibility(View.INVISIBLE);
        ivTitleRight.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.iv1, R.id.iv2, R.id.iv3, R.id.tv_version, R.id.iv_title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv1:
                break;
            case R.id.iv2:
                startActivity(new Intent(this, ContactUsActivity.class));
                break;
            case R.id.iv3:
                break;
            case R.id.tv_version:
                break;
            case R.id.iv_title_left:
                finish();
                break;
        }
    }

}
