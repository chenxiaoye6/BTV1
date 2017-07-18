package com.btv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.btv.Bean.JianKongBean;
import com.btv.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Hankkin on 16/4/19.
 */
public class JianKongAdapter extends BaseAdapter {
    ArrayList<JianKongBean> list;
    Context context;

    public JianKongAdapter(ArrayList<JianKongBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (vh == null) {
            convertView = View.inflate(context, R.layout.jiankkong_item, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }
//        JianKongBean jianKongBean=list.get(position);
//        if (null != jianKongBean) {
//
//
//
//        }

        vh.ivResourceitemGuanzhu.setVisibility(View.GONE);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_resourceitem_name)
        TextView tvResourceitemName;
        @InjectView(R.id.tv_resourceitem_ip)
        TextView tvResourceitemIp;
        @InjectView(R.id.iv_resourceitem_guanzhu)
        ImageView ivResourceitemGuanzhu;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
