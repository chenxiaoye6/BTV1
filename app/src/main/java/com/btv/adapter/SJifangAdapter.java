package com.btv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.btv.Bean.SJifangBean;
import com.btv.R;
import com.btv.Utils.L;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class SJifangAdapter extends BaseAdapter {


    private List<SJifangBean.RoomListBean> mList;
    private LayoutInflater inflater;
    private Context mContext;

    public SJifangAdapter(List<SJifangBean.RoomListBean> mList, Context context) {
        this.mList = mList;
        this.mContext = context;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.item_jifang_lv, null);
            holder.title = (TextView) view.findViewById(R.id.textview);//标题
            holder.address = (TextView) view.findViewById(R.id.tv_address);//地址
            holder.num = (TextView) view.findViewById(R.id.tv_num);//设施数量
            holder.zhanbi = (TextView) view.findViewById(R.id.tv_humidness);//湿度
            holder.nums = (TextView) view.findViewById(R.id.tv_nums);//告警数量
            holder.temp = (TextView) view.findViewById(R.id.tv_temp);//温度
            holder.state = (ImageView) view.findViewById(R.id.iv_state);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText(mList.get(i).getRoomName());
        holder.address.setText(mList.get(i).getRoomLocation());
        holder.num.setText(mList.get(i).getResCount());
        holder.nums.setText(mList.get(i).getRoomAlarmCount());
        List<SJifangBean.RoomListBean.RoomMetricInfoObjsBean> roomMetricInfoObjs = mList.get(i).getRoomMetricInfoObjs();
        for (int j = 0; j < roomMetricInfoObjs.size(); j++) {
            if (roomMetricInfoObjs.get(j).getRoomMetricType().equals("analog_humidity")) {
                L.e("*****************"+roomMetricInfoObjs.get(j).getRoomMetricType()+"=======");
                holder.zhanbi.setText(roomMetricInfoObjs.get(j).getRoomMetricValue());
                break;
            }
        }
        for (int j = 0; j < roomMetricInfoObjs.size(); j++) {
            if (roomMetricInfoObjs.get(j).getRoomMetricType().equals("analog_temperature")) {
                L.e("*****************"+roomMetricInfoObjs.get(j).getRoomMetricType()+"=======");
                holder.temp.setText(roomMetricInfoObjs.get(j).getRoomMetricValue());
                break;
            }
        }
        String roomState = mList.get(i).getRoomState();
        switch (roomState){
            case "RED":
                holder.state.setImageResource(R.drawable.redball);
                break;
            case "GREEN":
                holder.state.setImageResource(R.drawable.greenball);
                break;
            case "YELLOW":
                holder.state.setImageResource(R.drawable.yellowball);
                break;
            case "GRAY":
                holder.state.setImageResource(R.drawable.grayball);
                break;
        }
        return view;
    }

    class ViewHolder {
        TextView title, address, num, zhanbi, nums, temp;
        ImageView state;

    }


}
