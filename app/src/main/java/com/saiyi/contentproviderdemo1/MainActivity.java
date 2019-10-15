package com.saiyi.contentproviderdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
/**
 * <pre>
 *     author : Finn
 *     e-mail : 892603597@qq.com
 *     time   : 2019/10/14
 *     desc   : https://www.cnblogs.com/finn21/
 * </pre>
 */
public class MainActivity extends AppCompatActivity implements DataBaseMonitor {

    private StringBuilder Struser = new StringBuilder();
    private StringBuilder Strjob = new StringBuilder();
    private StringBuilder StrDa = new StringBuilder();
    private TextView text_v1,text_v2,text_v3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text_v1 = findViewById(R.id.text_v1);
        text_v2 = findViewById(R.id.text_v2);
        text_v3 = findViewById(R.id.text_v3);

        BaseContentProvider.setDataBaseMonitorListener(this);
        /**
         * 对user表进行操作
         */
        // 设置URI
        Uri uri_user = Uri.parse("content://"+BaseContentProvider.AUTOHORITY+"/user");

        // 插入表中数据
        ContentValues values = new ContentValues();
        values.put("_id", 3);
        values.put("name", "Iverson");


        // 获取ContentResolver
        ContentResolver resolver =  getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver.insert(uri_user,values);

        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = resolver.query(uri_user, new String[]{"_id","name"}, null, null, null);
        while (cursor.moveToNext()){
            System.out.println("query book:" + cursor.getInt(0) +" "+ cursor.getString(1));
            Log.e("查询user表的数据：","query job:" + cursor.getInt(0) +"=="+ cursor.getString(1));
            Struser.append(cursor.getInt(0) +"=="+ cursor.getString(1)+"\n");
            // 将表中数据全部输出
        }
        cursor.close();
        // 关闭游标
        text_v1.setText(Struser);
        /**
         * 对job表进行操作
         */
        // 和上述类似,只是URI需要更改,从而匹配不同的URI CODE,从而找到不同的数据资源
        Uri uri_job = Uri.parse("content://"+BaseContentProvider.AUTOHORITY+"/job");

        // 插入表中数据
        ContentValues values2 = new ContentValues();
        values2.put("_id", 3);
        values2.put("job", "NBA Player");

        // 获取ContentResolver
        ContentResolver resolver2 =  getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver2.insert(uri_job,values2);
        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor2 = resolver2.query(uri_job, new String[]{"_id","job"}, null, null, null);
        while (cursor2.moveToNext()){
            System.out.println("query job:" + cursor2.getInt(0) +" "+ cursor2.getString(1));
            Log.e("查询job表的数据：","query job:" + cursor2.getInt(0) +"=="+ cursor2.getString(1));
            Strjob.append(cursor2.getInt(0) +"=="+ cursor2.getString(1)+"\n");
            // 将表中数据全部输出
        }
        cursor2.close();
        // 关闭游标
        text_v2.setText(Strjob);

        // 和上述类似,只是URI需要更改,从而匹配不同的URI CODE,从而找到不同的数据资源
        Uri uri_da = Uri.parse("content://"+BaseContentProvider.AUTOHORITY+"/"+MyDataBaseHelper.TABLE_USER_AF);

        // 插入表中数据
        ContentValues values3 = new ContentValues();

        values3.put(MyDataBaseHelper.USER_CAR_AZ_AF, "465465");
        values3.put(MyDataBaseHelper.USER_CAR_NAME_AF, "NBA Player");
        values3.put(MyDataBaseHelper.USER_CAR_TYPE_AF, "NBA Player");
        values3.put(MyDataBaseHelper.USER_CAR_DATE_AF, "NBA Player");
        values3.put(MyDataBaseHelper.USER_CAR_LENGM_AF, "NBA Player");

        // 获取ContentResolver
        ContentResolver resolver3 =  getContentResolver();
        // 通过ContentResolver 根据URI 向ContentProvider中插入数据
        resolver3.insert(uri_da,values3);
        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor3 = resolver3.query(uri_da, new String[]{MyDataBaseHelper.USER_CAR_AZ_AF,MyDataBaseHelper.USER_CAR_NAME_AF
                ,MyDataBaseHelper.USER_CAR_TYPE_AF, MyDataBaseHelper.USER_CAR_DATE_AF,MyDataBaseHelper.USER_CAR_LENGM_AF},
                null, null, null);
        /**  获取方法一 */
        while (cursor3.moveToNext()){//系统方法，也可以用定义的接口中的OnRelist函数处理数据
            System.out.println("query job:" + cursor3.getInt(0) +" "+ cursor3.getString(1));
            StrDa.append(cursor3.getInt(0) +"=="+ cursor3.getString(1)+"=="+ cursor3.getString(2)
                    +"=="+ cursor3.getString(3)
                    +"=="+ cursor3.getString(4)
                    +"=="+ cursor3.getString(5)
                    +"=="+ cursor3.getString(6)+"\n");
            // 将表中数据全部输出
        }
        cursor3.close();
        MyDataBase.getInstance(this).CloseCursor();
        text_v3.setText(StrDa);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Strjob != null){
            Strjob.setLength(0);
            Strjob = null;
        }
        if(Struser != null){
            Struser.setLength(0);
            Struser = null;
        }
        if(StrDa != null){
            StrDa.setLength(0);
            StrDa = null;
        }
    }

    @Override
    public void OnRelist(List<CarInfo> info) {
        for(int i = 0; i<info.size();i++){
            Log.e("OnRelist",info.get(i).getAz()+"=="+ info.get(i).getCarDate()+"=="+info.get(i).getCarName() +"=="+
                    info.get(i).getCarLengM()+"=="+ info.get(i).getCarType()+"=="+ "\n");
//            StrDa.append(info.get(i).getAz()+"=="+ info.get(i).getCarDate()+"=="+info.get(i).getCarName() +"=="+
//                    info.get(i).getCarLengM()+"=="+ info.get(i).getCarType()+"=="+ "\n");
        }

    }
}
