package com.btv.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.btv.Bean.ModuleBean;
import com.btv.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TroubleModuleActivity extends AppCompatActivity {

    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_xiala)
    ImageView ivXiala;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;

    private ListView mListview;
    private List<ModuleBean> mList;
    private MyAdapter mAdaper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trouble_module);
        ButterKnife.inject(this);
        mListview = (ListView) findViewById(R.id.lv_module_detail);
        ivXiala.setVisibility(View.VISIBLE);
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("全部组件");
        ivXiala.setVisibility(View.VISIBLE);
        ivTitleRight.setVisibility(View.GONE);
        initData();

    }

    private void initData() {

        mList = new ArrayList<>();
        mList.add(new ModuleBean("aaa", "CPU", "0"));
        mList.add(new ModuleBean("aaa", "Fast Ethernet", "0/2"));
        mList.add(new ModuleBean("aaa", "Fast Ethernet", "1/1"));

        mAdaper = new MyAdapter();
        mListview.setAdapter(mAdaper);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(TroubleModuleActivity.this,ModleDetailActivity.class).putExtra("name",mList.get(i).getName()));
            }
        });
    }

    @OnClick({R.id.iv_title_left, R.id.iv_xiala})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.iv_xiala:
                //下拉组件显示
                ResourcePopWindow resourcePopWindow = new ResourcePopWindow(this);
                resourcePopWindow.showAsDropDown(ivTitleLeft, getWindowManager().getDefaultDisplay().getWidth() / 2 - resourcePopWindow.getWidth() / 2, 0);
                break;

        }
    }

    class ResourcePopWindow extends PopupWindow implements View.OnClickListener {
        private View conentView;

        public ResourcePopWindow(final Activity context) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            conentView = inflater.inflate(R.layout.module_popwindow, null);
            TextView mInterface = (TextView) conentView.findViewById(R.id.pop_interface);
            TextView mCPU = (TextView) conentView.findViewById(R.id.pop_cpu);
            TextView mFenqu = (TextView) conentView.findViewById(R.id.pop_fenqu);
            mInterface.setOnClickListener(this);
            mCPU.setOnClickListener(this);
            mFenqu.setOnClickListener(this);
            int h = context.getWindowManager().getDefaultDisplay().getHeight();
            int w = context.getWindowManager().getDefaultDisplay().getWidth();
            // 设置SelectPicPopupWindow的View
            this.setContentView(conentView);
            // 设置SelectPicPopupWindow弹出窗体的宽
            this.setWidth(w / 3);
            // 设置SelectPicPopupWindow弹出窗体的高
            this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            // 设置SelectPicPopupWindow弹出窗体可点击
            this.setFocusable(true);
            this.setOutsideTouchable(true);
            // 刷新状态
            this.update();
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0000000000);
            // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
            this.setBackgroundDrawable(dw);
            // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
            // 设置SelectPicPopupWindow弹出窗体动画效果
            this.setAnimationStyle(R.style.AnimationPreview);
        }

        @Override
        public void onClick(View v) {
            FragmentManager fm = getFragmentManager();
            // 开启Fragment事务
            FragmentTransaction ft = fm.beginTransaction();
            Fragment[] fragments = new Fragment[3];
            switch (v.getId()) {
                case R.id.pop_interface:
                    //加载不同的数据源
                    tvTitle.setText("网络接口");
                    break;
                case R.id.pop_cpu:
                    tvTitle.setText("CPU");
                    break;
                case R.id.pop_fenqu:
                    tvTitle.setText("分区");
                    break;

            }
            ft.commit();
            this.dismiss();
        }

        private void checkFragment(Fragment jiFangFragment, FragmentTransaction ft) {
            if (jiFangFragment == null) {

            } else {
                ft.hide(jiFangFragment);
            }
        }
    }

    class MyAdapter extends BaseAdapter {

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
                view = LayoutInflater.from(TroubleModuleActivity.this).inflate(R.layout.item_module_detail_lv, null);
                holder.icon = (ImageView) view.findViewById(R.id.iv_icon);
                holder.textView = (TextView) view.findViewById(R.id.tv_text);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            if (i%3==0){
                holder.icon.setImageResource(R.drawable.redball);
            }else if (i%3==1){
                holder.icon.setImageResource(R.drawable.greenball);
            }else{
                holder.icon.setImageResource(R.drawable.yellowball);
            }
            holder.textView.setText(mList.get(i).getName() + " " + mList.get(i).getNum());
            return view;
        }

        class ViewHolder {
            ImageView icon;
            TextView textView;
        }
    }
}
