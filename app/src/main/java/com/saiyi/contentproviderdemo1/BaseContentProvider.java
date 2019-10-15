package com.saiyi.contentproviderdemo1;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * <pre>
 *     author : Finn
 *     e-mail : 892603597@qq.com
 *     time   : 2019/10/14
 *     desc   : https://www.cnblogs.com/finn21/
 * </pre>
 */
public class BaseContentProvider extends ContentProvider{

    private Context mContext;
    DBHelper mDbHelper = null;
    SQLiteDatabase db = null;
    public static final String AUTOHORITY = "Finn.zy.myprovider";
    // 设置ContentProvider的唯一标识 ，二进程之间的通讯也需要这个中间地址来获取数据

    public static final int User_Code = 1;
    public static final int Job_Code = 2;
    public static final int AF_Code = 32;
    // UriMatcher类使用:在ContentProvider 中注册URI
    private static final UriMatcher mMatcher;

    public static DataBaseMonitor mListener = null;

    public static void setDataBaseMonitorListener(DataBaseMonitor listener) {
        mListener = listener;
    }

    static{
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // 初始化
        mMatcher.addURI(AUTOHORITY,DBHelper.USER_TABLE_NAME, User_Code); // 若URI资源路径 = "content://" + AUTOHORITY +"/user"
        mMatcher.addURI(AUTOHORITY, DBHelper.JOB_TABLE_NAME, Job_Code);// 若URI资源路径 = "content://" + AUTOHORITY +"/job"
        mMatcher.addURI(AUTOHORITY, MyDataBaseHelper.TABLE_USER_AF, AF_Code);// 若URI资源路径 = "content://" + AUTOHORITY +"/job"
        // 固定前缀 "content://" + AUTOHORITY，数据库链表的名称，则返回注册码User_Code
        //固定前缀 "content://" + AUTOHORITY ，数据库链表的名称，则返回注册码Job_Code
    }

    // 以下是ContentProvider的6个方法
    @Override
    public boolean onCreate() {
        //该方法在ContentProvider创建后就会被调用，Android开机后，
        // ContentProvider在其它应用第一次访问它时才会被创建，同时，要讲返回值修改成true。
        mContext = getContext();

        // 在ContentProvider创建时对数据库进行初始化
        // 运行在主线程，故不能做耗时操作,此处仅作展示
        mDbHelper = new DBHelper(getContext());
        db = mDbHelper.getWritableDatabase();

        // 初始化两个表的数据(先清空两个表,再各加入一个记录)
        db.execSQL("delete from user");
        db.execSQL("insert into user values(1,'Carson');");
        db.execSQL("insert into user values(2,'Kobe');");

        MyDataBase.getInstance(mContext).InsertCarAF(new CarInfo("宝马","奔驰","路虎","宾利","保时捷"));
        MyDataBase.getInstance(mContext).InsertCarAF(new CarInfo("宝马1","奔驰2","路虎3","宾利4","保时捷5"));

        return true;
    }
    public static final int ITEMS = 1;
    public static final int ITEMS_ID = 2;
//    如果操作的数据属于集合类型，那么MIME类型字符串应该以vnd.android.cursor.dir/开头。
//    例如：要得到所有person记录的Uri为content://com.cfox.contentprovid.PersonProvider/person
//    那么返回的MIME类型字符串应该为："vnd.android.cursor.dir/person"

    //    如果要操作的数据属于非集合类型数据，那么MIME类型字符串应该以vnd.android.cursor.item/开头
//    例如：得到id为10的person记录，Uri为content://com.cfox.contentprovid.PersonProvider/person/10
//    那么返回的MIME类型字符串为："vnd.android.cursor.item/person"
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //该方法用于供外部应用往ContentProvider添加数据。

        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面
        String table = getTableName(uri);
        Log.e("sdfsdf",table+"====="+MyDataBaseHelper.TABLE_USER_AF);
        if(table != null && !MyDataBaseHelper.TABLE_USER_AF.equals(table)){
            // 向该表添加数据
            db.insert(table, null, values);
            // 当该URI的ContentProvider数据发生变化时，通知外界（即访问该ContentProvider数据的访问者）
            mContext.getContentResolver().notifyChange(uri, null);
            return uri;
        }else{
            FinnInsert(uri,values);
            return uri;
        }
//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //该方法用于供外部应用从ContentProvider删除数据。
        String tableName = getTableName(uri);
        if(!MyDataBaseHelper.TABLE_USER_AF.equals(tableName)){
            //使用原生函数的数据库
            return 0;
        }else{
            //使用封装之后的数据库
            return 0;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return 0;
    }
    /**
     * 查询数据
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //该方法用于供外部应用从ContentProvider中获取数据。
        // 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
        // 该方法在最下面
        String tableName = getTableName(uri);
        FinnQuery(tableName);

//        // 通过ContentUris类从URL中获取ID
//        long personid = ContentUris.parseId(uri);
//        System.out.println(personid);
        // 查询数据+
        if(!MyDataBaseHelper.TABLE_USER_AF.equals(tableName)){
            //使用原生函数的数据库
            return db.query(tableName,projection,selection,selectionArgs,null,null,sortOrder,null);
        }else{
            //使用封装之后的数据库
            return  MyDataBase.getInstance(mContext).QueryCursor();
        }
    }



    /**
     * 根据URI匹配 URI_CODE，从而匹配ContentProvider中相应的表名
     */
    public static  final  String getTableName(Uri uri){
        String tableName = null;
        switch (mMatcher.match(uri)) {
            case User_Code:
                tableName = DBHelper.USER_TABLE_NAME;
                break;
            case Job_Code:
                tableName = DBHelper.JOB_TABLE_NAME;
                break;
            case AF_Code:
                tableName = MyDataBaseHelper.TABLE_USER_AF;
                break;
        }
        return tableName;
    }
    public void FinnInsert(Uri uri, ContentValues values) {
        //自行封装方法添加数据库
        String table = getTableName(uri);
        switch (table){//根据表名 插入数据
            case MyDataBaseHelper.TABLE_USER_AF://通过KEY获取value
                MyDataBase.getInstance(mContext).InsertCarAF(new CarInfo(
                        values.get("car_user_az_af").toString(), values.get("car_kfb_name_af").toString(),
                        values.get("car_user_cartype_af").toString(), values.get("car_kfb_date_af").toString(),
                        values.get("car_kfb_lengmei_af").toString()
                ));
//                MyDataBase.getInstance(mContext).InsertCarAF(new CarInfo(
//                        values.get(MyDataBaseHelper.USER_CAR_AZ_AF).toString(), values.get(MyDataBaseHelper.USER_CAR_NAME_AF).toString(),
//                        values.get(MyDataBaseHelper.USER_CAR_TYPE_AF).toString(), values.get(MyDataBaseHelper.USER_CAR_DATE_AF).toString(),
//                        values.get(MyDataBaseHelper.USER_CAR_LENGM_AF).toString()
//                ));
                break;
            default:
                break;
        }
    }

    public void FinnQuery(String tablename) {
        //自行封装方法添加数据库
        switch (tablename){//根据表名 插入数据
            case MyDataBaseHelper.TABLE_USER_AF:
                mListener.OnRelist(MyDataBase.getInstance(mContext).QueryListAF());
                break;
            default:
                break;
        }
    }

}

