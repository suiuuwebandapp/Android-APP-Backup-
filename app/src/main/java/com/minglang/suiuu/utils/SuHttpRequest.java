package com.minglang.suiuu.utils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


/**
 * 封装网络请求方法
 * <p/>
 * Created by Administrator on 2015/4/23.
 */
public class SuHttpRequest {

    private static final String TAG = SuHttpRequest.class.getSimpleName();

    /**
     * 网络请求核心类
     */
    private HttpUtils httpUtils;

    /**
     * 网络请求类型
     * <p/>
     * GET、POST等
     */
    private HttpRequest.HttpMethod httpMethod;

    /**
     * 请求参数
     */
    private RequestParams params;

    /**
     * 请求URL地址
     */
    private String httpPath;

    /**
     * 网络请求回调接口
     */
    private RequestCallBack<String> requestCallBack;

    private HttpHandler<String> httpHandler;

    public SuHttpRequest(HttpRequest.HttpMethod httpMethod, String httpPath, RequestCallBack<String> requestCallBack) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
        this.requestCallBack = requestCallBack;

        httpUtils = new HttpUtils();
        httpUtils.configRequestRetryCount(0);
    }

    public HttpRequest.HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setParams(RequestParams params) {
        this.params = params;
    }

    public RequestParams getParams() {
        return params;
    }

    public RequestCallBack<String> getRequestCallBack() {
        return requestCallBack;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public HttpHandler<String> getHttpHandler() {
        return httpHandler;
    }

    public void requestNetworkData() {
        if (httpMethod == null) {
            throw new NullPointerException("HttpMethod can not be empty!");
        }

        if (httpPath == null) {
            throw new NullPointerException("HttpPath can not be empty!");
        }

        if (requestCallBack == null) {
            throw new NullPointerException("RequestCallBack can not be empty!");
        }

        if (params == null) {
            httpHandler = httpUtils.send(httpMethod, httpPath, requestCallBack);
        } else {
            httpHandler = httpUtils.send(httpMethod, httpPath, params, requestCallBack);
        }
    }

}
