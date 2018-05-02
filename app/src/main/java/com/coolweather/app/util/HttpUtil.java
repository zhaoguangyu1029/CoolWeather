package com.coolweather.app.util;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtil {

    public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.d("WQL", "HttpUtil address: " + address);
                HttpURLConnection connection = null;
                InputStream is = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setUseCaches(false);
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    StringBuilder response = new StringBuilder();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    is = connection.getInputStream();
                    byte[] buffer = new byte[1024];
                    int length = 0;
                    try {
                        while ((length = is.read(buffer)) != -1) {
                            baos.write(buffer, 0, length);
                        }
                    } catch (EOFException e) {
                        LogUtil.e("WQL", e.getMessage());
                    } finally {
                        new CloseableUtil(is);
                    }
                    response.append(new String(baos.toByteArray()));
                    LogUtil.d("WQL", "HttpUtil response: " + response.toString());
                    if (null != listener) {
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e) {
                    LogUtil.e("WQL", "HttpUtil error msg: " + e.getMessage());
                    if (null != listener) {
                        listener.onError(e);
                    }
                } finally {
                    new CloseableUtil(is);
                    if (null != connection) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public interface HttpCallbackListener {
        void onFinish(String response);

        void onError(Exception error);
    }

}
