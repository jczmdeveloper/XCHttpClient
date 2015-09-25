package com.czm.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.czm.model.MyJsonData;
import com.czm.xchttpclient.JsonResponseCallback;
import com.czm.xchttpclient.R;
import com.czm.xchttpclient.Request;
import com.czm.xchttpclient.TextResponseCallback;
import com.czm.xchttpclient.XCHttpClient;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends Activity {

    private Context mContext;
    private TextView mResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        init();
    }
    private void init() {
        mResult = (TextView) findViewById(R.id.tv_result);
        findViewById(R.id.btn_get_string).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url  ="https://raw.githubusercontent.com/jczmdeveloper/XCHttpClient/master/data/jsondata.txt";
                for(int i = 0; i < 1;i++){
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
                        public void onFilure(Request request,Exception result) {
                            super.onFilure(request,result);
                        }
                    });
                }
            }
        });
        findViewById(R.id.btn_get_Json).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url  ="https://raw.githubusercontent.com/jczmdeveloper/XCHttpClient/master/data/jsondata.txt";
                for(int i = 0; i < 1;i++){
                    XCHttpClient.getInstance().get(url, null, new JsonResponseCallback<MyJsonData>() {

                        @Override
                        public void onSuccessOnGet(Request request, final MyJsonData data) {
                            super.onSuccessOnGet(request, data);
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String s = "";
                                    s += "name:"+data.persons.get(0).getName();
                                    s += "\r\naddress:"+data.persons.get(0).getAddress();
                                    mResult.setText(s);

                                }
                            });
                        }

                        @Override
                        public void onFilure(Request request,Exception result) {
                            super.onFilure(request, result);
                            Log.v("czm", "onFailuer");
                        }
                    });
                }
            }
        });
    }
}
