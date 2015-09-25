package com.czm.xchttpclient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Created by caizhiming on 2015/9/15.
 */
public class Request implements Runnable{

    private String mMethod;
    protected String mUrl;
    protected String mPostParams;
    protected ResponseCallback mCallback;
    public Request(String method,String url, String postParams, ResponseCallback callback){
        mMethod = method;
        mUrl = url;
        mPostParams = postParams;
        mCallback = callback;
    }
    @Override
    public void run() {
        URL url = null;
        InputStream is = null;
        OutputStream os = null;
        int responseCode = -1;
        try {
            url = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(6 * 1000);
            conn.setRequestMethod(mMethod);
            //不使用缓存
            conn.setUseCaches(false);
            //设置头参数
            conn.setRequestProperty("Accept-Encoding", "gzip");
            if(mMethod.equals("POST")){
                conn.setDoOutput(true);
                os = conn.getOutputStream();
                if(mCallback instanceof ResponseCallback){
                    os.write(mPostParams.getBytes());
                }
            }
            responseCode = conn.getResponseCode();
            conn.connect();
            if(responseCode == 200){
                String encoding = conn.getContentEncoding();
                is = new BufferedInputStream(conn.getInputStream());
                if(encoding!=null && encoding.contains("gzip")){
                    is = new GZIPInputStream(is);
                }
                mCallback.onSuccess(this,is);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            mCallback.onFilure(this,e);
        } catch (ProtocolException e) {
            e.printStackTrace();
            mCallback.onFilure(this,e);
        } catch (IOException e) {
            e.printStackTrace();
            mCallback.onFilure(this,e);
        }
    }
    public String getMethod() {
        return mMethod;
    }

    public void setMethod(String mMethod) {
        this.mMethod = mMethod;
    }
}
