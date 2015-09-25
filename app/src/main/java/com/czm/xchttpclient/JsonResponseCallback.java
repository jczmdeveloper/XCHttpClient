package com.czm.xchttpclient;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by caizhiming on 2015/9/15.
 */
public abstract class JsonResponseCallback<T> extends TextResponseCallback{

    public void onSuccessOnGet(Request request,T t){

    };
    @Override
    public void onSuccess(Request request,String result) {
        try {
            if(request.getMethod().equals("GET")){
                Gson gson = new Gson();
                //获取带有泛型的父类
                Type genType = getClass().getGenericSuperclass();
                Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                Class<T> clazz = (Class<T>) params[0];
                T t = gson.fromJson(result, clazz);
                onSuccessOnGet(request, t);
            }
        }catch (Exception e){
            e.printStackTrace();
            onFilure(request,e);
        }
    }

    @Override
    public void onFilure(Request request,Exception result) {
        super.onFilure(request,result);
    }
}
