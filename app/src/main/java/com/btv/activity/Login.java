package com.btv.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.btv.MyApplication;
import com.btv.R;
import com.btv.Utils.HttpUtils;
import com.btv.Utils.Internet;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/11/18.
 */

public class Login extends AutoLayoutActivity {

    @InjectView(R.id.et_name)
    EditText et_name;
    @InjectView(R.id.et_pwd)
    EditText et_pwd;
    @InjectView(R.id.btn_login)
    Button btnLogin;
    private SharedPreferences sp;
    private String userName;
    private String userPwd;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("123", msg.obj.toString() + "=============" + msg.toString());
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //使用SharedPreferences保存token
        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        ButterKnife.inject(this);
        //添加一键退出
        MyApplication.getInstance().addActivity(this);
    }

    @OnClick(R.id.btn_login)
    public void onClick() {
        userName = et_name.getText().toString();
        userPwd = et_pwd.getText().toString();
        HttpUtils.doGet(Internet.LOGIN + "?userName=" + userName + "&userPwd=" + userPwd, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("123", "failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("123", "data=====" + data);
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            JSONObject jsonObject2 = jsonObject1.getJSONObject("UserLoginInfo");
                            String token = jsonObject2.getString("token");
                            String userId = jsonObject2.getString("userId");
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("token", token);
                            editor.putString("userId", userId);
                            if (jsonObject2.getBoolean("success")) {
                                editor.putString("userName", userName);
                                editor.putString("userPwd", userPwd);
                                editor.commit();
                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(Login.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
