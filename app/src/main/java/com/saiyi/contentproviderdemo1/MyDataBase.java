package com.saiyi.contentproviderdemo1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


/**
 * <pre>
 *     author : Finn
 *     e-mail : 892603597@qq.com
 *     time   : 2019/10/14
 *     desc   : https://www.cnblogs.com/finn21/
 * </pre>
 */
public class MyDataBase {

    private static final String TAG = MyDataBase.class.getSimpleName();
    private static MyDataBaseHelper databaseHelper;
    private static MyDataBase myDisplacementBase;
    private static String USER_NAME = "13006602877";
    private static String TABLE_USER_NAME = "KFB";


    private MyDataBase(Context context) {
        databaseHelper = new MyDataBaseHelper(context);
    }

    public static MyDataBase getInstance(Context context) {
        if (myDisplacementBase == null) {
            myDisplacementBase = new MyDataBase(context.getApplicationContext());
        }
        return myDisplacementBase;
    }

    /**
     * 关闭当前的db
     *
     * @param db
     */
    synchronized void closeSQLDB(SQLiteDatabase db) {
        SQLiteDatabase.releaseMemory();
        if (db != null && db.isOpen()) {
            db.close();
            db = null;
        }
    }

    /**
     * 通知本地数据库有变化
     */
    public interface InotifyDBhasChange {
        int CODE_ERR_REPET = 0;
        void onChange(Object... obj);
        void err(int... code);
    }

    /**
     * --------------------------------------添加车名AF---------------------------------------
     */
    public synchronized void InsertCarAF(CarInfo info) {
        SQLiteDatabase writeDB = databaseHelper.getWritableDatabase();
        Cursor cursor = writeDB.query(MyDataBaseHelper.TABLE_USER_AF,
                new String[]{MyDataBaseHelper.USER_ID},
                MyDataBaseHelper.USER_USER_AF + " = ? ",
                new String[]{TABLE_USER_NAME}, null, null, null);

        cursor.close();

        ContentValues cv = new ContentValues();
        cv.put(MyDataBaseHelper.USER_USER_AF, TABLE_USER_NAME);
        cv.put(MyDataBaseHelper.USER_CAR_AZ_AF, info.getAz());
        cv.put(MyDataBaseHelper.USER_CAR_NAME_AF, info.getCarName());
        cv.put(MyDataBaseHelper.USER_CAR_TYPE_AF, info.getCarType());
        cv.put(MyDataBaseHelper.USER_CAR_DATE_AF, info.getCarDate());
        cv.put(MyDataBaseHelper.USER_CAR_LENGM_AF, info.getCarLengM());
        try {
            writeDB.insert(MyDataBaseHelper.TABLE_USER_AF, null, cv);//插入数据
        } catch (Exception e) {
            return;
        } finally {
            closeSQLDB(writeDB);
        }
    }

    /**
     * 查询所有用户
     */
    public synchronized List<CarInfo> QueryListAF() {
        List<CarInfo> list = new ArrayList<>();
        SQLiteDatabase readDB = databaseHelper.getReadableDatabase();
        Cursor cursor = readDB.query(MyDataBaseHelper.TABLE_USER_AF,
                null, MyDataBaseHelper.USER_USER_AF + " = ? ",
                new String[]{TABLE_USER_NAME}, null,
                null, MyDataBaseHelper.USER_ID, null);
        try {
//            int user = cursor.getColumnIndex(MyDataBaseHelper.USER_USER_AF);
            int pswd = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_AZ_AF);
            int icon = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_NAME_AF);
            int nickname = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_TYPE_AF);
            int vertion = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_DATE_AF);
            int veron = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_LENGM_AF);
            while (cursor.moveToNext()) {
                CarInfo info = new CarInfo(
                        cursor.getString(pswd),
                        cursor.getString(icon),
                        cursor.getString(nickname),
                        cursor.getString(vertion),
                        cursor.getString(veron)
                );
                list.add(info);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeSQLDB(readDB);
        }
        return list;
    }
    public Cursor cursor;
    public SQLiteDatabase readDB;
    public synchronized Cursor QueryCursor() {
        readDB = databaseHelper.getReadableDatabase();
        cursor = readDB.query(MyDataBaseHelper.TABLE_USER_AF,
                null, MyDataBaseHelper.USER_USER_AF + " = ? ",
                new String[]{TABLE_USER_NAME}, null,
                null, MyDataBaseHelper.USER_ID, null);
        return cursor;
    }

    public synchronized void CloseCursor() {
        if (cursor != null) {
            cursor.close();
        }
        closeSQLDB(readDB);
    }
    /**
     * 查询所有用户
     */
    public synchronized Cursor QueryListAFA() {
        SQLiteDatabase readDB = databaseHelper.getReadableDatabase();
        Cursor cursor = readDB.query(MyDataBaseHelper.TABLE_USER_AF,
                null, MyDataBaseHelper.USER_USER_AF + " = ? ",
                new String[]{TABLE_USER_NAME}, null,
                null, MyDataBaseHelper.USER_ID, null);
        try {
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeSQLDB(readDB);
        }
        return cursor;
    }
    /**
     * 查询AF特定名称的车类型
     */
    public synchronized List<CarInfo> QueryListAFCarName(String Carname) {
        List<CarInfo> list = new ArrayList<>();
        SQLiteDatabase readDB = databaseHelper.getReadableDatabase();
        Cursor cursor = readDB.query(MyDataBaseHelper.TABLE_USER_AF,
                null, MyDataBaseHelper.USER_USER_AF + " = ? and " + MyDataBaseHelper.USER_CAR_NAME_AF + " = ? ",
                new String[]{TABLE_USER_NAME, Carname}, null,
                null, MyDataBaseHelper.USER_ID, null);
        try {
//            int user = cursor.getColumnIndex(MyDataBaseHelper.USER_USER_AF);
            int pswd = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_AZ_AF);
            int icon = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_NAME_AF);
            int nickname = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_TYPE_AF);
            int vertion = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_DATE_AF);
            int veron = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_LENGM_AF);
            while (cursor.moveToNext()) {
                CarInfo info = new CarInfo(
                        cursor.getString(pswd),
                        cursor.getString(icon),
                        cursor.getString(nickname),
                        cursor.getString(vertion),
                        cursor.getString(veron)
                );
                list.add(info);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeSQLDB(readDB);
        }
        return list;
    }

    /**
     * 查询AF特定名称和车类型 的生产年份
     */
    public synchronized List<CarInfo> QueryListAFCarNameYeas(String Carname,String CarType) {
        List<CarInfo> list = new ArrayList<>();
        SQLiteDatabase readDB = databaseHelper.getReadableDatabase();
        Cursor cursor = readDB.query(MyDataBaseHelper.TABLE_USER_AF,
                null, MyDataBaseHelper.USER_USER_AF + " = ? and "
                        + MyDataBaseHelper.USER_CAR_NAME_AF + " = ? and "
                        + MyDataBaseHelper.USER_CAR_TYPE_AF + " = ? ",
                new String[]{TABLE_USER_NAME, Carname,CarType}, null,
                null, MyDataBaseHelper.USER_ID, null);
        try {
//            int user = cursor.getColumnIndex(MyDataBaseHelper.USER_USER_AF);
            int pswd = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_AZ_AF);
            int icon = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_NAME_AF);
            int nickname = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_TYPE_AF);
            int vertion = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_DATE_AF);
            int veron = cursor.getColumnIndex(MyDataBaseHelper.USER_CAR_LENGM_AF);
            while (cursor.moveToNext()) {
                CarInfo info = new CarInfo(
                        cursor.getString(pswd),
                        cursor.getString(icon),
                        cursor.getString(nickname),
                        cursor.getString(vertion),
                        cursor.getString(veron)
                );
                list.add(info);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            closeSQLDB(readDB);
        }
        return list;
    }

    /**
     * 删除列表数据
     */
    public synchronized void DeletInfoAF(InotifyDBhasChange dbChange) {
        SQLiteDatabase writeDB = databaseHelper.getWritableDatabase();
        try {
            writeDB.delete(
                    MyDataBaseHelper.TABLE_USER_AF,
                    MyDataBaseHelper.USER_USER_AF + " = ? "
                    , new String[]{TABLE_USER_NAME});
        } catch (Exception e) {
            dbChange.err();
            return;
        } finally {
            closeSQLDB(writeDB);
        }
        dbChange.onChange();
    }
}
