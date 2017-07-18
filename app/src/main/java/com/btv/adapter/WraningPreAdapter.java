package com.btv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btv.Bean.WarningPreBean;
import com.btv.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/7/11.
 */

public class WraningPreAdapter extends BaseAdapter {

    private List<WarningPreBean> mlist;
    private Context context;
    private LayoutInflater inflater;

    public WraningPreAdapter(List<WarningPreBean> mlist, Context context) {
        this.mlist = mlist;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int i) {
        return mlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = inflater.inflate(R.layout.item_warning_pre_lv, viewGroup, false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder= (ViewHolder) view.getTag();
        }
        return view;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_month)
        TextView tvMonth;
        @InjectView(R.id.tv_day)
        TextView tvDay;
        @InjectView(R.id.tv_hour)
        TextView tvHour;
        @InjectView(R.id.tv_textone)
        TextView tvTextone;
        @InjectView(R.id.texttwo)
        TextView texttwo;
        @InjectView(R.id.tv_text)
        TextView tvText;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
