package com.btv.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.btv.R;
import com.btv.Utils.Utils;
import com.btv.fragment.ResDetailInfoFragment;
import com.btv.fragment.ResDetailZhiBiaoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

//// TODO: 2017/7/6 cc 
public class SRescourceDetailActivity extends AppCompatActivity {

    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_xiala)
    ImageView ivXiala;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.tv_resourcedetali_name)
    TextView tvResourcedetaliName;
    @InjectView(R.id.tv_resourcedetali_ip)
    TextView tvResourcedetaliIp;
    @InjectView(R.id.tabs)
    TabLayout tabs;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    private List<String> mTitle = new ArrayList<String>();
    private ArrayList<Fragment> mFragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srescource_detail);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        ivTitleLeft.setImageResource(R.drawable.back);
        ivXiala.setVisibility(View.INVISIBLE);
        tvTitle.setText("详细信息");
        mTitle.add("基本信息");
        mTitle.add("指标一览");
        mFragment.add(new ResDetailInfoFragment());
        mFragment.add(new ResDetailZhiBiaoFragment());
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), mTitle, mFragment);
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
        tabs.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabs, 60, 60);
            }
        });
        tabs.setTabsFromPagerAdapter(adapter);
    }

    @OnClick({R.id.iv_title_left, R.id.iv_title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.iv_title_right:
                //弹出对话框
                ResDetailPopWindow resDetailPopWindow = new ResDetailPopWindow(this);
                resDetailPopWindow.showAsDropDown(ivTitleRight, -resDetailPopWindow.getWidth() / 2, 5);
                break;
        }
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

    //自定义对话框
    class ResDetailPopWindow extends PopupWindow implements View.OnClickListener {
        private View conentView;

        public ResDetailPopWindow(final Activity context) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            conentView = inflater.inflate(R.layout.resdetail_popwindow, null);
            TextView jiaoben = (TextView) conentView.findViewById(R.id.pop_res_jiaoben);
            TextView nfc = (TextView) conentView.findViewById(R.id.pop_res_nfc);
            jiaoben.setOnClickListener(this);
            nfc.setOnClickListener(this);
            int h = context.getWindowManager().getDefaultDisplay().getHeight();
            int w = context.getWindowManager().getDefaultDisplay().getWidth();
            // 设置SelectPicPopupWindow的View
            this.setContentView(conentView);
            // 设置SelectPicPopupWindow弹出窗体的宽
            this.setWidth(w / 3);
            // 设置SelectPicPopupWindow弹出窗体的高
            this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            // 设置SelectPicPopupWindow弹出窗体可点击
            this.setFocusable(true);
            this.setOutsideTouchable(true);
            // 刷新状态
            this.update();
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0000000000);
            // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
            this.setBackgroundDrawable(dw);
            // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            // 设置SelectPicPopupWindow弹出窗体动画效果
            this.setAnimationStyle(R.style.AnimationPreview);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pop_res_jiaoben:
                    
                    break;
                case R.id.pop_res_nfc:

                    break;
            }

        }
    }
}
