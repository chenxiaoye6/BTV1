package com.btv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.btv.Bean.ServiceInfoChild;
import com.btv.R;
import com.btv.activity.SserviceDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceBaseInfoFragment extends Fragment {

    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_resource_num)
    TextView tvResourceNum;
    @InjectView(R.id.tv_remark)
    TextView tvRemark;
    @InjectView(R.id.tv_reason)
    TextView tvReason;
    @InjectView(R.id.gv_department)
    GridView gvDepartment;
    @InjectView(R.id.lv_listview)
    ListView lvListview;
    @InjectView(R.id.iv_xiala)
    ImageView ivXiala;
    private View view;

    private MyGridViewAdapter gvAdapter;
    private List<String> mGetDataForGV = new ArrayList<>();//获取到全部GridView数据
    private List<String> mGvData = new ArrayList<>();//添加到Adapter中的数据
    private int flag = 0;//GridView收起时的状态0，展开时的状态为1；

    private List<ServiceInfoChild> mListData = new ArrayList<>();//子服务的数据集合
    private MyListViewAapter mLvAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_service_base_info, container, false);
        ButterKnife.inject(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {

        for (int i = 0; i < 10; i++) {
            mGetDataForGV.add("行政部门");
        }
        if (mGetDataForGV.size() > 3) {
            for (int i = 0; i < 3; i++) {
                mGvData.add(mGetDataForGV.get(i));
            }
        } else {
            mGvData.addAll(mGetDataForGV);
        }
        flag = 1;
        ivXiala.setImageResource(R.drawable.greendown);

        for (int i = 0; i < 3; i++) {
            mListData.add(new ServiceInfoChild());
        }

    }

    private void initView() {
        gvAdapter = new MyGridViewAdapter();
        setGridViewHeight(gvDepartment);
        gvDepartment.setAdapter(gvAdapter);
        gvAdapter.notifyDataSetChanged();
        mLvAdapter = new MyListViewAapter();
        lvListview.setAdapter(mLvAdapter);
        setListViewHeightBasedOnChildren(lvListview);
        lvListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO: 2017/7/14 获取数据传到下一个activit中
                startActivity(new Intent(getActivity(), SserviceDetailActivity.class).putExtra("", ""));
                getActivity().finish();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void setGridViewHeight(GridView gridview) {
        // 获取gridview的adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int numColumns = gridview.getNumColumns(); //5
        int totalHeight = 0;
        // 计算每一列的高度之和
        for (int i = 0; i < listAdapter.getCount(); i += numColumns) {
            // 获取gridview的每一个item
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight() + gridview.getVerticalSpacing();
        }
        // 获取gridview的布局参数
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        params.height = totalHeight;
        gridview.setLayoutParams(params);
    }

    //此方法在setAdapter之前调用

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
//        if (listAdapter.getCount()>6){
//            for (int i = 0; i < 6; i++) {
//                View listItem = listAdapter.getView(i, null, listView);
//                listItem.measure(0, 0);
//                totalHeight += listItem.getMeasuredHeight();
//            }
//        }else {
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
//            }
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    @OnClick(R.id.rl_xiala)
    public void onViewClicked() {
        if (flag == 0) {//收起
            mGvData.clear();
            if (mGetDataForGV.size() > 3) {
                for (int i = 0; i < 3; i++) {
                    mGvData.add(mGetDataForGV.get(i));
                }
            } else {
                mGvData.addAll(mGetDataForGV);
                ivXiala.setVisibility(View.INVISIBLE);
            }
            flag = 1;
            ivXiala.setImageResource(R.drawable.greendown);

        } else {
            mGvData.clear();
            mGvData.addAll(mGetDataForGV);

            ivXiala.setImageResource(R.drawable.greenup);
            flag = 0;
        }
        setGridViewHeight(gvDepartment);
        gvAdapter.notifyDataSetChanged();
    }


    class MyGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mGvData.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_sbi_gv, null);
                holder.tv = (TextView) view.findViewById(R.id.tv_text);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.tv.setText(mGvData.get(i));
            return view;
        }

        class ViewHolder {
            TextView tv;
        }
    }

    class MyListViewAapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_child_service_lview, null);
                holder.title = (TextView) view.findViewById(R.id.tv_title);
                holder.resNum = (TextView) view.findViewById(R.id.tv_res_num);
                holder.warNum = (TextView) view.findViewById(R.id.tv_warn_num);
                holder.serviceState = (ImageView) view.findViewById(R.id.iv_state);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (i % 2 == 0) {
                holder.serviceState.setImageResource(R.drawable.greenball);
            } else if (i % 2 == 1) {
                holder.serviceState.setImageResource(R.drawable.yellowball);
            } else {
                holder.serviceState.setImageResource(R.drawable.yellowball);
            }

            return view;
        }

        class ViewHolder {
            TextView title, resNum, warNum;
            ImageView serviceState;
        }
    }


}
