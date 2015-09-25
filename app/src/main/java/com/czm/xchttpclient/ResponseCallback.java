package com.czm.xchttpclient;

/**
 * Created by caizhiming on 2015/9/15.
 */
public interface ResponseCallback {
    void onFilure(Request request,Exception result);
    void onSuccess(Request request,Object result);
}
