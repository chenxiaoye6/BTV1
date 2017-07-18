package com.btv.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.btv.Bean.GDOpinionBean;
import com.btv.Bean.GongDanBean;
import com.btv.Bean.NewODetail;
import com.btv.R;
import com.btv.Utils.HttpUtils;
import com.btv.Utils.Internet;
import com.btv.Utils.Utils;
import com.btv.adapter.OpinionAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.btv.R.id.tv_nod_number;

/**
 * Created by Administrator on 2017/3/8.
 */

public class NewestOrderDetail extends AutoLayoutActivity {
    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.tv_nod_name)
    TextView tvNodName;
    @InjectView(R.id.tv_nod_bumen)
    TextView tvNodBumen;
    @InjectView(R.id.tv_nod_mobile)
    TextView tvNodMobile;
    @InjectView(R.id.tv_nod_call)
    ImageView tvNodCall;
    @InjectView(R.id.tv_nod_sms)
    ImageView tvNodSms;
    @InjectView(R.id.tv_nod_tell)
    TextView tvNodTell;
    @InjectView(tv_nod_number)
    TextView tvNodNumber;
    @InjectView(R.id.tv_nod_email)
    TextView tvNodEmail;
    @InjectView(R.id.tv_nod_address)
    TextView tvNodAddress;
    @InjectView(R.id.ll_nod_a)
    LinearLayout llNodA;
    @InjectView(R.id.tv_nod_show)
    TextView tvNodShow;
    @InjectView(R.id.tv_nod_type)
    TextView tvNodType;
    @InjectView(R.id.tv_nod_title)
    TextView tvNodTitle;
    @InjectView(R.id.tv_nod_des)
    TextView tvNodDes;
    @InjectView(R.id.tv_nod_thingplace)
    TextView tvNodThingplace;
    @InjectView(R.id.tv_nod_level)
    TextView tvNodLevel;
    @InjectView(R.id.tv_nod_system)
    TextView tvNodSystem;
    @InjectView(R.id.tv_nod_dutyplace)
    TextView tvNodDutyplace;
    @InjectView(R.id.tv_nod_runteam)
    TextView tvNodRunteam;
    @InjectView(R.id.tv_nod_actualtime)
    TextView tvNodActualtime;
    @InjectView(R.id.tv_nod_creattime)
    TextView tvNodCreattime;
    @InjectView(R.id.ll_nod_b)
    LinearLayout llNodB;
    @InjectView(R.id.tv_nod_show2)
    TextView tvNodShow2;
    @InjectView(R.id.nod_process_time1)
    TextView nodProcessTime1;
    @InjectView(R.id.nod_process_time2)
    TextView nodProcessTime2;
    @InjectView(R.id.nod_process_statelight)
    ImageView nodProcessStatelight;
    @InjectView(R.id.nod_process_thing)
    TextView nodProcessThing;
    @InjectView(R.id.nod_process_person)
    TextView nodProcessPerson;
    @InjectView(R.id.nod_process_callto)
    ImageView nodProcessCallto;
    @InjectView(R.id.nod_process_smsto)
    ImageView nodProcessSmsto;
    @InjectView(R.id.ll_nod_process)
    LinearLayout llNodProcess;
    @InjectView(R.id.lv_opinion)
    ListView lvOpinion;
    private SharedPreferences sp;
    private String token;
    private GongDanBean gongDan;
    private ArrayList<NewODetail.ProcessBean> nodList;
    private String userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neworderdetail);
        ButterKnife.inject(this);
        initView();

    }

    private void initView() {
        gongDan = (GongDanBean) getIntent().getSerializableExtra("gongdan");
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        userId = sp.getString("userId", "");
        String taskId = gongDan.getTaskId();
        String accountId = gongDan.getAccountId();
        String boInstId = gongDan.getBoInstId();
        String openMode = "";
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("taskId", taskId);
        params.put("accountId", accountId);
        params.put("boInstId", boInstId);
        params.put("openMode", "0");
        HttpUtils.doPost(Internet.GDDETAIL, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("789", "result============" + result);
                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            JSONObject data = jsonObject.getJSONObject("data");
                            JSONArray workODArray = data.getJSONArray("workOrderDetails");
                            JSONObject object = workODArray.getJSONObject(0);
                            Gson gson = new Gson();
                            NewODetail newODetail = gson.fromJson(object.toString(), NewODetail.class);
                            String opinion = object.getString("opinion");
                            ArrayList<GDOpinionBean> opinionList = gson.fromJson(opinion, new TypeToken<ArrayList<GDOpinionBean>>() {
                            }.getType());
                            OpinionAdapter adapter = new OpinionAdapter(opinionList, NewestOrderDetail.this);
                            lvOpinion.setAdapter(adapter);
                            Utils.setListViewHeight(lvOpinion);
                            //个人信息
                            tvNodName.setText(object.getString("duName"));
                            tvNodBumen.setText(object.getString("duOrgName"));
                            tvNodMobile.setText(object.getString("duMobile"));
                            tvNodTell.setText(object.getString("duTelephone"));
                            tvNodNumber.setText(object.getString("empNo"));
                            tvNodAddress.setText(object.getString("duWorkplace"));
                            tvNodEmail.setText(object.getString("duEmail"));
                            //工单信息
                            tvNodType.setText(object.getString("reqCategoryName"));
                            tvNodTitle.setText(object.getString("title"));
                            tvNodDes.setText(object.getString("desc"));
                            tvNodThingplace.setText(object.getString("workformState"));
                            tvNodSystem.setText(object.getString("businessSysName"));
                            tvNodDutyplace.setText(object.getString("dutyAreaName"));
                            tvNodRunteam.setText(object.getString("operationsGroup"));
                            tvNodActualtime.setText(object.getString("createTime"));
                            tvNodCreattime.setText(object.getString("happenTime"));
                            tvNodLevel.setText(object.getString("priority"));
                            nodList = newODetail.getProcess();
                            Log.e("789", "list.tos==============" + nodList.toString());
                            NewODetail.ProcessBean processBean = nodList.get(0);
                            String time[] = processBean.getSendTime().split(" ");
                            if (time.length == 2) {
                                nodProcessTime1.setText(time[1]);
                                nodProcessTime2.setText(time[0]);
                            } else {
                                nodProcessTime1.setText("");
                                nodProcessTime2.setText("");
                            }
                            nodProcessThing.setText(processBean.getActivityName());
                            nodProcessPerson.setText(processBean.getUserName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("工单详情");
        ivTitleRight.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.iv_title_left, R.id.tv_nod_call, R.id.tv_nod_sms, R.id.tv_nod_show, R.id.tv_nod_show2,
            R.id.nod_process_callto, R.id.nod_process_smsto, R.id.ll_nod_process})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.tv_nod_call:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setIcon(0)
                        .setTitle("是否拨打电话?")
                        .setMessage(tvNodMobile.getText())
                        .setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + tvNodMobile.getText()));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_nod_sms:
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
                dialog2.setIcon(0)
                        .setTitle("是否发送短信?")
                        .setMessage(tvNodMobile.getText())
                        .setPositiveButton("编辑", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Uri uri = Uri.parse("smsto:" + tvNodMobile.getText());
                                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                                startActivity(it);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_nod_show:
                Log.e("789", "asdfasdfasdf");
                if ("∨".equals(tvNodShow.getText())) {
                    llNodA.setVisibility(View.VISIBLE);
                    tvNodShow.setText("∧");
                } else {
                    llNodA.setVisibility(View.GONE);
                    tvNodShow.setText("∨");
                }
                break;
            case R.id.tv_nod_show2:
                Log.e("789", "asdfasdfasdf");
                if ("∨".equals(tvNodShow2.getText())) {
                    llNodB.setVisibility(View.VISIBLE);
                    tvNodShow2.setText("∧");
                } else {
                    llNodB.setVisibility(View.GONE);
                    tvNodShow2.setText("∨");
                }
                break;
            //流程电话
            case R.id.nod_process_callto:
                break;
            //流程短信
            case R.id.nod_process_smsto:
                break;
            //点击流程跳转到流程页面
            case R.id.ll_nod_process:
                Intent intent = new Intent(this, ProcessFollow.class);
                intent.putExtra("list", nodList);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
