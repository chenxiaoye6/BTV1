package com.btv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.btv.Bean.LvDataBean;
import com.btv.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/13.
 */

public class LVActivity extends AutoLayoutActivity {
    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.lv_nod_parent)
    ListView lvParent;
    private ArrayList<String> listParent;
    private ArrayList<ArrayList<String>> listAll;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_nod);
        ButterKnife.inject(this);
        initData();
    }

    //初始化数据
    private void initData() {
        listParent = new ArrayList<>();
        listAll = new ArrayList<>();
        type = getIntent().getIntExtra("resultCode", 0);
        listParent = getIntent().getStringArrayListExtra("listParent");
        Log.e("789", "listParent++++======" + listParent.toString());
        listAll = ((LvDataBean) getIntent().getSerializableExtra("listAll")).getList();
        Log.e("789", "listAll===========" + listAll.toString());
        ivTitleLeft.setImageResource(R.drawable.back);
        switch (type) {
            case 1:
                tvTitle.setText("事件类别");
                break;
            case 2:
                tvTitle.setText("业务系统");
                break;
        }

        ivTitleRight.setVisibility(View.INVISIBLE);
        lvParent.setAdapter(new ParentAdapter());
    }

    class ParentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listParent.size();
        }

        @Override
        public Object getItem(int position) {
            return listParent.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(LVActivity.this).inflate(R.layout.parent_nod_item, null);
            TextView tv_pt_type = (TextView) convertView.findViewById(R.id.tv_pt_type);
            ListView listView = (ListView) convertView.findViewById(R.id.lv_chlidren);
            tv_pt_type.setText(listParent.get(position));
            listView.setAdapter(new MyChildrenAdapter(listAll.get(position)));
            setListViewHeightBasedOnChildren(listView);
            return convertView;
        }
    }

    class MyChildrenAdapter extends BaseAdapter {
        private ArrayList<String> list3;

        public MyChildrenAdapter(ArrayList<String> listChildren) {
            this.list3 = listChildren;
        }

        @Override
        public int getCount() {
            return list3.size();
        }

        @Override
        public Object getItem(int position) {
            return list3.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(LVActivity.this).inflate(R.layout.children_item, null);
            final TextView tv_cd_title = (TextView) convertView.findViewById(R.id.tv_cd_title);
            final ImageView cb_cd = (ImageView) convertView.findViewById(R.id.cb_cd);
            LinearLayout ll_cd = (LinearLayout) convertView.findViewById(R.id.ll_cd);
            tv_cd_title.setText(list3.get(position));
            ll_cd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cb_cd.setSelected(true);
                    Intent intent = new Intent();
                    intent.putExtra("result", tv_cd_title.getText());
                    setResult(type, intent);
                    finish();
                }
            });
            return convertView;
        }
    }

    @OnClick(R.id.iv_title_left)
    public void onClick() {
        Intent intent = new Intent();
        setResult(0, intent);
        finish();
    }

    //重新测量list的高度
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        params.height += 5;//上面的分割线设置为5dp,所以这里每次加5个像素
        listView.setLayoutParams(params);
    }
}
