package com.lzj.weatherknow.util;

import com.lzj.weatherknow.listener.HttpCallbackListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2/16 0016.
 * 与服务端交互的工具类
 */
public class HttpUtil {

    /**
     * 发送数据请求
     * @param address
     * @param listener
     */

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();//打开url
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    //若接口数据不为空，则回调onResponseSuccess函数
                    if (listener != null){
                        listener.onResponseSuccess(response.toString());
                    }
                }catch (Exception e){
                    if (listener != null){
                        listener.onResponseError(e);
                    }
                }finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
