# XCHttpClient
Android Http网络开发框架（非第三方）

该框架是本人基于HttpConnection开发的一套 类似于android-async-http 和Volley那样的快速网络开发框架

支持同步，异步方式的请求；
支持GET，POST请求；


使用方法：

1、请求文本内容：

String url  ="https://raw.githubusercontent.com/jczmdeveloper/XCHttpClient/master/data/jsondata.txt";

        RequestParams params = new RequestParams();
        
        params.put("a","3");
        params.put("b",64);
        XCHttpClient.getInstance().post(url, params, new TextResponseCallback() {
            
            @Override
            public void onSuccess(Request request, final String result) {
                Log.v("czm", "result=" + result);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mResult.setText(result);
                        Log.v("czm", "result=" + result);
                    }
                });
            }

            @Override
            public void onFailure(Request request, Exception result) {
                super.onFailure(request, result);
            }
        });

2、请求Json数据：

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
        
        
3、请求二进制数据：

String url  ="https://raw.githubusercontent.com/jczmdeveloper/XCHttpClient/master/screenshots/img1080_1920.jpg";

        XCHttpClient.getInstance().get(url, null, new BinaryResponseCallback() {
            @Override
            public void onSuccess(Request request,   final byte[] binaryData) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    
                        InputStream is = new ByteArrayInputStream(binaryData);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        mImageView.setImageBitmap(bitmap);
                        mImageView.setVisibility(View.VISIBLE);

                    }
                });
            }

            @Override
            public void onFailure(Request request, Exception result) {
                super.onFailure(request, result);
            }
        });
4、下载文件：

String url = "https://raw.githubusercontent.com/jczmdeveloper/XCHttpClient/master/screenshots/img2880_5120.jpg";

        String targetPath = mContext.getFilesDir().getPath() + "/"+"img01.jpg";
        mDownloading = true;
        XCHttpClient.getInstance().download(url,null,new FileResponseCallback(targetPath){
            @Override
            public void onDownloaded(Request request, File response) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    final Bitmap bitmap = BitmapFactory.decodeFile(response.getAbsolutePath(),options);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mImageView.setImageBitmap(bitmap);
                            mImageView.setVisibility(View.VISIBLE);
                        }
                    });
                mDownloading = false;
            }


            @Override
            public void onDownloading(final long total, final long current) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadFile.setText("downloadFile 正在下载：" +current * 100/total + "%");
                    }
                });
            }
        });
5、上传文件：

String url = "http://raw.githubusercontent.com/jczmdeveloper/XCHttpClient/master/data/jsondata.txt";

        RequestParams params = new RequestParams();
        String path = mContext.getFilesDir().getPath() + "/"+"img01.jpg";
        File file = new File(path);
        XCHttpClient.getInstance().upload(url, params, new FileResponseCallback(path) {
            @Override
            public void onSuccess(Request request, Object result) {
                super.onSuccess(request, result);
                Log.v("czm","upload file completed");
            }


            @Override
            public void onFailure(Request request, Exception result) {
                super.onFailure(request, result);
                Log.v("czm", "upload file failed");
            }
        });
