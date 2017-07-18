package com.btv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btv.Bean.JiFangBean;
import com.btv.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/1/11.
 */

public class JiFangAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater = null;
    ArrayList<JiFangBean> list = new ArrayList<JiFangBean>();

    public JiFangAdapter(Context context, ArrayList<JiFangBean> list) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        //How many items are in the data set represented by this Adapter.
        //在此适配器中所代表的数据集中的条目数
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // Get the data item associated with the specified position in the data set.
        //获取数据集中与指定索引对应的数据项
        return position;
    }

    @Override
    public long getItemId(int position) {
        //Get the row id associated with the specified position in the list.
        //获取在列表中与指定索引对应的行id
        return position;
    }

    //Get a View that displays the data at the specified position in the data set.
    //获取一个在数据集中指定索引的视图来显示数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //如果缓存convertView为空，则需要创建View
        if (convertView == null) {

            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.jifang_item, null);
            holder = new ViewHolder(convertView);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        JiFangBean jiFangBean=list.get(position);
        if (null!=jiFangBean){
            holder.tvJifangName.setText(jiFangBean.getRoomName());
            holder.tvJifangAddress.setText(jiFangBean.getRoomLocation());
            holder.tvJifangSheshiNumber.setText("设施数量:"+jiFangBean.getResCount());
            holder.tvJifangShidu.setText(":  "+jiFangBean.getRoomJiguiOccCount()+"%");
            holder.tvJifangGaojingNumber.setText("告警数量:"+jiFangBean.getRoomAlarmCount());
            holder.tvJifangWendu.setText("   :  "+jiFangBean.getRoomJiguiCount()+"℃");
        }
        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.tv_jifang_name)
        TextView tvJifangName;
        @InjectView(R.id.tv_jifang_address)
        TextView tvJifangAddress;
        @InjectView(R.id.tv_jifang_sheshiNumber)
        TextView tvJifangSheshiNumber;
        @InjectView(R.id.tv_jifang_shidu)
        TextView tvJifangShidu;
        @InjectView(R.id.tv_jifang_gaojingNumber)
        TextView tvJifangGaojingNumber;
        @InjectView(R.id.tv_jifang_wendu)
        TextView tvJifangWendu;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
