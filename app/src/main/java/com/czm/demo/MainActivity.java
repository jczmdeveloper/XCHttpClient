package com.czm.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.czm.model.MyJsonData;
import com.czm.xchttpclient.BinaryResponseCallback;
import com.czm.xchttpclient.JsonResponseCallback;
import com.czm.xchttpclient.R;
import com.czm.xchttpclient.Request;
import com.czm.xchttpclient.TextResponseCallback;
import com.czm.xchttpclient.XCHttpClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MainActivity extends Activity implements View.OnClickListener {

    private Context mContext;
    private TextView mResult;
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        mResult = (TextView) findViewById(R.id.tv_result);
        findViewById(R.id.btn_get_string).setOnClickListener(this);
        findViewById(R.id.btn_get_Json).setOnClickListener(this);
        findViewById(R.id.btn_get_BinaryData).setOnClickListener(this);
        mImageView  = (ImageView)findViewById(R.id.iv_test);
    }

    @Override
    public void onClick(View v) {
        mImageView.setVisibility(View.GONE);
        switch (v.getId()){
            case R.id.btn_get_string:
                getString();
                break;
            case R.id.btn_get_Json:
                getJson();
                break;
            case R.id.btn_get_BinaryData:
                getBinaryData();
                break;
        }
    }

    private void getBinaryData() {
        String url  ="http://i5.download.fd.pchome.net/g1/M00/11/0E/oYYBAFX7zvGIW1NkAANru6u7_vwAACsaAKUVYkAA2vT184.jpg";
        XCHttpClient.getInstance().get(url, null, new BinaryResponseCallback() {
            @Override
            public void onSuccess(Request request,   final byte[] binaryData) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResult.setText(binaryData.toString());
                        InputStream is = new ByteArrayInputStream(binaryData);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        mImageView.setImageBitmap(bitmap);
                        mImageView.setVisibility(View.VISIBLE);
                        Log.v("czm", "result="+binaryData.toString());
                    }
                });
            }

            @Override
            public void onFailure(Request request, Exception result) {
                super.onFailure(request, result);
            }
        });
    }

    private void getString(){
        String url  ="https://raw.githubusercontent.com/jczmdeveloper/XCHttpClient/master/data/jsondata.txt";
        XCHttpClient.getInstance().get(url, null, new TextResponseCallback() {
            @Override
            public void onSuccess(Request request,final String result) {
                Log.v("czm","result="+result);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResult.setText(result);
                        Log.v("czm","result="+result);
                    }
                });
            }
            @Override
            public void onFailure(Request request, Exception result) {
                super.onFailure(request, result);
            }
        });
    }
    private void getJson(){
        String url = "https://raw.githubusercontent.com/jczmdeveloper/XCHttpClient/master/data/jsondata.txt";
        XCHttpClient.getInstance().get(url, null, new JsonResponseCallback<MyJsonData>() {

            @Override
            public void onSuccessOnGet(Request request, final MyJsonData data) {
                super.onSuccessOnGet(request, data);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s = "";
                        s += "name:" + data.persons.get(0).getName();
                        s += "\r\naddress:" + data.persons.get(0).getAddress();
                        mResult.setText(s);

                    }
                });
            }

            @Override
            public void onFailure(Request request, Exception result) {
                super.onFailure(request, result);
                Log.v("czm", "onFailuer");
            }
        });
    }
}
