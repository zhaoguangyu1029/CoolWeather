package com.coolweather.app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.coolweather.app.receiver.AutoUpdateReceiver;
import com.coolweather.app.util.ApiUrlUtil;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.ResponseHandle;


public class AutoUpdateService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 60 * 60 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent intent1 = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent1, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherCode = prefs.getString("weather_code", "");
        HttpUtil.sendHttpRequest(ApiUrlUtil.getWeatherinfoUrl(weatherCode), new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                ResponseHandle.handleWeatherResponse(AutoUpdateService.this, response);
            }

            @Override
            public void onError(Exception error) {

            }
        });
    }
}
