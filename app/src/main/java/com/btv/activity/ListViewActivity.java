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
import android.widget.ListView;
import android.widget.TextView;

import com.btv.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ListViewActivity extends AutoLayoutActivity {
    @InjectView(R.id.lv_listview)
    ListView lvListview;
    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    private ArrayList<String> listParent;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        listParent = new ArrayList<>();
        type = getIntent().getIntExtra("resultCode", 0);
        listParent = getIntent().getStringArrayListExtra("listParent");
        Log.e("789", "listParent++++======" + listParent.toString());
        ivTitleLeft.setImageResource(R.drawable.back);
        switch (type) {
            case 3:
                tvTitle.setText("所属责任区");
                break;
            case 4:
                tvTitle.setText("所属运维组");
                break;
        }

        ivTitleRight.setVisibility(View.INVISIBLE);
        lvListview.setAdapter(new MyChildrenAdapter(listParent));
    }

    @OnClick(R.id.iv_title_left)
    public void onClick() {
        Intent intent = new Intent();
        setResult(0, intent);
        finish();
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
            convertView = LayoutInflater.from(ListViewActivity.this).inflate(R.layout.children_item, null);
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
}

