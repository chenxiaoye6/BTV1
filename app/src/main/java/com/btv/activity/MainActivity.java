package com.btv.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.btv.MyApplication;
import com.btv.R;
import com.btv.fragment.HomePagerFragment;
import com.btv.fragment.SafeguardFragment;
import com.btv.fragment.SurveyFragment;
import com.btv.fragment.WarningFragment;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AutoLayoutActivity {

    private int mIndex = 0;
    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        //添加一键退出
        MyApplication.getInstance().addActivity(this);
        //初始化fragment
        initFragment();
    }

    private void initFragment() {
        HomePagerFragment homePagerFragment = new HomePagerFragment();
        WarningFragment warningFragment = new WarningFragment();
        SurveyFragment surFragment = new SurveyFragment();
        SafeguardFragment safeguardFragment = new SafeguardFragment();
        //添加到fragment数组
        mFragments = new Fragment[]{homePagerFragment, warningFragment, surFragment, safeguardFragment};
        //开启事务
        FragmentTransaction ft;
        ft = getFragmentManager().beginTransaction();
        //添加首页
        ft.add(R.id.content, homePagerFragment).commit();
        //设置默认为第0个
        setIndexSelected(0);
    }


    public void setIndexSelected(int index) {

        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        //隐藏当前fragment
        ft.hide(mFragments[mIndex]);
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.content, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }
        ft.commit();
        //再次赋值
        mIndex = index;
    }


    @OnClick({R.id.rb_homepager, R.id.rb_warning, R.id.rb_survey, R.id.rb_safeguard})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_homepager:
                setIndexSelected(0);
                break;
            case R.id.rb_warning:
                setIndexSelected(1);
                break;
            case R.id.rb_survey:
                setIndexSelected(2);
                break;
            case R.id.rb_safeguard:
                setIndexSelected(3);
                break;
        }
    }
}
