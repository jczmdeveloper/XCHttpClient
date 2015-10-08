package com.czm.xchttpclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by caizhiming on 2015/10/8.
 */
public abstract class BinaryResponseCallback implements ResponseCallback{
    @Override
    public void onFailure(Request request, Exception result) {

    }

    @Override
    public void onSuccess(Request request, Object result) {
        InputStream is = ((InputStream) result);
        onSuccess(request,toByteArray(is));
    }
    public static byte[] toByteArray(InputStream in) {
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024*4];
        int n=0;
        try {
            while((n=in.read(buffer)) != -1){
                out.write(buffer,0,n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
    public abstract void onSuccess(Request request,byte[] binaryData);
}
