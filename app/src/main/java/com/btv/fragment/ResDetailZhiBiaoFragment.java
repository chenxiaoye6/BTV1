package com.btv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.btv.R;
import com.btv.activity.TroubleModuleActivity;
import com.btv.activity.TroubleTargetActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
//// TODO: 2017/7/8 cc 指标一览界面
public class ResDetailZhiBiaoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_res_detail_zhi_biao, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_more1, R.id.iv_more2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_more1:
                //跳转到问题指标界面
                startActivity(new Intent(getActivity(), TroubleTargetActivity.class));
                break;
            case R.id.iv_more2:
                startActivity(new Intent(getActivity(), TroubleModuleActivity.class));
                break;
        }
    }
}
