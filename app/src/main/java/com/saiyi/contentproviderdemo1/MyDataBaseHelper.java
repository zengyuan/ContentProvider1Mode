package com.saiyi.contentproviderdemo1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * <pre>
 *     author : Finn
 *     e-mail : 892603597@qq.com
 *     time   : 2019/10/14
 *     desc   : https://www.cnblogs.com/finn21/
 * </pre>
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "KfbFinnSS.db";
    private static final int DATABASE_VERSION = 1;


    public static final String USER_ID = "userid_id";// 自动增长表id

    /***.用户名（存储车型af）**/
    public static final String TABLE_USER_AF = "table_user_af";
    public static final String USER_USER_AF = "car_user_name_af";//用户名
    public static final String USER_CAR_AZ_AF = "car_user_az_af";//字符分段
    public static final String USER_CAR_NAME_AF = "car_kfb_name_af";// 车名称
    public static final String USER_CAR_TYPE_AF = "car_user_cartype_af";// 车型号
    public static final String USER_CAR_DATE_AF = "car_kfb_date_af";// 年份
    public static final String USER_CAR_LENGM_AF = "car_kfb_lengmei_af";// 冷媒量


    public MyDataBaseHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

/** AF * */
        db.execSQL("CREATE TABLE " + TABLE_USER_AF + " (" + USER_ID + " INTEGER PRIMARY KEY," + USER_USER_AF + " INTEGER NOT NUll,"
                + USER_CAR_AZ_AF + " TEXT,"  + USER_CAR_NAME_AF + " TEXT," + USER_CAR_TYPE_AF
                + " TEXT," + USER_CAR_DATE_AF + " TEXT NOT NUll,"  + USER_CAR_LENGM_AF + " INTEGER default 0);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //数据库升级启动该方法

    }

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}
