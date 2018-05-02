package com.coolweather.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.coolweather.app.util.AssetsUtil;


public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

    public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String[] sqlArr = new String[]{"province.sql", "city.sql", "county.sql"};
        for (String sql : sqlArr) {
            sqLiteDatabase.execSQL(AssetsUtil.readFile(sql));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
