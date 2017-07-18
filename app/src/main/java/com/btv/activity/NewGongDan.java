package com.btv.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.btv.Bean.LvDataBean;
import com.btv.MyApplication;
import com.btv.R;
import com.btv.Utils.HttpUtils;
import com.btv.Utils.Internet;
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

import static com.btv.R.id.ll_level;

/**
 * Created by Administrator on 2017/3/4.
 */

public class NewGongDan extends AutoLayoutActivity {

    @InjectView(R.id.iv_title_left)
    ImageView ivTitleLeft;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.iv_title_right)
    ImageView ivTitleRight;
    @InjectView(R.id.et_ngd_call)
    EditText etNgdCall;
    @InjectView(R.id.tv_ngd_type)
    TextView tvNgdType;
    @InjectView(R.id.tv_ngd_state)
    TextView tvNgdState;
    @InjectView(R.id.tv_ngd_system)
    TextView tvNgdSystem;
    @InjectView(R.id.tv_ngd_duty)
    TextView tvNgdDuty;
    @InjectView(R.id.tv_ngd_runteam)
    TextView tvNgdRunteam;
    @InjectView(R.id.et_ngd_title)
    EditText etNgdTitle;
    @InjectView(R.id.et_ngd_describe)
    EditText etNgdDescribe;
    @InjectView(ll_level)
    LinearLayout llLevel;
    @InjectView(R.id.ll_nod_system)
    LinearLayout llNodSystem;
    @InjectView(R.id.ll_nod_duty)
    LinearLayout llNodDuty;
    @InjectView(R.id.ll_nod_runteam)
    LinearLayout llNodRunteam;
    @InjectView(R.id.btn_nod_save)
    Button btnNodSave;
    @InjectView(R.id.tv_ngd_submit)
    Button tvNgdSubmit;
    @InjectView(R.id.et_ngd_name)
    TextView etNgdName;
    private SharedPreferences sp;
    private String token;
    private String userId;
    private ArrayList<String> listParent, listChildren;
    private ArrayList<ArrayList<String>> listAll;
    private String result;
    private String empNo;
    private String address;
    private String email;
    private String mobile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.neworder);
        MyApplication.getInstance().addActivity(this);
        ButterKnife.inject(this);
        initView();
    }

    //初始化页面获取数据
    private void initView() {
        sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        token = sp.getString("token", "");
        userId = sp.getString("userId", "");
        ivTitleLeft.setImageResource(R.drawable.back);
        tvTitle.setText("新建事件单");
        ivTitleRight.setVisibility(View.GONE);
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        //使用的假数据
        params.put("userId", userId);
        HttpUtils.doPost(Internet.SHOWGDDATA, params, new Callback() {
            private JSONObject userObject;

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result = response.body().string();
                try {
                    Log.e("789", "result======" + result);
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray userArray = jsonObject.getJSONArray("UserInfo");
                    userObject = userArray.getJSONObject(0);
//                    "UserName": "曹宛莹",
//                            "EmpNo": "00009",
//                            "Address": "null",
//                            "DefaultMobile": "13000000000",
//                            "Email": "t@t.tt",
//                            "Mobile": "null"
                    empNo = userObject.getString("EmpNo");
                    address = userObject.getString("Address");
                    email = userObject.getString("Email");
                    mobile = userObject.getString("Mobile");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //在子线程更新ui
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            etNgdName.setText(userObject.getString("UserName"));
                            etNgdCall.setText(userObject.getString("etNgdCall"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @OnClick({R.id.ll_nod_type, R.id.ll_nod_state, R.id.ll_nod_system, R.id.ll_nod_duty, R.id.ll_nod_runteam, R.id.btn_nod_save,
            R.id.tv_ngd_high, R.id.tv_ngd_center, R.id.tv_ngd_low, R.id.tv_ngd_submit, R.id.iv_title_left})
    public void onClick(View view) {
        switch (view.getId()) {
            //分别实现点击事件带参跳转并返回数据
            case R.id.ll_nod_type:
                try {
                    listParent = new ArrayList<>();
                    listAll = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject data = jsonObject.getJSONObject("data");
                    Log.e("789", "data====" + data.toString());
                    JSONArray requestCArray = data.getJSONArray("RequestCategory");
                    for (int i = 0; i < requestCArray.length(); i++) {
                        listChildren = new ArrayList<String>();
                        JSONObject job = requestCArray.getJSONObject(i);
                        listParent.add(job.getString("text"));
                        JSONArray array = job.getJSONArray("RequestCategory");
                        for (int n = 0; n < array.length(); n++) {
                            listChildren.add(array.getJSONObject(n).getString("text"));
                        }
                        listAll.add(listChildren);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("789", listAll.toString());
                LvDataBean list = new LvDataBean();
                list.setList(listAll);
                Intent intent = new Intent(NewGongDan.this, LVActivity.class);
                intent.putExtra("resultCode", 1);
                intent.putStringArrayListExtra("listParent", listParent);
                Bundle bundle = new Bundle();
                bundle.putSerializable("listAll", list);//序列化
                intent.putExtras(bundle);//发送数据
                startActivityForResult(intent, 1);
                break;
            case R.id.ll_nod_state:
                llLevel.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_nod_system:
                try {
                    listParent = new ArrayList<>();
                    listAll = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray systemArray = jsonObject.getJSONArray("BusinessSystem");
                    Log.e("789", "systemArray====" + systemArray.toString());
                    for (int i = 0; i < systemArray.length(); i++) {
                        listChildren = new ArrayList<String>();
                        JSONObject job = systemArray.getJSONObject(i);
                        listParent.add(job.getString("text"));
                        JSONArray array = job.getJSONArray("BusinessSystem");
                        for (int n = 0; n < array.length(); n++) {
                            listChildren.add(array.getJSONObject(n).getString("text"));
                        }
                        listAll.add(listChildren);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("789", listAll.toString());
                LvDataBean list2 = new LvDataBean();
                list2.setList(listAll);
                Intent intent2 = new Intent(NewGongDan.this, LVActivity.class);
                intent2.putExtra("resultCode", 2);
                intent2.putStringArrayListExtra("listParent", listParent);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("listAll", list2);//序列化
                intent2.putExtras(bundle2);//发送数据
                startActivityForResult(intent2, 2);

                break;
            case R.id.ll_nod_runteam:
                listParent = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray operationsGArray = jsonObject.getJSONArray("OperationsGroup");
                    for (int i = 0; i < operationsGArray.length(); i++) {
                        JSONObject jsonObject1 = operationsGArray.getJSONObject(i);
                        listParent.add(jsonObject1.getString("text"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent3 = new Intent(NewGongDan.this, ListViewActivity.class);
                intent3.putExtra("resultCode", 3);
                intent3.putStringArrayListExtra("listParent", listParent);
                startActivityForResult(intent3, 3);
                break;
            case R.id.ll_nod_duty:
                listParent = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray operationsGArray = jsonObject.getJSONArray("DutyArea");
                    for (int i = 0; i < operationsGArray.length(); i++) {
                        JSONObject jsonObject1 = operationsGArray.getJSONObject(i);
                        listParent.add(jsonObject1.getString("dutyAreaName"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent4 = new Intent(NewGongDan.this, ListViewActivity.class);
                intent4.putExtra("resultCode", 4);
                intent4.putStringArrayListExtra("listParent", listParent);
                startActivityForResult(intent4, 4);
                break;
            case R.id.btn_nod_save:
                break;
            case R.id.tv_ngd_submit:
                //提交新建工单
                String title = etNgdTitle.getText().toString();
                String priority = tvNgdState.getText().toString();
                String businessSystemName = tvNgdSystem.getText().toString();
                String dutyAreaName = tvNgdDuty.getText().toString();
                String desc = etNgdDescribe.getText().toString();
                final String resourceTypeName = tvNgdType.getText().toString();
                String operationsGroup = tvNgdRunteam.getText().toString();
                String userName = etNgdName.getText().toString();
                HashMap<String, String> params = new HashMap<>();
                params.put("token", token);
                params.put("userId", getSharedPreferences("userInfo", MODE_PRIVATE).getString("userId", ""));
                params.put("title", title);
                params.put("priority", priority);
                params.put("businessSystemName", businessSystemName);
                params.put("dutyAreaName", dutyAreaName);
                params.put("desc", desc);
                params.put("resourceTypeName", resourceTypeName);
                params.put("operationsGroup", operationsGroup);
                params.put("userName", userName);
                params.put("empNo", empNo);
                params.put("address", address);
                params.put("email", email);
                params.put("mobile", etNgdCall.getText().toString());
                HttpUtils.doPost(Internet.CREATGD, params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String data = response.body().string();
                        Log.e("789", data.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    Toast.makeText(NewGongDan.this, jsonObject.getString("isSuccess"), Toast.LENGTH_SHORT).show();
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
                break;
            case R.id.iv_title_left:
                finish();
                break;
            case R.id.tv_ngd_high:
                tvNgdState.setText("高");
                llLevel.setVisibility(View.GONE);
                break;
            case R.id.tv_ngd_center:
                tvNgdState.setText("中");
                llLevel.setVisibility(View.GONE);
                break;
            case R.id.tv_ngd_low:
                tvNgdState.setText("低");
                llLevel.setVisibility(View.GONE);
                break;
        }
    }

    //返回过来的数据改变界面
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        switch (requestCode) {
            case 1:
                tvNgdType.setText(data.getStringExtra("result"));
                break;
            case 2:
                tvNgdSystem.setText(data.getStringExtra("result"));
                break;
            case 3:
                tvNgdRunteam.setText(data.getStringExtra("result"));
                break;
            case 4:
                tvNgdDuty.setText(data.getStringExtra("result"));
                break;
            default:
                break;
        }
    }
}
