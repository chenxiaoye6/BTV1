package com.btv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btv.Bean.GDOpinionBean;
import com.btv.R;
import com.btv.Utils.Utils;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/4/27.
 */

public class OpinionAdapter extends BaseAdapter {
    ArrayList<GDOpinionBean> list;
    Context context;

    public OpinionAdapter(ArrayList<GDOpinionBean> list, Context context) {
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
            convertView = View.inflate(context, R.layout.opinion_item, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        GDOpinionBean opinionBean = list.get(position);
        if (null != opinionBean) {
            vh.opinionItemOpinion.setText(opinionBean.getOpinion());

            vh.opinionItemPersiopn.setText(opinionBean.getUserName() + " " + Utils.convertGMTToLoacale(opinionBean.getInputDate().replace("CST", "")));
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.opinion_item_opinion)
        TextView opinionItemOpinion;
        @InjectView(R.id.opinion_item_persiopn)
        TextView opinionItemPersiopn;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
