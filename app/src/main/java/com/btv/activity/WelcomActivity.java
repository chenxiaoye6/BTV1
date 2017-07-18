package com.btv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.btv.MyApplication;
import com.emm.gateway.EMMGatewayUtil;
import com.emm.gateway.callback.InitGatewayCallback;
import com.emm.local.proxy.EMMHttpLocalProxyServer;
import com.sso.SSOUtil;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by Administrator on 2016/12/2.
 */

public class WelcomActivity extends AutoLayoutActivity {
    private EMMHttpLocalProxyServer mProxyServer;
    private String mProxyUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
//        初始化沙箱
//        EMMSandboxUtil.setEMMPackageName("com.ctvit.wisdommedia");
//        EMMSandboxUtil.initSandbox(this);
//        boolean a = EMMSandboxUtil.initSandbox(this);
//        Log.e("789", "初始化沙箱+" + a);
//        initProxy();
        startActivity(new Intent(this, IpLogin.class));
        finish();
    }

    private void setUrl() {
//        //设置token url
//        String url = "http://172.28.61.73:8080/btvTrans/btvTokenCheck";

        String url = "http://172.28.61.73:8080/btvTrans/btvTokenCheck";
//        mProxyUrl = loadProxyUrl(url, mProxyServer.getLocalPort());
        mProxyUrl = loadProxyUrl(url, 8080);
        SSOUtil.setGetTokenUrl(mProxyUrl);//设置token url
        Log.e("789", "设置url" + mProxyUrl);
        show("设置token 代理url成功...");
    }

    private void show(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private String mToken;

    public void getToken() {
        if (mProxyServer.getLocalPort() == 0) {
            Toast.makeText(WelcomActivity.this, "正在初始化代理....", Toast.LENGTH_SHORT).show();
            initProxy();
            return;
        }
        //获取sso的token
        /*
        * */
        Log.e("789", "1");
        SSOUtil.getSSOToken(WelcomActivity.this, "admin", "moreless", new SSOUtil.NetworkListener() {
            @Override
            public void onSuccess(String data) {
                show("获取token成功....token:" + data);
                Log.e("789", "2");
                mToken = data;
            }

            @Override
            public void onError(String error) {
                Log.e("789", "3");
                show("获取token失败....");
            }
        });
    }

    private void initProxy() {
        mProxyServer = new EMMHttpLocalProxyServer(this, "");
        EMMGatewayUtil.initGateway(this, new InitGatewayCallback() {
            @Override
            public void onSuccess() {
                boolean isSuccess = mProxyServer.init();
                Log.e("789", "初始化网关" + isSuccess);
                int proxyPort = mProxyServer.getLocalPort();  //代理端口
                Toast.makeText(WelcomActivity.this, "初始化代理成功....", Toast.LENGTH_SHORT).show();
                setUrl();
                getToken();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(WelcomActivity.this, "初始化代理失败....onFailure:" + i + ":" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(WelcomActivity.this, "初始化代理失败....onError：" + i + ":" + s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart(boolean b) {

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
        String tempurl = "http://172.28.61.73:" + proxyPort + method;

        return tempurl;
    }
}
