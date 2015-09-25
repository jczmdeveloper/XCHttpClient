package com.czm.xchttpclient;

import java.io.InputStream;

/**
 * Created by caizhiming on 2015/9/15.
 */
public abstract class TextResponseCallback implements ResponseCallback{
    @Override
    public void onFilure(Request request,Exception result) {

    }
    public abstract void onSuccess(Request request,String result);
    @Override
    public void onSuccess(Request request,Object result) {
        StringBuilder sb = null;
        sb = TextUtils.inputStreamToString((InputStream) result);
        onSuccess(request,sb.toString());
    }
}
