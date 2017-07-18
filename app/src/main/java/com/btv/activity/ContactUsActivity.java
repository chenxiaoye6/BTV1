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

public class ContactUsActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_contact_us);
        ButterKnife.inject(this);
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("关于");
        ivXiala.setVisibility(View.INVISIBLE);
        ivTitleRight.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.iv_title_left)
    public void onViewClicked() {
        finish();
    }
}
