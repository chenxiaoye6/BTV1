package com.btv.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.btv.Bean.SServiceBean;
import com.btv.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/7/11.
 */

public class SServiceAdapter extends BaseAdapter {

    private ArrayList<SServiceBean.BizSvcListBean> sserviceList;
    private Context context;

    public SServiceAdapter(ArrayList<SServiceBean.BizSvcListBean> sserviceList, Context context) {
        this.sserviceList = sserviceList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return sserviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return sserviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(context, R.layout.sservice_item, null);
            //找到控件
            vh.title = (TextView) convertView.findViewById(R.id.tv_title);
            vh.resNum = (TextView) convertView.findViewById(R.id.tv_res_num);
            vh.warnNum = (TextView) convertView.findViewById(R.id.tv_warn_num);
            vh.state = (ImageView) convertView.findViewById(R.id.serviceitem_state);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        switch (sserviceList.get(position).getBizsmState()) {
            case "YELLOW":
                vh.state.setImageResource(R.drawable.yellowball);
                break;
            case "RED":
                vh.state.setImageResource(R.drawable.redball);
                break;
            case "GREEN":
                vh.state.setImageResource(R.drawable.greenball);
                break;
            case "GRAY":
                vh.state.setImageResource(R.drawable.grayball);
                break;
        }
        vh.title.setText(sserviceList.get(position).getBizsmName());
        vh.resNum.setText("资源数量：" + sserviceList.get(position).getResNum());
        vh.warnNum.setText("告警数量：" + sserviceList.get(position).getAlarmNum());
        //设置控件的信息
        return convertView;
    }

    class ViewHolder {
        TextView title, resNum, warnNum;
        ImageView state;
    }
}
