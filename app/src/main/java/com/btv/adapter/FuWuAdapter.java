package com.btv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.btv.Bean.FuWuBean;
import com.btv.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2017/1/12.
 */

public class FuWuAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater = null;
    ArrayList<FuWuBean> list = new ArrayList<FuWuBean>();

    public FuWuAdapter(Context context, ArrayList<FuWuBean> list) {
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
            convertView = mInflater.inflate(R.layout.fuwu_item, null);
            holder = new ViewHolder(convertView);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        FuWuBean fuWuBean=list.get(position);
        if (null != fuWuBean) {
            holder.tvFuwuName.setText(fuWuBean.getBizsmName());
            holder.tvFuwuZiYuanNumber.setText("资源数量:" + fuWuBean.getResNum());
            holder.tvFuwuGaoJingNumber.setText("告警数量:" + fuWuBean.getAlarmNum());
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_fuwu_name)
        TextView tvFuwuName;
        @InjectView(R.id.iv_fuwu_lightstate)
        ImageView ivFuwuLightstate;
        @InjectView(R.id.tv_fuwu_ziYuanNumber)
        TextView tvFuwuZiYuanNumber;
        @InjectView(R.id.tv_fuwu_gaoJingNumber)
        TextView tvFuwuGaoJingNumber;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
