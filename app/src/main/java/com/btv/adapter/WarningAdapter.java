package com.btv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.btv.Bean.WarningBean;
import com.btv.R;

import java.util.ArrayList;

import static com.btv.R.id.ll;

/**
 * Created by Hankkin on 16/4/19.
 */
public class WarningAdapter extends BaseAdapter {
    private String months[] = {"Jua", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    Context context;
    private LayoutInflater mInflater = null;
    ArrayList<WarningBean> list = new ArrayList<WarningBean>();

    public WarningAdapter(Context context, ArrayList<WarningBean> list) {
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
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
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.warninghomepager, null);
            holder = new ViewHolder();
            holder.a = (TextView) convertView.findViewById(R.id.a);
            holder.b = (TextView) convertView.findViewById(R.id.b);
            holder.c = (TextView) convertView.findViewById(R.id.c);
            holder.d = (TextView) convertView.findViewById(R.id.d);
//            holder.e = (TextView) convertView.findViewById(R.id.e);
            holder.ll = (LinearLayout) convertView.findViewById(ll);
            holder.f = (TextView) convertView.findViewById(R.id.f);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        WarningBean item = list.get(position);
        String occurDate = item.getOccurDate();
        String[] dateArray = occurDate.split(" ");
        String[] dayArray = dateArray[0].split("-");
        String month = dayArray[1];
        // 获取天
        String day = dayArray[2];
        //获取小时和分钟
        String hour = (dateArray[1].split(":"))[0];
        String minute = (dateArray[1].split(":"))[1];
        String hm = hour + ":" + minute;
        String ipAddress = item.getIpAddress();

        //ip+objectName
        String io = ipAddress + "  " + item.getObjectName();
        //content
        String content = item.getContent();
        holder.a.setText(month);
        holder.b.setText(day);
        holder.c.setText(hm);
        holder.d.setText(io);
//        holder.e.setText(ipAddress);
        holder.f.setText(content);
        switch (item.getLevel()) {
            case "严重":
                holder.ll.setBackgroundResource(R.drawable.shap_square_serious);
                break;
            case "致命":
                holder.ll.setBackgroundResource(R.drawable.shap_square);
                break;
            case "次要":
                holder.ll.setBackgroundResource(R.drawable.shap_square_secondary);
                break;
            case "警告":
                holder.ll.setBackgroundResource(R.drawable.shap_square_ordinary);
                break;
            case "信息":
                holder.ll.setBackgroundResource(R.drawable.shap_square_information);
                break;
            case "未知":
                holder.ll.setBackgroundResource(R.drawable.shap_square_unknown);
                break;
        }
        return convertView;
    }

    public class ViewHolder {
        TextView a;
        TextView b;
        TextView c;
        TextView d;
        //        TextView e;
        TextView f;
        LinearLayout ll;
    }
}
