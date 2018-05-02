
package com.coolweather.app.util;

import com.coolweather.app.activity.BaseApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class AssetsUtil {
    private AssetsUtil() {
    }

    public static String readFile(String name) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(BaseApplication.getContext().getAssets().open(name)));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LogUtil.e("WQL", "AssetsUtil error: " + e.getMessage());
        } finally {
            new CloseableUtil(reader);
        }
        return sb.toString();
    }
}
