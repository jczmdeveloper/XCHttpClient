package com.czm.xchttpclient;
import android.content.Context;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by caizhiming on 2015/9/15.
 */
public class XCHttpClient {
    private static final String TAG = XCHttpClient.class.getSimpleName();
    private static XCHttpClient mInstance = null;
    private ExecutorService mThreadPool;
    public static XCHttpClient getInstance(){
        if(mInstance == null){
            synchronized (XCHttpClient.class){
                if(mInstance == null){
                    mInstance = new XCHttpClient();
                }
            }
        }
        return mInstance;
    }
    public XCHttpClient(){
        mThreadPool = Executors.newCachedThreadPool();
    }
    public void get(String strUrl, RequestParams params,
                    ResponseCallback callback){
        sendRequest("GET", getUrlWithQueryString(true, strUrl, null),null, callback);
    }
    public void post(String strUrl,RequestParams params,
                     ResponseCallback callback){
        sendRequest("POST", strUrl,getUrlWithQueryString(true, "", params), callback);
    }
    public void download(String strUrl,RequestParams params,
                    ResponseCallback callback){
        sendRequest("GET", getUrlWithQueryString(true, strUrl, null),null, callback);
    }
    private void sendRequest(String method,String strUrl,String postParams, ResponseCallback callback) {
        Request request = new Request(method,strUrl,null,callback);
        mThreadPool.execute(request);
    }
    public static String getUrlWithQueryString(boolean shouldEncodeUrl,
                                               String url, RequestParams params) {
        if (shouldEncodeUrl)
            url = url.replace(" ", "%20");
        if (params != null) {
            // Construct the query string and trim it, in case it
            // includes any excessive white spaces.
            String paramString = params.toString().trim();
            // Only add the query string if it isn't empty and it
            // isn't equal to '?'.
            if (!paramString.equals("") && !paramString.equals("?")) {
                url += url.contains("?") ? "&" : "?";
                url += paramString;
            }
        }
        Log.d(TAG, "url:" + url);
        return url;
    }

}
