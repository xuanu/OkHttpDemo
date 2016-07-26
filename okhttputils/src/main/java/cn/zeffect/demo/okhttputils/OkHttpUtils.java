package cn.zeffect.demo.okhttputils;

import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zeffect on 2016/7/25.
 */
public class OkHttpUtils {
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    public static final int PROGRESS_WHAT = 38;
    private static final int SUCCESS = 1;
    private static final int FAILE = -1;

    /**
     * GET请求
     *
     * @param pUrl
     * @param pHeader
     * @param pParam
     * @param pHandler 为null同步请求，不为null异步
     * @return
     */
    public static String get(String pUrl, Map<String, String> pHeader, Map<String, String> pParam, final Handler pHandler) {
        if (TextUtils.isEmpty(pUrl)) {
            return "empty url";
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).build();
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.headers(appendHeaders(pHeader));
        Request request = requestBuilder.url(appendGetParams(pUrl, pParam)).get().build();
        if (pHandler == null) {
            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Unexpected code " + response;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        } else {
            okHttpClient.newCall(request).enqueue(new Callback() {
                                                      @Override
                                                      public void onFailure(Call call, IOException e) {
                                                          if (pHandler != null) {
                                                              Message message = pHandler.obtainMessage();
                                                              message.what = FAILE;
                                                              message.obj = e.getMessage();
                                                              pHandler.sendMessage(message);
                                                          }
                                                      }

                                                      @Override
                                                      public void onResponse(Call call, Response response) throws IOException {
                                                          if (pHandler != null) {
                                                              Message message = pHandler.obtainMessage();
                                                              message.what = SUCCESS;
                                                              message.obj = response.body().string();
                                                              pHandler.sendMessage(message);
                                                          }
                                                      }
                                                  }

            );
            return "";
        }
    }

    /**
     * @param pUrl
     * @param pHeader
     * @param pParam
     * @param pHandler 为null同步请求，不为null异步
     * @return
     */
    public static String postParam(String pUrl, Map<String, String> pHeader, Map<String, String> pParam, final Handler pHandler) {
        if (TextUtils.isEmpty(pUrl)) {
            return "empty url";
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).build();
        RequestBody requestBody = appendPostParams(pParam);
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.headers(appendHeaders(pHeader));
        Request request = requestBuilder.url(pUrl).headers(appendHeaders(pHeader)).post(requestBody).build();
        if (pHandler == null) {
            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Unexpected code " + response;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        } else {
            okHttpClient.newCall(request).enqueue(new Callback() {
                                                      @Override
                                                      public void onFailure(Call call, IOException e) {
                                                          if (pHandler != null) {
                                                              Message message = pHandler.obtainMessage();
                                                              message.what = FAILE;
                                                              message.obj = e.getMessage();
                                                              pHandler.sendMessage(message);
                                                          }
                                                      }

                                                      @Override
                                                      public void onResponse(Call call, Response response) throws IOException {
                                                          if (pHandler != null) {
                                                              Message message = pHandler.obtainMessage();
                                                              message.what = SUCCESS;
                                                              message.obj = response.body().string();
                                                              pHandler.sendMessage(message);
                                                          }
                                                      }
                                                  }

            );
            return "";
        }
    }

    /**
     * postJson
     * @param pUrl
     * @param pHeader
     * @param pJsonData
     * @param pHandler 为null同步请求，不为null异步
     * @return
     */
    public static String postJson(String pUrl, Map<String, String> pHeader, String pJsonData, final Handler pHandler) {
        if (TextUtils.isEmpty(pUrl)) {
            return "empty url";
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).build();
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, pJsonData);
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.headers(appendHeaders(pHeader));
        Request request = requestBuilder.url(pUrl).headers(appendHeaders(pHeader)).post(requestBody).build();
        if (pHandler == null) {
            try {
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Unexpected code " + response;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        } else {
            okHttpClient.newCall(request).enqueue(new Callback() {
                                                      @Override
                                                      public void onFailure(Call call, IOException e) {
                                                          if (pHandler != null) {
                                                              Message message = pHandler.obtainMessage();
                                                              message.what = FAILE;
                                                              message.obj = e.getMessage();
                                                              pHandler.sendMessage(message);
                                                          }
                                                      }

                                                      @Override
                                                      public void onResponse(Call call, Response response) throws IOException {
                                                          if (pHandler != null) {
                                                              Message message = pHandler.obtainMessage();
                                                              message.what = SUCCESS;
                                                              message.obj = response.body().string();
                                                              pHandler.sendMessage(message);
                                                          }
                                                      }
                                                  }

            );
            return "";
        }
    }

    /**
     *
     * @param pUrl
     * @param pHeader
     * @param pParam
     * @param pFiles
     * @param pHandler 为null同步请求，不为null异步
     * @return
     */
    public static String upload(String pUrl, Map<String, String> pHeader, Map<String, String> pParam, Map<String, File> pFiles, final Handler pHandler) {
        if (TextUtils.isEmpty(pUrl)) {
            return "empty url";
        }
        OkHttpClient client =  new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).build();
        RequestBody requestBody = appendUploadParams(pParam, pFiles);
        Request request = new Request.Builder()
                .headers(appendHeaders(pHeader))
                .url(pUrl)
                .post(requestBody)
                .build();
        if (pHandler == null) {
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "Unexpected code " + response;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        } else {
            client.newCall(request).enqueue(new Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    if (pHandler != null) {
                                                        Message message = pHandler.obtainMessage();
                                                        message.what = FAILE;
                                                        message.obj = e.getMessage();
                                                        pHandler.sendMessage(message);
                                                    }
                                                }

                                                @Override
                                                public void onResponse(Call call, Response response) throws IOException {
                                                    if (pHandler != null) {
                                                        Message message = pHandler.obtainMessage();
                                                        message.what = SUCCESS;
                                                        message.obj = response.body().string();
                                                        pHandler.sendMessage(message);
                                                    }
                                                }
                                            }

            );
            return "";
        }

    }

    /**
     *
     * @param url
     * @param pFilePath
     * @param pFileName
     * @param pHandler 为null同步请求，不为null异步
     * @return
     */
    public static String download(String url, final String pFilePath, final String pFileName, final Handler pHandler) {
        if (TextUtils.isEmpty(url)) {
            return "empty url";
        }
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(60,TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(url).build();
        if (pHandler == null) {
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    return "unexpected code " + response;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.getMessage();
            }
        } else {
            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    if (pHandler != null) {
                        Message message = pHandler.obtainMessage();
                        message.what = FAILE;
                        message.obj = e.getMessage();
                        pHandler.sendMessage(message);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        is = response.body().byteStream();
                        long total = response.body().contentLength();
                        File file = new File(pFilePath, pFileName);
                        fos = new FileOutputStream(file);
                        long sum = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            sum += len;
                            int progress = (int) (sum * 1.0f / total * 100);
                            //
                            if (pHandler != null) {
                                Message msg = pHandler.obtainMessage();
                                msg.what = PROGRESS_WHAT;
                                msg.arg1 = progress;
                                pHandler.sendMessage(msg);
                            }
                        }
                        if (pHandler != null) {
                            Message msg = pHandler.obtainMessage();
                            msg.what = SUCCESS;
                            msg.obj = file.getAbsolutePath();
                            pHandler.sendMessage(msg);
                        }
                        fos.flush();
                    } catch (Exception e) {
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e) {
                        }
                        try {
                            if (fos != null)
                                fos.close();
                        } catch (IOException e) {
                        }
                    }
                }

            });
        }
        return "";
    }


    /**
     * 添加头参数
     *
     * @param pHeader
     * @return
     */
    private static Headers appendHeaders(Map<String, String> pHeader) {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (pHeader == null || pHeader.isEmpty()) pHeader = new HashMap<>();

        for (String key : pHeader.keySet()) {
            headerBuilder.add(key, pHeader.get(key));
        }
        return headerBuilder.build();
    }

    /**
     * 添加Get参数
     *
     * @param url
     * @param params
     * @return
     */
    private static String appendGetParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }

    /**
     * 添加Post参数
     *
     * @param params
     * @return
     */
    private static RequestBody appendPostParams(Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        FormBody.Builder builder = new FormBody.Builder();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.add(key, params.get(key));
        }
        return builder.build();
    }

    /**
     * 添加Upload参数
     *
     * @param params
     * @return
     */
    private static RequestBody appendUploadParams(Map<String, String> params, Map<String, File> pFiles) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (params != null) {
            Set<String> keys = params.keySet();
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                builder.addFormDataPart(key, params.get(key));
            }
        }
        if (pFiles != null) {
            RequestBody fileBody = null;
            Set<String> pFileskeys = pFiles.keySet();
            Iterator<String> pFilesiterator = pFileskeys.iterator();
            while (pFilesiterator.hasNext()) {
                String key = pFilesiterator.next();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(key)), pFiles.get(key));
                builder.addPart(Headers.of("Content-Disposition",
                                "form-data; name=\"" + key + "\"; filename=\"" + pFiles.get(key).getName() + "\""),
                        fileBody);
            }
        }

        return builder.build();
    }

    private static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
