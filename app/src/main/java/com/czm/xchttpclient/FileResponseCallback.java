package com.czm.xchttpclient;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by caizhiming on 2015/10/8.
 */
public abstract class FileResponseCallback implements ResponseCallback{
    private static final String TAG  = FileResponseCallback.class.getSimpleName();
    private String mTargetPath;
    private long mHasRead;
    public FileResponseCallback(String targetPath){
        super();
        mTargetPath = targetPath;
    }
    @Override
    public void onFailure(Request request, Exception result) {

    }
    @Override
    public void onSuccess(Request request, Object result) {
        InputStream is = ((InputStream) result);
        File file = new File(mTargetPath);
        if(file.exists()){
            file.delete();
        }
        Log.v("czm", "file path =" + file.getPath());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte buffer[] = new byte[4*1024];
            int len=-1;
            while ((len = is.read(buffer)) != -1) {
                fos.write(buffer,0,len);
                mHasRead += len;
                onDownloading(request.mTotal, mHasRead);
            }
            fos.flush();
            onDownloaded(request, file);
        } catch (FileNotFoundException e) {
            onFailure(request,e);
        } catch (IOException e) {
            onFailure(request,e);
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public abstract void onDownloaded(Request request, File response);
    public abstract  void onDownloading(long total,long current);
}
