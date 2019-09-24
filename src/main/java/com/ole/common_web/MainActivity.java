package com.ole.common_web;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ole.libwebview.BaseWebview;


import java.util.HashMap;
import java.util.Map;

/**
 * @desc webview 启动页
 * @author hyx
 */
public class MainActivity extends Activity {

    private BaseWebview baseWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseWeb=findViewById(R.id.common_webview);
        baseWeb.mWebView.addJavascriptInterface(new WebViewInterface(this),"commonBridge");
//        baseWeb.mWebView.addJavascriptInterface(new WebViewInterfaceOne(this),"commonBridge");
        baseWeb.setWebviewListen(webListen);
        //右键背景图片，右键是否显示
        //baseWeb.setButtonBackgroundImg(R.mipmap.app_ic_back,true);
//        baseWeb.webvewLoadUrl("http://10.53.132.71:10789/#/index");
        baseWeb.webvewLoadUrl("http://mp.test.olayc.cn/driver/driverSchool.html#/index?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJBcHAiLCJ1c2VyX2lkIjoidXNlcklkX0RNXzE1MjE3IiwiaXNzIjoiU2VydmljZSIsImlhdCI6MTU2OTMwNTQzM30.foqmKvgND8VyIwA69xLf5MnDhvb3bEIpmOcdkNGGZe0&adcode=130600&city=保定");
    }

    /**
     * js回调，业务处理
     */
    private class WebViewInterface {
        Context mContext;

        WebViewInterface(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void closeActivity() {
            //业务逻辑

        }

        /**
         * @desc version2.5
         * @打开URL
         */
        @JavascriptInterface
        public void openUrl(String json, final String callback) throws JSONException {
            if(callback!=null){
                Map map=new HashMap();
                final String returnStr=JsonFormatToStr(map);
                if(callback!=null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            baseWeb.webvewLoadUrl("javascript:" + callback + "(" + returnStr + ")");
                        }
                    });
                }
            }
            if(json!=null) {
                try{
                    JSONObject jsObject= JSON.parseObject(json);
                    if(jsObject.get("url")!=null){
                        final String url= (String) jsObject.get("url");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                baseWeb.webvewLoadUrl(url);
                            }
                        });

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        }
    }


    /**
     * js回调，业务处理
     */
    private class WebViewInterfaceOne {
        Context mContext;

        WebViewInterfaceOne(Context context) {
            mContext = context;
        }

        @JavascriptInterface
        public void closeActivity() {
            //业务逻辑

        }


    }

    /**
     * json封装并传输data数据
     * @param map
     * @return
     */
    public static String JsonFormatToStr(Map map){
        JSONObject js=new JSONObject();
        try {
            js.put("code",0);
            js.put("msg","获取成功");
            JSONObject jsdata=new JSONObject();
            for(Object str:map.keySet()){
                jsdata.put((String) str,map.get(str));
            }
            js.put("data",jsdata);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }

    /**
     * 接口回调
     */
    BaseWebview.webviewListen webListen= new BaseWebview.webviewListen() {
        @Override
        public void webviewFinish() {
           finish();
        }

        @Override
        public void rightButtonOnClick() {
            //处理对应业务逻辑
            Toast.makeText(MainActivity.this,"右击事件",Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseWeb.onDestroy();
    }
}
