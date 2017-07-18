package com.btv.activity;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.btv.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class NewsActivity extends AppCompatActivity {

    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_xiala)
    ImageView ivXiala;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.cb1)
    CheckBox cb1;
    @InjectView(R.id.cb2)
    CheckBox cb2;
    @InjectView(R.id.cb3)
    CheckBox cb3;
    @InjectView(R.id.tv1)
    TextView tv1;
    @InjectView(R.id.tv2)
    TextView tv2;
    @InjectView(R.id.tv3)
    EditText tv3;
    private int hour1;
    private int minute1;
    private int hour2;
    private int minute2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.inject(this);
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("设置");
        ivXiala.setVisibility(View.INVISIBLE);
        ivTitleRight.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.iv_title_left, R.id.tv1, R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.tv1:
                String t1 = tv1.getText().toString();
                if ("0".equals(t1.subSequence(0, 1))) {
                    hour1 = Integer.parseInt(t1.substring(1, 2));
                } else {
                    hour1 = Integer.parseInt(t1.substring(0, 2));
                }
                if ("0".equals(t1.subSequence(3, 4))) {
                    minute1 = Integer.parseInt(t1.substring(4, 5));
                } else {
                    minute1 = Integer.parseInt(t1.substring(3, 5));
                }
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Log.e("aaa",
                                "(NewsActivity.java:64)" + hourOfDay + "minute" + minute);
                        String time = (hourOfDay < 10 ? ("0" + hourOfDay) : (hourOfDay)) + ":" + (minute < 10 ? ("0" + minute) : (minute + ""));
                        Log.e("aaa",
                                "(NewsActivity.java:68)" + time);
                        tv1.setText(time);
                    }
                }, hour1, minute1, true);
                timePickerDialog.show();
                break;
            case R.id.tv2:

                String t2 = tv2.getText().toString();
                if ("0".equals(t2.subSequence(0, 1))) {
                    hour2 = Integer.parseInt(t2.substring(1, 2));
                } else {
                    hour2 = Integer.parseInt(t2.substring(0, 2));
                }
                if ("0".equals(t2.subSequence(3, 4))) {
                    minute2 = Integer.parseInt(t2.substring(4, 5));
                } else {
                    minute2 = Integer.parseInt(t2.substring(3, 5));
                }
                TimePickerDialog timePickerDialog2 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String time = (hourOfDay < 10 ? ("0" + hourOfDay) : (hourOfDay)) + ":" + (minute < 10 ? ("0" + minute) : (minute + ""));
                        tv2.setText(time);
                    }
                }, hour2, minute2, true);
                timePickerDialog2.setCanceledOnTouchOutside(true);
                timePickerDialog2.show();
                break;
        }
    }
}
