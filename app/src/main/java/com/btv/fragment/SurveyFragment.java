package com.btv.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.btv.Bean.JianKongBean;
import com.btv.R;
import com.btv.adapter.JianKongAdapter;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/17.
 */

public class SurveyFragment extends Fragment {
    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_xiala)
    ImageView ivXiala;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.id_content)
    FrameLayout idContent;

    private View view;
    public ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
    public BarDataSet dataset;
    public ArrayList<String> labels = new ArrayList<String>();
    private ArrayList<JianKongBean> jkbeans = new ArrayList<>();
    private JianKongAdapter adapter;
    private ResourceFragment resourceFragment;
    private JiFangFragment jiFangFragment;
    private ServicesFragment servicesFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.survey, container, false);
        ButterKnife.inject(this, view);
        //初始化数据
        ivTitleLeft.setVisibility(View.INVISIBLE);
        tvTitle.setText("资源");
        ivXiala.setVisibility(View.VISIBLE);
        ivTitleRight.setVisibility(View.INVISIBLE);

        setDefaultFragment();
        return view;
    }



    private void setDefaultFragment() {
        resourceFragment = new ResourceFragment();
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.id_content, resourceFragment);
        ft.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.iv_xiala)
    public void onViewClicked() {
        ResourcePopWindow resourcePopWindow = new ResourcePopWindow(getActivity());
        resourcePopWindow.showAsDropDown(ivTitleLeft, getActivity().getWindowManager().getDefaultDisplay().getWidth() / 2 - resourcePopWindow.getWidth() / 2, 0);
    }


    class ResourcePopWindow extends PopupWindow implements View.OnClickListener {
        private View conentView;

        public ResourcePopWindow(final Activity context) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            conentView = inflater.inflate(R.layout.resource_popwindow, null);
            TextView popResRes = (TextView) conentView.findViewById(R.id.pop_res_res);
            TextView jifang = (TextView) conentView.findViewById(R.id.pop_res_jihome);
            TextView service = (TextView) conentView.findViewById(R.id.pop_res_service);
            popResRes.setOnClickListener(this);
            jifang.setOnClickListener(this);
            service.setOnClickListener(this);
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
            FragmentManager fm = getChildFragmentManager();
            // 开启Fragment事务
            FragmentTransaction ft = fm.beginTransaction();
            Fragment[] fragments = new Fragment[3];
            switch (v.getId()) {
                case R.id.pop_res_res:
                    tvTitle.setText("资源");
                    if (resourceFragment == null) {
                        resourceFragment = new ResourceFragment();
                        ft.add(R.id.id_content, resourceFragment).show(resourceFragment);
                    } else {
                        ft.show(resourceFragment);
                    }
                    checkFragment(jiFangFragment, ft);
                    checkFragment(servicesFragment, ft);
                    break;
                case R.id.pop_res_jihome:
                    tvTitle.setText("机房");
                    if (jiFangFragment == null) {
                        jiFangFragment = new JiFangFragment();
                        ft.add(R.id.id_content, jiFangFragment).show(jiFangFragment);
                    } else {
                        ft.show(jiFangFragment);
                    }
                    checkFragment(resourceFragment, ft);
                    checkFragment(servicesFragment, ft);
                    break;
                case R.id.pop_res_service:
                    tvTitle.setText("服务");
                    if (servicesFragment == null) {
                        servicesFragment = new ServicesFragment();
                        ft.add(R.id.id_content, servicesFragment).show(servicesFragment);
                    } else {
                        ft.show(servicesFragment);
                    }
                    checkFragment(jiFangFragment, ft);
                    checkFragment(resourceFragment, ft);
                    break;

            }
            ft.commit();
            dismiss();
        }

        private void checkFragment(Fragment jiFangFragment, FragmentTransaction ft) {
            if (jiFangFragment == null) {

            } else {
                ft.hide(jiFangFragment);
            }
        }
    }
}
