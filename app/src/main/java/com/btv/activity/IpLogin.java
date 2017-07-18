package com.btv.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.btv.MyApplication;
import com.btv.R;
import com.btv.Utils.HttpUtils;
import com.btv.Utils.Internet;
import com.btv.Utils.ResourceUtil;
import com.btv.Utils.Utils;
import com.emm.gateway.EMMGatewayUtil;
import com.emm.gateway.callback.InitGatewayCallback;
import com.emm.local.proxy.EMMHttpLocalProxyServer;
import com.emm.sandbox.EMMInternalUtil;
import com.emm.sandbox.EMMSandboxUtil;
import com.emm.sandbox.container.DataContainer;
import com.emm.thirdparty.tools.EMMInfoUtil;
import com.sso.SSOUtil;
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
 * Created by Administrator on 2017/1/12.
 */

public class IpLogin extends AutoLayoutActivity {
    @InjectView(R.id.tv_iplogin_next)
    TextView tvIploginNext;
    @InjectView(R.id.login_general)
    RadioButton loginGeneral;
    @InjectView(R.id.login_safe)
    RadioButton loginSafe;
    @InjectView(R.id.et_ip)
    EditText etIp;
    @InjectView(R.id.et_host)
    EditText etHost;
    private EMMHttpLocalProxyServer mProxyServer;
    private SharedPreferences sp;
    private String mProxyUrl;
    private String name;
    private String mToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iplogin);
        MyApplication.getInstance().addActivity(this);
        ButterKnife.inject(this);
        sp = getSharedPreferences("userInfo", MODE_PRIVATE);
        initView();
        String data = "Thu Apr 27 15:59:25   2017";
        String date = Utils.convertGMTToLoacale(data);
        Log.e("789", date);

    }

    private void initView() {
        if ("".equals(sp.getString("ip", ""))) {
        } else {
            etIp.setText(sp.getString("ip", ""));
            etHost.setText(sp.getString("host", ""));
        }
    }

    @OnClick(R.id.tv_iplogin_next)
    public void onClick() {
        if ("".equals(etIp.getText().toString()) || "".equals(etHost.getText().toString())) {
            Toast.makeText(this, "请填入正确的端口号和IP", Toast.LENGTH_SHORT).show();
            return;
        }

        if (loginGeneral.isChecked()) {
            MyApplication.getInstance().setB("http://" + etIp.getText().toString() + ":" + etHost.getText().toString() + "/");
            if ("".equals(sp.getString("userName", ""))) {
                startActivity(new Intent(this, Login.class));
                finish();
            } else {
                HttpUtils.doGet(Internet.LOGIN + "?userName=" + sp.getString("userName", "") + "&userPwd=" + sp.getString("userPwd", ""), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        startActivity(new Intent(IpLogin.this, Login.class));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String data = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.e("123", data);
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (0 == jsonObject.getInt("result")) {
                                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                        JSONObject jsonObject2 = jsonObject1.getJSONObject("UserLoginInfo");
                                        String token = jsonObject2.getString("token");
                                        String userId = jsonObject2.getString("userId");
                                        Log.e("123", token);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("token", token);
                                        editor.putString("userId", userId);
                                        editor.commit();
                                        startActivity(new Intent(IpLogin.this, MainActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(IpLogin.this, jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(IpLogin.this, Login.class));
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        } else {
            if ("".equals(etIp.getText().toString()) || "".equals(etHost.getText().toString())) {
                Toast.makeText(this, "请填入正确的端口号和IP", Toast.LENGTH_SHORT).show();
                return;
            }
            ///
            DataContainer container = EMMInternalUtil.getDataContainer(IpLogin.this, "emm_gateway");
            if (container != null) {
                Log.e("789", "--------------" + container.getString("http_token", ""));
            }
            //        初始化沙箱
            EMMSandboxUtil.setEMMPackageName("com.ctvit.wisdommedia");
            EMMSandboxUtil.initSandbox(this);
            boolean a = EMMSandboxUtil.initSandbox(this);
            mProxyServer = new EMMHttpLocalProxyServer(this, "");
            //初始化代理
            initProxy();

        }
    }

    private void initProxy() {
        EMMGatewayUtil.initGateway(this, new InitGatewayCallback() {
            @Override
            public void onSuccess() {
                boolean isSuccess = mProxyServer.init();
                Log.e("789", "-------------" + EMMInfoUtil.getEMMDeviceId(IpLogin.this));
                int proxyPort = mProxyServer.getLocalPort();  //代理端口
                Toast.makeText(IpLogin.this, "初始化代理成功....", Toast.LENGTH_SHORT).show();
                String url = ResourceUtil.getConfString(IpLogin.this, "get_token_url");
                mProxyUrl = loadProxyUrl(url, mProxyServer.getLocalPort());
                SSOUtil.setGetTokenUrl(mProxyUrl);//设置token url
                Toast.makeText(IpLogin.this, "设置token 代理url成功...", Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(mProxyUrl)) {
                    Toast.makeText(IpLogin.this, "请设置token url", Toast.LENGTH_SHORT).show();
                    return;
                }
                getToken();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(IpLogin.this, "初始化代理失败....onFailure:" + i + ":" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(IpLogin.this, "初始化代理失败....onError：" + i + ":" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart(boolean b) {
            }
        });
    }

    public void getToken() {
        if (mProxyServer.getLocalPort() == 0) {
            Toast.makeText(IpLogin.this, "正在初始化代理....", Toast.LENGTH_SHORT).show();
            initProxy();
            return;
        }
        //获取sso的token
//        String name = EMMSandboxUtil.getUsername(IpLogin.this);
        String pwd = EMMInternalUtil.getPassword(IpLogin.this);
        name = EMMInternalUtil.getUsername(IpLogin.this);
        SSOUtil.getSSOToken(IpLogin.this, name, pwd, new SSOUtil.NetworkListener() {


            //+ data
            @Override
            public void onSuccess(String data) {
                Toast.makeText(IpLogin.this, "获取token成功....token:", Toast.LENGTH_SHORT).show();
                mToken = data;
                checkToken();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(IpLogin.this, "获取token失败....", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void checkToken() {
        if (mProxyServer.getLocalPort() == 0) {
            Toast.makeText(IpLogin.this, "正在初始化代理....", Toast.LENGTH_SHORT).show();
            initProxy();
            return;
        }
        if (TextUtils.isEmpty(mToken)) {
            Toast.makeText(IpLogin.this, "请先获取token....", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e("789", "mtoken=" + mToken + "name" + name);
        //根据登陆账号 token 验证token是否失效
        String url = "http://172.28.61.73:8080/btvTrans/btvTokenCheck";
        mProxyUrl = loadProxyUrl(url, mProxyServer.getLocalPort());
        SSOUtil.setCheckTokenUrl(mProxyUrl);
        SSOUtil.isSSOTokenValid(IpLogin.this, name, mToken, new SSOUtil.NetworkListener() {
            @Override
            public void onSuccess(String s) {
                Toast.makeText(IpLogin.this, "检验成功", Toast.LENGTH_SHORT).show();
                String token = mToken;
                String userId = EMMInfoUtil.getEMMDeviceId(IpLogin.this);
                Log.e("123", token);
                String url = "http://" + etIp.getText().toString() + ":" + etHost.getText().toString() + "/";
                String baseUrl = loadProxyUrl(url, mProxyServer.getLocalPort());
                Log.e("789", "baseurl" + baseUrl);
                MyApplication.getInstance().setB(baseUrl);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("token", token);
                editor.putString("userId", userId);
                editor.putString("ip", etIp.getText().toString());
                editor.putString("host", etHost.getText().toString());
                editor.commit();
                //
//                DataContainer container = EMMInternalUtil.getDataContainer(IpLogin.this, "emm_gateway");
//                if (container != null) {
//                    Log.e("789", "--------------" + container.getString("http_token", ""));
//                }
                startActivity(new Intent(IpLogin.this, Login.class));
            }

            @Override
            public void onError(String s) {
                Toast.makeText(IpLogin.this, "校验失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 将url转换成本地代理的url
     */
    private String loadProxyUrl(String url, int proxyPort) {
        int offset = url.indexOf("://");
        int idx = url.indexOf('/', offset > 0 ? offset + 3
                : offset);
        String method = url.substring(idx, url.length());
        String tempurl = "http://127.0.0.1:" + proxyPort + method;

        return tempurl;
    }

    private void show(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}
