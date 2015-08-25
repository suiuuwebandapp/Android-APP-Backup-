package com.minglang.suiuu.utils;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2015/8/25.
 * <p/>
 * OkHttp封装
 */
public class OkHttpManager {

    private static final String TAG = OkHttpManager.class.getSimpleName();

    private static OkHttpManager okHttpManager;

    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;

    private OkHttpManager() {
        mOkHttpClient = new OkHttpClient();
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    public static OkHttpManager getInstance() {
        if (okHttpManager == null) {
            synchronized (OkHttpManager.class) {
                if (okHttpManager == null) {
                    okHttpManager = new OkHttpManager();
                }
            }
        }
        return okHttpManager;
    }

    /**
     * Get同步请求
     *
     * @param url 接口URL
     * @return Response
     * @throws IOException
     */
    private Response getSyncRequest(String url) throws IOException {
        final Request request = new Request.Builder().url(url).build();
        Call call = mOkHttpClient.newCall(request);
        return call.execute();
    }

    /**
     * Get同步请求
     *
     * @param url 接口URL
     * @return 字符串
     * @throws IOException
     */
    private String getSyncRequestString(String url) throws IOException {
        return getSyncRequest(url).body().string();
    }

    /**
     * Get异步请求
     *
     * @param url      接口URL
     * @param callback 回调接口
     */
    private void getAsynRequest(String url, final ResultCallback<Object> callback) {
        final Request request = new Request.Builder().url(url).build();
        deliveryResult(callback, request);
    }

    //##########################################################################################################################\\

    /**
     * POST同步请求
     *
     * @param url    接口URL
     * @param params 参数列表
     * @return Response
     * @throws IOException
     */
    private Response postSyncRequest(String url, Params... params) throws IOException {
        Request request = buildPostRequest(url, params);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * POST同步请求 返回String数据
     *
     * @param url    接口URL
     * @param params 参数列表
     * @return String
     * @throws IOException
     */
    private String postSyncRequestString(String url, Params... params) throws IOException {
        return postSyncRequest(url, params).body().string();
    }

    //##########################################################################################################################\\

    /**
     * POST异步请求
     *
     * @param url      接口URL
     * @param callback 回调接口
     * @param params   参数列表
     */
    private void postAsynRequest(String url, final ResultCallback callback, Params... params) {
        Request request = buildPostRequest(url, params);
        deliveryResult(callback, request);
    }

    /**
     * POST异步请求,参数名与参数值放在Map中
     *
     * @param url      接口URL
     * @param callback 回调接口
     * @param params   Map参数集合(key是参数名，value是参数值)
     */
    private void postAsynRequest(String url, final ResultCallback callback, Map<String, String> params) {
        Request request = buildPostRequest(url, Map2Params(params));
        deliveryResult(callback, request);
    }

    //##########################################################################################################################\\

    /**
     * post同步上传多个文件，有参数
     *
     * @param url      接口URL
     * @param files    文件列表
     * @param fileKeys 文件名称
     * @param params   参数列表
     * @throws IOException
     */
    private Response postSyncUploadFile(String url, File[] files, String[] fileKeys, Params... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * post同步上传单个文件，无参数
     *
     * @param url     接口URL
     * @param file    文件列表
     * @param fileKey 文件名称
     * @return Response
     * @throws IOException
     */
    private Response postSyncUploadFile(String url, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        return mOkHttpClient.newCall(request).execute();
    }

    /**
     * post同步上传单个文件，有参数
     *
     * @param url     接口URL
     * @param file    文件列表
     * @param fileKey 文件名称
     * @param params  参数列表
     * @return Response
     * @throws IOException
     */
    private Response postSyncUploadFile(String url, File file, String fileKey, Params... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        return mOkHttpClient.newCall(request).execute();
    }

    //##########################################################################################################################\\

    /**
     * post异步上传多个文件，有参数
     *
     * @param url      接口URL
     * @param callback 回调接口
     * @param files    文件列表
     * @param fileKeys 文件名称
     * @param params   参数列表
     * @throws IOException
     */
    private void postAsynUploadFile(String url, ResultCallback callback, File[] files, String[] fileKeys, Params... params) throws IOException {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliveryResult(callback, request);
    }

    /**
     * post异步上传单个文件，无参数
     *
     * @param url      接口URL
     * @param callback 回调接口
     * @param file     文件
     * @param fileKey  文件名称
     * @throws IOException
     */
    private void postAsynUploadFile(String url, ResultCallback callback, File file, String fileKey) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        deliveryResult(callback, request);
    }

    /**
     * post异步上传单文件且携带其他form参数上传
     *
     * @param url      接口URL
     * @param callback 回调接口
     * @param file     文件
     * @param fileKey  文件名称
     * @param params   参数
     * @throws IOException
     */
    private void postAsynUploadFile(String url, ResultCallback callback, File file, String fileKey, Params... params) throws IOException {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliveryResult(callback, request);
    }

    //##########################################################################################################################\\

    /**
     * 异步下载文件
     *
     * @param url         接口URL
     * @param destFileDir 本地文件存储的文件夹
     * @param callback    回调接口
     */
    private void asynchronousDownloadFile(final String url, final String destFileDir, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();

        final Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onFailure(final Request request, final IOException e) {
                onFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    onSuccessResultCallback(file.getAbsolutePath(), callback);
                } catch (IOException e) {
                    onFailedStringCallback(response.request(), e, callback);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                        DeBugLog.e(TAG, "IOException:" + e.getMessage());
                    }

                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                        DeBugLog.e(TAG, "IOException:" + e.getMessage());
                    }
                }

            }

        });

    }


    //***********************************************对外公布的方法*****************************************************************\\

    /**
     * Get同步请求
     *
     * @param url 接口URL
     * @return Response
     * @throws IOException
     */
    public static Response onGetSyncRequest(String url) throws IOException {
        return getInstance().getSyncRequest(url);
    }

    /**
     * Get同步请求
     *
     * @param url 接口URL
     * @return String
     * @throws IOException
     */
    public static String onGetSyncRequestString(String url) throws IOException {
        return getInstance().getSyncRequestString(url);
    }

    /**
     * Get异步请求
     *
     * @param url      接口URL
     * @param callback 回调接口
     */
    public static void onGetAsynRequest(String url, ResultCallback callback) throws IOException {
        getInstance().getAsynRequest(url, callback);
    }

    //##########################################################################################################################\\

    /**
     * POST同步请求
     *
     * @param url    接口URL
     * @param params 参数列表
     * @throws IOException
     */
    public static void onPostSyncRequest(String url, Params... params) throws IOException {
        getInstance().postSyncRequest(url, params);
    }

    /**
     * POST同步请求 返回String数据
     *
     * @param url    接口URL
     * @param params 参数列表
     * @throws IOException
     */
    public static void onPostSyncRequestString(String url, Params params) throws IOException {
        getInstance().postSyncRequestString(url, params);
    }

    //##########################################################################################################################\\

    /**
     * POST异步请求
     *
     * @param url      接口URL
     * @param callback 回调接口
     * @param params   参数列表
     */
    public static void onPostAsynRequest(String url, ResultCallback callback, Params... params) throws IOException {
        getInstance().postAsynRequest(url, callback, params);
    }

    /**
     * POST异步请求,参数名与参数值放在Map中
     *
     * @param url      接口URL
     * @param callback 回调接口
     * @param params   Map参数集合(key是参数名，value是参数值)
     */
    public static void onPostAsynRequest(String url, final ResultCallback callback, Map<String, String> params) throws IOException {
        getInstance().postAsynRequest(url, callback, params);
    }

    //##########################################################################################################################\\

    /**
     * post同步上传多个文件，有参数
     *
     * @param url      接口URL
     * @param files    文件列表
     * @param fileKeys 文件名称
     * @param params   参数列表
     * @throws IOException
     */
    public static void onPostSyncUploadFile(String url, File[] files, String[] fileKeys, Params... params) throws IOException {
        getInstance().postSyncUploadFile(url, files, fileKeys, params);
    }

    /**
     * post同步上传单个文件，无参数
     *
     * @param url     接口URL
     * @param file    文件列表
     * @param fileKey 文件名称
     * @throws IOException
     */
    public static void onPostSyncUploadFile(String url, File file, String fileKey) throws IOException {
        getInstance().postSyncUploadFile(url, file, fileKey);
    }

    /**
     * post同步上传单个文件，有参数
     *
     * @param url     接口URL
     * @param file    文件列表
     * @param fileKey 文件名称
     * @param params  参数列表
     * @throws IOException
     */
    public static void onPostSyncUploadFile(String url, File file, String fileKey, Params... params) throws IOException {
        getInstance().postSyncUploadFile(url, file, fileKey, params);
    }

    //##########################################################################################################################\\

    /**
     * post异步上传多个文件，有参数
     *
     * @param url      接口URL
     * @param callback 回调接口
     * @param files    文件列表
     * @param fileKeys 文件名称
     * @param params   参数列表
     * @throws IOException
     */
    public static void onPostAsynUploadFile(String url, ResultCallback callback, File[] files, String[] fileKeys, Params... params) throws IOException {
        getInstance().postAsynUploadFile(url, callback, files, fileKeys, params);
    }

    /**
     * post异步上传单个文件，无参数
     *
     * @param url      接口URL
     * @param callback 回调接口
     * @param file     文件
     * @param fileKey  文件名称
     * @throws IOException
     */
    public static void onPostAsynUploadFile(String url, ResultCallback callback, File file, String fileKey) throws IOException {
        getInstance().postAsynUploadFile(url, callback, file, fileKey);
    }

    /**
     * post异步上传单文件且携带其他form参数上传
     *
     * @param url      接口URL
     * @param callback 回调接口
     * @param file     文件
     * @param fileKey  文件名称
     * @param params   参数
     * @throws IOException
     */
    public static void onPostAsynUploadFile(String url, ResultCallback callback, File file, String fileKey, Params... params) throws IOException {
        getInstance().postAsynUploadFile(url, callback, file, fileKey, params);
    }

    //########################################################################################################################################\\

    /**
     * 异步下载文件
     *
     * @param url         接口URL
     * @param destFileDir 本地文件存储的文件夹
     * @param callback    回调接口
     */
    public static void onAsynDownloadFile(final String url, final String destFileDir, final ResultCallback callback) {
        getInstance().asynchronousDownloadFile(url, destFileDir, callback);
    }

    //****************************************************************************************************************************\\

    private void deliveryResult(final ResultCallback callback, Request request) {

        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(final Request request, final IOException e) {
                onFailedStringCallback(request, e, callback);
            }

            @Override
            public void onResponse(final Response response) {
                try {
                    final String string = response.body().string();

                    if (callback.mType == String.class) {
                        onSuccessResultCallback(string, callback);
                    } else {
                        Object o = mGson.fromJson(string, callback.mType);
                        onSuccessResultCallback(o, callback);
                    }

                } catch (IOException | JsonParseException e) {
                    onFailedStringCallback(response.request(), e, callback);
                }

            }

        });

    }

    private void onSuccessResultCallback(final Object object, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onResponse(object);
                }
            }
        });
    }

    private void onFailedStringCallback(final Request request, final Exception e, final ResultCallback callback) {
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null)
                    callback.onError(request, e);
            }
        });
    }

    private static final String CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * 构建多文件上传请求
     *
     * @param url      接口URL
     * @param files    文件列表
     * @param fileKeys 文件名
     * @param params   参数
     * @return Request
     */
    private Request buildMultipartFormRequest(String url, File[] files, String[] fileKeys, Params[] params) {
        params = validateParam(params);

        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

        for (Params param : params) {
            builder.addPart(Headers.of(CONTENT_DISPOSITION, "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }

        if (files != null) {
            RequestBody fileBody;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of(CONTENT_DISPOSITION,
                                "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        return new Request.Builder().url(url).post(builder.build()).build();
    }

    /**
     * 构建POST请求
     *
     * @param url    接口URL
     * @param params 参数列表
     * @return Request
     */
    private Request buildPostRequest(String url, Params[] params) {
        if (params == null) {
            params = new Params[0];
        }

        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Params param : params) {
            builder.add(param.key, param.value);
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**
     * Map转数组
     *
     * @param params map集合
     * @return Params数组
     */
    private Params[] Map2Params(Map<String, String> params) {
        if (params == null) return new Params[0];

        int size = params.size();
        Params[] res = new Params[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Params(entry.getKey(), entry.getValue());
        }
        return res;
    }

    private Params[] validateParam(Params[] params) {
        if (params == null)
            return new Params[0];
        else return params;
    }

    /**
     * 推测文件类型
     *
     * @param path 文件地址
     * @return 文件类型
     */
    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     * 获取文件名
     *
     * @param path 文件URL
     * @return 文件名
     */
    private String getFileName(String path) {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    /**
     * 回调接口基类
     *
     * @param <T>
     */
    public static abstract class ResultCallback<T> {

        Type mType;

        public ResultCallback() {
            mType = getSuperclassTypeParameter(getClass());
        }

        static Type getSuperclassTypeParameter(Class<?> subclass) {
            Type superclass = subclass.getGenericSuperclass();
            if (superclass instanceof Class) {
                throw new RuntimeException("Missing type parameter.");
            }
            ParameterizedType parameter = (ParameterizedType) superclass;
            return $Gson$Types.canonicalize(parameter.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request, Exception e);

        public abstract void onResponse(T response);

    }

    /**
     * 参数基类
     */
    public static class Params {

        String key;
        String value;

        public Params(String key, String value) {
            this.key = key;
            this.value = value;
        }

    }

}