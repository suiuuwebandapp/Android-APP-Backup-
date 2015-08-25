package com.minglang.suiuu.utils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 封装网络请求方法
 * <p/>
 * Created by Administrator on 2015/4/23.
 */
public class SuiuuHttp {

    /**
     * 网络请求核心类
     */
    private HttpUtils httpUtils;

    /**
     * 网络请求类型
     * <p/>
     * GET、POST等
     */
    private HttpMethod httpMethod;

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

    public SuiuuHttp(){}

    public SuiuuHttp(HttpMethod httpMethod, String httpPath, RequestCallBack<String> requestCallBack) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
        this.requestCallBack = requestCallBack;

        httpUtils = new HttpUtils();
    }

    public void setParams(RequestParams params) {
        this.params = params;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    public void setRequestCallBack(RequestCallBack<String> requestCallBack) {
        this.requestCallBack = requestCallBack;
    }

    public void executive() {
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
            httpUtils.send(httpMethod, httpPath, requestCallBack);
        } else {
            httpUtils.send(httpMethod, httpPath, params, requestCallBack);
        }
    }

}