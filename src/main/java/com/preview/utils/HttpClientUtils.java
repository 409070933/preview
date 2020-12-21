package com.preview.utils;
import java.io.IOException;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public  class HttpClientUtils {

    public static final JSONObject doGet(String url) {
        // 指定get请求
        HttpGet httpGet = new HttpGet(url);
        // 创建httpclient
        HttpClient httpClient = new DefaultHttpClient();
        // 发送请求
        HttpResponse httpResponse;
        //返回的json
        JSONObject jsonObject = null;
        try {
            httpResponse = httpClient.execute(httpGet);
            // 验证请求是否成功
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 得到请求响应信息
                String str = EntityUtils.toString(httpResponse.getEntity(),
                        "utf-8");
                // 返回json
                jsonObject = JSONObject.parseObject(str);
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }


    public static final JSONObject doPost(String url, String jsonString) {
        // 指定Post请求
        HttpPost httpPost = new HttpPost(url);
        // 创建httpclient
        HttpClient httpClient = new DefaultHttpClient();
        // 发送请求
        HttpResponse httpResponse;
        // 返回的json
        JSONObject jsonObject = null;
        // 封装post请求数据
        StringEntity entity = new StringEntity(jsonString, "utf-8");
        httpPost.setEntity(entity);
        try {
            // 发送请求
            httpResponse = httpClient.execute(httpPost);
            // 判断请求是否成功
            if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                // 得到请求响应信息
                String str = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                // 返回json
                jsonObject = JSONObject.parseObject(str);
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsonObject;
    }

}
