package com.coolweather.app.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ResponseHandle {

    public synchronized static boolean handleProvinceResponse(CoolWeatherDB coolWeatherDB, String response) {
        if (!TextUtils.isEmpty(response) && null != coolWeatherDB) {
            String[] provinces = response.split(",");
            if (null != provinces && provinces.length > 0) {
                for (String p : provinces) {
                    String[] pArr = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(pArr[0]);
                    province.setProvinceName(pArr[1]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId) {
        if (!TextUtils.isEmpty(response) && null != coolWeatherDB) {
            String[] cities = response.split(",");
            if (null != cities && cities.length > 0) {
                for (String c : cities) {
                    String[] cArr = c.split("\\|");
                    City city = new City();
                    city.setCityCode(cArr[0]);
                    city.setCityName(cArr[1]);
                    city.setProvinceId(provinceId);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId) {
        if (!TextUtils.isEmpty(response) && null != coolWeatherDB) {
            String[] counties = response.split(",");
            if (null != counties && counties.length > 0) {
                for (String c : counties) {
                    String[] cArr = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(cArr[0]);
                    county.setCountyName(cArr[1]);
                    county.setCityId(cityId);
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city"),
                    weatherCode = weatherInfo.getString("cityid"),
                    temp1 = weatherInfo.getString("temp1"),
                    temp2 = weatherInfo.getString("temp2"),
                    weatherDesp = weatherInfo.getString("weather"),
                    publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2, weatherDesp, publishTime);
        } catch (Exception e) {
            LogUtil.e("WQL", "handleWeatherResponse error: " + e.getMessage());
        }
    }

    private static void saveWeatherInfo(Context context, String cityName, String weatherCode, String temp1, String temp2, String weatherDesp, String publishTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weatherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.apply();
    }

}
