package com.czm.xchttpclient;

import java.io.InputStream;

/**
 * Created by caizhiming on 2015/10/8.
 */
public class FileResponseCallback implements ResponseCallback{
    @Override
    public void onFailure(Request request, Exception result) {

    }

    @Override
    public void onSuccess(Request request, Object result) {
        InputStream is = ((InputStream) result);
    }
}
