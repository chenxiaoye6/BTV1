package com.btv.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.btv.R;
import com.btv.Utils.L;
import com.btv.Utils.Utils;
import com.btv.fragment.JFBaseInfoFragment;
import com.btv.fragment.JFResourcePreFragment;
import com.btv.fragment.JFWarningPreFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class JifangContentActivity extends AppCompatActivity {

    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.iv_image)
    ImageView ivImage;
    @InjectView(R.id.tab)
    TabLayout tab;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;

    private MyViewPagerAdapter mViewPagerAdapter;
    private List<Fragment> mFragmentList;
    private String roomId, state,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jifang_content);
        ButterKnife.inject(this);
        roomId = getIntent().getStringExtra("id");
        state = getIntent().getStringExtra("state");
        name = getIntent().getStringExtra("name");
        L.e("roomId == " + roomId);
        L.e("state == " + state);
        L.e("name == " + name);
        initView();
        initData();
    }

    private void initData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new JFBaseInfoFragment(roomId));
        mFragmentList.add(new JFResourcePreFragment());
        mFragmentList.add(new JFWarningPreFragment());
        mViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), mFragmentList);

        tab.setupWithViewPager(viewpager);
        viewpager.setAdapter(mViewPagerAdapter);
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.setTabTextColors(Color.GRAY, Color.parseColor("#62D6C8"));
        tab.getTabAt(0).setText("基本信息");
        tab.getTabAt(1).setText("资源一览");
        tab.getTabAt(2).setText("告警一览");
        tab.setTabMode(TabLayout.MODE_FIXED);
        tab.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tab, 0, 0);
            }
        });
    }

    private void initView() {
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("机房详情");
        ivTitleRight.setVisibility(View.GONE);
        tvName.setText(name);
        switch (state){
            case "RED":
                ivImage.setImageResource(R.drawable.redball);
                break;
            case "GREEN":
                ivImage.setImageResource(R.drawable.greenball);
                break;
            case "YELLOW":
                ivImage.setImageResource(R.drawable.yellowball);
                break;
            case "GRAY":
                ivImage.setImageResource(R.drawable.grayball);
                break;
        }

    }

    @OnClick(R.id.iv_title_left)
    public void onViewClicked() {
        finish();
    }

    class MyViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mList;

        public MyViewPagerAdapter(FragmentManager fm, List<Fragment> mList) {
            super(fm);
            this.mList = mList;
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
