package com.lzj.weatherknow.listener;

/**
 * Created by Administrator on 2/16 0016.
 * 服务端回调接口
 */
public interface HttpCallbackListener {

    void onResponseSuccess(String response);

    void onResponseError(Exception e);
}
