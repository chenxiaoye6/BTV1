package com.btv.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.btv.Bean.NewODetail;
import com.btv.R;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/9.
 */

public class ProcessFollow extends AutoLayoutActivity {

    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.lv_pf_process)
    ListView lvPfProcess;
    private ArrayList<NewODetail.ProcessBean> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processfollow);
        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("工单详情");
        ivTitleRight.setVisibility(View.INVISIBLE);
        list = (ArrayList<NewODetail.ProcessBean>) getIntent().getSerializableExtra("list");
        lvPfProcess.setAdapter(new ProcessBeanAdatper());
    }

    @OnClick(R.id.iv_title_left)
    public void onClick() {
        finish();
    }

    class ProcessBeanAdatper extends BaseAdapter {

        @Override
        public int getCount() {
            int count = 0;
            if (null != list) {
                count = list.size();
            }
            return count;
        }

        @Override
        public Object getItem(int position) {
            NewODetail.ProcessBean processBean = null;
            if (null != list) {
                processBean = list.get(position);
            }

            return processBean;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (null == convertView) {
                LayoutInflater mInflater = LayoutInflater.from(ProcessFollow.this);
                convertView = mInflater.inflate(R.layout.process_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            NewODetail.ProcessBean processBean = (NewODetail.ProcessBean) getItem(position);
            if (null != processBean) {
                String time[] = processBean.getSendTime().split(" ");
                if (time.length == 2) {
                    viewHolder.processItemTime1.setText((processBean.getSendTime().split(" "))[1]);
                    viewHolder.processItemTime2.setText((processBean.getSendTime().split(" "))[0]);
                } else {
                    viewHolder.processItemTime1.setText("");
                    viewHolder.processItemTime2.setText("");
                }
                viewHolder.processItemThing.setText(processBean.getActivityName());
                viewHolder.processItemPerson.setText(processBean.getUserName());
                viewHolder.processItemCallto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ProcessFollow.this, "打电话", Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.processItemSmsto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ProcessFollow.this, "发短信", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.process_item_time1)
            TextView processItemTime1;
            @InjectView(R.id.process_item_time2)
            TextView processItemTime2;
            @InjectView(R.id.process_item_statelight)
            ImageView processItemStatelight;
            @InjectView(R.id.process_item_thing)
            TextView processItemThing;
            @InjectView(R.id.process_item_person)
            TextView processItemPerson;
            @InjectView(R.id.process_item_callto)
            ImageView processItemCallto;
            @InjectView(R.id.process_item_smsto)
            ImageView processItemSmsto;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
