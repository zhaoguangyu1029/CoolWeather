package com.coolweather.app.util;


public final class ApiUrlUtil {
    private static final String PLACEHOLDER = "<?>";
    private static final String CITY_URL = "http://www.weather.com.cn/data/list3/city<?>.xml";
    private static final String WEATHERCODE_URL = "http://www.weather.com.cn/data/list3/city<?>.xml";
    private static final String WEATHERINFO_URL = "http://www.weather.com.cn/data/cityinfo/<?>.html";

    public static String getCityUrl(String code) {
        return CITY_URL.replace(PLACEHOLDER, null2Empty(code));
    }

    public static String getWeathercodeUrl(String code) {
        return WEATHERCODE_URL.replace(PLACEHOLDER, null2Empty(code));
    }

    public static String getWeatherinfoUrl(String code) {
        return WEATHERINFO_URL.replace(PLACEHOLDER, null2Empty(code));
    }

    private static String null2Empty(String code) {
        return null == code ? "" : code;
    }
}
