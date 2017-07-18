package com.btv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.btv.Bean.WarningBean;
import com.btv.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/1.
 */

public class HomeWarningAdapter extends BaseAdapter{
    Context context;
    private LayoutInflater mInflater = null;
    ArrayList<WarningBean> list=new ArrayList<WarningBean>();

    public HomeWarningAdapter(Context context, ArrayList<WarningBean> list ){
        this.context=context;
        this.list=list;
        this.mInflater = LayoutInflater.from(context);
    }

    static class ViewHolder {
        TextView a;
        TextView b;
        TextView c;
        TextView d;
        TextView e;
        TextView f;
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
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.warninghomepager, null);
            holder.a = (TextView) convertView.findViewById(R.id.a);
            holder.b = (TextView) convertView.findViewById(R.id.b);
            holder.c = (TextView) convertView.findViewById(R.id.c);
            holder.d = (TextView) convertView.findViewById(R.id.d);
//            holder.e = (TextView) convertView.findViewById(R.id.e);
            holder.f = (TextView) convertView.findViewById(R.id.f);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WarningBean item = list.get(position);
//        holder.a.setText(item.getA());
//        holder.b.setText(item.getB());
//        holder.c.setText(item.getC());
//        holder.d.setText(item.getD());
//        holder.e.setText(item.getE());
//        holder.f.setText(item.getF());
        return convertView;
    }
}
