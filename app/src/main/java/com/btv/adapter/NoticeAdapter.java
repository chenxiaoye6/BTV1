package com.btv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.btv.Bean.NoticeBean;
import com.btv.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/17.
 */

public class NoticeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<NoticeBean> list;

    public NoticeAdapter(Context context, ArrayList<NoticeBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = View.inflate(context, R.layout.notice_item, null);
            viewHolder = new ViewHolder();


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {

    }
}
