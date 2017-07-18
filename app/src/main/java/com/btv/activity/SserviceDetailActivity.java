package com.btv.activity;


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
import com.btv.Utils.Utils;
import com.btv.fragment.ServiceBaseInfoFragment;
import com.btv.fragment.ServiceResourceFragment;
import com.btv.fragment.ServiceWarningFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SserviceDetailActivity extends AppCompatActivity {

    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_xiala)
    ImageView ivXiala;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.iv_sservicedetail_state)
    ImageView ivSservicedetailState;
    @InjectView(R.id.sserviceTabs)
    TabLayout sserviceTabs;
    @InjectView(R.id.sserviceViewPager)
    ViewPager sserviceViewPager;
    private List<String> mTitle1 = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sservice_detail);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("服务详情");
        ivXiala.setVisibility(View.INVISIBLE);
        ivTitleRight.setVisibility(View.INVISIBLE);
        //初始化tab和viewpager的数据
        mTitle1.add("基本信息");
        mTitle1.add("资源一览");
        mTitle1.add("告警一览");
        mFragment.add(new ServiceBaseInfoFragment());
        mFragment.add(new ServiceResourceFragment());
        mFragment.add(new ServiceWarningFragment());
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), mTitle1, mFragment);
        sserviceViewPager.setAdapter(adapter);
        sserviceTabs.setupWithViewPager(sserviceViewPager);
        sserviceTabs.setTabsFromPagerAdapter(adapter);
        sserviceTabs.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(sserviceTabs, 10, 10);
            }
        });
    }

    @OnClick(R.id.iv_title_left)
    public void onViewClicked() {
        finish();
    }

    public class MyAdapter extends FragmentPagerAdapter {

        private List<String> title;
        private List<Fragment> views;

        public MyAdapter(FragmentManager fm, List<String> title, List<Fragment> views) {
            super(fm);
            this.title = title;
            this.views = views;
        }

        @Override
        public Fragment getItem(int position) {
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }


        //配置标题的方法
        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }
}
