# XCHttpClient
Android Http网络开发框架（非第三方）

该框架是本人基于HttpConnection开发的一套 类似于android-async-http 和Volley那样的快速网络开发框架
目前该框架支持 文本Get，Json Get请求，文件下载，以及常见的Post请求等Http，支持参数请求。

使用方法如下：
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
                    
