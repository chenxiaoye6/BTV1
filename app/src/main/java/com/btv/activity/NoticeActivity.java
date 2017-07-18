package com.btv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.btv.Bean.NoticeBean;
import com.btv.R;
import com.btv.adapter.NoticeAdapter;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NoticeActivity extends AppCompatActivity {

    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_xiala)
    ImageView ivXiala;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.lv_notice)
    ListView lvNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        ButterKnife.inject(this);
        ivTitleLeft.setImageResource(R.drawable.back);
        ivTitleRight.setVisibility(View.INVISIBLE);
        ivXiala.setVisibility(View.INVISIBLE);
        tvTitle.setText("公告");
        ArrayList<NoticeBean> noticelist = new ArrayList<>();
        NoticeAdapter adapter = new NoticeAdapter(this, noticelist);
        lvNotice.setAdapter(adapter);
        lvNotice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(NoticeActivity.this, NoticeDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.iv_title_left)
    public void onViewClicked() {
        finish();
    }

}
